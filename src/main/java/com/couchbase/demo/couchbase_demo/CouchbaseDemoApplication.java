package com.couchbase.demo.couchbase_demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CouchbaseDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CouchbaseDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


    }
}
