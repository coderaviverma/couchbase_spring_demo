package com.couchbase.demo.couchbase_demo.configutil;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "")
public interface ConfigRepository extends CouchbasePagingAndSortingRepository<ConfigDocument, String> {


}
