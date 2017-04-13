package com.hardwarestore.controller;

import com.hardwarestore.model.Category;
import com.hardwarestore.model.CategoryDao;
import com.hardwarestore.model.Orders;
import com.hardwarestore.model.OrdersDao;
import com.hardwarestore.model.Product;
import com.hardwarestore.model.ProductDao;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class represents controller responsible for all activities that occur caused by user on main page.
 * @author Radovan Tadic
 */
@Controller
public class SiteController {
    
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;
    
    @Autowired
    OrdersDao ordersDao;

    /**
     * This method returns a list of products by default category
     * @param modelMap ModelMap
     * @return A method byCategory with default value 
     */
    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);
        return byCategory(2, modelMap);
    }
    
    /**
     * This method returns products by selected category
     * @param categoryId Id of category
     * @param modelMap ModelMap
     * @return A view index
     */
    @RequestMapping("/{categoryId}")
    public String byCategory(@PathVariable int categoryId, ModelMap modelMap) {
        List<Category> categories = categoryDao.get();
        List<Product> products = productDao.findByCategory(categoryId);
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("products", products);
        return "index";
    }
    
    /**
     * This method adds products to cart by product id
     * @param productId Id of product
     * @param modelMap ModelMap
     * @return A view tocart
     */
    @RequestMapping("/tocart/{productId}")
    public String toCart(@PathVariable int productId, ModelMap modelMap) {
        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);
        Product product = productDao.findByProductId(productId);
        modelMap.addAttribute("product", product);
        return "tocart";
    }
    
    /**
     * This method creates cart 
     * @param request HttpServletRequest
     * @param productId Id of product
     * @param quantity Quantity of products
     * @param modelMap ModelMap
     * @return A view addtocart
     */
    @RequestMapping("/addtocart")
    public String addToCart(HttpServletRequest request, @RequestParam(required = true) Integer productId, @RequestParam(required = true) Integer quantity, ModelMap modelMap) {
        HttpSession session = request.getSession();
        HashMap<Integer, Product> cart;
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashMap<Integer, Product>());
        }
        cart = (HashMap<Integer, Product>) session.getAttribute("cart");
        if (!cart.containsKey(productId)) {
            Product product = productDao.findByProductId(productId);
            product.quantity = quantity;
            cart.put(productId, product);
        } else {
            Product productFromCart = cart.get(productId);
            productFromCart.quantity += quantity;
        }

        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);
        return "addtocart";
    }
    
     /**
     * This method represents cart and shows list of all products added to cart
     * @param request HttpServletRequest
     * @param modelMap ModelMap
     * @return A view cart
     */
    @RequestMapping("/cart")
    public String cart(HttpServletRequest request, ModelMap modelMap) {
        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);

        List<Product> products = new ArrayList<>();
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") != null) {
            HashMap<Integer, Product> sessionProducts = (HashMap<Integer, Product>) session.getAttribute("cart");
            for (Map.Entry<Integer, Product> p : sessionProducts.entrySet()) {
                products.add(p.getValue());
            }
        }
        modelMap.addAttribute("products", products);
        return "cart";
    }
    
     /**
     * This method removes products from cart
     * @param productId Id of product
     * @param request HttpServletRequest
     * @param modelMap ModelMap
     * @return A view remove
     */
    @RequestMapping("/remove")
    public String remove(@RequestParam(required = true) int productId, HttpServletRequest request, ModelMap modelMap) {
        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);
        HttpSession session = request.getSession();
        if (session.getAttribute("cart") != null) {
            HashMap<Integer, Product> products = (HashMap<Integer, Product>) session.getAttribute("cart");
            if (products.containsKey(productId)) {
                products.remove(productId);
            }
        }

        return "remove";
    } 

    /**
     * This method confirms order and creates json string with details of order. 
     * @param userdata Data from user
     * @param request HttpServletRequest
     * @param modelMap ModelMap
     * @return A view confirm
     */
    @RequestMapping("/confirm")
    public String confirm(@RequestParam(required = false) String userdata ,HttpServletRequest request, ModelMap modelMap) {
        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);
        
        if (userdata == null) {
            
        } else {
            StringBuilder sb = new StringBuilder();
            HttpSession session = request.getSession();
            HashMap<Integer, Product> sessionProducts = (HashMap<Integer, Product>) session.getAttribute("cart");
            sb.append("[");
            for (Map.Entry<Integer, Product> p : sessionProducts.entrySet()) {
                sb.append("{\"id\":");
                sb.append(p.getValue().getProductId());
                sb.append(",\"q\":");
                sb.append(p.getValue().getQuantity());
                sb.append("},");
            }
            String substr = sb.substring(0, sb.length()-1);
            substr += "]";
            
            Orders order = new Orders();
            order.setOrdertime(new Date(new java.util.Date().getTime()));
            order.setProducts(substr);
            order.setUserdata(userdata);
            ordersDao.save(order);
            
            session.removeAttribute("cart");
            
            return "confirmsuccess";
        }
        return "confirm";
    }
}