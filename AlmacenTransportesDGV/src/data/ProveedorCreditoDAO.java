/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.ProveedorCreditoDTO;
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
public class ProveedorCreditoDAO extends ProveedorDAO {
    
    public void agregarProveedorCredito(ProveedorCreditoDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO proveedor_credito(id_proveedor_credito, "
                + "id_proveedor, tiempo_limite_pago, status) VALUES(NULL,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, proveedor.getIdProveedor());
            pstmt.setShort(2, proveedor.getTiempoLimitePago());
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 526\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 526, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarProveedorCredito(ProveedorCreditoDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE proveedor_credito SET tiempo_limite_pago = ? WHERE id_proveedor = ? AND status = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setShort(1, proveedor.getTiempoLimitePago());
            pstmt.setInt(2, proveedor.getIdProveedor());
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 527\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 527, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarProveedorCredito(ProveedorCreditoDTO proveedor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE proveedor_credito SET status = ? WHERE id_proveedor = ? AND status = ?;";
                
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, proveedor.getIdProveedor());
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 528\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 528, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public ProveedorCreditoDTO obtenerProveedorCredito(int idProveedor) throws SQLException{
        ProveedorCreditoDTO proveedor = null;
        ProveedorDTO p = new ProveedorDTO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor_credito, id_proveedor, tiempo_limite_pago, status FROM proveedor_credito WHERE "
                + "id_proveedor = ? AND status = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idProveedor);
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                proveedor = new ProveedorCreditoDTO();
                proveedor.setIdProveedorCredito(rs.getInt("id_proveedor_credito"));
                proveedor.setTiempoLimitePago(rs.getShort("tiempo_limite_pago"));
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
            JOptionPane.showMessageDialog(null, "Código error: 529\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 529, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(proveedor != null){
            return proveedor;
        }
        return null;
    }
    
    public List<ProveedorCreditoDTO> obtenerProveedoresCredito() throws SQLException{
        List<ProveedorCreditoDTO> proveedores = null;
        ProveedorCreditoDTO proveedor = null;
        ProveedorDTO p = new ProveedorDTO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor_credito, id_proveedor, tiempo_limite_pago,"
                + " status FROM proveedor_credito WHERE status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            proveedores = new ArrayList<ProveedorCreditoDTO>();
            while (rs.next()) {
                proveedor = new ProveedorCreditoDTO(
                rs.getInt("id_proveedor_credito"),
                rs.getShort("tiempo_limite_pago"),
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
            JOptionPane.showMessageDialog(null, "Código error: 530\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(proveedor.toString(), 530, UserHome.getUsuario(), e);
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
