package com.couchbase.demo.couchbase_demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private RedisTemplate< String, Object > template;

    public RedisService(RedisTemplate<String, Object> template) {
        this.template = template;
    }


    public Object getValue(final String key ) {
        return template.opsForValue().get( key );
    }

    public void setValue( final String key, final Object value ) {
        template.opsForValue().set( key, value );
    }
}
