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
public class CategoryDao {
    
    @Autowired
    SessionFactory sessionFactory;
    
    /**
     * 
     * @return A list of categories
     */
    public List<Category> get(){
        Session session = sessionFactory.getCurrentSession();
        List<Category> result = session.createCriteria(Category.class).list();
        return result;
    }
     /**
      * 
      * @param categoryId Id of category
      * @return A category by categoryId
      */
    public Category findByCategoryId(int categoryId){
       return (Category)sessionFactory.getCurrentSession().get(Category.class, categoryId);
    }
    
    /**
     * This method updates category
     * @param cat Object Category
     */
    public void update (Category cat){
        sessionFactory.getCurrentSession().update(cat);
    }       

}
