/**
 * 
 */
package com.sci.integrator.persistence.test;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.persistence.IRouteDao;
import com.sci.integrator.persistence.hibernate.RouteDaoImpl;

/**
 * @author Abdiel
 *
 */
public class RouteDaoTest
{

  Session session;
  IRouteDao dao;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
    session = HibernateHelper.currentSession();
    dao = new RouteDaoImpl();
  }

  @After
  public void tearDown() throws Exception
  {
    if(session.isOpen())
    {
      session.close();
    }
  }

  /**
   * Test method for {@link com.sci.integrator.persistence.hibernate.RouteDaoImpl#save(com.sci.integrator.domain.core.Route)}.
   */
  @Test
  public void testSave()
  {
    Route route = createDummyRoute();
    SciiResult res = dao.save(route);
    long oid = res.getaffectedObjectOid();
    Route actualRoute = dao.getByOid(oid);
    assertEquals("Actual Route and DAO's returned Route must be equal", route, actualRoute);
  }

  /**
   * Test method for {@link com.sci.integrator.persistence.hibernate.RouteDaoImpl#update(com.sci.integrator.domain.core.Route)}.
   */
  @Test
  public void testUpdate()
  {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.sci.integrator.persistence.hibernate.RouteDaoImpl#getByOid(long)}.
   */
  @Test
  public void testGetByOid()
  {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link com.sci.integrator.persistence.hibernate.RouteDaoImpl#getAll()}.
   */
  @Test
  public void testGetAll()
  {
    fail("Not yet implemented");
  }

  
  private Route createDummyRoute()
  {
    Route r = new Route();
    
    r.setName("Dummy Route 1");
    r.setOrganizationId("123");
    r.setRoleId("456");
    r.setServerId("7890");
    
    return r;
  }


}
