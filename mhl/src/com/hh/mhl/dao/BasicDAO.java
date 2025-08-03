package com.hh.mhl.dao;
//basicDAO 是其他DAO的父类

import com.hh.mhl.utils.JDBCUtilByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T> {//泛型指定具体类型
   private QueryRunner qr =  new QueryRunner();
         //开发通用的dml 方法，针对任意的表
   public int update(String sql, Object... params) {
      Connection conn = null;
       try {
           conn = JDBCUtilByDruid.getConnection();
           return qr.update(conn, sql, params);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }finally {
          JDBCUtilByDruid.close(conn,null,null);
       }
   }
   //返回多个对象{即查询的结果是多行}，针对多表
   public List<T> queryMulti(String sql,Class<T> clazz,Object... parameters) {
      Connection conn = null;
       try {
           conn = JDBCUtilByDruid.getConnection();
           return qr.query(conn, sql, new BeanListHandler<T>(clazz), parameters);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }finally {
          JDBCUtilByDruid.close(conn,null,null);
       }
   }
   //单行查询结果的通用方法
   public T querySingle(String sql,Class<T> clazz ,Object... parameters) {
      Connection conn = null;
       try {
           conn = JDBCUtilByDruid.getConnection();
           return qr.query(conn, sql, new BeanHandler<T>(clazz), parameters);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }finally {
          JDBCUtilByDruid.close(conn,null,null);
       }
   }
   //查询单行单列的方法，即返回单值的方法
   public Object queryScalar(String sql,Object... parameters) {
      Connection conn = null;
       try {
           conn = JDBCUtilByDruid.getConnection();
           return qr.query(conn, sql, new ScalarHandler(), parameters);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }finally {
          JDBCUtilByDruid.close(conn,null,null);
       }
   }

}
