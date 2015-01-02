/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.ContactoProveedorDTO;
import beans.ProveedorDTO;
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
public class ContactoProveedorDAO {
    
    public void agregarContactoProveedor(ContactoProveedorDTO contacto, ProveedorDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO contacto_proveedor(id_contacto_proveedor, nombre,"
                + " apellidos, puesto, fecha_alta, fecha_baja, status, id_proveedor) "
                + "VALUES (NULL,?,?,?,NOW(),NULL,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getApellidos());
            pstmt.setString(3, contacto.getPuesto());
            pstmt.setBoolean(4, true);
            pstmt.setInt(5, proveedor.getIdProveedor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 464\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(contacto.toString() + proveedor.toString(), 464, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarContactoProveedor(ContactoProveedorDTO contacto, ProveedorDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE contacto_proveedor SET nombre = ?, apellidos = ?, "
                + "puesto = ?, id_proveedor = ?, fecha_alta = ?, fecha_baja = ? "
                + "WHERE id_contacto_proveedor = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getApellidos());
            pstmt.setString(3, contacto.getPuesto());
            pstmt.setInt(4, proveedor.getIdProveedor());
            pstmt.setTimestamp(5, contacto.getFechaAlta());
            pstmt.setTimestamp(6, contacto.getFechaBaja());
            pstmt.setInt(7, contacto.getIdContactoProveedor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 465\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(contacto.toString() + proveedor.toString(), 465, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarContactoProveedor(ContactoProveedorDTO contacto, ProveedorDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE contacto_proveedor SET fecha_alta = ?, fecha_baja = NOW(), "
                + "status = ? WHERE id_contacto_proveedor = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, contacto.getFechaAlta());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, contacto.getIdContactoProveedor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 466\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(contacto.toString() + proveedor.toString(), 466, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }

    public ContactoProveedorDTO obtenerContactoProveedor(int idContactoProveedor) throws SQLException{
        ContactoProveedorDTO contacto = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_contacto_proveedor, nombre, apellidos, puesto, "
                + "fecha_alta, fecha_baja, status, id_proveedor FROM contacto_proveedor "
                + "WHERE id_contacto_proveedor = ?";// AND status = ?;";//se elimino el filtrado de registros activos
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idContactoProveedor);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            contacto = new ContactoProveedorDTO();
            while (rs.next()) {
                contacto.setIdContactoProveedor(rs.getInt("id_contacto_proveedor"));
                contacto.setNombre(rs.getString("nombre"));
                contacto.setApellidos(rs.getString("apellidos"));
                contacto.setPuesto(rs.getString("puesto"));
                contacto.setFechaAlta(rs.getTimestamp("fecha_alta"));
                contacto.setFechaBaja(rs.getTimestamp("fecha_baja"));
                contacto.setStatus(rs.getBoolean("status"));
                //rs.getInt("id_proveedor");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 467\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(contacto.toString(), 467, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return contacto;
    }
    
    public List<ContactoProveedorDTO> obtenerContactosProveedor(ProveedorDTO proveedor) throws SQLException{
        List<ContactoProveedorDTO> contactos = null;
        ContactoProveedorDTO contacto = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_contacto_proveedor, nombre, apellidos, puesto, "
                + "fecha_alta, fecha_baja, status, id_proveedor FROM contacto_proveedor "
                + "WHERE id_proveedor = ? AND status = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, proveedor.getIdProveedor());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            contactos = new ArrayList<ContactoProveedorDTO>();
            while (rs.next()) {
                int idContacto = rs.getInt("id_contacto_proveedor");
                contacto = new ContactoProveedorDTO(
                idContacto, //id del contacto
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("puesto"),
                rs.getTimestamp("fecha_alta"),
                rs.getTimestamp("fecha_baja"),
                rs.getBoolean("status"),
                new ComunicacionContactoDAO().obtenerComunicacionesContacto(idContacto));
                contactos.add(contacto);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 468\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(contacto.toString(), 468, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(contactos != null){
            return contactos;
        }
        return null;
    }
    
    public int obtenerUltimoIdContactoProveedorCCanceladas() throws SQLException{
        int maxIdContactoProveedor = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_contacto_proveedor) AS max_contacto FROM contacto_proveedor;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdContactoProveedor = rs.getInt("max_contacto");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 469\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdContactoProveedorCCanceladas " + maxIdContactoProveedor, 469, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdContactoProveedor;
    }
    
    public boolean reestablecerAutoincrementContactoProveedor(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE contacto_proveedor AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 470\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementContactoProveedor " + index, 470, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarContactoProveedor() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdContactoProveedor = this.obtenerUltimoIdContactoProveedorCCanceladas();
        return this.reestablecerAutoincrementContactoProveedor(maxIdContactoProveedor + 1);
    }
    
}
