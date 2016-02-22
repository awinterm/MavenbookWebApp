/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre_000
 */
public class MySqlDBStrategy implements DBStrategy {
    private Connection conn;
    
    
    @Override
    public void openConnection(String driverClass, String url, 
            String userName, String password) throws ClassNotFoundException, SQLException {
        
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url,userName, password);
        
    }
    
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }
    /**
     * Must open and close a connection when using this method.
     * Future optimizations may include changing the return type to an array.
     * It will save on memory. Because ArrayLists have empty slots. 
     * 
     * @param tableName
     * @param maxRecords - Limits result set to this number, or if maxRecords is zero (0) then no limit
     * @return 
     * @throws java.sql.SQLException 
     */
    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, 
            int maxRecords) throws SQLException{
        
        // create MySql statement
        String sql;
        if (maxRecords < 1) {
        
        sql = "select * from " + tableName;
            } else {
        sql = "select * from " + tableName + " limit " + maxRecords;
                    }
        // sorting could happen here
        Statement stmt = conn.createStatement();
        // create result set object.
        ResultSet rs = stmt.executeQuery(sql);
        // get meta data
        ResultSetMetaData rsmd = rs.getMetaData();
        // find out how many columns there are in the table.
        int columnCount = rsmd.getColumnCount();
        // a list of maps to store our records
        List<Map<String, Object>> records = new ArrayList<>();
        
        while ( rs.next()){
            // loop for saving records into our map.
            Map<String, Object> record = new HashMap<>();
                for(int colNo = 1; colNo <= columnCount ; colNo++){
                    // get field of this column
                    Object colData = rs.getObject(colNo);
                    // get columnName
                    String columnName = rsmd.getColumnName(colNo);
                    // put them into a map.
                    record.put(columnName, colData);
                }
                // put our map into our list of maps.
                records.add(record);
        }
                // return this list of maps
        return records;
    }
    
    
    @Override
    public void deleteOneRecord(String tableName, String id) throws ClassNotFoundException, SQLException {
       // Validate the parameters here.
        
       // String sql = "DELETE FROM " + tableName + " WHERE " + column + "=" + value;
        String pKeyColumnName = "";
       // Statement stmt = conn.createStatement();
        
        
        DatabaseMetaData dmd = conn.getMetaData();
        ResultSet rs = null;
      
        // was told this is expensive. Could maybe solve this with an ENUM.
        rs = dmd.getPrimaryKeys(null, null, tableName);
        

        // this works only if there is only one PK... in a parent child relationship I may want to 
        // test for how many PKs I get back... if this is going to work for any table. 
       
            
        while(rs.next()){
        pKeyColumnName = rs.getString("COLUMN_NAME");
       // System.out.println("PK column name is " + pKeyColumnName);
        
        //String sql = "delete from " + tableName + " where " + pKeyColumnName + "=" + id;
        
        String sql2 = "delete from " + tableName + " where " + pKeyColumnName + "=?";
        
        PreparedStatement pstmt = conn.prepareStatement(sql2);
       
        pstmt.setInt(1, Integer.parseInt(id));
        
        pstmt.executeUpdate(); 
        }

    }
    
    /**
     * JIMS METHOD. 
     * @param tableName
     * @param pkColName
     * @param Value 
     * @throws java.sql.SQLException 
     */
    @Override
    public int deleteById(String tableName, String pkColName, Object Value) throws SQLException{
        String sql = "delete from " + tableName + " where " + pkColName + " =?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, Value);
        return pstmt.executeUpdate();
    }
    
    
    @Override
    public void CreateNewRecordInTable(String tableName, ArrayList<String> record ) throws SQLException{
        
   //     String sql = "INSERT INTO table_name (column1,column2,column3,...)\n" +
   //                  "VALUES (value1,value2,value3,...);";
        
        String columnName = "";
        String sql1 = "Select * from " + tableName;
        String valuePlaceHolder = "";
        
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql1);
         
        
        ResultSetMetaData rsmd = rs.getMetaData();
        
        int columnNumber = rsmd.getColumnCount();
       
        // test
        // System.out.println(columnNumber);   this equals 3 so thats right.
        for(int i = 1; i <= columnNumber; i++){
            if( i < columnNumber){
            columnName = columnName + rsmd.getColumnName(i) + ", ";
            valuePlaceHolder = valuePlaceHolder + "?,";
        }
            else if(i == columnNumber){
                columnName = columnName + rsmd.getColumnName(i) + ") ";
                valuePlaceHolder = valuePlaceHolder + "?)";
            }
        }
        System.out.println(columnName);
        System.out.println(valuePlaceHolder);
        
        String sql2 = "INSERT INTO " + tableName + " ( " + columnName
                     + "VALUES (" + valuePlaceHolder ;
        
        // test
        //System.out.println(sql2);
        
        PreparedStatement pstmt = conn.prepareStatement(sql2);
        
       
        
        for(int i = 1; i <= columnNumber; i++){
            pstmt.setString(i, record.get(i - 1));
       }
    //    System.out.println(columnName);
    //    System.out.println(pstmt);
        
        pstmt.executeUpdate();
        
//test
//        rs = stmt.executeQuery(sql1);
//        System.out.println(rs);
    }
    
    
    @Override
    public int updateRecords(String tableName, List<String> colNames, List<Object> colValues,
                             String pkColumnName, Object value )
                             throws SQLException, Exception
    {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;

        // do this in an excpetion handler so that we can depend on the
        // finally clause to close the connection
        try {
                    pstmt = buildUpdateStatement(conn,tableName,colNames, pkColumnName);

                    final Iterator i=colValues.iterator();
                    int index = 1;
                    Object obj = null;

                    // set params for column values
                    while( i.hasNext()) {
                        obj = i.next();
                        pstmt.setObject(index++, obj);
                    }
                    // and finally set param for wehere value
                    pstmt.setObject(index,value);                  
                    recsUpdated = pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            throw e;
        } finally {
                    try {
                            pstmt.close();
                            conn.close();
                    } catch(SQLException e) {
                            throw e;
                    } // end try
        } // end finally

        return recsUpdated;
    }
    
    /*
	 * Builds a java.sql.PreparedStatement for an sql update using only one where clause test
	 * @param conn - a JDBC <code>Connection</code> object
	 * @param tableName - a <code>String</code> representing the table name
	 * @param colDescriptors - a <code>List</code> containing the column descriptors for
	 * the fields that can be updated.
	 * @param whereField - a <code>String</code> representing the field name for the
	 * search criteria.
	 * @return java.sql.PreparedStatement
	 * @throws SQLException
	 */
	private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName,
						List colDescriptors, String whereField)
	throws SQLException {
		StringBuffer sql = new StringBuffer("UPDATE ");
		(sql.append(tableName)).append(" SET ");
		final Iterator i=colDescriptors.iterator();
		while( i.hasNext() ) {
			(sql.append( (String)i.next() )).append(" = ?, ");
		}
		sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) );
		((sql.append(" WHERE ")).append(whereField)).append(" = ?");
		final String finalSQL=sql.toString();
		return conn_loc.prepareStatement(finalSQL);
	}
    
    @Override
    public void insertNewRecordbyId (String tableName, ArrayList<String> record, int id ) throws SQLException{
        // is this not CreateNewRecordInTabledOneRecord method..? 
        String columnName = "";
        String sql1 = "Select * from " + tableName;
        String valuePlaceHolder = "";
        
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql1);
         
        
        ResultSetMetaData rsmd = rs.getMetaData();
        
        int columnNumber = rsmd.getColumnCount();
       
        // test
        // System.out.println(columnNumber);   this equals 3 so thats right.
        for(int i = 1; i <= columnNumber; i++){
            if( i < columnNumber){
            columnName = columnName + rsmd.getColumnName(i) + ", ";
            
            valuePlaceHolder = valuePlaceHolder + "?,";
        }
            else if(i == columnNumber){
                columnName = columnName + rsmd.getColumnName(i) + ") ";
                valuePlaceHolder = valuePlaceHolder + "?)";
            }
        }
        System.out.println(columnName);
        System.out.println(valuePlaceHolder);
        
        String sql2 = "INSERT INTO " + tableName + " ( " + columnName
                     + "VALUES (" + valuePlaceHolder + "where " + columnName + "=" + id ;
        
        // test
        //System.out.println(sql2);
        
        PreparedStatement pstmt = conn.prepareStatement(sql2);
        
       
        
        for(int i = 1; i <= columnNumber; i++){
            pstmt.setString(i, record.get(i - 1));
       }
    //    System.out.println(columnName);
    //    System.out.println(pstmt);
        
        pstmt.executeUpdate();
    }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        
        DBStrategy db = new MySqlDBStrategy();
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        System.out.println(db.findAllRecords("author", 0).toString());
        ArrayList<String> record = new ArrayList<>();
        record.add(null);
        record.add("Jack Kerouac");
        record.add("2015-11-01");
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        db.insertNewRecordbyId("author", record, 3); 
        db.closeConnection();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);
        db.closeConnection();
//        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);
//        List<String> colNames = Arrays.asList("author_name");
//        List<Object> colValues = Arrays.asList("Lucifer MorningStar");
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        int result = db.updateRecords("author", colNames, colValues, "author_id", 4);
//        db.closeConnection();
//        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);
        db.closeConnection();
        System.out.println(rawData);
        
        
        
        
    }
    
}
