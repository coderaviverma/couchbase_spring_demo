package com.couchbase.demo.couchbase_demo;


import com.couchbase.demo.couchbase_demo.configutil.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Service
public class MyService {
    @Autowired
    private Gson gson;
CouchbaseTemplate template;
    ConfigRepository configRepository;

    public MyService(CouchbaseTemplate template) {
        this.template = template;
    }


    public Object dataParser(String id) {

        System.out.println("id--------"+id);
        String[] filterData=id.split("\\.");

        System.out.println("filterData.length "+filterData.length);

        if (filterData.length>0){
            Optional<ConfigDocument> document= configRepository.findById(filterData[0]);

            LinkedTreeMap nsdlConfigJSON=document.get().getValue();

//            BaseConfigDocument baseConfigDocument=modelParser(filterData[0],nsdlConfigJSON);
//
//            ((NSDLConfigDocument)baseConfigDocument).getDatabaseConfig();

            System.out.println("nsdlConfigJSON "+nsdlConfigJSON);
            return nsdlConfigJSON;
        }

        return null;
    }

    private BaseConfigDocument modelParser(String className,String jsonData){

        BaseConfigDocument baseConfigDocument;
        switch (className){

            case "NDLS":
                NSDLConfigDocument nsdlConfigDocument= gson.fromJson(jsonData, NSDLConfigDocument.class);
                System.out.println(nsdlConfigDocument.toString());
                return nsdlConfigDocument;

                 default:
                     baseConfigDocument= gson.fromJson(jsonData, NSDLConfigDocument.class);

                     break;


        }

        return baseConfigDocument;
    }

}
