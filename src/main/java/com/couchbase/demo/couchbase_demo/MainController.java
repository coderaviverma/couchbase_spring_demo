package com.couchbase.demo.couchbase_demo;


import com.couchbase.demo.couchbase_demo.configutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {


    private MyService myService;

    public MainController(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(value = "/config", method  = RequestMethod.GET)
    public ResponseEntity<?> getConfig(@RequestParam("serviceName") String serviceName,@RequestParam("configType") String configType) {


        Map<String,Object> hashMap= myService.dataParser(serviceName,configType);

        System.out.println("ndls--->>"+hashMap);

        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

}
