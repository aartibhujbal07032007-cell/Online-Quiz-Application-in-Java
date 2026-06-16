import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/quizdb";
            String user = "root";
            String password = "tanu2108";

            con = DriverManager.getConnection(url, user, password);

            System.out.println("Database Connected");

        } catch (Exception e) {

            System.out.println("Connection Error");
            e.printStackTrace();
        }

        return con;
    }
}