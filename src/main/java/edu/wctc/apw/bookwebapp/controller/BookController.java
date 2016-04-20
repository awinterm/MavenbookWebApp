/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.controller;


import java.io.IOException;

import java.util.List;
import javax.inject.Inject;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.wctc.apw.bookwebapp.entity.Author;
import edu.wctc.apw.bookwebapp.entity.Book;
import edu.wctc.apw.bookwebapp.service.AuthorService;
import edu.wctc.apw.bookwebapp.service.BookService;
import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author andre_000
 */

public class BookController extends HttpServlet {
    
    private static final String ACTION_PARAMETER = "action";
    private static final String BOOK_LIST_PAGE = "/bookListjsp.jsp";
    
    private static final String SUBMIT_ACTION = "submit";
    
    private static final String GET_BOOK_LIST = "getList";
    private static final String ADD_EDIT_DELETE = "addEditDelete";
  
    private static final String DELETE_ACTION = "Delete";
    private static final String EDIT_ACTION = "Edit";
    private static final String ADD_EDIT_PAGE = "/addEdit.jsp";
    private static final String ADD_ACTION = "Add";
    private static final String CANCEL_ACTION = "Cancel";
    private static final String SAVE_ACTION = "Save";
    
  
    private static final String ERROR_MSG_KEY = "errMsg";
   
    
    private AuthorService authorServ;
    
    private BookService bookServ;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String destination = BOOK_LIST_PAGE;
        String action = request.getParameter(ACTION_PARAMETER);
        Book book = null;
        
        try {

          
            switch (action) {
                // this wont work.....
                case GET_BOOK_LIST:
                    // Jim made a method out of this. If I reuse these two lines I will do the same.
                    request.getParameter("id");
                    this.refreshBookList(request, bookServ);
                    // if you have two or more pages this tool can send to then this next line is smart.
                    destination = BOOK_LIST_PAGE;
                    break;
                    
                case ADD_EDIT_DELETE:
                    String subAction = request.getParameter(SUBMIT_ACTION);
                    String bookId = request.getParameter("bookId");

                    if (subAction.equals(DELETE_ACTION)) {
                        
                        System.out.println(bookId);
                        book = bookServ.findById(bookId);
                        bookServ.remove(book);
                        this.refreshBookList(request, bookServ);
                        destination = BOOK_LIST_PAGE;
                        
                    } else if (subAction.equals(EDIT_ACTION)) {
                        bookId = request.getParameter("bookId");
                        System.out.println(bookId);
                        destination = ADD_EDIT_PAGE;
                        System.out.println("MONKEY");
                        book = bookServ.findById(bookId);
                        System.out.println("MONKEY");
                        request.setAttribute("book", book);
                        this.refreshAuthorList(request, authorServ);
                    } 
                    else if (subAction.equals(ADD_ACTION)) {
                        
                        destination = ADD_EDIT_PAGE;
                    }
                    break;

                    case CANCEL_ACTION:
                    this.refreshBookList(request, bookServ);
                    this.refreshAuthorList(request, authorServ);
                    destination = BOOK_LIST_PAGE;
                    break; 
                    

                case SAVE_ACTION:
                    try {
                        String title = request.getParameter("title");
                        String isbn = request.getParameter("isbn");
                        String authorId = request.getParameter("authorId");
                        bookId = request.getParameter("bookId");
                       
                     if(bookId.isEmpty()){
                        book = new Book(0);
                        book.setTittle(title);
                        book.setIsbn(isbn);
                        Author author = null;
                        if(authorId != null){
                            author = authorServ.findById(authorId);
                            book.setAuthorId(author);
                        }
//                        this.refreshList(request, wineServ);
                     } else {
                         book = bookServ.findById(bookId);
                         book.setTittle(title);
                         book.setIsbn(isbn);
                         Author author = null;
                          if(authorId != null) {
                            author = authorServ.findById(authorId);
                            book.setAuthorId(author);
                        }
                     }
                         bookServ.edit(book);
                        
                        this.refreshBookList(request, bookServ);
                        this.refreshAuthorList(request, authorServ);
                        destination = BOOK_LIST_PAGE;
                    } catch (Exception e) {
                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
                        destination = ADD_EDIT_PAGE;
                    }
                    break;

                    
                 default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", "You are missing a param");
                    destination = BOOK_LIST_PAGE;
                    break;    
            }
        } 
        catch (Exception e) {
            request.setAttribute(ERROR_MSG_KEY, e.getMessage());
        }

        RequestDispatcher view
                = getServletContext().getRequestDispatcher(response.encodeURL(destination));
        view.forward(request, response);
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
        @Override
   public void init() throws ServletException {
       // Ask Spring for object to inject
       ServletContext sctx = getServletContext();
       WebApplicationContext ctx
               = WebApplicationContextUtils.getWebApplicationContext(sctx);
       authorServ = (AuthorService) ctx.getBean("authorService");
       bookServ = (BookService) ctx.getBean("bookService");
   }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    
    
    
    
private void refreshBookList(HttpServletRequest request, BookService bookService) throws Exception {
        List<Book> books = bookService.findAll();
        request.setAttribute("bookList", books);
    }

private void refreshAuthorList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }

}