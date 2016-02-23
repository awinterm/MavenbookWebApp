 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;

/**
 *
 * @author andre_000
 */
@Alternative
@SessionScoped
public class mockAuthorDao implements AuthorDaoStrategy, Serializable {
//        Author kerouac = new Author(1000, "Jack Kerouac", new Date());
//        Author king = new Author(1001, "Stephen King", new Date());
//        Author loftus = new Author(1002, "John w. Loftus", new Date());
    
    private List<Author> authorList = new ArrayList<>(Arrays.asList
        (new Author(1000, "Jack Kerouac"), new Author(1001, "Stephen King"), new Author(1002, "John w. Loftus")));
        
    private DBStrategy db;       

    @Override
    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) throws IllegalArgumentException{
        if( authorList.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.authorList = authorList;
    }

    public List returnAllAuthors(){

        return authorList;
    }
    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
      return 1;  
    }
    // test
//     public static void main(String[] args) {
//         AuthorService aServ = new AuthorService();
//         List<Author> List = aServ.returnAllAuthors();
//         for(Author a : List)
//         System.out.println(a.toString());
//     }

    @Override
    public int createNewRecordInTable(String tableName, ArrayList<String> record) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateRecords(String tableName, List<String> colNames, List<Object> colValues, String pkColumnName, Object value) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBStrategy getDb() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDb(DBStrategy db) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
