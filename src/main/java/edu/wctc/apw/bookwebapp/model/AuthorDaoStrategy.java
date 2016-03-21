/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author andre_000
 */
public interface AuthorDaoStrategy {

    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException, Exception;
    
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException, Exception;
    public int createNewRecordInTable(String authorName ) throws SQLException, ClassNotFoundException, Exception;
   
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
     public Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException;
     public boolean updateRecords( Integer authorId, String authorName )
                             throws SQLException, Exception;
     
     public abstract void initDao(DataSource ds) throws Exception;
    
}
