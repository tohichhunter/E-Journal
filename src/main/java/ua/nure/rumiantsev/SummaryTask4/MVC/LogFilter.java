/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.MVC;

import java.sql.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@WebFilter("/LogFilter")
public class LogFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig config)
            throws ServletException {
        this.context = config.getServletContext();
        String testParam = config.getInitParameter("driverClassName");
        this.context.log("Database provided by: " + testParam);
    }

    @Override
    public void doFilter(ServletRequest request,  ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {

        String ipAddress = request.getRemoteAddr();
        this.context.log("IP " + ipAddress + ", Time "
                + new Date(System.currentTimeMillis()).toString());
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
