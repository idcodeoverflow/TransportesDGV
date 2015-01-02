/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.ProveedorContadoDTO;
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
public class ProveedorContadoDAO extends ProveedorDAO {
    
    public void agregarProveedorContado(ProveedorContadoDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO proveedor_contado(id_proveedor_contado, "
                + "id_proveedor, status) VALUES(NULL,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, proveedor.getIdProveedor());
            pstmt.setBoolean(2, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 521\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 521, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * No esta habilitado este metodo por que no hay datos que puedan ser modificados.
     * @param proveedor
     * @throws SQLException 
     */
    public void modificarProveedorContado(ProveedorContadoDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE proveedor_contado WHERE id_proveedor = ?;";//no hay campos para realizar cambios
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 522\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 522, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarProveedorContado(ProveedorContadoDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE proveedor_contado SET status = ? WHERE id_proveedor = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, proveedor.getIdProveedor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 523\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 523, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public ProveedorContadoDTO obtenerProveedorContado(int idProveedor) throws SQLException{
        ProveedorContadoDTO proveedor = null;
        ProveedorDTO p = new ProveedorDTO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor_contado, id_proveedor, status FROM proveedor_contado WHERE "
                + "id_proveedor = ? AND status = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idProveedor);
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                proveedor = new ProveedorContadoDTO();
                proveedor.setIdProveedorContado(rs.getInt("id_proveedor_contado"));
                proveedor.setStatus(rs.getBoolean("status"));
                p = new ProveedorDAO().obtenerProveedor(rs.getInt("id_proveedor"), false, false);
                proveedor.setColonia(p.getColonia());
                proveedor.setDireccion(p.getDireccion());
                proveedor.setFechaAlta(p.getFechaAlta());
                proveedor.setFechaBaja(p.getFechaBaja());
                proveedor.setIdProveedor(p.getIdProveedor());
                proveedor.setMail(p.getMail());
                proveedor.setNombre(p.getNombre());
                proveedor.setRfc(p.getRfc());
                proveedor.setStatus(p.isStatus());
                proveedor.setTel(p.getTel());
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 524\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 524, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(proveedor != null){
            return proveedor;
        }
        return null;
    }
    
    public List<ProveedorContadoDTO> obtenerProveedoresContado() throws SQLException{
        List<ProveedorContadoDTO> proveedores = null;
        ProveedorContadoDTO proveedor = null;
        ProveedorDTO p = new ProveedorDTO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor_contado, id_proveedor, status FROM proveedor_contado WHERE "
                + "status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            proveedores = new ArrayList<ProveedorContadoDTO>();
            while (rs.next()) {
                proveedor = new ProveedorContadoDTO(
                rs.getInt("id_proveedor_contado"),
                rs.getBoolean("status"));
                p = new ProveedorDAO().obtenerProveedor(rs.getInt("id_proveedor"), false, false);
                proveedor.setColonia(p.getColonia());
                proveedor.setDireccion(p.getDireccion());
                proveedor.setFechaAlta(p.getFechaAlta());
                proveedor.setFechaBaja(p.getFechaBaja());
                proveedor.setIdProveedor(p.getIdProveedor());
                proveedor.setMail(p.getMail());
                proveedor.setNombre(p.getNombre());
                proveedor.setRfc(p.getRfc());
                proveedor.setStatus(p.isStatus());
                proveedor.setTel(p.getTel());
                proveedores.add(proveedor);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 525\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 525, UserHome.getUsuario(), e);
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
