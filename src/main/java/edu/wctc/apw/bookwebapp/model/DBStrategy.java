/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre_000
 */
public interface DBStrategy {

    public abstract void openConnection(String driverClass, String url,
            String userName, String password)
            throws ClassNotFoundException, SQLException;

    public abstract void closeConnection() throws SQLException;

    public abstract List<Map<String, Object>> findAllRecords(String tableName,
            int maxRecords) throws SQLException;

    public void deleteOneRecord(String tableName, String id) throws ClassNotFoundException, SQLException;

    public void CreateNewRecordInTable(String tableName, ArrayList<String> record) throws SQLException;

    public int deleteById(String tableName, String pkColName, Object Value) throws SQLException;

    public int updateRecords(String tableName, List<String> colNames, List<Object> colValues,
            String pkColumnName, Object value)
            throws SQLException, Exception;
}
