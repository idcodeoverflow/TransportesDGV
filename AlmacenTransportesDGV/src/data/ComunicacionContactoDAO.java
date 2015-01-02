/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.ComunicacionContactoDTO;
import beans.ContactoProveedorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;
import support.DBConnection;

/**
 *
 * @author David Israel
 */
public class ComunicacionContactoDAO {
    
    public void agregarComunicacion(ComunicacionContactoDTO comunicacion, ContactoProveedorDTO contacto) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO comunicacion_contacto(id_comunicacion, nombre, datos, id_contacto_proveedor) VALUES(NULL,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, comunicacion.getNombre());
            pstmt.setString(2, comunicacion.getDatos());
            pstmt.setInt(3, contacto.getIdContactoProveedor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 457\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(comunicacion.toString() + contacto.toString(), 457, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarComunicacion(ComunicacionContactoDTO comunicacion) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE comunicacion_contacto SET nombre = ?, datos = ? WHERE id_comunicacion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, comunicacion.getNombre());
            pstmt.setString(2, comunicacion.getDatos());
            pstmt.setInt(3, comunicacion.getIdComunicacion());
            pstmt.executeUpdate();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 458\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(comunicacion.toString(), 458, UserHome.getUsuario(), ex);
        } finally{
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarComunicacion(ComunicacionContactoDTO comunicacion) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "DELETE FROM comunicacion_contacto WHERE id_comunicacion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, comunicacion.getIdComunicacion());
            pstmt.executeUpdate();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 459\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(comunicacion.toString(), 459, UserHome.getUsuario(), ex);
        } finally{
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public ComunicacionContactoDTO obtenerComunicacionContacto(int idComunicacion) throws SQLException{
        ComunicacionContactoDTO comunicacion = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_comunicacion, nombre, datos, id_contacto_proveedor FROM comunicacion_contacto WHERE "
                + "id_comunicacion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idComunicacion);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            comunicacion = new ComunicacionContactoDTO();
            while (rs.next()) {
                comunicacion.setIdComunicacion(rs.getInt("id_comunicacion"));
                comunicacion.setNombre(rs.getString("nombre"));
                comunicacion.setDatos(rs.getString("datos"));
                //comunicacion.setStatus(rs.getBoolean("status"));//en proceso decidir el uso de la informacion de contacto
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 460\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(comunicacion.toString(), 460, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return comunicacion;
    }
    
    public List<ComunicacionContactoDTO> obtenerComunicacionesContacto(int idContacto) throws SQLException{
        List<ComunicacionContactoDTO> comunicaciones = null;
        ComunicacionContactoDTO comunicacion = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_comunicacion, nombre, datos, id_contacto_proveedor"
                + " FROM comunicacion_contacto WHERE id_contacto_proveedor = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idContacto);
            rs = pstmt.executeQuery();
            comunicaciones = new ArrayList<ComunicacionContactoDTO>();
            while (rs.next()) {
                comunicacion = new ComunicacionContactoDTO(
                rs.getInt("id_comunicacion"),
                rs.getString("nombre"),
                rs.getString("datos"));
                //en proceso decidir el uso de la informacion de contacto.
                comunicaciones.add(comunicacion);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 461\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(comunicacion.toString() + " id_contacto " + idContacto, 461, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(comunicaciones != null){
            return comunicaciones;
        }
        return null;
    }
    
    public int obtenerUltimoIdComunicacionContactoCCanceladas() throws SQLException{
        int maxIdComunicacion = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_comunicacion) AS max_id FROM comunicacion_contacto;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdComunicacion = rs.getInt("max_id");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 462\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdComunicacionContactoCCanceladas " + maxIdComunicacion, 462, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdComunicacion;
    }
    
    public boolean reestablecerAutoincrementComunicacionContacto(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE comunicacion_contacto AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 463\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementComunicacionContacto " + index, 463, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarComunicacionContacto() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdComunicacionContacto = this.obtenerUltimoIdComunicacionContactoCCanceladas();
        return this.reestablecerAutoincrementComunicacionContacto(maxIdComunicacionContacto + 1);
    }
    
}
