<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update product</title>
    </head>
    <body>
        <form method="post" action="updateproduct" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${product.productId}" />
            Name: <input type="text" name="name" value="${product.name}"/><br/>
            Price: <input type="text" name="price" value="${product.price}"/><br/>
            <input type="submit" name="update" value="Update product" />
            <input type="file" name="image" />
        </form>
    </body>
</html>
