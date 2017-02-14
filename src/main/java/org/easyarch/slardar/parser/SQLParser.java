package org.easyarch.slardar.parser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.easyarch.slardar.mapping.SqlType;
import org.easyarch.slardar.utils.CollectionUtils;
import org.easyarch.slardar.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.easyarch.slardar.parser.Token.KEY;
import static org.easyarch.slardar.parser.Token.PLACEHOLDER;

/**
 * Description :
 * Created by xingtianyu on 17-1-11
 * 上午12:41
 * description:
 * 语法：
 * 1.select * from user where id = $user.id$    //对象反射取值
 * 2.select * from user where id = $map.id$     //从map的键中取值
 * 3.select * from user where id = $id$         //从@SqlParam中取值
 * 3.select * from user where id = ?            //通过左值表达式和@Column中的映射取值
 */
public class SQLParser extends ParserAdapter {

    private Statement statement;

    private SqlType type;

    private String sql;

    private String preparedSql;

    private List<String> paramNames;

    @Override
    public void parse(String src) {
        this.sql = src;
        preparedSql = sql;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        paramNames = new ArrayList<>();

        if (statement instanceof Select){
            type = SqlType.SELECT;
            Select select = (Select) statement;
            selectCase(select);
        }else if (statement instanceof Insert){
            type = SqlType.INSERT;
            Insert insert = (Insert) statement;
            insertCase(insert);
        }else if (statement instanceof Update){
            type = SqlType.UPDATE;
            Update update = (Update) statement;
            updateCase(update);
        }else if (statement instanceof Delete){
            type = SqlType.DELETE;
            Delete delete = (Delete) statement;
            deleteCase(delete);
        }
        for (String param: paramNames){
            preparedSql = preparedSql.replace(StringUtils.center(param,0, KEY),PLACEHOLDER);
        }
        List<String> paramNames = new ArrayList<>();
        for (String param:this.paramNames){
            paramNames.add(StringUtils.strip(param, KEY));
        }
        this.paramNames = paramNames;
    }
    public List<String> getSqlParamNames(){
        return paramNames;
    }

    /**
     * insert 语句解析
     * @param insert
     */
    public void insertCase(Insert insert){
        ItemsList itemsList = insert.getItemsList();
        if (itemsList instanceof MultiExpressionList){
            MultiExpressionList multiExpressionList = (MultiExpressionList) itemsList;
            List<ExpressionList> expressionLists = multiExpressionList.getExprList();
            for (ExpressionList list:expressionLists){
                List<Expression> expressions = list.getExpressions();
                for (Expression exp : expressions){
                    paramNames.add(exp.toString());
                }
            }
        }else if (itemsList instanceof  ExpressionList){
            ExpressionList expressionList = (ExpressionList) itemsList;
            List<Expression> expressions = expressionList.getExpressions();
            for (Expression exp : expressions){
                paramNames.add(exp.toString());
            }
        }
        List<Expression> expressions = insert.getDuplicateUpdateExpressionList();
        if (CollectionUtils.isEmpty(expressions)){
            return;
        }
        for (Expression exp:expressions){
            if (exp instanceof Function){
                Function function = (Function) exp;
                ExpressionList expressionList = function.getParameters();
                List<Expression> exps = expressionList.getExpressions();
                for (Expression e:exps){
                    if (e.toString().contains(Token.KEY)){
                        String columnName = e.toString();
                        paramNames.add(columnName);
                    }
                }
            }else if (exp instanceof Column){
                Column column = (Column) exp;
                String columnName = column.getColumnName();
                if (columnName.contains(Token.KEY)){
                    paramNames.add(columnName);
                }
            }
        }
    }

    private void deleteCase(Delete delete){
        handleWhereCause(delete.getWhere(), paramNames);
    }

    private void updateCase(Update update){
        List<Expression> expressions = update.getExpressions();
        for (Expression exp:expressions){
            if (exp instanceof Column){
                Column column = (Column) exp;
                if (column.getColumnName().contains(Token.KEY)){
                    paramNames.add(column.getColumnName());
                }
            }
            handleWhereCause(update.getWhere(), paramNames);
        }
    }

    /**
     * select 语句解析
     * @param select
     */
    private void selectCase(Select select){
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        Expression where = plain.getWhere();
        handleWhereCause(where, paramNames);
    }

    /** where字句 解析，select update delete中均可能用到
     * @param whereAfter
     * @param params
     * 注意：Column对象在getColumnName的时候会根据 . 做分割
     */
    private void handleWhereCause(Expression whereAfter, List<String> params){
        if (whereAfter instanceof Column){
            return;
        }
        if (whereAfter instanceof BinaryExpression){
            BinaryExpression binaryExpression = (BinaryExpression) whereAfter;
            Expression leftExpression = binaryExpression.getLeftExpression();
            Expression rightExpression = binaryExpression.getRightExpression();
//            System.out.println("rightExpression :"+rightExpression.getClass());
            if (leftExpression instanceof Column &&rightExpression instanceof Column){
                String columnName = rightExpression.toString();
                params.add(columnName);
            }else if (rightExpression instanceof Function){
                Function function = (Function) rightExpression;
                ExpressionList expressionList = function.getParameters();
                List<Expression> expressions = expressionList.getExpressions();
                for (Expression exp:expressions){
                    if (exp.toString().contains(Token.KEY)){
                        String columnName = exp.toString();
                        params.add(columnName);
                    }
                }
            }
            // 访问左子树
            handleWhereCause(leftExpression,params);
            // 访问右子树
            handleWhereCause(rightExpression,params);
        }else if (whereAfter instanceof Between){
            Between between = (Between) whereAfter;
            //between 没有只有左子树有column，右子树没有
            Expression frontVal = between.getBetweenExpressionStart();
            Expression backVal = between.getBetweenExpressionEnd();
            params.add(frontVal.toString());
            params.add(backVal.toString());
            handleWhereCause(between.getLeftExpression(),params);
        }else if (whereAfter instanceof InExpression){
            InExpression inExpression = (InExpression) whereAfter;
            ItemsList itemsList = inExpression.getRightItemsList();
            if (itemsList instanceof ExpressionList){
                ExpressionList expressionList = (ExpressionList) itemsList;
                List<Expression> expressions = expressionList.getExpressions();
                if (CollectionUtils.isNotEmpty(expressions)){
                    for (Expression e:expressions){
                        params.add(e.toString());
                    }
                }
            }
            handleWhereCause(inExpression.getLeftExpression(),params);
        }
    }

    public String getOriginSql(){
        return sql;
    }

    public String getPreparedSql(){
        return preparedSql;
    }

    public SqlType getType(){
        return type;
    }

    public void setType(SqlType type) {
        this.type = type;
    }

    public void setPreparedSql(String preparedSql) {
        this.preparedSql = preparedSql;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public static void main(String[] args) throws JSQLParserException {
//        Statement statement = CCJSqlParserUtil.parse("select a,b,c from test where test.id = ? and oid in (?,?,?) " +
//                "and  user.age = ? and user.create_at between ? and ? and label like ?");
//        Select select = (Select) statement;
//        PlainSelect plain = (PlainSelect) select.getSelectBody();
//        Expression where = plain.getWhere();
        SQLParser parser = new SQLParser();
        parser.parse("select a,b,c from test where id = $user.id$ and oid in ($map.pid$,$map.oid$,$map.mid$) " +
                "and age = $map.age$ and create_at between $map.begin$ and $map.end$ and label like $map.label$");
        for (String param:parser.getSqlParamNames()){
            System.out.println(StringUtils.strip(param, KEY));
        }
        System.out.println("preparedSql:"+parser.getPreparedSql());
//        Map<Integer,String> map = new HashMap<>();
//        map.put(2,"2");
//        map.put(3,"3");
//        map.put(1,"1");
//        System.out.println(map.get(map.size()));
//        System.out.println(parser.getCurrentIndex(map));
    }


}
