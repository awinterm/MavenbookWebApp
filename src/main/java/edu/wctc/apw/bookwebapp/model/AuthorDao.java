/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    private String driver;
    private String url;
    private String user;
    private String password;

    
    
    
    
    public AuthorDao(){   
    }

    @Override
   public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
       db.openConnection(driver, url, user, password);
       
       List<Map<String, Object>> rawData = db.findAllRecords("author", 0);
        System.out.println(rawData.toString());
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
       
       System.out.println(" I WORK!");
       db.closeConnection();
       return authors;
   }
    
    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
        db.openConnection(driver, url, user, password);
        int result = db.deleteById("author", "author_id", id);
        db.closeConnection();
        return result;
    }
    
    // jims method
//     @Override
//    public boolean saveAuthor(Integer authorId, String authorName) throws DataAccessException {
//        db.openConnection(driver, url, user, pwd);
//        boolean result = false;
//        
//        if(authorId == null || authorId.equals(0)) {
//            // must be a new record
//            result = db.insertRecord("author", Arrays.asList("author_name","date_added"), 
//                                      Arrays.asList(authorName,new Date()));
//        } else {
//            // must be an update of an existing record
//            int recsUpdated = db.updateRecords("author", Arrays.asList("author_name"), 
//                                       Arrays.asList(authorName),
//                                       "author_id", authorId);
//            if(recsUpdated > 0) {
//                result = true;
//            }
//        }
//        return result;
//    }
    
    // BAD DREW 
    @Override
    public int createNewRecordInTable(String authorName) throws SQLException, ClassNotFoundException {
        if( authorName.isEmpty() || authorName == null){
            // catch this bad boy... if nulls get in your data base your getauthorList methods DO NOT WORK!
            throw new IllegalArgumentException();
        }
        ArrayList<String> record = new ArrayList<>();
        record.add(null);
        record.add(authorName);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        
        record.add(df.format(date));
        
        db.openConnection(driver, url, user, password);
        int result = db.createNewRecordInTable("author", record);
        db.closeConnection();
        return result;
    }
    
    @Override
       public boolean updateRecords( Integer authorId, String authorName )
                             throws SQLException, Exception{
           boolean result = false;
           db.openConnection(driver, url, user, password);    
           int recsUpdated = db.updateRecords("author", Arrays.asList("author_name"), 
                                       Arrays.asList(authorName),
                                       "author_id", authorId);
            if(recsUpdated > 0) {
                result = true;
           
            }
           db.closeConnection();
           return result;
       }
       
   @Override  
    public Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException {
        
        db.openConnection(driver, url, user, password);
        
        Map<String,Object> rawRec = db.findById("author", "author_id", authorId);
        Author author = new Author();
        author.setAuthorId((Integer)rawRec.get("author_id"));
        author.setAuthorName(rawRec.get("author_name").toString());
        author.setDateAdded((Date)rawRec.get("date_added"));
        
        return author;
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

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public void initDao(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    
    
}
