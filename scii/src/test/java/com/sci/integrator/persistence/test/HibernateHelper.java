/**
 * 
 */
package com.sci.integrator.persistence.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Abdiel
 *
 */
public class HibernateHelper
{

  // ********** properties **********
  
  private static SessionFactory sessionFactory;

  // ********** Constructors **********
  
  public HibernateHelper()
  {
    // TODO Auto-generated constructor stub
  }


  // ********** Helper Methods **********
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    HibernateHelper.sessionFactory = sessionFactory;
  } 
  
  public static Session currentSession() 
  {
    return sessionFactory.getCurrentSession();
  }
  
}
