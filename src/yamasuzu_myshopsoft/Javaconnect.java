
package yamasuzu_myshopsoft;

import java.sql.*;

 public class Javaconnect {
     Connection conn = null;    
     public static Connection ConnecrDb(){
         try{ 
             Class.forName("com.mysql.jdbc.Driver");
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yamasuzudb","root","JesusChrist1");  
             //Connection conn = DriverManager.getConnection("jdbc:mysql://skylink-PC:3306/mydb","root","");  
             return conn;     
         }catch(Exception e){
             System.out.println(e+("Please Check on the Network Connection."
                     + " This Computer must be in the same Network with the Server..."));
             System.exit(0);
             return null;
         }   
     }
             
        
}

