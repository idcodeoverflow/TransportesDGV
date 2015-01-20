/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.RefaccionDTO;
import beans.SalidaTallerDTO;
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
public class SalidaTallerDAO extends SalidaAlmacenDAO {
    
    public boolean agregarSalidaTaller(SalidaTallerDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO salida_taller(id_salida_taller, costo, status, "
                + "cantidad, fecha_registro, clave_refaccion, numero_usuario, "
                + "numero_orden, tipo) VALUES(NULL,?,?,?,NOW(),?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, salida.getCosto());
            pstmt.setBoolean(2, true);
            pstmt.setDouble(3, salida.getCantidad());
            pstmt.setString(4, salida.getRefaccion().getClaveRefaccion());
            pstmt.setInt(5, salida.getUsuario().getNumeroUsuario());
            pstmt.setInt(6, salida.getOrdenReparacion().getNumeroOrden());
            pstmt.setInt(7, salida.getTipo());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2039\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 2039, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    /**
     * Aun no aplica.
     * @param salida
     * @throws SQLException 
     */
    public void modificarSalidaTaller(SalidaTallerDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "";
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
    
    public void eliminarSalidaTaller(SalidaTallerDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE salida_taller SET status = ?, fecha_registro = ? "
                + "WHERE id_salida_taller = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, salida.getFechaRegistro());
            pstmt.setInt(3, salida.getIdSalidaTaller());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2040\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 2040, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public SalidaTallerDTO obtenerSalidaTaller(int idSalidaTaller, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaTallerDTO salidaTaller = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_taller, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden FROM salida_taller "
                + "WHERE id_salida_taller = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSalidaTaller);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaTaller = new SalidaTallerDTO();
                salidaTaller.setIdSalidaTaller(rs.getInt("id_salida_taller"));
                salidaTaller.setCantidad(rs.getDouble("cantidad"));
                salidaTaller.setCosto(rs.getDouble("costo"));
                salidaTaller.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaTaller.setNumeroSalida(rs.getInt("id_salida_taller"));
                salidaTaller.setOrdenReparacion(null);
                salidaTaller.setRefaccion(null);
                salidaTaller.setStatus(rs.getBoolean("status"));
                salidaTaller.setUsuario(null);
                if(persistence){
                    salidaTaller.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaTaller.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaTaller.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2041\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaTaller.toString(), 2041, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaTaller;
    }
    
    public List<SalidaTallerDTO> obtenerSalidasTaller(boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaTallerDTO> salidasTaller = null;
        SalidaTallerDTO salidaTaller = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_taller, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden FROM salida_taller;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            salidasTaller = new ArrayList<SalidaTallerDTO>();
            while (rs.next()) {
                salidaTaller = new SalidaTallerDTO();
                salidaTaller.setIdSalidaTaller(rs.getInt("id_salida_taller"));
                salidaTaller.setCantidad(rs.getDouble("cantidad"));
                salidaTaller.setCosto(rs.getDouble("costo"));
                salidaTaller.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaTaller.setNumeroSalida(rs.getInt("id_salida_taller"));
                salidaTaller.setOrdenReparacion(null);
                salidaTaller.setRefaccion(null);
                salidaTaller.setStatus(rs.getBoolean("status"));
                salidaTaller.setUsuario(null);
                if(persistence){
                    salidaTaller.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaTaller.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaTaller.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasTaller.add(salidaTaller);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2042\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaTaller.toString(), 2042, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasTaller;
    }
    
    public List<SalidaTallerDTO> obtenerSalidasTallerSinCanceladas(boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaTallerDTO> salidasTaller = null;
        SalidaTallerDTO salidaTaller = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_taller, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden FROM salida_taller WHERE status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            salidasTaller = new ArrayList<SalidaTallerDTO>();
            while (rs.next()) {
                salidaTaller = new SalidaTallerDTO();
                salidaTaller.setIdSalidaTaller(rs.getInt("id_salida_taller"));
                salidaTaller.setCantidad(rs.getDouble("cantidad"));
                salidaTaller.setCosto(rs.getDouble("costo"));
                salidaTaller.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaTaller.setNumeroSalida(rs.getInt("id_salida_taller"));
                salidaTaller.setOrdenReparacion(null);
                salidaTaller.setRefaccion(null);
                salidaTaller.setStatus(rs.getBoolean("status"));
                salidaTaller.setUsuario(null);
                if(persistence){
                    salidaTaller.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaTaller.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaTaller.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasTaller.add(salidaTaller);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2043\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaTaller.toString(), 2043, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasTaller;
    }
    
    public List<SalidaTallerDTO> obtenerSalidasTallerPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaTallerDTO> salidasTaller = null;
        SalidaTallerDTO salidaTaller = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_taller, costo, "
                + "status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden "
                + "FROM salida_taller WHERE numero_orden = ? AND status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasTaller = new ArrayList<SalidaTallerDTO>();
            while (rs.next()) {
                salidaTaller = new SalidaTallerDTO();
                salidaTaller.setIdSalidaTaller(rs.getInt("id_salida_taller"));
                salidaTaller.setCantidad(rs.getDouble("cantidad"));
                salidaTaller.setCosto(rs.getDouble("costo"));
                salidaTaller.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaTaller.setNumeroSalida(rs.getInt("id_salida_taller"));
                salidaTaller.setOrdenReparacion(null);
                salidaTaller.setRefaccion(null);
                salidaTaller.setStatus(rs.getBoolean("status"));
                salidaTaller.setUsuario(null);
                if(persistence){
                    salidaTaller.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaTaller.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaTaller.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasTaller.add(salidaTaller);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2044\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaTaller.toString(), 2044, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasTaller;
    }
    
    public List<SalidaTallerDTO> obtenerSalidasTallerPRefaccionSinCanceladas(RefaccionDTO refaccion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaTallerDTO> salidasTaller = null;
        SalidaTallerDTO salidaTaller = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_taller, costo, "
                + "status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden "
                + "FROM salida_taller WHERE clave_refaccion = ? AND status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, refaccion.getClaveRefaccion());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasTaller = new ArrayList<SalidaTallerDTO>();
            while (rs.next()) {
                salidaTaller = new SalidaTallerDTO();
                salidaTaller.setIdSalidaTaller(rs.getInt("id_salida_taller"));
                salidaTaller.setCantidad(rs.getDouble("cantidad"));
                salidaTaller.setCosto(rs.getDouble("costo"));
                salidaTaller.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaTaller.setNumeroSalida(rs.getInt("id_salida_taller"));
                salidaTaller.setOrdenReparacion(null);
                salidaTaller.setRefaccion(null);
                salidaTaller.setStatus(rs.getBoolean("status"));
                salidaTaller.setUsuario(null);
                if(persistence){
                    salidaTaller.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaTaller.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaTaller.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasTaller.add(salidaTaller);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2045\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaTaller.toString(), 2045, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasTaller;
    }
    
    public double obtenerTotalSalidasTallerPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalSalidasTaller = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(costo), 0.0) AS salidas_taller FROM "
                + "salida_taller WHERE numero_orden = ? AND status = ?;";
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
                totalSalidasTaller = rs.getDouble("salidas_taller");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2046\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasTallerPReparacion " + totalSalidasTaller, 2046, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalSalidasTaller;
    }
    
    public int obtenerUltimoIdSalidaTallerCCanceladas() throws SQLException{
        int maxIdSalidaTaller = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_salida_taller) AS max_salida FROM salida_taller;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdSalidaTaller = rs.getInt("max_salida");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2047\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdSalidaTallerCCanceladas " + maxIdSalidaTaller, 2047, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdSalidaTaller;
    }
    
    public boolean reestablecerAutoincrementSalidaTaller(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE salida_taller AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2048\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementSalidaTaller " + index, 2048, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarSalidaTaller() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdSalidaTaller = this.obtenerUltimoIdSalidaTallerCCanceladas();
        return this.reestablecerAutoincrementSalidaTaller(maxIdSalidaTaller + 1);
    }
    
}
