package day1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void main(String[] args) throws Exception {
        
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
            
            String query = "drop table demo";
            Statement stmt = con.createStatement();
            
            stmt.execute(query);
            System.out.println("Table deleted");
            
        
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
