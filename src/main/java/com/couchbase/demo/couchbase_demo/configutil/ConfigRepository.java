package com.couchbase.demo.couchbase_demo.configutil;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository extends CouchbasePagingAndSortingRepository<ConfigDocument, String> {

//    @Query("Select meta().id as id, appId, firstName, lastname from  #{#n1ql.bucket} where #{#n1ql.filter} " +
//            " and  cuid = $1 order by firstName asc limit $3 offset $2 ")
//    Object listTenantUsers(String appId, Integer offset, Integer limit);


//    @Query("SELECT count(*) , META(nsdl).id as _ID, META(nsdl).cas as _CAS FROM nsdl")
//    @Query("SELECT $2 FROM laas_config WHERE  meta().id = $1")
//    @Query("Select databaseConfig from laas_config where meta().id = nsdl")
    @Query("Select META().id as _ID, META().cas as _CAS,databaseConfig from `laas_config` where meta().id = 'nsdl'")
    List<ConfigDocument> listTenantUsers(String serviceName, String configName);


}
