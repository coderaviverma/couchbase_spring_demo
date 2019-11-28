package com.couchbase.demo.couchbase_demo.configutil;

import java.io.Serializable;

public class Result implements Serializable {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
