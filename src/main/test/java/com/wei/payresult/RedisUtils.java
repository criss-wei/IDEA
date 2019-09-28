package com.wei.payresult;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author wei
 * @date 2019/7/6-14:45
 */
public class RedisUtils {
    private volatile  static  RedisUtils redisUtils;
    private  RedisUtils(){}

    public  static  RedisUtils getInstance(){
        if (redisUtils==null){
            synchronized (RedisUtils.class){
                if (redisUtils==null){
                    redisUtils=new RedisUtils();
                }
            }
        }
        return  redisUtils;
    }
    public RedissonClient getRedisson(){
        Config config=new Config();
        config.useSingleServer().setAddress("redis://47.100.195.246:6379");
        RedissonClient redissonClient= Redisson.create(config);
        return  redissonClient;
    }
}
