/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.SalidaOperadorDTO;
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
public class SalidaOperadorDAO extends SalidaAlmacenDAO {
    
    public boolean agregarSalidaOperador(SalidaOperadorDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO salida_operador(id_salida_operador, numero_operador, costo, status, "
                + "cantidad, fecha_registro, clave_refaccion, numero_usuario, "
                + "numero_orden, tipo) VALUES(NULL,?,?,?,?,NOW(),?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, salida.getOperador().getNumeroOperador());
            pstmt.setDouble(2, salida.getCosto());
            pstmt.setBoolean(3, true);
            pstmt.setDouble(4, salida.getCantidad());
            pstmt.setString(5, salida.getRefaccion().getClaveRefaccion());
            pstmt.setInt(6, salida.getUsuario().getNumeroUsuario());
            pstmt.setInt(7, salida.getOrdenReparacion().getNumeroOrden());
            pstmt.setInt(8, salida.getTipo());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 579\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 579, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    /**
     * No aplica aun.
     * @throws SQLException 
     */
    public void modificarSalidaOperador() throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = null;
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarSalidaOperador(SalidaOperadorDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE salida_almacen SET status = ?, fecha_registro = ? "
                + "WHERE id_salida_operador = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, salida.getFechaRegistro());
            pstmt.setInt(3, salida.getIdSalidaOperador());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 580\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 580, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public SalidaOperadorDTO obtenerSalidaOperador(int idSalidaOperador, boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        SalidaOperadorDTO salidaOperador = null;
        OperadorDAO accesoOperador = new OperadorDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_operador, numero_operador, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_operador "
                + "WHERE id_salida_operador = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSalidaOperador);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaOperador = new SalidaOperadorDTO(rs.getInt("id_salida_operador"), 
                        accesoOperador.obtenerOperador(rs.getInt("numero_operador"), false, false),
                        null);//salida almacen
                salidaOperador.setCantidad(rs.getDouble("cantidad"));
                salidaOperador.setCosto(rs.getDouble("costo"));
                salidaOperador.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaOperador.setNumeroSalida(rs.getInt("numero_salida"));
                salidaOperador.setOrdenReparacion(null);
                salidaOperador.setRefaccion(null);
                salidaOperador.setStatus(rs.getBoolean("status"));
                salidaOperador.setTipo(rs.getInt("tipo"));
                salidaOperador.setUsuario(null);
                if(persistence){
                    salidaOperador.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaOperador.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaOperador.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 581\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaOperador.toString(), 581, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaOperador;
    }
    /*
    public SalidaOperadorDTO obtenerSalidaOperadorPSalidaAlmacen(int numeroSalida, boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        SalidaOperadorDTO salidaOperador = null;
        SalidaAlmacenDAO accesoSalida = new SalidaAlmacenDAO();
        OperadorDAO accesoOperador = new OperadorDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_operador, numero_operador, numero_salida FROM "
                + "salida_operador WHERE numero_salida = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroSalida);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaOperador = new SalidaOperadorDTO(rs.getInt("id_salida_operador"), 
                        accesoOperador.obtenerOperador(rs.getInt("numero_operador"), false, false), 
                        accesoSalida.obtenerSalidaAlmacen(rs.getInt("numero_salida"), persistence, false, false));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 582\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaOperador.toString(), 582, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaOperador;
    }
    */
    public List<SalidaOperadorDTO> obtenerSalidasOperadores(boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        OperadorDAO accesoOperador = new OperadorDAO();
        List<SalidaOperadorDTO> salidasOperador = null;
        SalidaOperadorDTO salidaOperador = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_operador, numero_operador, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_operador;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            salidasOperador = new ArrayList<SalidaOperadorDTO>();
            while (rs.next()) {
                salidaOperador = new SalidaOperadorDTO(rs.getInt("id_salida_operador"), 
                        accesoOperador.obtenerOperador(rs.getInt("numero_operador"), false, false),
                        null);//salida almacen
                salidaOperador.setCantidad(rs.getDouble("cantidad"));
                salidaOperador.setCosto(rs.getDouble("costo"));
                salidaOperador.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaOperador.setNumeroSalida(rs.getInt("numero_salida"));
                salidaOperador.setOrdenReparacion(null);
                salidaOperador.setRefaccion(null);
                salidaOperador.setStatus(rs.getBoolean("status"));
                salidaOperador.setTipo(rs.getInt("tipo"));
                salidaOperador.setUsuario(null);
                if(persistence){
                    salidaOperador.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaOperador.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaOperador.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasOperador.add(salidaOperador);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 583\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaOperador.toString(), 583, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasOperador;
    }
    
    public List<SalidaOperadorDTO> obtenerSalidasOperadoresPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        OperadorDAO accesoOperador = new OperadorDAO();
        List<SalidaOperadorDTO> salidasOperador = null;
        SalidaOperadorDTO salidaOperador = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_operador, numero_operador, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_operador"
                + "WHERE numero_orden = ? AND status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasOperador = new ArrayList<SalidaOperadorDTO>();
            while (rs.next()) {
                salidaOperador = new SalidaOperadorDTO(rs.getInt("id_salida_operador"), 
                        accesoOperador.obtenerOperador(rs.getInt("numero_operador"), false, false),
                        null);//salida almacen
                salidaOperador.setCantidad(rs.getDouble("cantidad"));
                salidaOperador.setCosto(rs.getDouble("costo"));
                salidaOperador.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaOperador.setNumeroSalida(rs.getInt("numero_salida"));
                salidaOperador.setOrdenReparacion(null);
                salidaOperador.setRefaccion(null);
                salidaOperador.setStatus(rs.getBoolean("status"));
                salidaOperador.setTipo(rs.getInt("tipo"));
                salidaOperador.setUsuario(null);
                if(persistence){
                    salidaOperador.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaOperador.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaOperador.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasOperador.add(salidaOperador);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 584\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaOperador.toString(), 584, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasOperador;
    }

    public double obtenerTotalSalidasOperadorPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalSalidasOperador = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(costo), 0.0) AS salidas_operador FROM "
                + "salida_almacen WHERE numero_orden = ? AND status = ?;";
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
                totalSalidasOperador = rs.getDouble("salidas_operador");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 585\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasOperadorPReparacion " + totalSalidasOperador, 585, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalSalidasOperador;
    }
    
    public int obtenerUltimoIdSalidaOperadorCCanceladas() throws SQLException{
        int maxIdSalidaOperador = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_salida_operador) AS max_salida FROM salida_operador;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdSalidaOperador = rs.getInt("max_salida");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 586\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdSalidaOperadorCCanceladas " + maxIdSalidaOperador, 586, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdSalidaOperador;
    }
    
    public boolean reestablecerAutoincrementSalidaOperador(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE salida_operador AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 587\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementSalidaOperador " + index, 587, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarSalidaOperador() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdSalidaOperador = this.obtenerUltimoIdSalidaOperadorCCanceladas();
        return this.reestablecerAutoincrementSalidaOperador(maxIdSalidaOperador + 1);
    }
    
}
