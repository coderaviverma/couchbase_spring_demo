package com.couchbase.demo.couchbase_demo.configutil;


import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.google.gson.internal.LinkedTreeMap;
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
public class ConfigDocument {

    @NotNull
    @Id
    private String id;

    @NotNull
    @Field
    private LinkedTreeMap value;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedTreeMap getValue() {
        return value;
    }

    public void setValue(LinkedTreeMap value) {
        this.value = value;
    }
}
