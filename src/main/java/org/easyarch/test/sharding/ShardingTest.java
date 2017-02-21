package org.easyarch.test.sharding;

import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.ShardingDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.easyarch.test.sharding.algorithm.ModuloDatabaseShardingAlgorithm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Description :
 * Created by xingtianyu on 17-2-21
 * 上午10:11
 * description:
 */

public class ShardingTest {

    private static ShardingDataSource getShardingDataSource() {
        DataSourceRule dataSourceRule = new DataSourceRule(createDataSourceMap());
        TableRule orderTableRule = TableRule.builder("t_order").actualTables(Arrays.asList("t_order_0", "t_order_1")).dataSourceRule(dataSourceRule).build();
        TableRule orderItemTableRule = TableRule.builder("t_order_item").actualTables(Arrays.asList("t_order_item_0", "t_order_item_1")).dataSourceRule(dataSourceRule).build();
        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(Arrays.asList(orderTableRule, orderItemTableRule))
                .bindingTableRules(Collections.singletonList(new BindingTableRule(Arrays.asList(orderTableRule, orderItemTableRule))))
//                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
                .tableShardingStrategy(new TableShardingStrategy("order_id", new ModuloDatabaseShardingAlgorithm())).build();
        return new ShardingDataSource(shardingRule);
    }

    private static Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2);
        result.put("ds", MasterSlaveDataSourceFactory.createDataSource("ds",
                createDataSource("127.0.0.1", 3306, "ds_master"),
                createDataSource("127.0.0.1", 3306, "ds_slave_0"),
                createDataSource("127.0.0.1", 3306, "ds_slave_1")));
        return result;
    }

    private static DataSource createDataSource(final String ip, final Integer port, final String database) {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://%s:%d/%s", ip, port, database));
        result.setUsername("root");
        result.setPassword("123456");
        return result;
    }

    private static void printSimpleSelect(final DataSource dataSource, final Integer userId, final Integer orderId) throws SQLException {
        System.out.println("select");
        String sql = "SELECT i.*,o.status FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";
        Connection conn = null;
        ResultSet rs = null;
        try{
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, orderId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println("data: "+rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4));
            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            rs.close();
            conn.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = getShardingDataSource();
        printSimpleSelect(dataSource, 10, 1001);
        printSimpleSelect(dataSource, 10, 1002);
        printSimpleSelect(dataSource, 10, 1003);
        printSimpleSelect(dataSource, 10, 1004);
    }


}
