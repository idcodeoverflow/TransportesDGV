/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.sql.*;
import javax.swing.JOptionPane;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public final class DBConnection {

         private static Connection conn = null;
	 private static final String userName = "root";
	 private static final String password = "remex2013";
	 private static final String url = "jdbc:mysql://localhost/inventario";
	 
	 public static void createConnection(){
            	 try{
                     Class.forName("com.mysql.jdbc.Driver").newInstance();
                     conn = (Connection) DriverManager.getConnection(url, userName, password);
		 } catch(SQLException e){
                        System.out.println(e.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: 200 al Conectar a la base de datos: "
                                + e.getMessage() + "\nCodigo SQL: " + e.getErrorCode() + "\nEstado Servidor: " 
                                + e.getSQLState(), "Error BD!!!", JOptionPane.ERROR_MESSAGE);
                        ErrorLogger.scribirLog("Conexion", 200, null, e);
		 } catch (Exception ex){
			System.out.println(ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Error: 201 al Conectar a la base de datos: "
                                + ex.getMessage(), "Error BD!!!", JOptionPane.ERROR_MESSAGE);
                        ErrorLogger.scribirLog("Conexion", 201, null, ex);
		 }
	}

	public static Connection getConn() {
		return conn;
	}

	private static void setConn(Connection conn) {
		DBConnection.conn = conn;
	}

	public static void closeConn() throws SQLException {
			conn.close();
	}
        
}
