/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped; 
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author Bahadır
 */
@ManagedBean  (name="loginBean" )
@SessionScoped
public class Login {
    
    public static String username;    //username entered by user
    public static String password;    //password entered by user
    private String r_username;  //real username
    private String r_password;  //real password

    public String getUsername() {
        return username;
    }

    public String getR_username() {
        return r_username;
    }

    public void setR_username(String r_username) {
        this.r_username = r_username;
    }

    public String getR_password() {
        return r_password;
    }

    public void setR_password(String r_password) {
        this.r_password = r_password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    CachedRowSet rowSet=null;

    /**
     * Creates a new instance of loginBean
     */

  DataSource dataSource;
   public Login() {
        try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/addressbook");
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
  
   public String validateLogin() throws SQLException{ 
       
        // check whether dataSource was injected by the server
        if ( dataSource == null )
        throw new SQLException( "Unable to obtain DataSource" );

         // obtain a connection from the connection pool
        Connection connection = dataSource.getConnection();

        // check whether connection was successful
        if ( connection == null )
        throw new SQLException( "Unable to connect to DataSource" );

        try
        {
            // create a PreparedStatement to insert a new address book entry
            PreparedStatement ps =
            connection.prepareStatement( "select COUNT(ADMIN.ID) as say from ADMIN where ADMIN.ID=? and ADMIN.PASSWORD=?"); 

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            rs.next();
            if(rs.getInt(1)==1){
                return "index.xhtml";
            }else           
                return "admin.xhtml";

        } // end try
        finally{
            connection.close(); // return this connection to pool
        }
   }
   
}
