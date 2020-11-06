package com.epam.db;

import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SqliteTest{
    SqliteHelper sqliteHelper;
    Connection connection;
    ResultSet resultSet;

    @BeforeClass
    public void setUp() throws SQLException, ClassNotFoundException {
        sqliteHelper = new SqliteHelper();
        connection = sqliteHelper.connectToSqlite();
    }

    @AfterClass
    public void tearDown() throws SQLException{
        connection.close();
    }

    @AfterMethod
    public void closeQuery() throws SQLException{
        resultSet.close();
    }

    @Test(groups = "db", priority = 1)
    public void testPriceNotZero() throws SQLException {
        String sql = "select * from products where price = 0";
        resultSet = sqliteHelper.queryDB(connection,sql);

        assertThat(sqliteHelper.getRestltSetRowCount(resultSet), is(equalTo(0)));
    }

    @Test(groups = "db", priority = 2)
    public void testCreateCategory() throws SQLException {
        String insertCategorySql = "insert into categories values (5, 'discount')";
        String queryCategorySql = "select count(*) from categories where name = 'discount'";
        sqliteHelper.updateDB(connection,insertCategorySql);
        resultSet = sqliteHelper.queryDB(connection, queryCategorySql);
        assertThat(sqliteHelper.getRestltSetRowCount(resultSet), is(equalTo(1)));
    }

    @Test(groups = "db", priority = 3)
    public void testUpdateProductsCategory() throws SQLException {
        String updateCategorySql = "update products set categoryId = 5 where price > 100";
        String queryProductSql = "select * from products where categoryId = 5";
        sqliteHelper.updateDB(connection,updateCategorySql);
        resultSet = sqliteHelper.queryDB(connection, queryProductSql);
        assertThat(sqliteHelper.getRestltSetRowCount(resultSet), is(equalTo(2)));
    }

    @Test(groups = "db", priority = 4)
    public void testUpdateProductsPrice() throws SQLException {
        String updatePriceSql = "update products set price = round(price * 0.7,2) where categoryId = 5";
        String queryProductSql = "select * from products where categoryId = 5 and (price = 349.99 or price = 734.99)";
        sqliteHelper.updateDB(connection,updatePriceSql);
        resultSet = sqliteHelper.queryDB(connection, queryProductSql);
        assertThat(sqliteHelper.getRestltSetRowCount(resultSet), is(equalTo(2)));
    }

}