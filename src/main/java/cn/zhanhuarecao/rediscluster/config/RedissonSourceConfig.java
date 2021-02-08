package cn.zhanhuarecao.rediscluster.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

@Configuration
public class RedissonSourceConfig {

    @Autowired
    private RedissonEntity redissonEntity;

    @Bean
    RedissonClient redissonClient() throws Exception {
        Config config = new Config();
        config.useClusterServers().setPassword(redissonEntity.getPassword())
                .addNodeAddress(redissonEntity.getNodeAddresses().toArray(new String[0]))
                .setReadMode(ReadMode.SLAVE).setSubscriptionMode(SubscriptionMode.SLAVE);
        // 默认的序列化会导致存储的时候key有双引号存在，与jedis不兼容
        // Codec codec=(Codec) ClassUtils.forName("org.redisson.codec.JsonJacksonCodec", ClassUtils.getDefaultClassLoader()).newInstance();
        // 用这个能解决key有双引号的问题
        Codec codec=(Codec) ClassUtils.forName("org.redisson.client.codec.StringCodec", ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        return Redisson.create(config);
    }

}
