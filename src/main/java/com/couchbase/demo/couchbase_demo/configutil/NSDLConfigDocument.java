package com.couchbase.demo.couchbase_demo.configutil;


import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NSDLConfigDocument implements BaseConfigDocument {

    @NotNull
    @Id
    private DatabaseConfig databaseConfig;


    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }


    @Override
    public String toString() {
        return "NSDLConfigDocument{" +
                "databaseConfig=" + databaseConfig +
                '}';
    }
}