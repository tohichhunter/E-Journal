/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.MVC;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import ua.nure.rumiantsev.SummaryTask4.Constant.ResponseFlag;

/**
 * <p>
 * Main servlet of application, represents dispatcher function</p>
 * MainServlet uses reflection mechanism to find appropriate methods for request
 * handling inside
 * {@link ua.nure.rumiantsev.SummaryTask4.MVC.Controller Controller}. To map
 * requests take advantage of
 * {@link ua.nure.rumiantsev.SummaryTask4.MVC.RequestFocuse RequestFocuse}
 * annotation
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@WebServlet("/")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = -4840003794523302045L;
    private static final Logger LOGGER = Logger.getLogger(MainServlet.class);
    private Controller controller;

    /**
     * Handles all the GET requests.
     *
     * @param req - HTTP request object
     * @param resp - HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    /**
     * Handles all the POST requests
     *
     * @param req - HTTP request object
     * @param resp - HTTP response object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doRequest(req, resp);
    }

    /**
     * Handles all kinds of requests, that supports by application. Invokes
     * single controller method for every request, and locates all the response
     * attributes in session scope.
     *
     * @param req - HTTP request object
     * @param resp - HTTP response object
     * @throws ServletException
     * @throws IOException
     * @throws IllegalArgumentException
     */
    private void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IllegalArgumentException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String[] requestParts = req.getRequestURL().toString().split("/");
        String requestName = requestParts[requestParts.length - 1];

        ModelAndView result = null;
        Method[] methods = Controller.class.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annot : annotations) {
                if (annot.annotationType() == RequestFocuse.class) {
                    RequestFocuse r = (RequestFocuse) annot;
                    if (r.value().equals(requestName)) {
                        try {
                            controller = Controller.getInstance();
                            LOGGER.debug(this.controller + "==>" + this);
                            result = (ModelAndView) method.invoke(controller, req);
                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            LOGGER.error(ex.getMessage(), ex);
                        }
                    }
                }
            }
        }
        if (result != null) {
            for (Entry<String, Object> e : result.getAttributes()) {
                req.getSession().setAttribute(e.getKey(), e.getValue());
            }
            if (result.getView().startsWith(ResponseFlag.JS.name())) {                
                resp.getWriter().write(result.getView().replace(ResponseFlag.JS.name(), ""));
                LOGGER.debug("PRINTED " + result.getView().replace(ResponseFlag.JS.name(), ""));
                resp.getWriter().flush();
                
            } else if (result.getView().startsWith(ResponseFlag.XML.name())) {
                result.getView();
                resp.getOutputStream().print(result.getView().replace(ResponseFlag.XML.name(), ""));
                resp.getOutputStream().flush();
            } else {
                LOGGER.debug("REDIRECTING TO " + result.getView());
                resp.sendRedirect(req.getContextPath() + "/" + result.getView());
            }

        } else {
            resp.sendError(500);
        }
    }
}
