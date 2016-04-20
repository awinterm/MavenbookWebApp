<%-- 
    Document   : bookListjsp
    Created on : Apr 4, 2016, 1:11:10 PM
    Author     : andre_000
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ava Wine</title>
        <%-- Custom CSS goes here --%>
        
        <link href="site.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet"
              
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    </head>
    <body>
       
         <div class="col-md-11" align="right">
        <form method="POST" action=<%= response.encodeURL("BookController") %>>
        <input class="btn"  type="submit" name="action" value="logOut" align="right"  />
        </form>
         </div>
        <br>
            <div class="container" id="pageWrapper" >
            <div class="row" >
                <h1>Book List</h1>
                <div class="col-md-8 ScrollStyle">
                    <form method="POST" action=<%= response.encodeURL("BookController") %>>
                        <input type="hidden" name="action" value="addEditDelete" />
                        <input class="btn"  type="submit" name="submit" value="Add"  />
                    </form>

                    <table class="table table-hover active" align="center">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Isbn</th>
                            </tr>
                        </thead>


                        <tbody> 

                            <c:forEach var="book" items="${bookList}">
                                <tr class="${book.bookId}" name="${book.bookId}" id=${book.bookId} >
                                    <td>
                                        <c:out value="${book.bookId}" />
                                    </td>
                                    <td>
                                        <c:out value="${book.tittle}"/>
                                    </td>
                                    <td>            
                                        <c:out value="${book.isbn}"/>
                                    </td>
                                    <td>
                                        <form method="POST" action="BookController">
                                            <input type="hidden" name="action" value="addEditDelete" />
                                            <input type="hidden" name="bookId" value="${book.bookId}" />
                                            
                                            <input class="btn"  type="submit" name="submit" value="Edit"  />
                                            <input class="btn"  type="submit" name="submit" value="Delete"  />
                                        </form>
                                    </td>    
                                </tr>
                            </c:forEach>  
                        </tbody>

                    </table>
                    <h1>${errMsg}</h1>
                </div>
            </div>
        </div>
                 
    </body>
</html>