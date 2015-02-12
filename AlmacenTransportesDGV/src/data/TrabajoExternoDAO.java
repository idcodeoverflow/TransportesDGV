/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.FacturaDTO;
import beans.OrdenReparacionDTO;
import beans.TrabajoExternoDTO;
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
public class TrabajoExternoDAO {
    
    public void agregarTrabajoExterno(TrabajoExternoDTO trabajo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO trabajo_externo(numero_trabajo_externo, fecha_registro, descripcion, "
                + "cantidad, precio_unitario, subtotal, iva, monto, status, id_proveedor, "
                + "folio, numero_orden, clave, numero_usuario) VALUES(NULL,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, trabajo.getFechaRegistro());
            pstmt.setString(2, trabajo.getDescripcion());
            pstmt.setDouble(3, trabajo.getCantidad());
            pstmt.setDouble(4, trabajo.getPrecioUnitario());
            pstmt.setDouble(5, trabajo.getSubtotal());
            pstmt.setDouble(6, trabajo.getIva());
            pstmt.setDouble(7, trabajo.getMonto());
            pstmt.setBoolean(8, true);
            pstmt.setInt(9, trabajo.getFactura().getProveedor().getIdProveedor());
            pstmt.setString(10, trabajo.getFactura().getFolio());
            pstmt.setInt(11, trabajo.getOrdenReparacion().getNumeroOrden());
            pstmt.setString(12, trabajo.getUnidadTransporte().getClave());
            pstmt.setInt(13, trabajo.getUsuario().getNumeroUsuario());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 602\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(trabajo.toString(), 602, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarTrabajoExterno(TrabajoExternoDTO trabajo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE trabajo_externo SET fecha_registro = ?, descripcion = ?, cantidad = ?, "
                + "precio_unitario = ?, subtotal = ?, iva = ?, monto = ?, id_proveedor = ?, "
                + "folio = ?, numero_orden = ?, clave = ? WHERE numero_trabajo_externo = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, trabajo.getFechaRegistro());
            pstmt.setString(2, trabajo.getDescripcion());
            pstmt.setDouble(3, trabajo.getCantidad());
            pstmt.setDouble(4, trabajo.getPrecioUnitario());
            pstmt.setDouble(5, trabajo.getSubtotal());
            pstmt.setDouble(6, trabajo.getIva());
            pstmt.setDouble(7, trabajo.getMonto());
            pstmt.setInt(8, trabajo.getFactura().getProveedor().getIdProveedor());
            pstmt.setString(9, trabajo.getFactura().getFolio());
            pstmt.setInt(10, trabajo.getOrdenReparacion().getNumeroOrden());
            pstmt.setString(11, trabajo.getUnidadTransporte().getClave());
            pstmt.setInt(12, trabajo.getNumeroTrabajoExterno());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 603\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(trabajo.toString(), 603, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarTrabajoExterno(TrabajoExternoDTO trabajo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE trabajo_externo SET fecha_registro = ?, status = ? WHERE numero_trabajo_externo = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, trabajo.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, trabajo.getNumeroTrabajoExterno());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 604\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(trabajo.toString(), 604, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public TrabajoExternoDTO obtenerTrabajoExterno(int numeroTrabajoExterno, boolean persistence) throws SQLException{
        TrabajoExternoDTO trabajo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_trabajo_externo, fecha_registro, descripcion, cantidad, "
                + "precio_unitario, subtotal, iva, monto, status, id_proveedor, "
                + "folio, numero_orden, clave, numero_usuario FROM trabajo_externo WHERE numero_trabajo_externo = ?";// AND status = ?;";//Se elimino el filtrado de registros activos
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroTrabajoExterno);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            trabajo = new TrabajoExternoDTO();
            while (rs.next()) {
                trabajo.setNumeroTrabajoExterno(rs.getInt("numero_trabajo_externo"));
                trabajo.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                trabajo.setDescripcion(rs.getString("descripcion"));
                trabajo.setCantidad(rs.getDouble("cantidad"));
                trabajo.setFactura(null);
                trabajo.setIva(rs.getDouble("iva"));
                trabajo.setMonto(rs.getDouble("monto"));
                trabajo.setPrecioUnitario(rs.getDouble("precio_unitario"));
                trabajo.setStatus(rs.getBoolean("status"));
                trabajo.setSubtotal(rs.getDouble("subtotal"));
                trabajo.setOrdenReparacion(null);
                trabajo.setUnidadTransporte(null);
                trabajo.setUsuario(null);
                if(persistence){
                    trabajo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    trabajo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    trabajo.setUnidadTransporte(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                    trabajo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 605\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(trabajo.toString(), 605, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return trabajo;
    }
    
    public List<TrabajoExternoDTO> obtenerTrabajosExternos(boolean persistence) throws SQLException{
        List<TrabajoExternoDTO> trabajos = null;
        TrabajoExternoDTO trabajo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_trabajo_externo, fecha_registro, descripcion, cantidad, "
                + "precio_unitario, subtotal, iva, monto, status, id_proveedor, "
                + "folio, numero_orden, clave, numero_usuario FROM trabajo_externo WHERE status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            trabajos = new ArrayList<TrabajoExternoDTO>();
            while (rs.next()) {
                trabajo = new TrabajoExternoDTO(
                rs.getInt("numero_trabajo_externo"),
                rs.getTimestamp("fecha_registro"),
                rs.getString("descripcion"),
                rs.getDouble("cantidad"),
                rs.getDouble("precio_unitario"),
                rs.getDouble("subtotal"),
                rs.getDouble("iva"),
                rs.getDouble("monto"),
                rs.getBoolean("status"),
                null,//factura
                null,//orden de reparacion       
                null,//unidad de transporte
                null);//usuario
                
                if(persistence){
                    trabajo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    trabajo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    trabajo.setUnidadTransporte(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                    trabajo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                trabajos.add(trabajo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 606\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(trabajo.toString(), 606, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(trabajos != null){
            return trabajos;
        }
        return null;
    }
    
    public List<TrabajoExternoDTO> obtenerTrabajosExternosPFactura(FacturaDTO factura, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<TrabajoExternoDTO> trabajos = null;
        TrabajoExternoDTO trabajo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_trabajo_externo, fecha_registro, descripcion, cantidad, "
                + "precio_unitario, subtotal, iva, monto, status, id_proveedor, "
                + "folio, numero_orden, clave, numero_usuario FROM trabajo_externo WHERE status = ? AND id_proveedor = ? "
                + "AND folio = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, factura.getProveedor().getIdProveedor());
            pstmt.setString(3, factura.getFolio());
            rs = pstmt.executeQuery();
            trabajos = new ArrayList<TrabajoExternoDTO>();
            while (rs.next()) {
                trabajo = new TrabajoExternoDTO(
                rs.getInt("numero_trabajo_externo"),
                rs.getTimestamp("fecha_registro"),
                rs.getString("descripcion"),
                rs.getDouble("cantidad"),
                rs.getDouble("precio_unitario"),
                rs.getDouble("subtotal"),
                rs.getDouble("iva"),
                rs.getDouble("monto"),
                rs.getBoolean("status"),
                null,//factura
                null,//orden de reparacion
                null,//unidad de transporte
                null);//usuario
                
                if(persistence){
                    trabajo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    trabajo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    trabajo.setUnidadTransporte(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                    trabajo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                trabajos.add(trabajo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 607\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(trabajo.toString(), 607, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(trabajos != null){
            return trabajos;
        }
        return null;
    }
    
    public List<TrabajoExternoDTO> obtenerTrabajosExternosPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<TrabajoExternoDTO> trabajos = null;
        TrabajoExternoDTO trabajo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_trabajo_externo, fecha_registro, descripcion, cantidad, "
                + "precio_unitario, subtotal, iva, monto, status, id_proveedor, "
                + "folio, numero_orden, clave, numero_usuario FROM trabajo_externo WHERE status = ? "
                + "AND numero_orden = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, ordenReparacion.getNumeroOrden());
            rs = pstmt.executeQuery();
            trabajos = new ArrayList<TrabajoExternoDTO>();
            while (rs.next()) {
                trabajo = new TrabajoExternoDTO(
                rs.getInt("numero_trabajo_externo"),
                rs.getTimestamp("fecha_registro"),
                rs.getString("descripcion"),
                rs.getDouble("cantidad"),
                rs.getDouble("precio_unitario"),
                rs.getDouble("subtotal"),
                rs.getDouble("iva"),
                rs.getDouble("monto"),
                rs.getBoolean("status"),
                null,//factura
                null,//orden de reparacion
                null,//unidad de transporte
                null);//usuario
                
                if(persistence){
                    trabajo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    trabajo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    trabajo.setUnidadTransporte(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                    trabajo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                trabajos.add(trabajo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 608\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(trabajo.toString(), 608, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(trabajos != null){
            return trabajos;
        }
        return null;
    }
    
    public double obtenerTotalTrabajosExternoPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalSalidasEspeciales = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(monto), 0.0) AS trabajos_externos FROM "
                + "trabajo_externo WHERE numero_orden = ? AND status = ?;";
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalSalidasEspeciales = rs.getDouble("trabajos_externos");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 609\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalTrabajosExternoPReparacion " + totalSalidasEspeciales, 609, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalSalidasEspeciales;
    }
    
    public double obtenerTotalTrabajosExternosTractoPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(monto), 0.0) AS total_trabajos FROM trabajo_externo WHERE numero_orden = ? AND status = ? AND clave "
                + "IN(SELECT clave FROM unidad_transporte WHERE id_tipo != ?);";
        double totalTrabajosExternos = 0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalTrabajosExternos = rs.getDouble("total_trabajos");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 610\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalTrabajosExternosTractoPReparacion " + totalTrabajosExternos, 610, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalTrabajosExternos;
    }
    
    public double obtenerTotalTrabajosExternosPlanaPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(monto), 0.0) AS total_trabajos FROM trabajo_externo WHERE numero_orden = ? AND status = ? AND clave "
                + "IN(SELECT clave FROM unidad_transporte WHERE id_tipo = ?);";
        double totalTrabajosExternos = 0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalTrabajosExternos = rs.getDouble("total_trabajos");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 611\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalTrabajosExternosPlanaPReparacion " + totalTrabajosExternos, 611, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalTrabajosExternos;
    }
    
    public int obtenerUltimoNumeroTrabajoExternoCCanceladas() throws SQLException{
        int maxNumeroTrabajoExterno = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_trabajo_externo) AS max_trabajo FROM trabajo_externo;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxNumeroTrabajoExterno = rs.getInt("max_trabajo");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 612\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoNumeroTrabajoExternoCCanceladas " + maxNumeroTrabajoExterno, 612, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxNumeroTrabajoExterno;
    }
    
    public boolean reestablecerAutoincrementTrabajoExterno(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE trabajo_externo AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 613\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementTrabajoExterno " + index, 613, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarTrabajoExterno() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxNumeroTrabajoExterno = this.obtenerUltimoNumeroTrabajoExternoCCanceladas();
        return this.reestablecerAutoincrementTrabajoExterno(maxNumeroTrabajoExterno + 1);
    }
    
}
