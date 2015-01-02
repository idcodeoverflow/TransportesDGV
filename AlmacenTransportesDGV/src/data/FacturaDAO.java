/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.FacturaDTO;
import beans.ProveedorDTO;
import beans.UsuarioDTO;
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
public class FacturaDAO {
    
    public void agregarFacturaCredito(FacturaDTO factura) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO factura(id_proveedor, folio, fecha_registro, "
                + "fecha_pago, subtotal, iva, total, pagada, status, numero_usuario) "
                + "VALUES(?,?,NOW(),NULL,?,?,?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, factura.getProveedor().getIdProveedor());
            pstmt.setString(2, factura.getFolio());
            pstmt.setDouble(3, factura.getSubtotal());
            pstmt.setDouble(4, factura.getIva());
            pstmt.setDouble(5, factura.getTotal());
            pstmt.setBoolean(6, false);
            pstmt.setBoolean(7, true);
            pstmt.setInt(8, factura.getUsuario().getNumeroUsuario());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 480\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 480, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void agregarFacturaContado(FacturaDTO factura) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO factura(id_proveedor, folio, fecha_registro, "
                + "fecha_pago, subtotal, iva, total, pagada, status, numero_usuario) "
                + "VALUES(?,?,NOW(),NOW(),?,?,?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, factura.getProveedor().getIdProveedor());
            pstmt.setString(2, factura.getFolio());
            pstmt.setDouble(3, factura.getSubtotal());
            pstmt.setDouble(4, factura.getIva());
            pstmt.setDouble(5, factura.getTotal());
            pstmt.setBoolean(6, true);
            pstmt.setBoolean(7, true);
            pstmt.setInt(8, factura.getUsuario().getNumeroUsuario());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 481\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 481, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void pagarFactura(FacturaDTO factura) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        
        String query = "UPDATE factura SET fecha_registro = ?, fecha_pago = NOW(), "
                + "pagada = ? WHERE id_proveedor = ? AND folio = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, factura.getFechaRegistro());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, factura.getProveedor().getIdProveedor());
            pstmt.setString(4, factura.getFolio());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 482\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE); 
            ErrorLogger.scribirLog(factura.toString(), 482, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarFactura(FacturaDTO factura) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE factura SET fecha_registro = ?, fecha_pago = ?, "
                + "subtotal = ?, iva = ?, total = ?, pagada = ?, status = ? "
                + "WHERE id_proveedor = ? AND folio = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, factura.getFechaRegistro());
            pstmt.setTimestamp(2, factura.getFechaPago());
            pstmt.setDouble(3, factura.getSubtotal());
            pstmt.setDouble(4, factura.getIva());
            pstmt.setDouble(5, factura.getTotal());
            pstmt.setBoolean(6, factura.isPagada());
            pstmt.setBoolean(7, factura.isStatus());
            pstmt.setInt(8, factura.getProveedor().getIdProveedor());
            pstmt.setString(9, factura.getFolio());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 483\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE); 
            ErrorLogger.scribirLog(factura.toString(), 483, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarFactura(FacturaDTO factura) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE factura SET fecha_registro = ?, fecha_pago = ?, "
                + "status = ? WHERE id_proveedor = ? AND folio = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, factura.getFechaRegistro());
            pstmt.setTimestamp(2, factura.getFechaPago());
            pstmt.setBoolean(3, false);
            pstmt.setInt(4, factura.getProveedor().getIdProveedor());
            pstmt.setString(5, factura.getFolio());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 484\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 484, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public FacturaDTO obtenerFactura(String folio, int idProveedor, boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        FacturaDTO factura = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, folio, fecha_registro, fecha_pago, "
                + "subtotal, iva, total, pagada, status, numero_usuario FROM factura "
                + "WHERE id_proveedor = ? AND folio = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idProveedor);
            pstmt.setString(2, folio);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                factura = new FacturaDTO(null, 
                        rs.getString("folio"), rs.getTimestamp("fecha_registro"),
                        rs.getTimestamp("fecha_pago"), rs.getDouble("subtotal"), rs.getDouble("iva"),
                        rs.getDouble("total"), rs.getBoolean("pagada"), rs.getBoolean("status"),
                        null);
                if(persistence){
                    factura.setProveedor(new ProveedorDAO().obtenerProveedorIncCancelado(rs.getInt("id_proveedor"), false, false));
                    factura.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 485\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 485, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return factura;
    }
    
    public List<FacturaDTO> obtenerFacturas(boolean persistence) throws SQLException{
        List<FacturaDTO> facturas = null;
        FacturaDTO factura = null;
        UsuarioDTO usuario = null;
        ProveedorDTO proveedor = null;
        
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, folio, fecha_registro, fecha_pago, "
                + "subtotal, iva, total, pagada, status, numero_usuario FROM factura "
                + "ORDER BY fecha_registro DESC;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            facturas = new ArrayList<FacturaDTO>();
            while (rs.next()) {
                factura = new FacturaDTO(
                        null,
                        rs.getString("folio"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getTimestamp("fecha_pago"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"), 
                        rs.getDouble("total"),
                        rs.getBoolean("pagada"),
                        rs.getBoolean("status"),
                        null); //usuario
                if(persistence){
                    proveedor = new ProveedorDAO().obtenerProveedorIncCancelado(rs.getInt("id_proveedor"), false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    factura.setProveedor(proveedor);
                    factura.setUsuario(usuario);
                }
                facturas.add(factura);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 486\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 486, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(facturas != null){
            return facturas;
        }
        return null;
    }

    public List<FacturaDTO> obtenerFacturasVencidas(boolean persistence) throws SQLException{
        List<FacturaDTO> facturas = null;
        FacturaDTO factura = null;
        UsuarioDTO usuario = null;
        ProveedorDTO proveedor = null;
        
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, folio, fecha_registro, fecha_pago, "
                + "subtotal, iva, total, pagada, status, numero_usuario FROM "
                + "factura WHERE status = TRUE AND pagada = FALSE AND DATEDIFF(NOW(), "
                + "fecha_registro) > (SELECT tiempo_limite_pago FROM proveedor_credito "
                + "WHERE status = TRUE AND proveedor_credito.id_proveedor = factura.id_proveedor) "
                + "ORDER BY fecha_registro ASC;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            facturas = new ArrayList<FacturaDTO>();
            while (rs.next()) {
                factura = new FacturaDTO(
                        null,
                        rs.getString("folio"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getTimestamp("fecha_pago"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"), 
                        rs.getDouble("total"),
                        rs.getBoolean("pagada"),
                        rs.getBoolean("status"),
                        null); //usuario
                if(persistence){
                    proveedor = new ProveedorDAO().obtenerProveedorIncCancelado(rs.getInt("id_proveedor"), false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    factura.setProveedor(proveedor);
                    factura.setUsuario(usuario);
                }
                facturas.add(factura);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 1358\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 1358, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(facturas != null){
            return facturas;
        }
        return null;
    }
    
    public FacturaDTO buscarFactura(FacturaDTO facturaBuscar, boolean persistence) throws SQLException{
        FacturaDTO factura = null;
        UsuarioDTO usuario = null;
        ProveedorDTO proveedor = null;
        
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_proveedor, folio, fecha_registro, fecha_pago, "
                + "subtotal, iva, total, pagada, status, numero_usuario FROM "
                + "factura WHERE id_proveedor = ? AND folio = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, facturaBuscar.getProveedor().getIdProveedor());
            pstmt.setString(2, facturaBuscar.getFolio());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                factura = new FacturaDTO(
                        null,
                        rs.getString("folio"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getTimestamp("fecha_pago"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"), 
                        rs.getDouble("total"),
                        rs.getBoolean("pagada"),
                        rs.getBoolean("status"),
                        null); //usuario
                if(persistence){
                    proveedor = new ProveedorDAO().obtenerProveedorIncCancelado(rs.getInt("id_proveedor"), false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    factura.setProveedor(proveedor);
                    factura.setUsuario(usuario);
                }
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 487\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(facturaBuscar.toString(), 487, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(factura != null){
            return factura;
        }
        return null;
    }
    
}
