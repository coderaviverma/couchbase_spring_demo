package com.couchbase.demo.couchbase_demo;


import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.demo.couchbase_demo.config.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MyService {
//    @Autowired
//    private Gson gson;

    @Autowired
    private RedisService redisService;


    //    @Cacheable(value = "users", key = "{ #serviceName, #configType}")
    public JsonObject dataParser(String serviceName, String configType ){
        System.out.println("serviceName--->>"+serviceName);
        System.out.println("configType--->>"+configType);

        String[] configTypes=configType.split(",");

        JsonObject rootObject= JsonObject.create();

        boolean anyDataRemaning=false;
        String[] remaningConfigType=new String[configTypes.length];
        int configCounter=0;
        //Checking data exit in redis
        for (int i = 0; i < configTypes.length; i++) {
            JsonObject result = chechDataInRedis(configTypes[i]);

            System.out.println("chechDataInRedis-->"+result+"configTypes--> "+configTypes[i]+" i --> "+i);

            if (result!=null){
                rootObject.put(configTypes[i],result);
            }else {
                anyDataRemaning = true;
                remaningConfigType[configCounter]=configTypes[i];
                configCounter++;
            }
        }

        //Checking for remaning data in db
        if (anyDataRemaning){
            System.out.println("anyDataRemaning--> "+anyDataRemaning);
            N1qlQueryResult result=getDataFromDB(serviceName,remaningConfigType);

            for (int i = 0; i < remaningConfigType.length; i++) {
                JsonObject document = getDocument(remaningConfigType[i], result);
                System.out.println("anyDataRemaning_JsonObject--> "+document);


                if (document!=null){
                    rootObject.put(remaningConfigType[i],document);

                }

                //Save data to redis

                saveDataToCache(result,remaningConfigType,serviceName);
            }

        }
        // Through the builder
//     System.setProperty("com.couchbase.queryEnabled", "true");

        return rootObject;
    }

    private JsonObject chechDataInRedis(String key){

        //Get data from Redis
        JsonObject dataFound= (JsonObject) redisService.getValue(key);

        if (dataFound!=null)
            System.out.println("chechDataInRedis -->> "+dataFound.toString());

        return dataFound;
    }


    private void saveDataToCache(N1qlQueryResult result,String[] remaningConfigType,String ServiceName){


        for (int i = 0; i < remaningConfigType.length; i++) {

            //Save data in Redis
            JsonObject document = getDocument(remaningConfigType[i], result);
            System.out.println("saveDataToCache -->> "+document+" remaningConfigType[i]---> "+remaningConfigType[i]);


            if (document!=null){
                //save this map to redis

                redisService.setValue(remaningConfigType[i],document);
//                redisService.setExpire(ServiceName,1, TimeUnit.DAYS);

            }

        }

    }


    private N1qlQueryResult getDataFromDB(String serviceName,String[] configTypes){

        String configType=String.join(", ", configTypes);

        Cluster cluster = CouchbaseCluster.create();
        cluster.authenticate("Administrator", "avi123");
        Bucket bucket = cluster.openBucket("laas_config");

        String query = "Select "+configType+" from `laas_config` where meta().id = '"+serviceName+"'";

        System.out.println("query-->>>> "+query);

        N1qlQueryResult result = bucket.query(N1qlQuery.simple(query));
        System.out.println("N1qlQueryResult--> "+result);

        return result;
    }


/*    protected JsonObject getDocument(N1qlQueryResult result) {
        List<N1qlQueryRow> attributes = result.allRows();
        if (attributes.isEmpty()) {
            return null;
        }
//        return attributes.get(0).value().getObject(rootNode);
        return attributes.get(0).value();
    }*/

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
