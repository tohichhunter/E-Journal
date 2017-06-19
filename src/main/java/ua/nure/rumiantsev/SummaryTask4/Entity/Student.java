/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Entity;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * Entity of student</p>
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class Student extends User implements Serializable {

    private static final long serialVersionUID = -5425327590154500292L;

    /**
     * RDBMS identifier in table
     */
    private long id;

    /**
     * First name of student
     */
    private String firstName;

    /**
     * Last name of student
     */
    private String lastName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + '}';
    }

}
