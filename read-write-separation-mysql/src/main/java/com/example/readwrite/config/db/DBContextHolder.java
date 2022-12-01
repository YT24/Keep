package com.example.readwrite.config.db;
 
/**
 * @author YT
 * @create 2020-12-30 21:30
 */
import com.example.readwrite.constants.DBTypeEnum;

import java.util.concurrent.atomic.AtomicInteger;
 
public class DBContextHolder {
 
  private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();
 
  private static final AtomicInteger counter = new AtomicInteger(-1);
 
  public static void set(DBTypeEnum dbType) {
    contextHolder.set(dbType);
  }
 
  public static DBTypeEnum get() {
    return contextHolder.get();
  }
 
  public static void master() {
    set(DBTypeEnum.MASTER);
    System.out.println("切换到master");
  }
 
  public static void slave() {
    //  轮询
    set(DBTypeEnum.SLAVE);
    System.out.println("切换到slave");
  }
}