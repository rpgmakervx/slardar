package org.easyarch.test.sharding.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Description :
 * Created by xingtianyu on 17-2-22
 * 上午1:22
 * description:
 */

public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {
    @Override
    public String doEqualSharding(final Collection<String> dataSourceNames, final ShardingValue<Integer> shardingValue) {
        System.out.println("datasource:"+dataSourceNames+" , ShardingValue:"+shardingValue);
        for (String each : dataSourceNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(final Collection<String> dataSourceNames, final ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(dataSourceNames.size());
        for (Integer value : shardingValue.getValues()) {
            for (String dataSourceName : dataSourceNames) {
                if (dataSourceName.endsWith(value % 2 + "")) {
                    result.add(dataSourceName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(final Collection<String> dataSourceNames, final ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(dataSourceNames.size());
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : dataSourceNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}
