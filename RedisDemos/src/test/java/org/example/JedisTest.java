package org.example;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

/**
 * @Classname JedisTest
 * @Description 学习测试 Redis在Java中的常规使用
 * @Date 2020/12/1 23:05
 * @Created by guo
 * @ProjectName LearnDemos
 */
public class JedisTest {
    @Test
    public void testJedis() {
        Jedis jedis = new Jedis("127.0.0.1", 6379, 1000);
        try {
            //设置登录密码
            jedis.auth("123456");
            //选择字典数据库
            jedis.select(3);
            jedis.flushDB();//清空
            jedis.set("guo", "guoyawen");
            System.out.println(jedis.get("guo"));
            jedis.mset(new String[]{"wu", "wutao", "chu", "chuzhigang", "a", "1"});
            List<String> strs = jedis.mget(new String[]{"wu", "chu"});
            System.out.println(strs);
            System.out.println(jedis.incr("a"));
            Long b = jedis.del("wu");
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }

    @Test
    public void testHash() {
        Jedis jedis = new Jedis("127.0.0.1", 6379, 1000);
        try {
            jedis.auth("123456");
            jedis.select(3);
            jedis.flushDB();
            jedis.hset("user:1:info", "name", "guoyawen");
            jedis.hset("user:1:info", "other", "wutao");
            jedis.hset("user:1:info", "age", "22");
            Map<String, String> all = jedis.hgetAll("user:1:info");
            System.out.println(all);
            System.out.println(jedis.hget("user:1:info", "name"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
    }
    @Test
    public void testJedisPool() {
        //Create a Jedis Pool
        GenericObjectPoolConfig config = new JedisPoolConfig();
        //Set pool maximum 100 jedis object
        config.setMaxTotal(100);
        //Set pool allow maximum link number
        config.setMaxIdle(50);
        //Set pool allow minimum link number
        config.setMinIdle(10);
        //设置借出连接的时候是否测试有效性 推荐false 提升效率
        config.setTestOnBorrow(false);
        //设置归还连接的时候是否测试有效性 推荐false 提升效率
        config.setTestOnReturn(false);
        //创建时候测试有效性，设置true（注意这个属性设置true的话，如果不是本地连接则会报错，是由于自我保护引起的问题）
        config.setTestOnCreate(true);
        //当连接池内jedis无可用资源的时候，是否等待资源
        config.setBlockWhenExhausted(true);
        //没有获取资源时最长等待时间设置 1 秒，1秒后还没有的话就报错
        config.setMaxWaitMillis(1000);

        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);
        Jedis jedis = null;
        try {
            //borrow a jedis object from pool
            jedis = pool.getResource();
            jedis.auth("123456");
            jedis.set("abc", "bb");
            String abc = jedis.get("abc");
            System.out.println(abc);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (jedis != null) {
                //在使用连接池的时候，使用close方法不是关闭，而是归还到连接池中
                jedis.close();
            }
        }
    }

}
