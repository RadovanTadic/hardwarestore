<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <table border="1">
            <tr><td>id</td><td>name</td><td>price</td><td>photo</td><td></td></tr>
            <c:forEach items="${products}" var="product">
                <tr><td>${product.productId}</td><td>${product.name}</td><td>${product.price}</td><td><img src="../resources/images/${product.image}" /></td><td><a href="updateproduct?id=${product.productId}">edit</a></td></tr>
            </c:forEach>
        </table>
        <c:forEach begin="1" end="${totalpages}" varStatus="counter">
            <a href="products?page=${counter.count}">${counter.count}</a>
        </c:forEach>
    </body>
</html>
