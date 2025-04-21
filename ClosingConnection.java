package day1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClosingConnection {

    public static void main(String[] args) throws SQLException {
        
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/student";
        String user = "root"; // <-- Add your MySQL username here
        String pwd = "abc123"; // <-- Semicolon was missing here
        
        Connection con = null;
        
        try {
            Class.forName(driver);
            System.out.println("Driver is ready");
            
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connection is ready");
            
        
        } 
        catch (Exception e) { 
            System.out.println("Exception: " + e.getMessage()); // Capital 'S' in System
        }
        
        finally 
        {
        	if(con!=null)
        	{
        	con.close();
        	System.out.println("Connection Closed");
        	}
        }
    }
}
