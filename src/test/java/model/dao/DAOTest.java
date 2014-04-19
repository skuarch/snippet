package model.dao;

import junit.framework.TestCase;
import model.beans.TestBean;

/**
 *
 * @author skuarch
 */
public class DAOTest extends TestCase {
    
    public DAOTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSomeMethod() throws NoSuchFieldException {
        
        TestBean tb = new TestBean();
        tb.setName("some");
        DataAccessObject.create(tb);
        tb.setName("mocos");
        DataAccessObject.update(tb);
        
        //tb = DataAccessObject.get(2, tb);
        //assertEquals(tb.getName(), "some");
        //DataAccessObject.delete(tb);
        
        System.out.println(DataAccessObject.getList(new TestBean()));
        
        DataAccessObject.delete(tb);
        
    }
    
}
