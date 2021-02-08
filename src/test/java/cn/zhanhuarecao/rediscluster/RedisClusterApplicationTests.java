package cn.zhanhuarecao.rediscluster;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class RedisClusterApplicationTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void contextLoads() {
        String strKey = "str:dict";
        RBucket<String> bucket = redissonClient.getBucket(strKey);
        bucket.set("test");
        String hashKey = "hash:user:1";
        RMap<String, Object> rMap = redissonClient.getMap(hashKey);
        rMap.put("id", 1);
        rMap.put("name", "张三");
        rMap.put("gmtCreated", new Date());
        System.out.println(bucket.get());
        System.out.println("------------");
        System.out.println(rMap.get("name"));
        System.out.println("id" + rMap.get("id"));
        System.out.println("-------------");
        String zsetKey = "zset:test";
        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(zsetKey);
        sortedSet.add(1.0, "test");
        sortedSet.add(2.0, "test2");
        System.out.println(sortedSet.getScore("test"));
        RScoredSortedSet<Long> set = redissonClient.getScoredSortedSet("zset:long");
        set.add(22.0, 1L);
        set.add(88, 2L);
        System.out.println(set.getScore(2L));
    }
}
