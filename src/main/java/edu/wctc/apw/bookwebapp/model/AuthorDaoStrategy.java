/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre_000
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException;
    public int createNewRecordInTable(String tableName, ArrayList<String> record ) throws SQLException, ClassNotFoundException;
    public int updateRecords(String tableName, List<String> colNames, List<Object> colValues,
                             String pkColumnName, Object value )
                             throws SQLException, Exception;
     public DBStrategy getDb();
     public void setDb(DBStrategy db);
     public void initDao(String driver, String url, String user, String password);
     public void setPassword(String password);
     public String getPassword();
     public void setUser(String user);
     public String getUser();
     public void setUrl(String url);
      public String getUrl();
       public void setDriver(String driver);
        public String getDriver();

}
