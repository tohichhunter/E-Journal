/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Entity;

import java.io.Serializable;
import java.time.DateTimeException;
import java.sql.Date;

/**
 * <p>
 * Entity of learning course</p>
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class Course implements Serializable {

    private static final long serialVersionUID = -7052827516087027040L;

    /**
     * RDBMS table identifier
     */
    private long id;
    
    /**
     * Name of course
     */
    private String name;

    /**
     * Amount of students, subscribed for this course
     */
    private int capacity;
    
    /**
     * Theme of course
     */
    private String theme;
    
    /**
     * RDBMS identifier of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Teacher} who teaches this
     * course
     */
    private long teacherId;

    /**
     * Date of beginning of lessons
     */
    private Date beginning;
    
    /**
     * Date of ending of lessons
     */
    private Date ending;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTerm() {
        return (ending == null || beginning == null)
                ? 0
                : (ending.getTime() - beginning.getTime());

    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public Date getBeginning() {
        return beginning;
    }

    public void setBeginning(Date beginning) {
        if (ending != null && beginning.before(ending)) {
            this.beginning = beginning;
        } else if (ending == null) {
            this.beginning = beginning;
        } else {
            throw new DateTimeException("Begining date must be sooner than ending");
        }
    }

    public Date getEnding() {
        return ending;
    }

    public void setEnding(Date ending) {
        if (beginning != null && ending.after(beginning)) {
            this.ending = ending;
        } else if (beginning == null) {
            this.ending = ending;
        } else {
            throw new DateTimeException("Ending date must be later than begining");
        }
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", theme=" + theme + ", teacheId=" + teacherId + '}';
    }

}
