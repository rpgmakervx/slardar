package org.easyarch.slardar.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import org.easyarch.slardar.mapping.SqlType;
import org.easyarch.slardar.utils.CollectionUtils;
import org.easyarch.slardar.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.easyarch.slardar.parser.Token.*;

/**
 * Description :
 * Created by xingtianyu on 17-2-18
 * 上午1:49
 * description:
 */

public class SQLParser extends ParserAdapter {

    private MySqlStatementParser statementParser;

    private SqlType type;

    private String sql;

    private String preparedSql;

    private List<String> paramNames;

    @Override
    public void parse(String src) {
        this.sql = this.preparedSql = src;
        this.paramNames = new ArrayList<>();
        statementParser = new MySqlStatementParser(src);
        SQLStatement statement = statementParser.parseStatement();
        if (statement instanceof SQLSelectStatement){
            this.type = SqlType.SELECT;
            selectCase((SQLSelectStatement) statement);
        }else if (statement instanceof SQLInsertStatement){
            this.type = SqlType.INSERT;
            insertCase((SQLInsertStatement) statement);
        }else if (statement instanceof SQLUpdateStatement){
            this.type = SqlType.UPDATE;
            updateCase((SQLUpdateStatement) statement);
        }else if (statement instanceof SQLDeleteStatement){
            this.type = SqlType.DELETE;
            deleteCase((SQLDeleteStatement) statement);
        }
        for (String param: paramNames){
            preparedSql = preparedSql.replace(param,PLACEHOLDER);
        }
        List<String> paramNames = new ArrayList<>();
        for (String param:this.paramNames){
            paramNames.add(StringUtils.strip(param, KEY,KEY_R));
        }
        this.paramNames = paramNames;
    }

    private void selectCase(SQLSelectStatement statement){
        SQLSelect select = statement.getSelect();
        SQLSelectQueryBlock selectQuery = (SQLSelectQueryBlock) select.getQuery();
        handleWhereCause(selectQuery.getWhere(),paramNames);
    }

    private void insertCase(SQLInsertStatement statement){
        List<SQLInsertStatement.ValuesClause> valuesClauses = statement.getValuesList();
        for (SQLInsertStatement.ValuesClause value : valuesClauses){
            List<SQLExpr> exprs = value.getValues();
            for (SQLExpr expr:exprs){
                paramNames.add(expr.toString());
            }
        }
    }

    private void updateCase(SQLUpdateStatement updateStatement){
        List<SQLUpdateSetItem> items = updateStatement.getItems();
        for (SQLUpdateSetItem item:items){
            SQLExpr column = item.getColumn();
            SQLExpr value = item.getValue();
            if (value instanceof SQLIdentifierExpr){
                paramNames.add(((SQLIdentifierExpr) value).getName());
            }else if (value instanceof SQLVariantRefExpr
                    || value instanceof SQLValuableExpr){
                paramNames.add(((SQLIdentifierExpr)column).getName());
            }
        }
        handleWhereCause(updateStatement.getWhere(),paramNames);
    }

    private void deleteCase(SQLDeleteStatement deleteStatement){
        handleWhereCause(deleteStatement.getWhere(),paramNames);
    }

    public void handleWhereCause(SQLExpr whereAfter, List<String> paramNames){
        if (whereAfter instanceof SQLIdentifierExpr){
            return;
        }
        if (whereAfter instanceof SQLBinaryOpExpr){
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) whereAfter;
            SQLExpr leftExpr = binaryOpExpr.getLeft();
            SQLExpr rightExpr = binaryOpExpr.getRight();
            if (leftExpr instanceof SQLIdentifierExpr){
                if (rightExpr instanceof SQLIdentifierExpr){
                    String columnName = ((SQLIdentifierExpr) rightExpr).getName();
                    paramNames.add(columnName);
                }else if (rightExpr instanceof SQLVariantRefExpr
                        || rightExpr instanceof SQLValuableExpr){
                    String columnName = ((SQLIdentifierExpr) leftExpr).getName();
                    paramNames.add(columnName);
                }else if (rightExpr instanceof SQLMethodInvokeExpr){
                    List<SQLExpr> exprs = ((SQLMethodInvokeExpr) rightExpr).getParameters();
                    for (SQLExpr expr : exprs){
                        if (expr instanceof SQLIdentifierExpr){
                            String columnName = ((SQLIdentifierExpr) expr).getName();
                            paramNames.add(columnName);
                        }else if (expr instanceof SQLVariantRefExpr
                                || expr instanceof SQLValuableExpr){
                            String columnName = ((SQLIdentifierExpr) leftExpr).getName();
                            paramNames.add(columnName);
                        }
                    }
                }
            }
            handleWhereCause(leftExpr,paramNames);
            handleWhereCause(rightExpr,paramNames);
        }else if (whereAfter instanceof SQLBetweenExpr){
            SQLExpr beginExpr = ((SQLBetweenExpr) whereAfter).getBeginExpr();
            SQLExpr endExpr = ((SQLBetweenExpr) whereAfter).getEndExpr();
            paramNames.add(beginExpr.toString());
            paramNames.add(endExpr.toString());
            handleWhereCause(((SQLBetweenExpr) whereAfter).getTestExpr(),paramNames);
        }else if (whereAfter instanceof SQLInListExpr){
            List<SQLExpr> exprs = ((SQLInListExpr) whereAfter).getTargetList();
            if (CollectionUtils.isNotEmpty(exprs)){
                for (SQLExpr expr : exprs){
                    paramNames.add(expr.toString());
                }
            }
        }
    }

    public SqlType getType() {
        return type;
    }

    public void setType(SqlType type) {
        this.type = type;
    }

    public String getOriginSql() {
        return sql;
    }

    public void setOriginSql(String sql) {
        this.sql = sql;
    }

    public String getPreparedSql() {
        return preparedSql;
    }

    public void setPreparedSql(String preparedSql) {
        this.preparedSql = preparedSql;
    }

    public List<String> getSqlParamNames() {
        return paramNames;
    }

    public void setSqlParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public static void main(String[] args) {
        String sql = " select * from event where eventId = ? and eventKey = key and eventName between ? and ?";
        String insert = "insert into user (client_d,username,password,phone) values(clientId,userName,password,phone)";
//        MySqlStatementParser statementParser = new MySqlStatementParser(insert);
//        SQLStatement statement = statementParser.parseStatement();
//        SQLInsertStatement insertStatement = (SQLInsertStatement) statement;
//        System.out.println(insertStatement.getValuesList());
        SQLParser parser = new SQLParser();
        SqlParser parser2 = new SqlParser();
        long begin = System.nanoTime();
        for (int index=0;index<50;index++){
            parser2.parse(sql);
        }
        long end = System.nanoTime();
        System.out.println("cost:"+(end - begin));
    }
}
