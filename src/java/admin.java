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
import javax.faces.bean.RequestScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
/**
 *
 * @author Bahadır
 */
@ManagedBean ( name="adminBean" )
@RequestScoped
public class admin {
    
    private String id;
    private String eskipw;
    private String yenipw;
    private String kontrolpw;
    private String guvenliksorusu;
    private String guvenlikyaniti;
    private String gcevap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEskipw() {
        return eskipw;
    }

    public void setEskipw(String eskipw) {
        this.eskipw = eskipw;
    }

    public String getYenipw() {
        return yenipw;
    }

    public void setYenipw(String yenipw) {
        this.yenipw = yenipw;
    }

    public String getKontrolpw() {
        return kontrolpw;
    }

    public void setKontrolpw(String kontrolpw) {
        this.kontrolpw = kontrolpw;
    }

    public String getGuvenliksorusu() {
        return guvenliksorusu;
    }

    public void setGuvenliksorusu(String guvenliksorusu) {
        this.guvenliksorusu = guvenliksorusu;
    }

    public String getGuvenlikyaniti() {
        return guvenlikyaniti;
    }

    public void setGuvenlikyaniti(String guvenlikyaniti) {
        this.guvenlikyaniti = guvenlikyaniti;
    }

    public String getGcevap() {
        return gcevap;
    }

    public void setGcevap(String gcevap) {
        this.gcevap = gcevap;
    }
    
    DataSource dataSource;
   public admin() 
   {       
            try {       Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/addressbook");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
    }
   CachedRowSet rowSet=null;
   public String adminEkle() throws SQLException
 {
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
 PreparedStatement addEntry =
 connection.prepareStatement( "INSERT INTO admin" +
 "(id,guvenliksoru,password,guvenlikyanit)" +
 "VALUES ( ?, ?, ?, ?)" );
 
 // specify the PreparedStatement's arguments
 addEntry.setString( 1, getId() );
 addEntry.setString( 2, getGuvenliksorusu() );
 addEntry.setString( 3, getYenipw() );
 addEntry.setString( 4, getGuvenlikyaniti() );
 
addEntry.executeUpdate(); // insert the entry
 return "index.xhtml"; // go back to index.xhtml home page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 
   
   
    public ResultSet sorubul() throws SQLException
 {
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
  PreparedStatement ps =
            connection.prepareStatement( "select GUVENLIKSORU from ADMIN");        
 
 rowSet = new com.sun.rowset.CachedRowSetImpl();
 rowSet.populate( ps.executeQuery() );
return rowSet;
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } 
 }


public String kontrol() throws SQLException
 {
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
 
  PreparedStatement ps =
            connection.prepareStatement( "select GUVENLIKYANIT from ADMIN where ADMIN.GUVENLIKYANIT=?"); 
  
            ps.setString(1, getGcevap());         
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            rs.next();
            if(rs.getString(1).equals(gcevap)){
                return "degistir.xhtml";
            }else           
                return "index.xhtml";

        } // end try
        finally{
            connection.close(); // return this connection to pool
        } // end finally 

 }   
  
    public String sifreyenileme() throws SQLException
 {
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
 PreparedStatement addEntry =
 connection.prepareStatement( "UPDATE ADMIN SET PASSWORD=?" +
         "(yenipw)" + "VALUES (?)" );
 
 addEntry.setString(1,getYenipw());
 
addEntry.executeUpdate(); // insert the entry
 return "index.xhtml"; // go back to index.xhtml home page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 
}
