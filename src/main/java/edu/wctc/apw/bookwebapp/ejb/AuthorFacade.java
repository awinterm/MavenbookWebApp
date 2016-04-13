/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.ejb;

import edu.wctc.apw.bookwebapp.model.Author;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author andre_000
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {
    // jims version unitName = "book_PU" dunno.
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
    
      public List<Author> findByName(String name) {
        String jpql = "select a from Author where a.authorName = ?1";
        Query q = getEntityManager().createQuery(jpql);
        q.setParameter(1, name);
        return q.getResultList();
    }
}
