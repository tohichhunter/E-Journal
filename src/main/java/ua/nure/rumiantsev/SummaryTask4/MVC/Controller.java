/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.MVC;

import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.nure.rumiantsev.SummaryTask4.BusinessLogic.AuthorisationManager;
import ua.nure.rumiantsev.SummaryTask4.BusinessLogic.BLO;
import ua.nure.rumiantsev.SummaryTask4.Constant.ResponseFlag;
import ua.nure.rumiantsev.SummaryTask4.Entity.User;

/**
 * <p>
 * Controller for requests</p>
 * Implements singleton pattern and appears part of MVC pattern. Contains all
 * methods that supposed to handle requests and delegate it's running to special
 * service.
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@org.springframework.stereotype.Controller
public class Controller {
    
    private static final Logger LOGGER = Logger.getLogger(Controller.class);
    private AuthorisationManager authorisationManager;
    private BLO blo;
    
    public Controller() {
    }
    
    public static Controller getInstance() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        context.refresh();
        return context.getBean(Controller.class);
    }
    
    @Autowired
    public void setAuthorisationManager(AuthorisationManager authorisationManager) {
        this.authorisationManager = authorisationManager;
        LOGGER.debug(this.authorisationManager + "==>" + this);
    }
    
    @Autowired
    public void setBlo(BLO blo) {
        this.blo = blo;
        LOGGER.debug(this.blo + "==>" + this);
    }

    /**
     * Handles authentication request redirects to users cabinet
     *
     * @param request HTTP request
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("auth")
    public ModelAndView auth(HttpServletRequest request) {
        
        System.out.println(this.authorisationManager + "<==" + this);
        try {
            this.authorisationManager.authenticate(request);
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return new ModelAndView("cabinet.jsp");
    }

    /**
     * Handles log out request invalidates session redirects to index page
     *
     * @param request HTTP request
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("remove")
    public ModelAndView remove(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("index.jsp");
    }

    /**
     * Handles getCourses request returns list of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Course Course} in certain
     * order
     *
     * @param request HTTP request
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("getCourses")
    public ModelAndView getCourses(HttpServletRequest request) {
        return new ModelAndView(ResponseFlag.JS.name() + blo.getCourses(request.getParameter("sort")));
    }

    /**
     * Handles getMyCourses request returns specific list in JSON in certain
     * order
     *
     * @param request HTTP request
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("getMyCourses")
    public ModelAndView getMyCourses(HttpServletRequest request) {
        LOGGER.debug(request);
        return new ModelAndView(ResponseFlag.JS.name() + blo.getCourses(request.getParameter("sort"),
                (String) request.getSession().getAttribute("role"),
                (long) request.getSession().getAttribute("userId")));
    }

    /**
     * Handles setCourses request
     *
     * @param request HTTP JSON with modified courses
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("setCourses")
    public ModelAndView setCourses(HttpServletRequest request) {
        blo.setCourses(request.getParameter("crs"));
        return new ModelAndView(ResponseFlag.JS.name() + "{name : OK}");
    }

    /**
     * Handles newCourse request
     *
     * @param request HTTP JSON with new instance of course
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("newCourse")
    public ModelAndView newCourse(HttpServletRequest request) {
        blo.addCourse(request.getParameter("crs"));
        return new ModelAndView(ResponseFlag.JS.name() + "{name : OK}");
    }

    /**
     * Handles getTeachers request returns list of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Teacher Teacher}
     *
     * @param request HTTP request
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("getTeachers")
    public ModelAndView getTeachers(HttpServletRequest request) {
        return new ModelAndView(ResponseFlag.JS.name() + blo.getTeachers());
    }

    /**
     * Handles setTeacher request
     *
     * @param request HTTP JSON with list of modified teachers
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("setTeachers")
    public ModelAndView setTeachers(HttpServletRequest request) {
        blo.setTeachers(request.getParameter("tcs"));
        return new ModelAndView(ResponseFlag.JS.name() + "{name : OK}");
    }

    /**
     * Handles newTeacher request for registration new teacher
     *
     * @param request HTTP JSON with new instance of teacher
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("newTeacher")
    public ModelAndView newTeacher(HttpServletRequest request) {
        blo.addTeacher(request.getParameter("tcs"));
        return new ModelAndView(ResponseFlag.JS.name() + "{name : OK}");
    }

    /**
     * Handles registrate request for registration new student
     *
     * @param request HTTP JSON with new instance of student
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("registrate")
    public ModelAndView registrate(HttpServletRequest request) {
        blo.addStudent(request);
        return new ModelAndView("login.jsp");
    }

    /**
     * Handles subscribe request for subscribing student to course
     *
     * @param request HTTP JSON with course id and student id
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("subscribe")
    public ModelAndView subscribe(HttpServletRequest request) {
        blo.subscribeStudent(Long.valueOf(request.getParameter("course")),
                Long.valueOf(request.getParameter("student")));
        return new ModelAndView(ResponseFlag.JS.name() + "{result : OK}");
    }

    /**
     * Handles getJournal request to show student's marks
     *
     * @param request HTTP JSON with course id
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("getJournal")
    public ModelAndView getJournal(HttpServletRequest request) {
        return new ModelAndView("journal.jsp").addAttriute(
                "journal", blo.getJournal(
                        Long.valueOf(request.getParameter("id"))));
    }

    /**
     * Handles setJournal request to update marks
     *
     * @param request HTTP JSON with journal
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("setJournal")
    public ModelAndView setJournal(HttpServletRequest request) {
        return new ModelAndView("journal.jsp").addAttriute("journal", blo.setJournal(request));
    }
    
    /**
     * Handles setJournal request to update marks
     *
     * @param request HTTP JSON with journal
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("updateCreds")
    public ModelAndView updateCreds(HttpServletRequest request) {
        User user = blo.updateUser(request);
        return new ModelAndView("cabinet.jsp")
                .addAttriute("user", user)
                .addAttriute("firstName", user.getFirstName());
    }
    
    /**
     * Handles translate request to change language
     *
     * @param request HTTP with locale
     * @return
     * {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("translate")
    public ModelAndView translate(HttpServletRequest request) {
        blo.setLanguage(request);
        return new ModelAndView("cabinet.jsp");
    }
    
    /**
     * Handles block request to block some student
     * @param request HTTP request with student id
     * @return {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("block")
    public ModelAndView block(HttpServletRequest request) {
        blo.blockStudent(Long.valueOf(request.getParameter("id")));
        return new ModelAndView("students.jsp").addAttriute("allStudents", blo.getStudents());
    }
    
    /**
     * Handles unblock request to unblock some student
     * @param request HTTP request with student id
     * @return {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("unblock")
    public ModelAndView unblock(HttpServletRequest request) {
        blo.unblockStudent(Long.valueOf(request.getParameter("id")));
        return new ModelAndView("students.jsp").addAttriute("allStudents", blo.getStudents());
    }
    
    /**
     * Handles unblock request to unblock some student
     * @param request HTTP request with student id
     * @return {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("getStudents")
    public ModelAndView getStudents(HttpServletRequest request) {        
        return new ModelAndView("students.jsp").addAttriute("allStudents", blo.getStudents());
    }
    
     /**
     * Handles getMyStudents request to get students of some teacher
     * @param request HTTP request with student id
     * @return {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("getMyStudents")
    public ModelAndView getMyStudents(HttpServletRequest request) {        
        return new ModelAndView("myStudents.jsp").addAttriute(
                "myStudents",blo.getTeachersStudents((long)request.getSession().getAttribute("userId")));
    }
    
    /**
     * Handles getMyStudents request to get students of some teacher
     * @param request HTTP request with student id
     * @return {@link ua.nure.rumiantsev.SummaryTask4.MVC.ModelAndView ModelAndView}
     */
    @RequestFocuse("removeCourse")
    public ModelAndView removeCourse(HttpServletRequest request) {
        blo.removeCourse((Long.valueOf(request.getParameter("toRemove"))));        
        return new ModelAndView("myStudents.jsp");
    }
    
}
