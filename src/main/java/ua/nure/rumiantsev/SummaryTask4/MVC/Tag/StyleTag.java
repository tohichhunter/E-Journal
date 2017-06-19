/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.MVC.Tag;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import ua.nure.rumiantsev.SummaryTask4.Entity.Course;

/**
 * Tag for print collection of
 * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Course Course} from session
 * scope, if any found.
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class StyleTag extends TagSupport {

    private static final Logger LOGGER = Logger.getLogger(StyleTag.class);

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            
            out.println("<style>\n" +
"    .modal {\n" +
"        display: none; /* Hidden by default */\n" +
"        position: fixed; /* Stay in place */\n" +
"        z-index: 1; /* Sit on top */\n" +
"        left: 0;\n" +
"        top: 0;\n" +
"        width: 100%; /* Full width */\n" +
"        height: 100%; /* Full height */\n" +
"        overflow: auto; /* Enable scroll if needed */\n" +
"        background-color: rgb(0,0,0); /* Fallback color */\n" +
"        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */\n" +
"    }\n" +
"\n" +
"    /* Modal Content/Box */\n" +
"    .modal-content {\n" +
"        background-color: #fefefe;\n" +
"        margin: 15% auto; /* 15% from the top and centered */\n" +
"        padding: 20px;\n" +
"        border: 1px solid #888;\n" +
"        width: 80%; /* Could be more or less, depending on screen size */\n" +
"    }\n" +
"\n" +
"    /* The Close Button */\n" +
"    .close {\n" +
"        color: #aaa;\n" +
"        float: right;\n" +
"        font-size: 28px;\n" +
"        font-weight: bold;\n" +
"    }\n" +
"\n" +
"    .close:hover,\n" +
"    .close:focus {\n" +
"        color: black;\n" +
"        text-decoration: none;\n" +
"        cursor: pointer;\n" +
"    }\n" +
"</style>");
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return SKIP_BODY;
    }

}
