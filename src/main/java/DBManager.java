import javafx.util.Pair;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    Class that takes control of the database.
*/
public class DBManager {

    private static Connection connection;

    /*
        Sets the connection with the SQLite database
        and the database name.
    */
    public void setConnection(String databaseName) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
    }

    /*
        Returns list of URLs taking into account what are
        not visited and older than "days".
    */
    public List<Pair<Integer, String>> getURLs(int days) throws SQLException {
        List<Pair<Integer, String>> links = new ArrayList<Pair<Integer, String>>();

        String query = "SELECT * FROM urls WHERE visited_date IS NOT NULL LIMIT 30;";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        DateTime limitTime = new DateTime().plusDays(-days);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        while (rs.next()) {
            int id = rs.getInt("ID");
            String url = rs.getString("url");
            String date = rs.getString("visited_date");


            DateTime visitedDate = fmt.parseDateTime(date);
            if (visitedDate.isBefore(limitTime)){
                links.add(new Pair<Integer, String>(id, url));
            }

        }

        query = "SELECT * FROM urls WHERE visited_date IS NULL LIMIT 30;";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();

        while (rs.next()) {
            String url = rs.getString("url");
            int id = rs.getInt("ID");
            links.add(new Pair<Integer, String>(id, url));
        }

        ps.close();
        rs.close();
        return links;
    }

    /*
        Updates the specified URL with the current date.
     */
    public void updateURL(int ID) {
        String query = "UPDATE urls SET visited_date = date('now') WHERE ID = ?;";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ID);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Could not update visited_date for id=" + ID);
        }
    }

    /*
        Inserts new url in the database
        for its future visit.
    */
    public void insertURL(String url) {
        String query = "INSERT INTO urls (url, visited_date) values (?, NULL);";
        try {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, url);
        ps.execute();
        ps.close();
        } catch (Exception e) {
            //System.out.println(e.getMessage() + " - URL: "+ url);
        }
    }

    /*
        Closes the DB connection.
    */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /*
        Checks if the database is empty.
     */
    public boolean checkEmptyDB() throws SQLException {
        String query = "select count() from urls;";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            long storedLinks = rs.getLong(1);
            rs.close();

            if (storedLinks > 0) {
                return false;
            }
        }

        return true;
    }

    /*
        Loads database with given url list.
    */
    public void inserURLList(List<String> URLList) {
        for(String url: URLList){
            this.insertURL(url);
        }
    }

}
