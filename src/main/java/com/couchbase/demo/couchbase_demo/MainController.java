package com.couchbase.demo.couchbase_demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    private MyService myService;

    public MainController(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping(value = "/config", method  = RequestMethod.GET)
    public ResponseEntity<?> getConfig(@RequestParam("id") String id) {
        Object result= myService.dataParser(id);

        System.out.println("ndls.document");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
