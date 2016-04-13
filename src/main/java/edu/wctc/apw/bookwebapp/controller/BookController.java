/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.controller;

import edu.wctc.apw.bookwebapp.ejb.AbstractFacade;
import edu.wctc.apw.bookwebapp.ejb.AuthorFacade;
import edu.wctc.apw.bookwebapp.ejb.BookFacade;
import edu.wctc.apw.bookwebapp.model.Author;
import edu.wctc.apw.bookwebapp.model.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author andre_000
 */

public class BookController extends HttpServlet {
    
     private static final String ACTION_PARAMETER = "action";
    private static final String BOOK_LIST_PAGE = "/bookListjsp.jsp";
    
    
    private static final String GET_BOOK_LIST = "getList";
    
    private static final String ADD_EDIT_DELETE = "addEditDelete";
    private static final String WINE_ID_PARAMETER_KEY = "wineID";
    private static final String SUBMIT_ACTION = "submit";
    private static final String DELETE_ACTION = "Delete";
    private static final String EDIT_ACTION = "Edit";
    private static final String ADD_EDIT_PAGE = "/addEdit.jsp";
    private static final String ADD_ACTION = "Add";
    private static final String CANCEL_ACTION = "Cancel";
    private static final String SAVE_ACTION = "Save";
    private static final String LOG_OUT_ACTION = "logOut";
    private static final String LOG_OUT_PAGE_URL = "/logOut.jsp";
    private static final String ERROR_MSG_KEY = "errMsg";
    private static final String IMAGE_URL_KEY = "imageUrl";
    private static final String PRICE_KEY = "price";
    private static final String PRODUCT_NAME_KEY = "productName";
    private static final String WINE_KEY = "wine";
    private static final String WINE_LIST_KEY = "wineList";
    
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbJndiName; 
    
    @Inject
     private AbstractFacade bookServ;
   
    @Inject
    private AbstractFacade<Author> authService;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String destination = BOOK_LIST_PAGE;
        String action = request.getParameter(ACTION_PARAMETER);
        
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
//                case ADD_EDIT_DELETE:
//                    String subAction = request.getParameter(SUBMIT_ACTION);
//                    String wineId = request.getParameter(WINE_ID_PARAMETER_KEY);
//
//                    if (subAction.equals(DELETE_ACTION)) {
//                        bookServ.deleteWineById(wineId);
//                        this.refreshList(request, wineServ);
//                        destination = BOOK_LIST_PAGE;
//                    } else if (subAction.equals(EDIT_ACTION)) {
//                        destination = ADD_EDIT_PAGE;
//                        Wine wine = wineServ.getWineById(wineId);
//                        request.setAttribute(WINE_KEY, wine);
//                    } else if (subAction.equals(ADD_ACTION)) {
//                        request.setAttribute(WINE_KEY, null);
//                        destination = ADD_EDIT_PAGE;
//                    }
//                    break;

//                case CANCEL_ACTION:
//                    this.refreshList(request, wineServ);
//                    destination = BOOK_LIST_PAGE;
//                    break;

//                case SAVE_ACTION:
//                    try {
//                        String wineName = request.getParameter(PRODUCT_NAME_KEY);
//                        String price = request.getParameter(PRICE_KEY);
//                        String imageUrl = request.getParameter(IMAGE_URL_KEY);
//                        String Id = request.getParameter(WINE_ID_PARAMETER_KEY);
////                    System.out.println("THIS HAPPENED. " + wineName + price + imageUrl + Id);
//                        wineServ.saveOrUpdateWine(Id, wineName, price, imageUrl);
//                        this.refreshList(request, wineServ);
//                        destination = BOOK_LIST_PAGE;
//                    } catch (ParameterMissingException e) {
//                        request.setAttribute(ERROR_MSG_KEY, e.getMessage());
//                        destination = ADD_EDIT_PAGE;
//                    }
//                    break;

                case LOG_OUT_ACTION:
                    destination = LOG_OUT_PAGE_URL;
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

private void refreshBookList(HttpServletRequest request, AbstractFacade<Book> bookService) throws Exception {
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
    }

}
