package com.couchbase.demo.couchbase_demo;


import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.demo.couchbase_demo.config.CouchbaseConfig;
import com.couchbase.demo.couchbase_demo.configutil.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.couchbase.client.java.query.Select.select;


@Service
public class MyService {
//    @Autowired
//    private Gson gson;

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;



//    @Cacheable(value = "users", key = "{ #serviceName, #configType}")
    public Map<String, Object> dataParser(String serviceName, String configType ){
     System.out.println("serviceName--->>"+serviceName);
     System.out.println("configType--->>"+configType);

     String[] configTypes=configType.split(",");

        HashMap<String,Object> map=new HashMap<>();

        boolean anyDataRemaning=false;
        String[] remaningConfigType=new String[configTypes.length];
        int configCounter=0;
        //Checking data exit in redis
        for (int i = 0; i < configTypes.length; i++) {

          Map result = chechDataInRedis(configTypes[i]);

          if (result!=null){
              map.putAll(result);
          }else {
              anyDataRemaning = true;
              remaningConfigType[configCounter]=configTypes[i];
              configCounter++;
          }
        }

        //Checking for remaning data in db
        if (anyDataRemaning){
            N1qlQueryResult result=getDataFromDB(serviceName,remaningConfigType);

            JsonObject document = getDocument(configType, result);

            Map resultFromDb = jsonToMapParser(document);

            if (resultFromDb!=null){
                map.putAll(resultFromDb);
            }

            //Save data to redis

            saveDataToCache(result,remaningConfigType);
        }




     // Through the builder
     System.setProperty("com.couchbase.queryEnabled", "true");

        return map;
    }

    private Map<String, Object> chechDataInRedis(String key){

        //Get data from Redis

        return new HashMap<>();
    }


    private void saveDataToCache(N1qlQueryResult result,String[] remaningConfigType){


        for (int i = 0; i < remaningConfigType.length; i++) {

            //Save data in Redis
            JsonObject document = getDocument(remaningConfigType[i], result);

            Map resultFromDb = jsonToMapParser(document);

            if (resultFromDb!=null){

                //save this map to redis

            }

        }

    }


    private N1qlQueryResult getDataFromDB(String serviceName,String[] configTypes){

        String configType=String.join(",", configTypes);

        Cluster cluster = CouchbaseCluster.create();
        cluster.authenticate("Administrator", "avi123");
        Bucket bucket = cluster.openBucket("laas_config");

        String query = "Select "+configType+" from `laas_config` where meta().id = '"+serviceName+"'";
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(query));

       return result;
    }


    protected JsonObject getDocument(N1qlQueryResult result) {
        List<N1qlQueryRow> attributes = result.allRows();
        if (attributes.isEmpty()) {
            return null;
        }
//        return attributes.get(0).value().getObject(rootNode);
        return attributes.get(0).value();
    }

    protected JsonObject getDocument(String rootNode, N1qlQueryResult result) {
        List<N1qlQueryRow> attributes = result.allRows();
        if (attributes.isEmpty()) {
            return null;
        }
        return attributes.get(0).value().getObject(rootNode);
//        return attributes.get(0).value();
    }

    private Map<String,Object> jsonToMapParser(JsonObject document){

        if (document == null) {
            return null;
        }

        return document.toMap();
    }

}
