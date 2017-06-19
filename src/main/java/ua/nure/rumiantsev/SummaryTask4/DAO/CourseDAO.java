/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nure.rumiantsev.SummaryTask4.Entity.Course;
import ua.nure.rumiantsev.SummaryTask4.Utils.Connection;
import ua.nure.rumiantsev.SummaryTask4.Utils.ConnectionPoolManager;

/**
 * <p>Data access object for {@link ua.nure.rumiantsev.SummaryTask4.Entity.Course Course}</p>
 * Works with DBMS, implements {@link ua.nure.rumiantsev.SummaryTask4.DAO.EntityDAO EntityDAO}
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Repository
public class CourseDAO implements EnityDAO<Course>{

    private static final Logger LOGGER = Logger.getLogger(CourseDAO.class);
    @Override
    public Course create(Course t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        try{        
        PreparedStatement ps = connection.prepareStatement("INSERT INTO courses "
                + "(name, capacity, theme, teacher_id, begining, ending) "
                + "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, t.getName());
        ps.setInt(2, t.getCapacity());
        ps.setString(3, t.getTheme());
        ps.setLong(4, t.getTeacherId());
        ps.setDate(5, t.getBeginning());
        ps.setDate(6, t.getEnding());
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()) {
            t.setId(rs.getLong("id"));
        }        
        connection.commit();
        } catch(Exception e){
            connection.rollback();
            LOGGER.error(e.getMessage(),e);
        }finally{
            connection.close();
        }
        return t;
    }

    @Override
    public Course read(long id) {
        Course c = new Course();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM courses "
                    + "WHERE courses.id = ?");
            ps.setLong(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setTheme(rs.getString("theme"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setBeginning(rs.getDate("begining"));
                c.setEnding(rs.getDate("ending"));
                c.setCapacity(rs.getInt("capacity"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return c;
    }

    @Override
    public boolean update(Course t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE courses "
                    + "SET (name, theme, teacher_id, begining, ending, capacity) "
                    + "= (?, ?, ?, ?, ?, ?) "
                    + "WHERE courses.id = ?");
            ps.setString(1, t.getName());
            ps.setString(2, t.getTheme());
            ps.setLong(3, t.getTeacherId());
            ps.setDate(4, t.getBeginning());
            ps.setDate(5, t.getEnding());
            ps.setInt(6, t.getCapacity());
            ps.setLong(7, t.getId());
            ps.execute();
            connection.commit();
            success = true;
        } catch (Exception e) {
            connection.rollback();
            success = false;
            LOGGER.error(e.getMessage(), e);
        } finally {
            connection.close();
            return success;
        }
    }

    @Override
    public boolean delete(Course t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM courses "
                    + "WHERE courses.id = ? ");
            ps.setLong(1, t.getId());
            ps.execute();
            connection.commit();
            success = true;
        } catch (Exception e) {
            connection.rollback();
            success = false;
            LOGGER.error(e.getMessage(), e);
        } finally {
            connection.close();
            return success;
        }
    }
    /**
     * Returns list of all courses
     * @return collection of course
     */
    public Collection<Course> readAll(){
        Collection<Course> result = new ArrayList<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM courses ");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setTheme(rs.getString("theme"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setBeginning(rs.getDate("begining"));
                c.setEnding(rs.getDate("ending"));
                c.setCapacity(rs.getInt("capacity"));
                result.add(c);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * Returns list of courses, leaded by certain teacher
     * @param id teacher's id
     * @return collection of course
     */
     public Collection<Course> readByTeacherId(long id) {
        Collection<Course> result = new ArrayList<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM courses "
                    + "WHERE courses.teacher_id = ?");
            ps.setLong(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setTheme(rs.getString("theme"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setBeginning(rs.getDate("begining"));
                c.setEnding(rs.getDate("ending"));
                c.setCapacity(rs.getInt("capacity"));
                result.add(c);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
     /**
     * Returns list of courses in progress
     * @param id student id
     * @return collection of course
     */
    public Collection<Course> readByStudentIdInProgress(long id) {
        Collection<Course> result = new ArrayList<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM courses "
                        + "INNER JOIN marks "
                        + "ON marks.course_id = courses.id "
                        + "WHERE marks.student_id = ? AND "
                    + "courses.begining < ? AND courses.ending > ?");
            Date now = new Date(System.currentTimeMillis());
            ps.setLong(1, id);
            ps.setDate(2, now);
            ps.setDate(3, now);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setTheme(rs.getString("theme"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setBeginning(rs.getDate("begining"));
                c.setEnding(rs.getDate("ending"));
                c.setCapacity(rs.getInt("capacity"));
                ps = connection.prepareStatement("SELECT mark FROM marks WHERE student_id = ? AND course_id = ?");
                ps.setLong(1, id);
                ps.setLong(2, c.getId());
                ps.execute();
                ResultSet mrs = ps.getResultSet();
                if(mrs.next()){
                    c.setId(mrs.getInt("mark"));
                }
                result.add(c);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
    /**
     * Returns list of oncoming courses
     * @param id student id
     * @return collection of course
     */
    public Collection<Course> readByStudentIdOncoming(long id) {
        Collection<Course> result = new ArrayList<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM courses "
                        + "INNER JOIN marks "
                        + "ON marks.course_id = courses.id "
                        + "WHERE marks.student_id = ? AND "
                    + "courses.begining > ?");
            Date now = new Date(System.currentTimeMillis());
            ps.setLong(1, id);
            ps.setDate(2, now);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setTheme(rs.getString("theme"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setBeginning(rs.getDate("begining"));
                c.setEnding(rs.getDate("ending"));
                c.setCapacity(rs.getInt("capacity"));
                ps = connection.prepareStatement("SELECT mark FROM marks WHERE student_id = ? AND course_id = ?");
                ps.setLong(1, id);
                ps.setLong(2, c.getId());
                ps.execute();
                ResultSet mrs = ps.getResultSet();
                if(mrs.next()){
                    c.setId(mrs.getInt("mark"));
                }
                result.add(c);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
    /**
     * Little dirty hack down here: mark is storing in course id property.
     * Returns list of finished courses with marks
     * @param id student id
     * @return collection of course
     */
    public Collection<Course> readByStudentIdFinished(long id) {
        Collection<Course> result = new ArrayList<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM courses "
                        + "INNER JOIN marks "
                        + "ON marks.course_id = courses.id "
                        + "WHERE marks.student_id = ? AND "
                    + "courses.ending < ?");
            Date now = new Date(System.currentTimeMillis());
            ps.setLong(1, id);
            ps.setDate(2, now);
            ps.execute();
            
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setTheme(rs.getString("theme"));
                c.setTeacherId(rs.getLong("teacher_id"));
                c.setBeginning(rs.getDate("begining"));
                c.setEnding(rs.getDate("ending"));
                c.setCapacity(rs.getInt("capacity"));
                ps = connection.prepareStatement("SELECT mark FROM marks WHERE student_id = ? AND course_id = ?");
                ps.setLong(1, id);
                ps.setLong(2, c.getId());
                ps.execute();
                ResultSet mrs = ps.getResultSet();
                if(mrs.next()){
                    c.setId(mrs.getInt("mark"));
                }
                result.add(c);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
    
    /**
     * Deletes certain course by it's ID
     * @param id course ID
     * @return true if success or false otherwise
     */
    public boolean delete(long id) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM courses "
                    + "WHERE courses.id = ? ");
            ps.setLong(1, id);
            ps.execute();
            connection.commit();
            success = true;
        } catch (Exception e) {
            connection.rollback();
            success = false;
            LOGGER.error(e.getMessage(), e);
        } finally {
            connection.close();
            return success;
        }
    }
    
}