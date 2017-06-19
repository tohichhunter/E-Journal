/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4;

import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import ua.nure.rumiantsev.SummaryTask4.BusinessLogic.AuthorisationManager;
import ua.nure.rumiantsev.SummaryTask4.BusinessLogic.BLO;
import ua.nure.rumiantsev.SummaryTask4.MVC.Controller;
import ua.nure.rumiantsev.SummaryTask4.MVC.MainServlet;
import ua.nure.rumiantsev.SummaryTask4.Utils.AsyncThreadExecutor;
import ua.nure.rumiantsev.SummaryTask4.Utils.CourseComparatorFactory;

/**
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */

public class AppTest {
    
    @Test
    public void testComparatorFactory1(){
        Assert.assertTrue(CourseComparatorFactory.getComparator("az") instanceof Comparator);
    }
    @Test
    public void testComparatorFactory2(){
        try{
        CourseComparatorFactory.getComparator("wrong");
        Assert.fail();
        }catch(Exception e){
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    public void testSpringContext() throws InterruptedException, ExecutionException, ServletException, IOException{
        Controller controller = Controller.getInstance();
        Assert.assertNotNull(controller);
        
    }
    
}
