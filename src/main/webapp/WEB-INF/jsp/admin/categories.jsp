<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Categories</title>
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <form action="updatecategory" method="post">
            <select onchange="if(this.value!=-1) window.location='./categories?id='+this.value" name="id">
                <option value="-1">Select category:</option>
                <c:forEach items="${categories}" var="category">
                    <option <c:if test="${category.categoryId==selectedCategory.categoryId}">selected</c:if> value="${category.categoryId}">${category.category}</option>
                </c:forEach>
            </select><br/>
            Category: <input type="text" name="category" value="${selectedCategory.category}" /><br />
            <input type="submit" name="update" value="Update"  />
        </form>
    </body>
</html>
