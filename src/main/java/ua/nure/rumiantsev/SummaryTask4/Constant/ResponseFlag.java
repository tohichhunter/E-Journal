/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Constant;

/**
 * <p>Enumeration of special prefixes of HTTP response</p>
 * Points at type of content, therefore it can have different type of out
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public enum ResponseFlag {
    /**
     * JavaScript response, string or JSON
     */
    JS, 
    /**
     * XML response
     */
    XML;
    
}
