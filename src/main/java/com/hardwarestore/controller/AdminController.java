package com.hardwarestore.controller;

import com.hardwarestore.model.Category;
import com.hardwarestore.model.CategoryDao;
import com.hardwarestore.model.OrdersDao;
import com.hardwarestore.model.Product;
import com.hardwarestore.model.ProductDao;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class represents controller responsible for all activities that occur by admin on the admin page.
 * @author Radovan Tadic
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    OrdersDao ordersDao;

    /**
     * This method creates initial admin view
     * @return A view admin/index
     */
    @RequestMapping("/")
    public String index() {
        return "admin/index";
    }
    
     /**
     * This method selects chosen category
     * @param id Id of category
     * @param modelMap ModelMap
     * @return A view admin/categories
     */
    @RequestMapping("/categories")
    public String categories(@RequestParam(required = false) Integer id, ModelMap modelMap) {
        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);
        if (id != null) {
            Category selectedCategory = categoryDao.findByCategoryId(id);
            modelMap.addAttribute("selectedCategory", selectedCategory);
        }
        return "admin/categories";
    }
    
    /**
     * This method updates selected category 
     * @param id Id of category
     * @param category Category name
     * @param modelMap ModelMap
     * @return A view admin/categories
     */
    @RequestMapping("/updatecategory")
    public String updateCategory(
            @RequestParam Integer id,
            @RequestParam String category,
            ModelMap modelMap) {
        Category selectedCategory = categoryDao.findByCategoryId(id);
        selectedCategory.setCategory(category);
        categoryDao.update(selectedCategory);
        List<Category> categories = categoryDao.get();
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("selectedCategory", selectedCategory);
        return "admin/categories";
    }

    /**
     * This method shows list of all products per page
     * @param page Represents one page
     * @param modelMap ModelMap
     * @return A view admin/products
     */
    @RequestMapping("/products")
    public String products(@RequestParam(defaultValue = "1") Integer page, ModelMap modelMap) {
        List<Product> products = productDao.findByPage(page - 1);
        modelMap.addAttribute("products", products);
        modelMap.addAttribute("totalpages", productDao.pages());
        return "admin/products";
    }
    
    /**
     * This method creates view for updating products 
     * @param id Id of product
     * @param modelMap ModelMap
     * @return  A view admin/updateproduct
     */
    @RequestMapping("/updateproduct")
    public String updateProduct(@RequestParam Integer id, ModelMap modelMap) {
        Product product = productDao.findByProductId(id);
        modelMap.addAttribute("product", product);
        return "admin/updateproduct";
    }

    /**
     * This method updates product information
     * @param id Id of product
     * @param name Name of product
     * @param price Price of product
     * @param image Image of product
     * @param modelMap ModelMap
     * @param request HttpServletRequest
     * @return A view admin/updateproduct
     * @throws FileNotFoundException
     * @throws IOException 
     */
    @RequestMapping(value = "/updateproduct", method = RequestMethod.POST)
    public String updateProductPost(
            @RequestParam Integer id,
            @RequestParam String name,
            @RequestParam String price,
            @RequestParam MultipartFile image,
            ModelMap modelMap, HttpServletRequest request) throws FileNotFoundException, IOException {

        Product product = productDao.findByProductId(id);
        product.setName(name);
        if (image != null &&! image.isEmpty()) {
            String filepath = request.getServletContext().getRealPath("resources/images");
            FileOutputStream fos = new FileOutputStream(filepath + "/" + "resources/images" + image.getOriginalFilename());
            product.setImage(image.getOriginalFilename());
            fos.write(image.getBytes());
            fos.close();
        }
        product.setPrice(new BigDecimal(price));
        productDao.update(product);
        modelMap.addAttribute("product", product);
        return "admin/updateproduct";
    }
}