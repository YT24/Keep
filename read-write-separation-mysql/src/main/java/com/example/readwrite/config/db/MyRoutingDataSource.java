package com.example.readwrite.config.db;
 
/**
 * @author chaird
 * @create 2020-12-30 21:37
 */
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;
 
public class MyRoutingDataSource extends AbstractRoutingDataSource {
  @Nullable
  @Override
  protected Object determineCurrentLookupKey() {
    System.out.println("线程名："+Thread.currentThread().getName()+":"+DBContextHolder.get());
    return DBContextHolder.get();
  }
}