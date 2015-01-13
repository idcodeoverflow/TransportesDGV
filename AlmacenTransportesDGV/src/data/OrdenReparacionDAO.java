/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OperadorDTO;
import beans.OrdenReparacionDTO;
import beans.SalidaUnidadDTO;
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
public class OrdenReparacionDAO {
    
    public void agregarOrdenReparacion(OrdenReparacionDTO orden) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO orden_reparacion(numero_orden, fecha_entrada, "
                + "fecha_salida, status, numero_operador, numero_usuario) VALUES(NULL,?,NULL,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, orden.getFechaEntrada());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, orden.getOperador().getNumeroOperador());
            pstmt.setInt(4, orden.getUsuario().getNumeroUsuario());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 511\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(orden.toString(), 511, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * Esta accion no esta soportada.
     * @param orden
     * @throws SQLException 
     */
    public void modificarOrdenReparacion(OrdenReparacionDTO orden) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 512\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(orden.toString(), 512, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void terminarOrdenReparacion(OrdenReparacionDTO orden) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE orden_reparacion SET fecha_entrada = ?, "
                + "fecha_salida = NOW() WHERE numero_orden = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, orden.getFechaEntrada());
            pstmt.setInt(2, orden.getNumeroOrden());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 513\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(orden.toString(), 513, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarOrdenReparacion(OrdenReparacionDTO orden) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE orden_reparacion SET status = ?, fecha_entrada = ?, "
                + "fecha_salida = ? WHERE numero_orden = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, orden.getFechaEntrada());
            pstmt.setTimestamp(3, orden.getFechaSalida());
            pstmt.setInt(4, orden.getNumeroOrden());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 514\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(orden.toString(), 514, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public OrdenReparacionDTO obtenerOrdenReparacion(int numeroOrden, boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        OrdenReparacionDTO ordenReparacion = null;
        OperadorDTO operador = null;
        UsuarioDTO usuario = null;
        List<SalidaUnidadDTO> salidas = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_orden, fecha_entrada, fecha_salida, status, "
                + "numero_operador, numero_usuario FROM orden_reparacion WHERE "
                + "numero_orden = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroOrden);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ordenReparacion = new OrdenReparacionDTO();
                ordenReparacion.setNumeroOrden(rs.getInt("numero_orden"));
                ordenReparacion.setFechaEntrada(rs.getTimestamp("fecha_entrada"));
                ordenReparacion.setFechaSalida(rs.getTimestamp("fecha_salida"));
                ordenReparacion.setStatus(rs.getBoolean("status"));
                ordenReparacion.setOperador(null);
                ordenReparacion.setUsuario(null);
                ordenReparacion.setSalidasAlmacen(null);
                ordenReparacion.setCargosDirectos(null);
                if(persistence){
                    operador = new OperadorDAO().obtenerOperador(rs.getInt("numero_operador"), false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    ordenReparacion.setOperador(operador);
                    ordenReparacion.setUsuario(usuario);
                }
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 515\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 515, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return ordenReparacion;
    }
    
    public List<OrdenReparacionDTO> obtenerOrdenesReparacion(boolean persistence) throws SQLException{
        List<OrdenReparacionDTO> ordenesReparacion = null;
        OrdenReparacionDTO ordenReparacion = null;
        OperadorDTO operador = null;
        UsuarioDTO usuario = null;
        //List<SalidaUnidadDTO> ordenesSalida = null;se quito por simplicidad
        
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_orden, fecha_entrada, fecha_salida, status, "
                + "numero_operador, numero_usuario FROM "
                + "orden_reparacion ORDER BY fecha_entrada DESC;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            ordenesReparacion = new ArrayList<OrdenReparacionDTO>();
            while (rs.next()) {
                ordenReparacion = new OrdenReparacionDTO(
                        rs.getInt("numero_orden"),
                        rs.getTimestamp("fecha_entrada"),
                        rs.getTimestamp("fecha_salida"),
                        rs.getBoolean("status"),
                        null, //operador
                        null, //usuario
                        null); //salidas almacen
                if(persistence){
                    operador = new OperadorDAO().obtenerOperador(rs.getInt("numero_operador"), false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    ordenReparacion.setOperador(operador);
                    ordenReparacion.setUsuario(usuario);
                    /*ordenReparacion.setSalidasAlmacen(null);
                    ordenReparacion.setCargosDirectos(null);se agregan estos valores en el constructor*/
                }
                ordenesReparacion.add(ordenReparacion);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 516\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 516, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(ordenesReparacion != null){
            return ordenesReparacion;
        }
        return null;
    }
    
    public List<OrdenReparacionDTO> obtenerOrdenesReparacionPendientes(boolean persistence) throws SQLException{
        List<OrdenReparacionDTO> ordenesReparacion = null;
        OrdenReparacionDTO ordenReparacion = null;
        OperadorDTO operador = null;
        UsuarioDTO usuario = null;
        
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_orden, fecha_entrada, fecha_salida, status, "
                + "numero_operador, numero_usuario FROM orden_reparacion WHERE "
                + "fecha_salida IS NULL AND status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            ordenesReparacion = new ArrayList<OrdenReparacionDTO>();
            while (rs.next()) {
                ordenReparacion = new OrdenReparacionDTO(
                        rs.getInt("numero_orden"),
                        rs.getTimestamp("fecha_entrada"),
                        rs.getTimestamp("fecha_salida"),
                        rs.getBoolean("status"),
                        null, //operador
                        null, //usuario
                        null); //salidas almacen
                if(persistence){
                    operador = new OperadorDAO().obtenerOperador(rs.getInt("numero_operador"), false, false);
                    usuario = new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                    ordenReparacion.setOperador(operador);
                    ordenReparacion.setUsuario(usuario);
                    ordenReparacion.setSalidasAlmacen(null);
                }
                ordenesReparacion.add(ordenReparacion);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 517\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 517, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(ordenesReparacion != null){
            return ordenesReparacion;
        }
        return null;
    }
    
    public int obtenerUltimaOrdenReparacion(boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_orden) AS max_orden FROM orden_reparacion;";
        int max = 0;
        
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                max = rs.getInt("max_orden");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 518\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimaOrdenReparacion " + max, 518, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return max;
    }
    
    public int obtenerUltimoNumeroOrdenReparacionCCanceladas() throws SQLException{
        int maxNumeroOrdenReparacion = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_orden) AS max_orden FROM orden_reparacion;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxNumeroOrdenReparacion = rs.getInt("max_orden");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 519\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoNumeroOrdenReparacionCCanceladas " + maxNumeroOrdenReparacion, 519, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxNumeroOrdenReparacion;
    }
    
    public boolean reestablecerAutoincrementOrdenReparacion(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE orden_reparacion AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 520\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementOrdenReparacion " + index, 520, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarOrdenReparacion() throws SQLException{
        //JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
        //        "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        //int maxNumeroOrdenReparacion = this.obtenerUltimoNumeroOrdenReparacionCCanceladas();
        return true;//this.reestablecerAutoincrementOrdenReparacion(maxNumeroOrdenReparacion + 1);
    }
    
}
