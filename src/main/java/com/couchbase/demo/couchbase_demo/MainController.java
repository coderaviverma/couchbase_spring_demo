package com.couchbase.demo.couchbase_demo;


import com.couchbase.client.java.document.json.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MainController {


    private MyService myService;

    public MainController(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(value = "/config", method  = RequestMethod.GET)
    public ResponseEntity<String> getConfig(@RequestParam("serviceName") String serviceName, @RequestParam("configType") String configType) {


        JsonObject jsonObject= myService.dataParser(serviceName,configType);

        System.out.println("ndls--->>"+jsonObject);

        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

}
