package org.easyarch.test.druid;

import com.alibaba.druid.sql.ast.expr.SQLBetweenExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;

/**
 * Description :
 * Created by xingtianyu on 17-2-17
 * 下午11:54
 * description:
 */

public class SqlParserTest {

    public static void main(String[] args) {
        String sql = " select * from event where eventId = 0001 and eventKey = key and eventName between begin and end";
        String insert = " insert into uservalues(id,ooo)";
        //使用mysql解析
        MySqlStatementParser sqlStatementParser = new MySqlStatementParser(sql) ;
        //解析select查询
//        System.out.println("parse select "+sqlStatementParser.parseStatement());
        SQLSelectStatement sqlStatement = (SQLSelectStatement) sqlStatementParser.parseStatement();
        SQLSelect sqlSelect = sqlStatement.getSelect() ;
        //获取sql查询块
        SQLSelectQueryBlock sqlSelectQuery = (SQLSelectQueryBlock)sqlSelect.getQuery() ;
        StringBuffer out = new StringBuffer() ;
//        //创建sql解析的标准化输出
//        SQLASTOutputVisitor sqlastOutputVisitor = SQLUtils.createFormatOutputVisitor(out , SQLUtils.parseStatements(sql, JdbcUtils.MYSQL) , JdbcUtils.MYSQL) ;
        SQLBinaryOpExpr expr = (SQLBinaryOpExpr)sqlSelectQuery.getWhere();
        System.out.println("expr:"+expr.getClass().getName());
//        sqlastOutputVisitor.visit(expr);
        SQLBetweenExpr betweenExpr = (SQLBetweenExpr) expr.getRight();
        System.out.println(betweenExpr.getBeginExpr().toString());
//        SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) exprRight.getLeft();
//        sqlIdentifierExpr.accept(sqlastOutputVisitor);
//        List<Object> params = sqlVariantRefExpr.getName();
//        System.out.println(sqlIdentifierExpr.getName());
        int index = 0;
//        for (TableStat.Column column:sqlastOutputVisitor.getgetColumns()){
//            System.out.println(column.getName()+":"+params.get(index));
//            index++;
//        }
//
//        //解析select项
//        out.delete(0, out.length()) ;
//        for (SQLSelectItem sqlSelectItem : sqlSelectQuery.getSelectList()) {
//            if(out.length()>1){
//                out.append(",") ;
//            }
//            sqlSelectItem.accept(sqlastOutputVisitor);
//        }
//        System.out.println("SELECT "+out) ;
//
//        //解析from
//        out.delete(0, out.length()) ;
//        sqlSelectQuery.getFrom().accept(sqlastOutputVisitor) ;
//        System.out.println("FROM "+out) ;
//
//        //解析where
//        out.delete(0, out.length()) ;
//        sqlSelectQuery.getWhere().accept(sqlastOutputVisitor) ;
//        System.out.println("WHERE "+out);

    }
}
