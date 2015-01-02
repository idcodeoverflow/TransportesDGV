/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.EntradaAlmacenDTO;
import beans.FacturaDTO;
import beans.RefaccionDTO;
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
public class EntradaAlmacenDAO {
    
    public void agregarEntradaAlmacen(EntradaAlmacenDTO entrada) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO entrada_almacen(numero_entrada, fecha_registro, cantidad, "
                + "precio_unitario, subtotal, iva, monto, status, clave_refaccion, "
                + "id_proveedor, folio, numero_usuario) VALUES(NULL,NOW(),?,?,?,?,?,?,?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, entrada.getCantidad());
            pstmt.setDouble(2, entrada.getPrecioUnitario());
            pstmt.setDouble(3, entrada.getSubtotal());
            pstmt.setDouble(4, entrada.getIva());
            pstmt.setDouble(5, entrada.getMonto());
            pstmt.setBoolean(6, true);
            pstmt.setString(7, entrada.getRefaccion().getClaveRefaccion());
            pstmt.setInt(8, entrada.getFactura().getProveedor().getIdProveedor());
            pstmt.setString(9, entrada.getFactura().getFolio());
            pstmt.setInt(10, entrada.getUsuario().getNumeroUsuario());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 471\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(entrada.toString(), 471, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarEntradaAlmacen(EntradaAlmacenDTO entrada) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE entrada_almacen SET fecha_registro = ?, cantidad = ?, precio_unitario = ?,"
                + " subtotal = ?, iva = ?, monto = ?, clave_refaccion = ? WHERE numero_entrada = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, entrada.getFechaRegistro());
            pstmt.setDouble(2, entrada.getCantidad());
            pstmt.setDouble(3, entrada.getPrecioUnitario());
            pstmt.setDouble(4, entrada.getSubtotal());
            pstmt.setDouble(5, entrada.getIva());
            pstmt.setDouble(6, entrada.getMonto());
            pstmt.setString(7, entrada.getRefaccion().getClaveRefaccion());
            pstmt.setInt(8, entrada.getNumeroEntrada());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 472\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(entrada.toString(), 472, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarEntradaAlmacen(EntradaAlmacenDTO entrada) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE entrada_almacen SET fecha_registro = ?, status = ? "
                + "WHERE numero_entrada = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, entrada.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, entrada.getNumeroEntrada());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 473\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(entrada.toString(), 473, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public EntradaAlmacenDTO obtenerEntradaAlmacen(int idEntradaAlmacen, boolean persistence) throws SQLException{
        EntradaAlmacenDTO entrada = null;
        RefaccionDTO refaccion = null;
        FacturaDTO factura = null;
        UsuarioDTO usuario = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_entrada, fecha_registro, cantidad, precio_unitario, subtotal, "
                + "iva, monto, status, clave_refaccion, id_proveedor, folio, numero_usuario FROM "
                + "entrada_almacen WHERE numero_entrada = ?";// AND status = ?;";//se elimino el filtrado de registros activos
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idEntradaAlmacen);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            entrada = new EntradaAlmacenDTO();
            while (rs.next()) {
                entrada.setNumeroEntrada(rs.getInt("numero_entrada"));
                entrada.setCantidad(rs.getDouble("cantidad"));
                entrada.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                entrada.setPrecioUnitario(rs.getDouble("precio_unitario"));
                entrada.setSubtotal(rs.getDouble("subtotal"));
                entrada.setIva(rs.getDouble("iva"));
                entrada.setMonto(rs.getDouble("monto"));
                entrada.setStatus(rs.getBoolean("status"));
                entrada.setRefaccion(null);
                entrada.setFactura(null);
                entrada.setUsuario(null);
                if(persistence){
                    refaccion = new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false);
                    factura = new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    entrada.setRefaccion(refaccion);
                    entrada.setFactura(factura);
                    entrada.setUsuario(usuario);
                }
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 474\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(entrada.toString(), 474, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return entrada;
    }
    
    public List<EntradaAlmacenDTO> obtenerEntradasAlmacen(boolean persistence) throws SQLException{
        List<EntradaAlmacenDTO> entradas = null;
        EntradaAlmacenDTO entrada = null;
        FacturaDTO factura = null;
        RefaccionDTO refaccion = null;
        UsuarioDTO usuario = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_entrada, fecha_registro, cantidad, precio_unitario, subtotal, "
                + "iva, monto, status, clave_refaccion, id_proveedor, folio, numero_usuario FROM "
                + "entrada_almacen WHERE status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            entradas = new ArrayList<EntradaAlmacenDTO>();
            while (rs.next()) {
                entrada = new EntradaAlmacenDTO(
                        rs.getInt("numero_entrada"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"),
                        rs.getDouble("monto"),
                        rs.getBoolean("status"),
                        null, //refaccion
                        null, //factura
                        null); //usuario
                if(persistence){
                    refaccion = new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false);
                    factura = new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    entrada.setRefaccion(refaccion);
                    entrada.setFactura(factura);
                    entrada.setUsuario(usuario);
                }
                entradas.add(entrada);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 475\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(entrada.toString(), 475, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(entradas != null){
            return entradas;
        }
        return null;
    }
    
    public List<EntradaAlmacenDTO> obtenerEntradasAlmacenPFactura(FacturaDTO facturaP, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException {
        List<EntradaAlmacenDTO> entradas = null;
        EntradaAlmacenDTO entrada = null;
        FacturaDTO factura = null;
        RefaccionDTO refaccion = null;
        UsuarioDTO usuario = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_entrada, fecha_registro, cantidad, precio_unitario, subtotal, "
                + "iva, monto, status, clave_refaccion, id_proveedor, folio, numero_usuario FROM "
                + "entrada_almacen WHERE status = ? AND id_proveedor = ? AND folio = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, facturaP.getProveedor().getIdProveedor());
            pstmt.setString(3, facturaP.getFolio());
            rs = pstmt.executeQuery();
            entradas = new ArrayList<EntradaAlmacenDTO>();
            while (rs.next()) {
                entrada = new EntradaAlmacenDTO(
                        rs.getInt("numero_entrada"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"),
                        rs.getDouble("monto"),
                        rs.getBoolean("status"),
                        null, //refaccion
                        null, //factura
                        null); //usuario
                if(persistence){
                    refaccion = new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false);
                    factura = new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    entrada.setRefaccion(refaccion);
                    entrada.setFactura(factura);
                    entrada.setUsuario(usuario);
                }
                entradas.add(entrada);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 476\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(entrada.toString(), 476, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(entradas != null){
            return entradas;
        }
        return null;
    }
    
    public List<EntradaAlmacenDTO> obtenerEntradasAlmacenPRefaccionSinCanceladas(String claveRefaccion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<EntradaAlmacenDTO> entradas = null;
        EntradaAlmacenDTO entrada = null;
        FacturaDTO factura = null;
        RefaccionDTO refaccion = null;
        UsuarioDTO usuario = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_entrada, fecha_registro, cantidad, precio_unitario, subtotal, "
                + "iva, monto, status, clave_refaccion, id_proveedor, folio, numero_usuario FROM "
                + "entrada_almacen WHERE status = ? AND clave_refaccion = ? ORDER BY "
                + "numero_entrada DESC;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, claveRefaccion);
            rs = pstmt.executeQuery();
            entradas = new ArrayList<EntradaAlmacenDTO>();
            while (rs.next()) {
                entrada = new EntradaAlmacenDTO(
                        rs.getInt("numero_entrada"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"),
                        rs.getDouble("monto"),
                        rs.getBoolean("status"),
                        null, //refaccion
                        null, //factura
                        null); //usuario
                if(persistence){
                    refaccion = new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false);
                    factura = new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    entrada.setRefaccion(refaccion);
                    entrada.setFactura(factura);
                    entrada.setUsuario(usuario);
                }
                entradas.add(entrada);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 477\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(entrada.toString(), 477, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(entradas != null){
            return entradas;
        }
        return null;
    }
    
    public int obtenerUltimoNumeroEntradaAlmacenCCanceladas() throws SQLException{
        int maxNumeroEntradaAlmacen = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_entrada) AS max_entrada FROM entrada_almacen;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxNumeroEntradaAlmacen = rs.getInt("max_entrada");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 478\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoNumeroEntradaAlmacenCCanceladas " + maxNumeroEntradaAlmacen, 478, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxNumeroEntradaAlmacen;
    }
    
    public boolean reestablecerAutoincrementEntradaAlmacen(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE entrada_almacen AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 479\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementEntradaAlmacen " + index, 479, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarEntradaAlmacen() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxNumeroEntradaAlmacen = this.obtenerUltimoNumeroEntradaAlmacenCCanceladas();
        return this.reestablecerAutoincrementEntradaAlmacen(maxNumeroEntradaAlmacen + 1);
    }
    
}
