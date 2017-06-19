/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.MVC;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *<p>Transport object for response attributes</p>
 * Uses to transfer view information and data from controller
 * to main servlet View info is strictly required, so has to be set via 
 * constructor. In case of specifying response, like JSON or XML format
 * necessary to admit it, using one of 
 * {@link ua.nure.rumiantsev.SummaryTask4.Constant.ResponseFlag ResponseFlag}
 *  prefixes. 
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class ModelAndView {

    private final String view;
    private final Map<String, Object> attributes;

    public ModelAndView(String view) {
        this.view = view;
        attributes = new HashMap<>();
    }

    public String getView() {
        return view;
    }

    /**
     * Adds new object to attributes, returns reference of current instance.
     * @param key String key
     * @param value Object value
     * @return - {@code this}
     */
    public ModelAndView addAttriute(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    public Object getAttribute(String key) {
        return attributes.get(key); 
    }

    public Set<Entry<String, Object>> getAttributes() {
        return attributes.entrySet();
    }
}
