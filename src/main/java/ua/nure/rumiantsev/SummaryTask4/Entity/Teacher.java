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
 * Entity of teacher</p>
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class Teacher extends User implements Serializable {

    private static final long serialVersionUID = -7596238537887063281L;

    /**
     * RDBMS identifier in table
     */
    private long id;

    /**
     * Teacher's first name
     */
    private String firstName;

    /**
     * Teacher's last name
     */
    private String lastName;

    /**
     * Collection of courses that current teacher leads
     */
    private Set<Course> courses;

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

    public boolean addCourses(Course course) {
        return this.courses.add(course);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", courses=" + courses + super.toString() + '}';
    }

   

}
