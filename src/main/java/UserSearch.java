import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserSearch {
    public static void main(String[] args) {
        try {
            // SOURCE: Using a System Property (e.g., -Duser.id=...)
            // CodeQL recognizes System.getProperty as a potential source.
            String userId = System.getProperty("userId");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "pass");
            Statement statement = conn.createStatement();

            // VULNERABILITY: String concatenation
            String query = "SELECT * FROM users WHERE id = '" + userId + "'";

            // SINK: Execution
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                System.out.println(rs.getString("username"));
            }
        } catch (Exception e) {
            // Log error
        }
    }
}
