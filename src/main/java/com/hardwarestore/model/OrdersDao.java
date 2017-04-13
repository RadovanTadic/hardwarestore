package com.hardwarestore.model;

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
public class OrdersDao {
    
    @Autowired
    SessionFactory sessionFactory;
    
    /**
     * This method saves order
     * @param order Object Orders
     */
    public void save(Orders order){
        Session session = sessionFactory.getCurrentSession();
        session.save(order);
    }

}
