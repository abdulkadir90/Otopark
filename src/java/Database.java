/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean ( name="databaseBean" )
@RequestScoped
public class Database {
    
private int id;    
private String plaka;
private String renk;
private String marka;
private String yakit;
private String cıkacak_plaka;
private Date date;
private double time;
private double total;
double fee_ph;
//private java.sql.Time saat;

DataSource dataSource;
   public Database() {
       
            try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/addressbook");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
    }
   CachedRowSet rowSet=null;

    public String getCıkacak_plaka() {
        return cıkacak_plaka;
    }

    public void setCıkacak_plaka(String cıkacak_plaka) {
        this.cıkacak_plaka = cıkacak_plaka;
    }   
   
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getRenk() {
        return renk;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getYakit() {
        return yakit;
    }

    public void setYakit(String yakit) {
        this.yakit = yakit;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
    
  
   
   public String aracEkle() throws SQLException
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
 connection.prepareStatement( "INSERT INTO arac" +
 "(id,plaka,renk,marka,yakit,süre)" +
 "VALUES ( ?, ?, ?, ?, ?, ? )" );
 

 addEntry.setInt( 1, getId() );
 addEntry.setString( 2, getPlaka() );
 addEntry.setString( 3, getRenk() );
 addEntry.setString( 4, getMarka() );
 addEntry.setString( 5, getYakit() );
 addEntry.setDouble( 6, getTime() );


addEntry.executeUpdate(); // insert the entry
 return "index"; // go back to index.xhtml home page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 
   
   
   public ResultSet bul() throws SQLException
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
 PreparedStatement ps =
 connection.prepareStatement( "select * from arac" );
 
 rowSet = new com.sun.rowset.CachedRowSetImpl();
 rowSet.populate( ps.executeQuery() );
return rowSet;
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 
   
   public ResultSet musteriBul() throws SQLException
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
 PreparedStatement ps =
 connection.prepareStatement( "select * from musteri" );
 
 rowSet = new com.sun.rowset.CachedRowSetImpl();
 rowSet.populate( ps.executeQuery() );
return rowSet;
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

   
   
    public void sil() throws SQLException
 {
 // check whether dataSource was injected by the server
 if ( dataSource == null ){ 
      throw new SQLException( "Unable to obtain DataSource" );
 }

 // obtain a connection from the connection pool
 Connection connection = dataSource.getConnection();

 // check whether connection was successful
 if ( connection == null )
 throw new SQLException( "Unable to connect to DataSource" );

 try
 {
 // create a PreparedStatement to insert a new address book entry
 PreparedStatement myObject =
 connection.prepareStatement( "delete from arac where plaka=?");

 // specify the PreparedStatement's arguments
 myObject.setString( 1,(getCıkacak_plaka()) );
 
myObject.executeUpdate(); // insert the entry
 
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 
   
    public String cıkıs(){
        String yakitTipi = getYakit();
        
        if (yakitTipi.equals("lpg"))
        {   
                fee_ph = 7.99; //lpg'li araçlar için saatlik konaklama ücreti           
        }else
        {
                fee_ph = 6.75; //saatlik konaklama ücreti
        }
        
        total = time * fee_ph; //toplam borcun hesaplanması
        
        setTotal(total);
        
    try {
        sil();
    } catch (SQLException ex) {
        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
        return "cıkıs.xhtml";
    }
    
}
