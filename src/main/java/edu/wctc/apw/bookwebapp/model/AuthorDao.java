/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre_000
 */
public class AuthorDao implements AuthorDaoStrategy {
    // domain specific so like a list of authors vs a list of Maps 
    //private DBStrategy DBStrategy;
    
    // this is bad dependent on a low level class remove later. 
    private DBStrategy db = new MySqlDBStrategy();
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/book";
    private final String USER = "root";
    private final String PASSWORD = "admin";
    

    
    AuthorDao(){
        
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
    
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);
    }
}
