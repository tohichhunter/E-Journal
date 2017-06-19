/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nure.rumiantsev.SummaryTask4.Entity.Student;
import ua.nure.rumiantsev.SummaryTask4.Utils.Connection;
import ua.nure.rumiantsev.SummaryTask4.Utils.ConnectionPoolManager;

/**
 *<p>Data access object for {@link ua.nure.rumiantsev.SummaryTask4.Entity.Student Student}</p>
 * Works with DBMS, implements {@link ua.nure.rumiantsev.SummaryTask4.DAO.EntityDAO EntityDAO}
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Repository
public class StudentDAO implements EnityDAO<Student>{

    private static final Logger LOGGER = Logger.getLogger(StudentDAO.class);
    @Override
    public Student create(Student t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users "
                    + "(first_name, last_name) "
                    + "VALUES ( ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getFirstName());
            ps.setString(2, t.getLastName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong("id"));
            }
            ps = connection.prepareStatement("INSERT INTO authorities "
                    + "(login, password, role, user_id) "
                    + "VALUES (?, ?, ?, ?)");
            ps.setString(1, t.getLogin());
            ps.setString(2, t.getPassword());
            ps.setString(3, t.getRole());
            ps.setLong(4, t.getId());
            ps.execute();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            LOGGER.error(e.getMessage(), e);
        } finally {
            connection.close();
        }
        return t;
    }

    @Override
    public Student read(long id) {
        Student t = new Student();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users "
                    + "WHERE users.id = ?");
            ps.setLong(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                t.setId(rs.getLong("id"));
                t.setFirstName(rs.getString("first_name"));
                t.setLastName(rs.getString("last_name"));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return t;
    }

    @Override
    public boolean update(Student t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users "
                    + "SET (first_name, last_name) "
                    + "= (?, ?) "
                    + "WHERE users.id = ?");
            ps.setString(1, t.getFirstName());
            ps.setString(2, t.getLastName());
            ps.setLong(3, t.getId());
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
    public boolean delete(Student t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM users "
                    + "WHERE users.id = ? ");
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
     * Returns list of students, studying at certain course
     * @param id course id
     * @return collection of student
     */
    public Collection<Student> readByTeacherId(long id) {
        Set<Student> s = new HashSet<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users "
                    + "INNER JOIN marks "
                    + "ON marks.student_id = users.id "
                    + "INNER JOIN courses "
                    + "ON courses.id = marks.course_id "
                    + "WHERE courses.teacher_id = ?");
            ps.setLong(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Student t = new Student();
                t.setId(rs.getLong("id"));
                t.setFirstName(rs.getString("first_name"));
                t.setLastName(rs.getString("last_name"));s.add(t);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return s;
    }
    
    public Collection<Student> readByCourseId(long id) {
        Set<Student> s = new HashSet<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users "
                    + "INNER JOIN marks "
                    + "ON marks.student_id = users.id "
                    + "WHERE marks.course_id = ?");
            ps.setLong(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Student t = new Student();
                t.setId(rs.getLong("id"));
                t.setFirstName(rs.getString("first_name"));
                t.setLastName(rs.getString("last_name"));s.add(t);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return s;
    }
    
}
