/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.ejb;

import edu.wctc.apw.bookwebapp.model.Author;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andre_000
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "edu.wctc.apw_bookWebApp_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }
    
    public void deleteById(String id) {
        Author author = this.find(new Integer(id));
        this.remove(author);
    }
    
    public void saveOrUpdate(String id, String name) {
        Author author = new Author();
        if(id == null) {
            // must be a new record
            author.setAuthorName(name);
            author.setDateAdded(new Date());
        } else {
            // modify record
            author.setAuthorId(new Integer(id));
            author.setAuthorName(name);
        }
        this.getEntityManager().merge(author);
    }
    
    
}