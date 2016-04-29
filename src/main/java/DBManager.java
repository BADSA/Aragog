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

    private Connection connection;

    public DBManager(String databaseName) {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pair<Integer, String> > getURLs() {
        List<Pair<Integer, String> > links = new ArrayList<Pair<Integer, String>>();

        String query = "select ID, url from urls where visited_date != NULL";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                String url = rs.getString("url");
                int id = rs.getInt("ID");

                links.add(new Pair<Integer, String>(id, url));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return links;
    }

    public void updateURL(int ID) {
        String query = "UPDATE url SET visited_date = date('now') WHERE ID = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, ID);
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
