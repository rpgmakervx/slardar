package org.easyarch.slardar.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import org.easyarch.slardar.mapping.SqlType;

import java.util.List;

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
        statementParser = new MySqlStatementParser(src);
        SQLStatement statement = statementParser.parseStatement();
        if (statement instanceof SQLSelectStatement){
            selectCase((SQLSelectStatement) statement);
        }else if (statement instanceof SQLInsertStatement){
            insertCase((SQLInsertStatement) statement);
        }else if (statement instanceof SQLUpdateStatement){
            updateCase((SQLUpdateStatement) statement);
        }else if (statement instanceof SQLDeleteStatement){
            deleteCase((SQLDeleteStatement) statement);
        }
    }

    private void selectCase(SQLSelectStatement statement){
        SQLSelect select = (statement).getSelect();
        SQLSelectQueryBlock selectQuery = (SQLSelectQueryBlock) select.getQuery();
        handleWhereCause(selectQuery.getWhere(),paramNames);
    }

    private void insertCase(SQLInsertStatement statement){
        SQLInsertStatement.ValuesClause values = (statement).getValues();

    }

    private void updateCase(SQLUpdateStatement updateStatement){

    }

    private void deleteCase(SQLDeleteStatement deleteStatement){

    }

    public void handleWhereCause(SQLExpr whereAfter, List<String> paramNames){
        if (whereAfter instanceof SQLIdentifierExpr){
            return;
        }
        if (whereAfter instanceof SQLBinaryOpExpr){
            SQLBinaryOpExpr binaryOpExpr = (SQLBinaryOpExpr) whereAfter;
            SQLExpr leftExpr = binaryOpExpr.getLeft();
            SQLExpr rightExpr = binaryOpExpr.getRight();
            handleWhereCause(leftExpr,paramNames);
            handleWhereCause(rightExpr,paramNames);
        }
    }
}
