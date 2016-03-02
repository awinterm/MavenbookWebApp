<%-- 
    Document   : Index
    Created on : Feb 8, 2016, 12:43:30 PM
    Author     : andre_000
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Inventory</title>
        <link href="bookCss.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <a href="#" class="navbar-brand">Author Inventory</a>
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#collapse-menu">
                        <span class="sr-only">Toggle Navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="collapse-menu">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="active"><a href="index.html">Home</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container" id="pageWrapper">
            <div class="row">
                <h1>Author Inventory</h1>
                <div class="col-md-8 ScrollStyle">
                    

                    <table class="table table-hover active">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Author Name</th>
                                <th>Date Added</th>
                            </tr>
                        </thead>


                        <tbody>

                            <c:forEach var="author" items="${authorList}">
                                <tr class="${author.dateAdded}" name="${author.authorName}" id=${author.authorId} >
                                    <td>
                                        <c:out value="${author.authorId}" />
                                    </td>
                                    <td>
                                        <c:out value="${author.authorName}"/>
                                    </td>
                                    <td>            
                                        <c:out value="${author.dateAdded}"/>
                                    </td>
                                </tr>
                            </c:forEach>  
                        </tbody>

                    </table>
                    <h1>${errorMsg}</h1>
                </div>
            </div>
        </div>



        <nav  class="footer footer-inverse footer-fixed-bottom" >

            <div class="container">
                <div class="container">
                    <div class="row">
                        <div id="add" class="col-md-8">


                            <form method="POST" action="AuthorController">
                                <h2>Add a new Author</h2>
                                <label>Enter Name</label>
                                <input type="text" name="name" value="" id="nameInput"/>
                                <input type="hidden" name="action" value="add" />
                                <input class="btn" color="black" type="submit" name="submit" value="add" id="addButton" />
                            </form>

                        </div>
                        <div id="editDelete" class="col-md-8">

                            <form method="POST" action="AuthorController" >
                                <h2>Edit or Delete?</h2>
                                
                                
                     
                                <input id="id" color="black" type="hidden" name="id" value=""/>
                                <label>Name:</label>
                                
                                <input id="name" color="black" type="text" name="name" value=""/>
                                <br>
                                <label>Date Added:</label>
                                <input id="date" color="black" type="text" name="date" value="" readonly/>
                                <br>
                                <input type="hidden" name="action" value="editDelete" />
                                <input type="submit" class="btn" color="black" value="edit" name="submit" />
                                <input type="submit" class="btn" color="black" value="delete" name="submit" />
                                <input class="btn" color="black" type="submit" id="cancel" value="cancel" name="submit" />
                            </form>
                         </div>  
                        
                    
                </div>
                
            </div>
            </div>
        </nav>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                var name;
                var id;
                var date;
                $('#editDelete').hide();
                $('#cancelDiv').hide();
                 
                
                $('.table > tbody > tr').click(function () {
                    date = $(this).attr('class');
                    name = $(this).attr('name');
                    id = $(this).attr('id'); 
                    $('#editDelete').show();
                    $('#cancelDiv').show();
                    $('#add').hide();
                    $('#id').val(id);
                    $('#name').val(name);
                    $('#date').val(date);
                });
                
            
                
                
                
            });

        </script>


    </body>
</html>
