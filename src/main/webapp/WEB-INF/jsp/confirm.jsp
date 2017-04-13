<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <base href="/hardwarestore/" />
        <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1" />
        <meta http-equiv="Content-Style-Type" content="text/css" />
        <link rel="stylesheet" href="resources/font-awesome-4.6.3/css/font-awesome.min.css"/>

        <title>
            Hardware Store
        </title>

        <link rel="stylesheet" href="resources/style.css" type="text/css" media="screen" />

    </head>

    <body>

        <div id="container">

            <!-- Start of Page Header -->

            <div id="page_header">

                <div id="page_heading">
                    <h1><span>Hardware Store</span></h1>
                    <h2><span>Your punchline here</span></h2>
                </div>

                <div id="page_headerlinks">

                    <ul>
                        <li><a href="/hardwarestore/"><i class="fa fa-home" aria-hidden="true"></i> Home</a></li>
                        <li><a href="cart"><i class="fa fa-shopping-cart" aria-hidden="true"></i> Shopping Cart</a></li>
                        <li><a href="http://www.freewebsitetemplates.com/"><i class="fa fa-info" aria-hidden="true"></i> Contact</a></li>
                    </ul>

                </div>

                <div class="clearthis">&nbsp;</div>

            </div>

            <!-- End of Page Header -->

            <!-- Start of Left Sidebar -->

            <div id="left_sidebar">


                <!-- Start of Categories Box -->

                <div id="categories">
                    <div id="categories_header">
                        <h2>Categories</h2>
                    </div>   
                    <ul>
                        <c:forEach items="${categories}" var="category">
                            <li><a href="./${category.categoryId}">${category.category}</a></li>
                            </c:forEach>
                    </ul>
                    <div class="clearthis">&nbsp;</div>
                </div>

                <!-- End of Categories Box -->


            </div>

            <!-- End of Left Sidebar -->


            <!-- Start of Main Content Area -->

            <div id="main_content">


                <div class="h_divider">&nbsp;</div>


                <!-- Start of Sub Item Descriptions -->

                <div class="sub_items">



                    <div class="sub_left">

                        Confirm order:<br />
                        <form method="post" action="confirm">
                            Enter your details: <br />
                            <textarea name="userdata"></textarea>
                            <input type="submit" value="Confirm" />
                        </form>

                        <div class="clearthis">&nbsp;</div>

                    </div>    

                    <div class="clearthis">&nbsp;</div>

                </div>

                <!-- End of Sub Item Descriptions -->


                <div class="h_divider">&nbsp;</div>

            </div>

            <!-- End of Main Content Area -->


            <div class="clearthis">&nbsp;</div>


            <!-- Start of Page Footer -->

            <div id="page_footer">

                <div id="product_brands">

                    <ul>
                        <li class="zalcom"><span>Zalcom</span></li>
                        <li class="digital"><span>Digital</span></li>
                        <li class="adept"><span>Adept</span></li>
                        <li class="simtel"><span>Simtel</span></li>
                    </ul>

                </div>

                <div id="powered_by">
                    Powered by <a href="http://www.freewebsitetemplates.com/">Free Website Templates</a>
                </div>

                <div class="clearthis">&nbsp;</div>
            </div>

            <!-- End of Page Footer -->

        </div>

    </body>
</html>
