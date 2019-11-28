package com.couchbase.demo.couchbase_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
@Configuration
@EnableCouchbaseRepositories
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    protected List<String> getBootstrapHosts() {

        List <String> list= Arrays.asList("http://127.0.0.1:8091");

        return list;
    }

    @Override
    protected String getBucketName() {
        return "laas_config";
    }

    @Override
    protected String getBucketPassword() {
        return "avi123";
    }
}
