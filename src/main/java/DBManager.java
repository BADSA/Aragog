import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBManager {

    private static Connection connection;

    public DBManager(String databaseName) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pair<Integer, String> > getURLs() throws SQLException {
        List<Pair<Integer, String> > links = new ArrayList<Pair<Integer, String>>();

        String query = "select ID, url from urls where visited_date != NULL";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();

        while (rs.next()) {
            String url = rs.getString("url");
            int id = rs.getInt("ID");

            links.add(new Pair<Integer, String>(id, url));
        }

        return links;
    }

    public void updateURL(int ID) throws SQLException {
        String query = "UPDATE url SET visited_date = date('now') WHERE ID = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            ps.executeQuery();
    }

    public void insertURL(String url) throws SQLException{
        String query = "INSERT INTO url (url) values (?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, url);
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
