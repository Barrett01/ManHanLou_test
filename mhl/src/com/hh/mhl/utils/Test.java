package com.hh.mhl.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        //测试一下工具类好不好用
//        System.out.println("请输入一个整数");
//        int i = Utility.readInt();
//        System.out.println(i);

        //测试一下jdbcUtileByDruid
        Connection connection = JDBCUtilByDruid.getConnection();
        System.out.println(connection.getClass());

    }
}
