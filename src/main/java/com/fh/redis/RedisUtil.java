package com.fh.redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisUtil {

    //设置
    public static void set(String key,String value){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getJedis();
            jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    public static void hSet(String key,String filed,String value){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getJedis();
            jedis.hset(key,filed,value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    //设置+失效时间
    public static void set(String key,String value,int seconds){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getJedis();
            jedis.set(key,value);
            jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    //设置+失效时间
    public static void hSet(String key,String filed,String value,int seconds){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getJedis();
            jedis.hset(key,filed,value);
            jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    //获取
    public static String get(String key){
        Jedis jedis =null;
        String s =null;
        try {
            jedis = RedisPool.getJedis();
            s = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    }
    //
    public static String hGet(String key,String field){
        Jedis jedis =null;
        String s =null;
        try {
            jedis = RedisPool.getJedis();
            s = jedis.hget(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    }
    //
    public static List<String> hvals(String key){
        Jedis jedis =null;
        List<String> hvals = new ArrayList<>();
        try {
            jedis = RedisPool.getJedis();
            hvals = jedis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return hvals;
    }
    //删除
    public static Long del(String key){
        Jedis jedis =null;
        Long del = null;
        try {
            jedis = RedisPool.getJedis();
            del = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return del;
    }
    public static void hdel(String key,String field){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getJedis();
            jedis.hdel(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    //是否存在
    public static Boolean exists(String key){
        Jedis jedis =null;
        Boolean exists =null;
        try {
            jedis = RedisPool.getJedis();
            exists = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return exists;
    }
    //是否存在
    public static Boolean hExists(String key,String field){
        Jedis jedis =null;
        Boolean exists =null;
        try {
            jedis = RedisPool.getJedis();
            exists = jedis.hexists(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return exists;
    }
}
