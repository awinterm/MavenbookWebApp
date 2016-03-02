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
     
     private static final String SUBMIT_ACTION = "submit";
     private static final String DELETE_ACTION = "delete";
     private static final String EDIT_ACTION = "edit";
     private static final String ADD = "add";
     private static final String EDIT_DELETE_ACTION = "editDelete";
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
            List<Author> authors;
            
        switch (action) {
                case GET_AUTHOR_LIST_ACTION:
                        // Jim made a method out of this. If I reuse these two lines I will do the same.
                        this.refreshList(request, authorServ);
                        // if you have two or more pages this tool can send to then this next line is smart.
                        destination = RESPONSE_PAGE;
                    break;

                case ADD:
                    String authorName = request.getParameter("name");
                    authorServ.createNewRecordInTable(authorName);
                    this.refreshList(request, authorServ);
                    destination = RESPONSE_PAGE;
                    break;
                    
                case EDIT_DELETE_ACTION:
                    String subAction = request.getParameter(SUBMIT_ACTION);
                    System.out.println(subAction);
                    if (subAction.equals(DELETE_ACTION)) {
                         String authorId = request.getParameter("id");
                         authorServ.deleteAuthorById(authorId);
                         
                    } else if((subAction.equals(EDIT_ACTION))){
                        String name = request.getParameter("name");
                        String authorId = request.getParameter("id");
                        String date = request.getParameter("date");
                        authorServ.editAuthorRecord(authorId, name);       
                    } else {
                        // must be cancel do nothing
                        
                    }
                    destination = RESPONSE_PAGE;
                    this.refreshList(request, authorServ);
                    break;
                    default:
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAMETER_MSG);
                    destination = RESPONSE_PAGE;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", "Something Bad");
        }

        RequestDispatcher view =
                    request.getRequestDispatcher(destination);
           view.forward(request, response);
    }



//                        // must be add or edit, go to addEdit page
//                        String[] authorIds = request.getParameterValues("authorId");
//                        if (authorIds == null) {
//                            // must be an add action, nothing to do but
//                            // go to edit page
//                        } else {
//                            // must be an edit action, need to get data
//                            // for edit and then forward to edit page
//                            
//                            // Only process first row selected
//                            String authorId = authorIds[0];
//                            Author author = authorServ.getAuthorById(authorId);
//                            request.setAttribute("author", author);
//                        }
//
//                        destination = ADD_EDIT_PAGE;
//
//                    } else {
//                        // must be DELETE
//                        // get array based on records checked
//                        String[] authorIds = request.getParameterValues("authorId");
//                        for (String id : authorIds) {
//                            authorServ.deleteAuthorById(id);
//                        }
//
//                        this.refreshList(request, authorServ);
//                        destination = RESPONSE_PAGE;
//                    }
//                    break;

//                default:
//                    // no param identified in request, must be an error
//                    request.setAttribute("errMsg", NO_PARAMETER_MSG);
//                    destination = RESPONSE_PAGE;
//                    break;
//            }
//
//        } catch (Exception e) {
//            request.setAttribute("errMsg", "Something Bad");
//        }
              
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
//        RequestDispatcher view =
//                    request.getRequestDispatcher(destination);
//           view.forward(request, response);
//    }
    
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
    
     private void refreshList(HttpServletRequest request, AuthorService authorService) throws Exception {
        List<Author> authors = authorService.getAuthorList();
        request.setAttribute("authorList", authors);
    }
    
    
}


