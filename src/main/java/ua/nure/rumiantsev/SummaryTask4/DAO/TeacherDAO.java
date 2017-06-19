/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.DAO;

import ua.nure.rumiantsev.SummaryTask4.Utils.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nure.rumiantsev.SummaryTask4.Entity.Course;
import ua.nure.rumiantsev.SummaryTask4.Entity.Teacher;
import ua.nure.rumiantsev.SummaryTask4.Utils.ConnectionPoolManager;

/**
 * <p>
 * Data access object for
 * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Teacher}</p>
 * Works with DBMS, implements
 * {@link ua.nure.rumiantsev.SummaryTask4.DAO.EntityDAO EntityDAO}
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Repository
public class TeacherDAO implements EnityDAO<Teacher> {

    private static final Logger LOGGER = Logger.getLogger(TeacherDAO.class);

    @Override
    public Teacher create(Teacher t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users "
                    + "(first_name, last_name) "
                    + "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getFirstName());
            ps.setString(2, t.getLastName());
            int raws = ps.executeUpdate();
            if(raws < 1) {
                throw new SQLException("No raws affected");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong("id"));
            }
            LOGGER.debug(t);
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
    public Teacher read(long id) {

        Teacher t = new Teacher();
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
                t.setCourses((Set) new CourseDAO().readByTeacherId(id));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return t;
    }

    @Override
    public boolean update(Teacher t) {
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
            System.out.println(connection);
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
    public boolean delete(Teacher t) {
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
     * Returns list of all teachers
     * @return collection of teacher
     */
    public Collection<Teacher> readAll(){
        Collection<Teacher> result = new ArrayList<>();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT users.id, users.first_name, users.last_name "
                    + "FROM users "
                    + "INNER JOIN authorities "
                    + "ON users.id = authorities.user_id "
                    + "WHERE authorities.role = ?");
            ps.setString(1, "teacher");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Teacher c = new Teacher();
                c.setId(rs.getLong("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setCourses(new HashSet<Course>(new CourseDAO().readByTeacherId(c.getId())) );
                result.add(c);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

}
