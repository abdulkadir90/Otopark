/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bahadır
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
@ManagedBean ( name="musteriBean" )
@RequestScoped
public class musteri {
    
private String ad;
private String soyad;
private int dogumyili;
private String cinsiyet;
private String plaka;

DataSource dataSource;
   public musteri() {
       
            try {
			Context ctx = new InitialContext();
			dataSource = (DataSource)ctx.lookup("jdbc/addressbook");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public int getDogumyili() {
        return dogumyili;
    }

    public void setDogumyili(int dogumyili) {
        this.dogumyili = dogumyili;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }
   
    
   public String musteriEkle() throws SQLException
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
 connection.prepareStatement( "INSERT INTO musteri" +
 "(ad,soyad,dogumyılı,cinsiyet,plaka)" +
 "VALUES ( ?, ?, ?, ?, ? )" );

 // specify the PreparedStatement's arguments
 addEntry.setString( 1, getAd() );
 addEntry.setString( 2, getSoyad() );
 addEntry.setInt( 3, getDogumyili() );
 addEntry.setString( 4, getCinsiyet() );
 addEntry.setString( 5, getPlaka() );
 //addEntry.setTime( 6, getSaat() );

addEntry.executeUpdate(); // insert the entry
 return "index.xhtml"; // go back to index.xhtml home page
 } // end try
 finally
 {
 connection.close(); // return this connection to pool
 } // end finally
 } 
    
}

