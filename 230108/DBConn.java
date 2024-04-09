package com.checkingAccount;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
    private static Connection dbConn;
    public static Connection getConnection(){
        if(dbConn==null){
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/sampledb";
                String user = "root";
                String password = "zaq12wsx";
                Class.forName("com.mysql.cj.jdbc.Driver");
                dbConn = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return dbConn;
    }
    public static void close(){
        if(dbConn!=null){
            try {
                if(!dbConn.isClosed()){
                    dbConn.close();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        dbConn = null;
    }
}
