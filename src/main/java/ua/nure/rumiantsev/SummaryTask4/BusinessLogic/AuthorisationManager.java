/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.BusinessLogic;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.rumiantsev.SummaryTask4.Constant.Role;
import ua.nure.rumiantsev.SummaryTask4.Entity.User;
import ua.nure.rumiantsev.SummaryTask4.Utils.AsyncThreadExecutor;
import ua.nure.rumiantsev.SummaryTask4.Utils.Connection;
import ua.nure.rumiantsev.SummaryTask4.Utils.ConnectionPoolManager;

/**
 * <p>
 * Manager of user's authentication and authorisation</p>
 * Authentication and authorisation aren't parts of business logic so that
 * functionality was replaced to this class.
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Service
public class AuthorisationManager {

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger LOGGER = Logger.getLogger(AuthorisationManager.class);

    /**
     * Gets entered login and password from request and passes users role and
     * first name to session scope
     *
     * @param request
     */
    public void authenticate(HttpServletRequest request) throws IllegalAccessException {

        User user = authenticate(request.getParameter("login"), request.getParameter("password"));
        if (user == null) {
            return;
        }
        if (user.getRole().equals(Role.BLOCKED.getValue())) {
            throw new IllegalAccessException("User is blocked, access denied");
        }
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("role", user.getRole());
        request.getSession().setAttribute("firstName", user.getFirstName());
        request.getSession().setAttribute("userId", user.getId());

        ResourceBundle resourceBundle = (ResourceBundle) request.getSession().getAttribute("resourceBundle");
        if (resourceBundle == null) {
            Locale.setDefault(new Locale("en"));
            resourceBundle = ResourceBundle.getBundle("resources", Locale.getDefault());
            request.getSession().setAttribute("resourceBundle", resourceBundle);

        }
    }

    /**
     * Accesses DB and gets and compares credentials with presented in DB. If
     * they math, then returns
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.User User} object
     *
     * @param login - user's login
     * @param password - user's password
     * @return instance of User or null, if no any was found
     */
    private User authenticate(final String login, final String password) {

        AsyncThreadExecutor<User> executor = new AsyncThreadExecutor<User>() {
            @Override
            public User task() {
                User user = null;
                try (Connection connection = ConnectionPoolManager.getInstance().getConnection(true)) {
                    PreparedStatement ps = connection.prepareStatement("SELECT users.id, users.first_name, users.last_name, authorities.password, authorities.role "
                            + "  FROM authorities "
                            + "  INNER JOIN users "
                            + "  ON authorities.user_id = users.id "
                            + "  WHERE authorities.login = ? ");
                    ps.setString(1, login);
                    ps.execute();
                    ResultSet rs = ps.getResultSet();
                    if (rs.next()) {

                        if (password.equals(rs.getString("password"))) {
                            user = new User();
                            user.setId(rs.getLong("id"));
                            user.setRole(rs.getString("role"));
                            user.setFirstName(rs.getString("first_name"));
                            user.setLastName(rs.getString("last_name"));
                        } else if (passwordEncoder.matches(password, rs.getString("password"))) {
                            user = new User();
                            user.setId(rs.getLong("id"));
                            user.setRole(rs.getString("role"));
                            user.setFirstName(rs.getString("first_name"));
                            user.setLastName(rs.getString("last_name"));
                        } else {
                            throw new AuthenticationException("Password isn't correct ");
                        }
                    }
                } catch (SQLException | AuthenticationException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
                return user;
            }
        };
        try {
            return executor.execute();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
