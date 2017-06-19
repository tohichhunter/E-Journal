/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.MVC;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *<p>Annotation for methods, that use to handle requests</p>
 * Annotate methods inside controller.
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestFocuse {

    /**
     * Value of handling request
     * @return value of request name
     */
    String value();
}
