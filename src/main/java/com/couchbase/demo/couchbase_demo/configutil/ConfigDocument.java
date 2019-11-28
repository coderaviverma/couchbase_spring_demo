package com.couchbase.demo.couchbase_demo.configutil;


import com.couchbase.client.java.repository.annotation.Field;

import com.couchbase.client.java.repository.annotation.Id;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.data.couchbase.core.mapping.Document;


@Document
public class ConfigDocument{
    @Id
    private String id;

    private DatabaseConfig databaseConfig;

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
