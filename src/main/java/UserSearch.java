import java.io.*;
import java.net.*;
import java.sql.*;

public class UserSearch {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket clientSocket = serverSocket.accept();
            
            // SOURCE: Data coming from a network socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String taintedInput = in.readLine(); 

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "pass");
            Statement statement = conn.createStatement();

            // VULNERABILITY: Direct concatenation
            String query = "SELECT * FROM users WHERE id = '" + taintedInput + "'";

            // SINK: Execution of tainted string
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                System.out.println(rs.getString("username"));
            }
        } catch (Exception e) {
            // Error handling
        }
    }
}
