package model.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import model.util.DataBaseUtilities;

/**
 *
 * Connection with mysql database.
 *
 * @author skuarch
 */
public class DataBaseConnection {

    private String user = null;
    private String password = null;
    private String server = null;
    private String dataBaseName = null;
    private int port;
    private DataSource dataSource = null;
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private String url = null;

    //==========================================================================
    /**
     * create a instance using driver.
     *
     * @param user String
     * @param password String
     * @param server String
     * @param dataBaseName String
     * @param port int
     */
    public DataBaseConnection(String user, String password, String server, String dataBaseName, int port) {
        this.user = user;
        this.password = password;
        this.server = server;
        this.dataBaseName = dataBaseName;
        this.port = port;
    } // end DataBaseConnection  

    //==========================================================================
    /**
     * create a instance using DataSource
     *
     * @param dataSource DataSource
     */
    public DataBaseConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    } // end DataBaseConnection

    //==========================================================================
    private Connection createConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Connection connection;

        if (dataSource != null) {
            connection = dataSource.getConnection();
        } else {
            //url = "jdbc://" + server + "/" + dataBaseName + ":" + port + "?user=" + user + "&password=" + password;
            //jdbc:mysql://192.168.208.15:3306/sam_4
            url = "jdbc:mysql://" + server + ":" + port + "/" + dataBaseName + "?user=" + user + "&password=" + password;
            Class.forName(DRIVER).newInstance();
            connection = DriverManager.getConnection(url);
        }

        return connection;

    } // end connection   

    //=========================================================================    
    public ResultSet executeQuery(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        if (sql == null || sql.length() < 1) {
            throw new NullPointerException("sql is null or empty");
        }

        ResultSet resultSet = null;
        Connection connection = null;
        Statement statement = null;
        CachedRowSet crs = null;

        try {

            connection = createConnection();

            if (connection == null) {
                return null;
            }

            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);

            //resultset is null ?
            if (resultSet == null) {
                throw new NullPointerException("resultSet is null");
            }

            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(resultSet);

        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            DataBaseUtilities.closeConnection(connection);
            DataBaseUtilities.closeStatement(statement);
            DataBaseUtilities.closeResultSet(resultSet);
        }

        return crs;
    } //end executeQuery

    //==========================================================================
    public void update(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        if (sql == null || sql.length() < 1) {
            throw new NullPointerException("sql is null or empty");
        }

        Connection connection = null;
        Statement statement = null;

        try {

            connection = createConnection();

            if (connection == null) {
                return;
            }

            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            DataBaseUtilities.closeConnection(connection);
            DataBaseUtilities.closeStatement(statement);
            sql = null;
        }

    } //end update
} // end class
