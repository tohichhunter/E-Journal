/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nure.rumiantsev.SummaryTask4.Entity.User;
import ua.nure.rumiantsev.SummaryTask4.Utils.Connection;
import ua.nure.rumiantsev.SummaryTask4.Utils.ConnectionPoolManager;

/**
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Repository
public class UserDAO implements EnityDAO<User>{

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);
    @Override
    public User create(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User read(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User t) {
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
            ps = connection.prepareStatement("UPDATE authorities "
                    + "SET (login, password) "
                    + "= (?, ?) "
                    + "WHERE authorities.user_id = ?");
            ps.setString(1, t.getLogin());
            ps.setString(2, t.getPassword());
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
    public boolean delete(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Sets field role of certain student as blocked
     * @param id student id
     */
    public void block(long id){
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE authorities "
                    + "SET role = ? "
                    + "WHERE authorities.user_id = ?");
            ps.setString(1, "blocked");
            ps.setLong(2, id);
            ps.execute();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            LOGGER.error(e.getMessage(), e);
        } finally {
            connection.close();
        }
    }
    /**
     * Sets field role of certain student as student
     * @param id student id
     */
    public void unblock(long id){
        Connection connection = ConnectionPoolManager.getInstance().getConnection(false);
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE authorities "
                    + "SET role = ? "
                    + "WHERE authorities.user_id = ?");
            ps.setString(1, "student");
            ps.setLong(2, id);
            ps.execute();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            LOGGER.error(e.getMessage(), e);
        } finally {
            connection.close();
        }
    }
    /**
     * Reads all the users
     * @return collection of all users
     */
    public Collection<User> readAll(){
        Collection<User> users = new ArrayList<>();
                try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
                    PreparedStatement ps = connection.prepareStatement("SELECT users.id, users.first_name, users.last_name, authorities.role "
                            + "  FROM authorities "
                            + "  INNER JOIN users "
                            + "  ON authorities.user_id = users.id "
                            + "  WHERE authorities.role = ? OR "
                            + "  authorities.role = ?");
                    ps.setString(1, "student");
                    ps.setString(2, "blocked");
                    ps.execute();
                    ResultSet rs = ps.getResultSet();
                    while (rs.next()) {
                            User user = new User();
                            user.setId(rs.getLong("id"));
                            user.setRole(rs.getString("role"));
                            user.setFirstName(rs.getString("first_name"));
                            user.setLastName(rs.getString("last_name"));
                            users.add(user);
                        } 
                    } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(),ex);
        }
        return users;
    }
    
}
