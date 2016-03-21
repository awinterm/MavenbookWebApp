 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author andre_000
 */
@SessionScoped
public class AuthorService implements Serializable {
    @Inject
    private AuthorDaoStrategy dao;

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }

    public AuthorService() {
    }
    
     public Author getAuthorById(String authorId) throws ClassNotFoundException, SQLException {
        return dao.getAuthorById(Integer.parseInt(authorId));
    }
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException, Exception{
        return dao.getAuthorList();
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
    }
    
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException, Exception{
        return dao.deleteAuthorById(id);
    }
    
     public int createNewRecordInTable(String authorName ) throws SQLException, ClassNotFoundException, Exception {
         return dao.createNewRecordInTable(authorName);        
     }
     
     public boolean editAuthorRecord(String authorId, String authorName) throws Exception{
         
         return dao.updateRecords(Integer.valueOf(authorId), authorName);
     }
     
   
}
