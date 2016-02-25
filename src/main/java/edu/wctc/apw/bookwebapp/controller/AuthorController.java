/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.apw.bookwebapp.controller;

import java.io.IOException;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.wctc.apw.bookwebapp.model.Author;
import edu.wctc.apw.bookwebapp.model.AuthorService;
import javax.inject.Inject;

/**
 *
 * @author andre_000
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {
     private static final String RESPONSE_PAGE = "/ResponsePage.jsp";
     private static final String ACTION_PARAMETER = "action";
     private static final String GET_AUTHOR_LIST_ACTION = "getList";
     private static final String NO_PARAMETER_MSG = "No matching parameter found";
     private static final String ADD_EDIT_DELETE_ACTION = "addEditDelete";
     // WAY MORE CONSTANTS GO HERE! 
     
    private String driverClass;
    private String url;
    private String userName;
    private String password;
     
    @Inject
     private AuthorService authorServ;
     
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String destination = RESPONSE_PAGE;
        String action = request.getParameter(ACTION_PARAMETER);
        
        configDbConnection();
        
        try{
        switch (action) {
                case GET_AUTHOR_LIST_ACTION:
                        // Jim made a method out of this. If I reuse these two lines I will do the same.
                        List<Author> authors = authorServ.getAuthorList();
                        request.setAttribute("authorList", authors);
                        // if you have two or more pages this tool can send to then this next line is smart.
                        destination = RESPONSE_PAGE;
                    break;

                    // MORE CASE STATMENTS TO MATCH THE UI... BUILD THAT THEN THIS I THINK

                default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAMETER_MSG);
                    destination = RESPONSE_PAGE;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }
              
//        try{
//            
////            AuthorService authorServ = new AuthorService();
//            List<Author> authors;
//            authors = authorServ.getAuthorList();
//            request.setAttribute("authorList", authors);
//            
//          
//            
//        } catch(Exception e) {
//            request.setAttribute("errorMsg", e.getMessage());
//        }
//        
        RequestDispatcher view =
                    request.getRequestDispatcher(destination);
           view.forward(request, response);
    }
    
    private void configDbConnection() { 
        authorServ.getDao().initDao(driverClass, url, userName, password);   
    }
    
    
//
//    }

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

    @Override
    public void init() throws ServletException {
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        userName = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
    }
    
    
}


