/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Entity;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Entity of course journal</p>
 * Contains marks of student studying
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class Journal implements Serializable {

    private static final long serialVersionUID = 4418600996653163387L;

    /**
     * RDBMS identifier in table
     */
    private long id;

    /**
     * Map of students id and summary point
     */
    private Map<Long, Integer> points;

    /**
     * Collection of subscribed students
     */
    private Set<Student> students;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Long, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Long, Integer> points) {
        this.points = points;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Journal{" + "id=" + id + ", points=" + points + ", students=" + students + '}';
    }

        

}
