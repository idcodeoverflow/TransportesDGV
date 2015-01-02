/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
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
public class ProveedorDAO {
    
    public void agregarProveedor(ProveedorDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO proveedor(id_proveedor, nombre, rfc, direccion,"
                + " colonia, tel, mail, fecha_alta, fecha_baja, status) "
                + "VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), NULL, ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getRfc());
            pstmt.setString(3, proveedor.getDireccion());
            pstmt.setString(4, proveedor.getColonia());
            pstmt.setString(5, proveedor.getTel());
            pstmt.setString(6, proveedor.getMail());
            pstmt.setBoolean(7, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 531\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 531, UserHome.getUsuario(), ex);
        } finally {
            conn.close();
            pstmt.close();
        }
    }
    
    public void modificarProveedor(ProveedorDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE proveedor SET nombre = ?, rfc = ?, direccion = ?, "
                + "colonia = ?, tel = ?, mail = ?, fecha_alta = ?, fecha_baja = ? "
                + "WHERE id_proveedor = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getRfc());
            pstmt.setString(3, proveedor.getDireccion());
            pstmt.setString(4, proveedor.getColonia());
            pstmt.setString(5, proveedor.getTel());
            pstmt.setString(6, proveedor.getMail());
            pstmt.setTimestamp(7, proveedor.getFechaAlta());
            pstmt.setTimestamp(8, proveedor.getFechaBaja());
            pstmt.setInt(9, proveedor.getIdProveedor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 532\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 532, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarProveedor(ProveedorDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE proveedor SET fecha_alta = ?, fecha_baja = NOW(), "
                + "status = ? WHERE id_proveedor = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, proveedor.getFechaAlta());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, proveedor.getIdProveedor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 533\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 533, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public ProveedorDTO obtenerProveedor(int idProveedor, boolean abrir, boolean cerrar) throws SQLException{
        ProveedorDTO proveedor = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, nombre, rfc, direccion, colonia, tel,"
                + " mail, fecha_alta, fecha_baja, status FROM proveedor WHERE "
                + "id_proveedor = ?";// AND status = ?;";//Se elimino el filtrado de registros activos
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idProveedor);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            proveedor = new ProveedorDTO();
            while (rs.next()) {
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setRfc(rs.getString("rfc"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setColonia(rs.getString("colonia"));
                proveedor.setTel(rs.getString("tel"));
                proveedor.setMail(rs.getString("mail"));
                proveedor.setFechaAlta(rs.getTimestamp("fecha_alta"));
                proveedor.setFechaBaja(rs.getTimestamp("fecha_baja"));
                proveedor.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 534\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 534, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(proveedor != null){
            return proveedor;
        }
        return null;
    }
    
    public ProveedorDTO obtenerProveedorIncCancelado(int idProveedor, boolean abrir, boolean cerrar) throws SQLException{
        ProveedorDTO proveedor = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, nombre, rfc, direccion, colonia, tel,"
                + " mail, fecha_alta, fecha_baja, status FROM proveedor WHERE "
                + "id_proveedor = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idProveedor);
            rs = pstmt.executeQuery();
            proveedor = new ProveedorDTO();
            while (rs.next()) {
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setRfc(rs.getString("rfc"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setColonia(rs.getString("colonia"));
                proveedor.setTel(rs.getString("tel"));
                proveedor.setMail(rs.getString("mail"));
                proveedor.setFechaAlta(rs.getTimestamp("fecha_alta"));
                proveedor.setFechaBaja(rs.getTimestamp("fecha_baja"));
                proveedor.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 535\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 535, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(proveedor != null){
            return proveedor;
        }
        return null;
    }
    
    public List<ProveedorDTO> obtenerProveedores() throws SQLException{
        List<ProveedorDTO> proveedores = null;
        ProveedorDTO proveedor = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, nombre, rfc, direccion, colonia, tel,"
                + " mail, fecha_alta, fecha_baja, status FROM proveedor WHERE status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            proveedores = new ArrayList<ProveedorDTO>();
            while (rs.next()) {
                proveedor = new ProveedorDTO(
                rs.getInt("id_proveedor"),
                rs.getString("nombre"),
                rs.getString("rfc"),
                rs.getString("direccion"),
                rs.getString("colonia"),
                rs.getString("tel"),
                rs.getString("mail"),
                rs.getTimestamp("fecha_alta"),
                rs.getTimestamp("fecha_baja"),
                rs.getBoolean("status"));
                proveedores.add(proveedor);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 536\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 536, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(proveedores != null){
            return proveedores;
        }
        return null;
    }
    
    public int obtenerMaxIdProveedor() throws SQLException{
        int max = 0;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_proveedor) AS max_id FROM proveedor;";
        try {
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next()){
                max = rs.getInt("max_id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 537\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerMaxIdProveedor " + max, 537, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return max;
    }
    
    public boolean tieneFacturasPendientes(ProveedorDTO proveedor, boolean abrir, boolean cerrar) throws SQLException{
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, folio FROM factura WHERE id_proveedor = ? AND pagada = ? AND status = ?;";
        try {
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, proveedor.getIdProveedor());
            pstmt.setBoolean(2, false);
            pstmt.setBoolean(3, true);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 538\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 538, UserHome.getUsuario(), ex);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return false;
    }
    
    public int obtenerUltimoIdProveedorCCanceladas() throws SQLException{
        int maxIdProveedor = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_proveedor) AS max_proveedor FROM proveedor;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdProveedor = rs.getInt("max_proveedor");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 539\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdProveedorCCanceladas " + maxIdProveedor, 539, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdProveedor;
    }
    
    public boolean reestablecerAutoincrementProveedor(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE proveedor AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 540\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementProveedor " + index, 540, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarProveedor() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdProveedor = this.obtenerUltimoIdProveedorCCanceladas();
        return this.reestablecerAutoincrementProveedor(maxIdProveedor + 1);
    }
    
    public List<ProveedorDTO> buscarProveedor(ProveedorDTO proveedorBuscar) throws SQLException{
        List<ProveedorDTO> proveedores = null;
        ProveedorDTO proveedor = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, nombre, rfc, direccion, colonia, tel, mail, fecha_alta, fecha_baja, status"
                + " FROM proveedor WHERE status = ? AND nombre LIKE CONCAT('%',?,'%')" 
                + " ORDER BY nombre ASC;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, proveedorBuscar.getNombre());
            rs = pstmt.executeQuery();
            proveedores = new ArrayList<ProveedorDTO>();
            while (rs.next()) {
                proveedor = new ProveedorDTO(
                rs.getInt("id_proveedor"),
                rs.getString("nombre"),
                rs.getString("rfc"),
                rs.getString("direccion"),
                rs.getString("colonia"),
                rs.getString("tel"),
                rs.getString("mail"),
                rs.getTimestamp("fecha_alta"),
                rs.getTimestamp("fecha_baja"),
                rs.getBoolean("status"));
                proveedores.add(proveedor);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 633\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 633, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(proveedores != null){
            return proveedores;
        }
        return null;
    }
    
}
