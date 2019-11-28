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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.couchbase.client.java.query.Select.select;


@Service
public class MyService {
//    @Autowired
//    private Gson gson;

    ConfigRepository configRepository;

    CouchbaseTemplate couchbaseConfig;

    public MyService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

//    public Object dataParser(String serviceName,String configType ){
//
//       /* String query = "SELECT name FROM `travel-sample` " +
//                "WHERE type = 'airport' LIMIT 100";
//        N1qlQueryResult result1 = configRepository.query(N1qlQuery.simple(query));
//*/
//
//        return configRepository.listTenantUsers(serviceName,configType);
//    }
/* public Object dataParser(String serviceName,String configType ){


     Cluster cluster = CouchbaseCluster.create("localhost");
     Bucket bucket =
     cluster.openBucket("laas_config","avi123");

//        String query = "SELECT name FROM `travel-sample` " +
//                "WHERE type = 'airport' LIMIT 100";

        String query = "Select databaseConfig from `laas_config` where meta().id = 'nsdl'";
        N1qlQueryResult result1 = bucket.query(N1qlQuery.simple(query));


        return result1.allRows();
    }*/

//    @Cacheable(value = "users", key = "{ #serviceName, #configType}")
    @Cacheable(value = "users", key = "#serviceName.concat('-').concat( #configType)")
    public Map<String, Object> dataParser(String serviceName, String configType ){
     System.out.println("serviceName--->>"+serviceName);
     System.out.println("configType--->>"+configType);


     // Through the builder
     System.setProperty("com.couchbase.queryEnabled", "true");

     Cluster cluster = CouchbaseCluster.create();
     cluster.authenticate("Administrator", "avi123");
     Bucket bucket = cluster.openBucket("laas_config");

        String query = "Select "+configType+" from `laas_config` where meta().id = '"+serviceName+"'";
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(query));
     System.out.println(result);
//     bucket.async()
//             .query(select("*").from("laas_config").limit(10))
//             .subscribe(result -> {
//                         result.errors()
//                                 .subscribe(
//                                         e -> System.err.println("N1QL Error/Warning: " + e),
//                                         runtimeError -> runtimeError.printStackTrace()
//                                 );
//                         result.rows()
//                                 .map(row -> row.value())
//                                 .subscribe(
//                                         rowContent -> System.out.println("rowContent--->>>"+rowContent),
//                                         runtimeError -> runtimeError.printStackTrace()
//                                 );
//                     }
//             );

//     Result result=new Result();
//     result.setData(result1.allRows().toString());


     JsonObject document = getDocument(configType, result);
     if (document == null) {
         return null;
     }
     return document.toMap();
    }


    protected JsonObject getDocument(String rootNode, N1qlQueryResult result) {
        List<N1qlQueryRow> attributes = result.allRows();
        if (attributes.isEmpty()) {
            return null;
        }
        return attributes.get(0).value();
    }

}
