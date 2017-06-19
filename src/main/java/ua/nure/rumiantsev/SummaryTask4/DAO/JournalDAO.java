/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nure.rumiantsev.SummaryTask4.Entity.Journal;
import ua.nure.rumiantsev.SummaryTask4.Entity.Student;
import ua.nure.rumiantsev.SummaryTask4.Utils.Connection;
import ua.nure.rumiantsev.SummaryTask4.Utils.ConnectionPoolManager;

/**
 *<p>Data access object for {@link ua.nure.rumiantsev.SummaryTask4.Entity.Journal Journal}</p>
 * Works with DBMS, implements {@link ua.nure.rumiantsev.SummaryTask4.DAO.EntityDAO EntityDAO}
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Repository
public class JournalDAO implements EnityDAO<Journal>{

    private static final Logger LOGGER = Logger.getLogger(JournalDAO.class);
    @Override
    public Journal create(Journal t) {
         Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO marks "
                    + "(course_id, student_id) "
                    + "VALUES (?, ?)");
            ps.setLong(1, t.getId());
            PreparedStatement ps2 = connection.prepareStatement("UPDATE courses "
                    + "SET capacity = capacity + 1 "
                    + "WHERE id = ?");
            for (Student s : t.getStudents()) {
                ps.setLong(2, s.getId());
                ps.execute();
                ps2.setLong(1, t.getId());
                ps2.execute();
            }
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
    public Journal read(long id) {
         Journal t = new Journal();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM marks "
                    + "WHERE marks.course_id = ?");
            ps.setLong(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            t.setId(id);
            Map<Long,Integer> m = new HashMap<>();
            while (rs.next()) {                
                m.put(rs.getLong("student_id"), rs.getInt("mark"));
            }
            t.setPoints(m);
            t.setStudents(new HashSet<Student>(new StudentDAO().readByCourseId(id)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return t;
    }

    @Override
    public boolean update(Journal t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE marks "
                    + "SET mark = ? "
                    + "WHERE marks.course_id = ? AND marks.student_id = ?");
            for(Student s: t.getStudents()){
                ps.setInt(1, t.getPoints().get(s.getId()));
                ps.setLong(2, t.getId());
                ps.setLong(3, s.getId());
                ps.executeUpdate();
            }
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
    public boolean delete(Journal t) {
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        boolean success = false;
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM marks "
                    + "WHERE marks.course_id = ? ");
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
    
    public Journal readAll(long id) {
         Journal t = new Journal();
        try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM marks ");
            ps.execute();
            ResultSet rs = ps.getResultSet();
            t.setId(id);
            Map<Long,Integer> m = new HashMap<>();
            while (rs.next()) {                
                m.put(rs.getLong("student_id"), rs.getInt("mark"));
            }
            t.setPoints(m);
            t.setStudents(new HashSet<Student>(new StudentDAO().readByCourseId(id)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return t;
    }
    
}
