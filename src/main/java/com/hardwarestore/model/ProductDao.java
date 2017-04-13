package com.hardwarestore.model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Radovan Tadic
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ProductDao {
    
    @Autowired
    SessionFactory sessionFactory;
    
    /**
     * 
     * @return A list of products
     */
    public List<Product> get(){
        Session session = sessionFactory.getCurrentSession();
        List<Product> result = session.createCriteria(Product.class).list();
        return result;
    }
    
    /**
     * 
     * @param categoryId Id of category
     * @return A list of products by selected category
     */
    public List<Product> findByCategory(int categoryId){
        Session session = sessionFactory.getCurrentSession();
         List<Product> result = session.getNamedQuery("Product.findByCategory").setInteger("category", categoryId).list();
         return result;
    }
    
    /**
     * 
     * @param productId Id of product
     * @return A product by productId
     */
    public Product findByProductId(int productId){
       return (Product)sessionFactory.getCurrentSession().get(Product.class, productId);
    }
    
    /**
     * 
     * @param page Represents one page
     * @return A list of max 3 products per page
     */
    public List<Product> findByPage(int page){
        int perpage = 3;
        Session session = sessionFactory.getCurrentSession();
         List<Product> result = session.createQuery("from Product").setFirstResult(page*perpage).setMaxResults(perpage).list();
         return result;
    }
    
    /**
     * This method updates product
     * @param product Object Product
     */
    public void update (Product product){
        sessionFactory.getCurrentSession().update(product);
    }
    
    /**
     * 
     * @return A rounded value of pages
     */
    public Long pages(){
        return ((Double)Math.ceil((Long)sessionFactory.getCurrentSession().createQuery("SELECT COUNT(id) FROM Product").uniqueResult()/3)).longValue();
    }
}