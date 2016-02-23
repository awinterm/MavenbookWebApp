/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author andre_000
 */
@SessionScoped
public class AuthorDao implements AuthorDaoStrategy, Serializable {
    // domain specific so like a list of authors vs a list of Maps 
    //private DBStrategy DBStrategy;
    
    
    @Inject
    private DBStrategy db;
    
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/book";
    private final String USER = "root";
    private final String PASSWORD = "admin";
    

    
    public AuthorDao(){   
    }

    @Override
   public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
       db.openConnection(DRIVER, URL, USER, PASSWORD);
       
       
       List<Map<String, Object>> rawData = db.findAllRecords("author", 0);
       List<Author> authors = new ArrayList<>();
       
       for(Map record : rawData){
           Author author = new Author();
           Integer id = new Integer(record.get("author_id").toString());
           author.setAuthorId(id);
           String name = record.get("author_name") == null ? "" : record.get("author_name").toString();
           author.setAuthorName(name);
           Date date = record.get("date_added") == null ? null : (Date)record.get("date_added");
           author.setDateAdded(date);
           authors.add(author);
       }
       
       
       db.closeConnection();
       return authors;
   }
    
    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, URL, USER, PASSWORD);
        int result = db.deleteById("author", "author_id", id);
        db.closeConnection();
        return result;
    }
    
  
    
    @Override
    public int createNewRecordInTable(String tableName, ArrayList<String> record ) throws SQLException, ClassNotFoundException {
        db.openConnection(DRIVER, URL, USER, PASSWORD);
        int result = db.createNewRecordInTable(tableName, record);
        db.closeConnection();
        return result;
    }
    
    @Override
       public int updateRecords(String tableName, List<String> colNames, List<Object> colValues,
                             String pkColumnName, Object value )
                             throws SQLException, Exception{
           db.openConnection(DRIVER, URL, USER, PASSWORD);
           int result = db.updateRecords(tableName, colNames, colValues, pkColumnName, value);
           db.closeConnection();
           return result;
           
       }
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);
    }

    @Override
    public DBStrategy getDb() {
        return db;
    }

    @Override
    public void setDb(DBStrategy db) {
        this.db = db;
    }
    
    
}
