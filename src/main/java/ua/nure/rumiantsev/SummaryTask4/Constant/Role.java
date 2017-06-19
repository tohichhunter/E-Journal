/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Constant;

/**
 * Roles of application users
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public enum Role {
    TEACHER("teacher"), STUDENT("student"), ADMIN("admin"), BLOCKED("blocked");

    private String value;
    private Role(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }
    
}
