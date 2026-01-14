import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

public class UserSearch {

    public void doGet(HttpServletRequest request) {
        try {
            // SOURCE: User input from a URL parameter
            String userId = request.getParameter("userId");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "pass");
            Statement statement = conn.createStatement();

            // VULNERABILITY: Concatenating user input directly into the SQL string
            String query = "SELECT * FROM users WHERE id = '" + userId + "'";

            // SINK: Executing the tainted string
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                System.out.println(rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
