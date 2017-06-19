/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *<p>Pool manager for for {@link ua.nure.rumiantsev.SummaryTask4.Utils.Connection Connection}</p>
 * Stores connections in list and takes one from there on getConnection method invocation.
 * Connection returns on it's close method invocation
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
public class ConnectionPoolManager {

    
    private  final List<java.sql.Connection> connections;
    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolManager.class);
    private static final ConnectionPoolManager INSTANCE = new ConnectionPoolManager();

    private ConnectionPoolManager() {
        connections = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            connections.add(getDataSourceConnection(true));
        }
    }

    public static ConnectionPoolManager getInstance() {
        return INSTANCE;
    }

    /**
     * Method with package level access for using inner collection in 
     * {@link ua.nure.rumiantsev.SummaryTask4.Utils.Connection Connection} close method
     * @return inner collection of {@link java.sql.Connection Connection}
     */
    List<java.sql.Connection> getConnections() {
        return connections;
    }

    /**
     * Returns {@link ua.nure.rumiantsev.SummaryTask4.Utils.Connection Connection}
     * prepared to use
     * @param autoCommit - whether or not connection do auto commit
     * @return Connection
     */
    public synchronized Connection getConnection(boolean autoCommit) {
        if (connections.size() > 10) {
            try {
                connections.remove(0).close();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        if (connections.size() > 0) {
            java.sql.Connection c = connections.remove(0);
            LOGGER.debug(connections);
            try {
                c.setAutoCommit(autoCommit);
                return new Connection(c);
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

        }
        return new Connection(getDataSourceConnection(autoCommit));
    }

    /**
     * Obtains new connection from DBMS via Data Source mechanism.
     * @param autoCommit - whether or not connection do auto commit
     * @return {@link java.sql.Connection Connection}
     */
    private java.sql.Connection getDataSourceConnection(boolean autoCommit) {
        java.sql.Connection connection = null;
        try {
            DataSource dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/confluence");
            connection = dataSource.getConnection();
            connection.setAutoCommit(autoCommit);
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return connection;
    }

}
