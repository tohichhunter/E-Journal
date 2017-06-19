/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Entity;

import java.io.Serializable;

/**
 * <p>
 * Entity of abstract user</p>
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class User implements Serializable{

    /**
     * RDBMS identifier in table
     */
    private long id;

    /**
     * User's role in system
     */
    private String role;

    /**
     * User's first name
     */
    private String firstName;

    /**
     * User's last name
     */
    private String lastName;

    /**
     * User's login
     */
    private String login;

    /**
     * User's password
     */
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "role=" + role + ", login=" + login + ", password=" + password + '}';
    }
    

}
