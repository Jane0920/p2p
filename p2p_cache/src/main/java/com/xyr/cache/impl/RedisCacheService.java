package com.xyr.cache.impl;

import com.xyr.cache.BaseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xyr on 2017/9/1.
 */
@Service("redisCache")
public class RedisCacheService implements BaseCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 这个方法是在redis中设置一个String类型的变量。Key对应的值是value
     * 在redis中对应的命令是SET key value  此命令用于在指定键设置值
     *
     * @param key
     * @param value
     */
    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 这个方法是在redis中得到一个String类型的变量。
     * 在redis中对应的命令是SET key value  此命令用于在指定键设置
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 在redis中对应的key中追加value值
     * 在redis中对应的命令是APPEND key value追加值到一个键
     *
     * @param key
     * @param value
     */
    @Override
    public void append(String key, String value) {
        redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 在redis中删除对应key的值
     *
     * @param key
     */
    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置一个Key的超时时间以秒为单位。如果超过这个时间队应的key就失效了哦
     * 在redis中对应的命令是expire key time
     *
     * @param key
     * @param seconds
     */
    @Override
    public void expire(String key, long seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 得到对应key的剩余存活时间
     * 在redis中对应的命令是ttl key得到key的剩余生命时间
     *
     * @param key
     * @return
     */
    @Override
    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 对redis的key对应的List尾部放入java的list里面的元素
     * 在redis中对应的命令是rpush key value是往队尾添加数据
     *
     * @param key
     * @param list
     * @param <T>
     * @return
     */
    @Override
    public <T> long rPush(String key, List<T> list) {
        return redisTemplate.opsForList().rightPushAll(key, list.toArray());
    }

    /**
     * 返回键值为key的list列表中下标为index的元素
     * 在redis中对应的命令是lindex key number
     *
     * @param key
     * @param index
     * @param <T>
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T lindex(String key, long index) {
        return (T) redisTemplate.opsForList().index(key, index);
    }

    /**
     * 对redis的key对应的List查询从start到end对应的元素
     * 在redis中对应的命令是range key start end是查询队列范围从start到end的数据
     *
     * @param key
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> lRange(String key, long start, long end) {
        return (List<T>) redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 对redis的key对应的List删除从start到end对应的元素
     * 在redis中对应的命令是LTRIM key start stop 修剪列表到指定的范围内
     *
     * @param key
     * @param start
     * @param end
     */
    @Override
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 删除redis对应的key,然后把key设置为队列，把java的list参数放入到队列中
     * 在redis中对应的命令是先执行del key命令删除对应的key值然后执行rpush命令把list内的对象放入到标示为key的列表中。
     *
     * @param key
     * @param list
     * @param <T>
     */
    @Override
    public <T> void setList(String key, List<T> list) {
        this.del(key);
        this.rPush(key, list);
    }

    /**
     * 在redis中创建一个hashTable,键为key并且在这个hashTable内存储一个键为12454的map对象的值
     * 在redis中对应的命令是先执行del key命令删除对应的key值然后执行rpush命令把list内的对象放入到标示为key的列表中。
     *
     * @param key
     * @param map
     */
    @Override
    public void setHmap(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().put(key, "12454", map);
    }

    /**
     * 在redis中查找键值为key的HashTable，然后从其中找出键值为12454的存储的对象。
     * 在redis中对应的命令是HSET key field value 设置哈希字段的字符串值
     *
     * @param key
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getHmap(String key) {
        return (Map<String, Object>) redisTemplate.opsForHash().get(key, "12454");
    }

    /**
     * 在redis中把key的expire time去掉，变成没有时间限制的key
     * 在redis中对应的命令是persist key field 把这个key对应field的生存时间限制去掉
     *
     * @param key
     */
    @Override
    public void setPersist(String key) {
        redisTemplate.persist(key);
    }

    /**
     * 判断是否存在key对应的键
     * 在redis中对应的命令是exists key判断这key是否存在
     *
     * @param key
     * @return
     */
    @Override
    public boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }
}
