package com.epam.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteHelper {

    public Connection connectToSqlite() throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        String db = "D:\\AutomationDemo\\Ex_Files_SQL_Testers\\Exercise Files\\02_01_SettingUpASiteForTesting\\SQL-for-testers-practice-site\\SQL-for-testers-practice-site-master\\database.db";
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + db);
        return conn;
    }

    public ResultSet queryDB(Connection connection, String sql) throws SQLException{
        Statement state = connection.createStatement();
        return state.executeQuery(sql);
    }

    public void updateDB(Connection connection, String sql) throws SQLException{
        Statement state = connection.createStatement();
        state.executeUpdate(sql);
    }

    public int getRestltSetRowCount(ResultSet resultSet){
        int rowCount = 0;
        try{
            while (resultSet.next()){
                System.out.println(rowCount);
                rowCount += 1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return rowCount;
    }

    public List convertResultSetToList(ResultSet rs)throws SQLException{

        List list =new ArrayList();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        while(rs.next()) {
            Map rowData =new HashMap();
            for(int i =1; i <= columnCount; i++)
                {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
            list.add(rowData);
        }
        return list;
    }
}