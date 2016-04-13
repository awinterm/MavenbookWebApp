<%-- 
    Document   : addEdit
    Created on : Mar 20, 2016, 9:07:46 PM
    Author     : andre_000
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>book add edit page</title>
        <link href="site.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    </head>
    <body>
       

        <div class="col-md-10" align="right">
            <form method="POST" action="<%= response.encodeURL("BookController")%>"  name="addEdit">
                <input action="<%= response.encodeURL("BookController")%>" type="submit" value="Cancel" name="action" />
            </form>
        </div>
        <div class="col-md-6" align="center">

            <form method="POST" action="<%= response.encodeURL("BookController")%>"  name="addEdit">

                <table width="auto" border="1" cellspacing="0" cellpadding="4" >


                    <tr> 
                        <td align="left"><input type="text" value="${book.bookId}" name="bookId" hidden /></td>
                    </tr>         


                    <tr>
                        <td style="background-color: lightgray; color:black;" align="left">isbn </td>
                        <td align="left"><input type="text" value="${book.isbn}" name="isbn" required/></td>
                    </tr>


                    <tr>
                        <td style="background-color: lightgray; color:black;" align="left">title</td>
                        <td align="left"><input type="text" value="${book.tittle}" name="title" required /></td>

                    </tr>

                    <tr>
                        <td style="background-color: lightgray; color:black;" align="left">author Id</td>
                        <td align="left"><input type="text" value="${book.authorId.authorId}" name="authorId"  /></td>

                    </tr>

                </table>
                <br>
                <input type="submit" value="Save" name="action" align="center"/> 
            </form>
            <h3>${errMsg}</h3>

        </div>

      


        
       
    </body>
</html>
