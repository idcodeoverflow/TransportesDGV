/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.SalidaUnidadDTO;
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
public class SalidaUnidadDAO extends SalidaAlmacenDAO {
    
    public boolean agregarSalidaUnidad(SalidaUnidadDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO salida_unidad(id_salida_unidad, clave, costo, status, "
                + "cantidad, fecha_registro, clave_refaccion, numero_usuario, "
                + "numero_orden, tipo) VALUES(NULL,?,?,?,?,NOW(),?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, salida.getTransporte().getClave());
            pstmt.setDouble(2, salida.getCosto());
            pstmt.setBoolean(3, true);
            pstmt.setDouble(4, salida.getCantidad());
            pstmt.setString(5, salida.getRefaccion().getClaveRefaccion());
            pstmt.setInt(6, salida.getUsuario().getNumeroUsuario());
            pstmt.setInt(7, salida.getOrdenReparacion().getNumeroOrden());
            pstmt.setInt(8, salida.getTipo());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 588\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 588, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    /**
     * No aplica aun.
     * @param salida
     * @throws SQLException 
     */
    public void modificarSalidaUnidad(SalidaUnidadDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 589\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 589, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarSalidaUnidad(SalidaUnidadDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE salida_almacen SET status = ?, fecha_registro = ? "
                + "WHERE id_salida_unidad = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, salida.getFechaRegistro());
            pstmt.setInt(3, salida.getIdSalidaUnidad());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 590\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 590, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public SalidaUnidadDTO obtenerSalidaUnidad(int idSalidaUnidad, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaUnidadDTO salidaUnidad = null;
        UnidadTransporteDAO accesoUnidad = new UnidadTransporteDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_unidad, clave, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_unidad "
                + "WHERE id_salida_unidad = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSalidaUnidad);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaUnidad = new SalidaUnidadDTO(rs.getInt("id_salida_unidad"), 
                        accesoUnidad.obtenerUnidad(rs.getString("clave"), persistence, false, false), 
                        null);//salida almacen
                salidaUnidad.setCantidad(rs.getDouble("cantidad"));
                salidaUnidad.setCosto(rs.getDouble("costo"));
                salidaUnidad.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaUnidad.setNumeroSalida(rs.getInt("numero_salida"));
                salidaUnidad.setOrdenReparacion(null);
                salidaUnidad.setRefaccion(null);
                salidaUnidad.setStatus(rs.getBoolean("status"));
                salidaUnidad.setTipo(rs.getInt("tipo"));
                salidaUnidad.setUsuario(null);
                if(persistence){
                    salidaUnidad.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaUnidad.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaUnidad.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 591\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaUnidad.toString(), 591, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaUnidad;
    }
    /*
    public SalidaUnidadDTO obtenerSalidaUnidadPSalidaUnidad(int numeroSalida, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaUnidadDTO salidaUnidad = null;
        SalidaAlmacenDAO accesoSalida = new SalidaAlmacenDAO();
        UnidadTransporteDAO accesoUnidad = new UnidadTransporteDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_unidad, numero_salida, clave FROM "
                + "salida_unidad WHERE numero_salida = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroSalida);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaUnidad = new SalidaUnidadDTO(rs.getInt("id_salida_unidad"), 
                        accesoUnidad.obtenerUnidad(rs.getString("clave"), persistence, false, false), 
                        accesoSalida.obtenerSalidaAlmacen(rs.getInt("numero_salida"), persistence, false, false));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 592\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaUnidad.toString(), 592, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaUnidad;
    }
    */
    public List<SalidaUnidadDTO> obtenerSalidasUnidad(boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        UnidadTransporteDAO accesoUnidad = new UnidadTransporteDAO();
        List<SalidaUnidadDTO> salidasUnidad = null;
        SalidaUnidadDTO salidaUnidad = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_unidad, clave, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_unidad;";
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            salidasUnidad = new ArrayList<SalidaUnidadDTO>();
            while (rs.next()) {
                salidaUnidad = new SalidaUnidadDTO(rs.getInt("id_salida_unidad"), 
                        accesoUnidad.obtenerUnidad(rs.getString("clave"), persistence, false, false), 
                        null);//salida almacen
                salidaUnidad.setCantidad(rs.getDouble("cantidad"));
                salidaUnidad.setCosto(rs.getDouble("costo"));
                salidaUnidad.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaUnidad.setNumeroSalida(rs.getInt("numero_salida"));
                salidaUnidad.setOrdenReparacion(null);
                salidaUnidad.setRefaccion(null);
                salidaUnidad.setStatus(rs.getBoolean("status"));
                salidaUnidad.setTipo(rs.getInt("tipo"));
                salidaUnidad.setUsuario(null);
                if(persistence){
                    salidaUnidad.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaUnidad.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaUnidad.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
                salidasUnidad.add(salidaUnidad);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 593\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaUnidad.toString(), 593, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasUnidad;
    }
    
    public List<SalidaUnidadDTO> obtenerSalidasUnidadPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        UnidadTransporteDAO accesoUnidad = new UnidadTransporteDAO();
        List<SalidaUnidadDTO> salidasUnidad = null;
        SalidaUnidadDTO salidaUnidad = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_unidad, clave, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_unidad "
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
            salidasUnidad = new ArrayList<SalidaUnidadDTO>();
            while (rs.next()) {
                salidaUnidad = new SalidaUnidadDTO(rs.getInt("id_salida_unidad"), 
                        accesoUnidad.obtenerUnidad(rs.getString("clave"), persistence, false, false), 
                        null);//salida almacen
                salidaUnidad.setCantidad(rs.getDouble("cantidad"));
                salidaUnidad.setCosto(rs.getDouble("costo"));
                salidaUnidad.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaUnidad.setNumeroSalida(rs.getInt("numero_salida"));
                salidaUnidad.setOrdenReparacion(null);
                salidaUnidad.setRefaccion(null);
                salidaUnidad.setStatus(rs.getBoolean("status"));
                salidaUnidad.setTipo(rs.getInt("tipo"));
                salidaUnidad.setUsuario(null);
                if(persistence){
                    salidaUnidad.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaUnidad.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaUnidad.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
                salidasUnidad.add(salidaUnidad);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 594\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaUnidad.toString(), 594, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasUnidad;
    }
    
    public double obtenerTotalSalidasUnidadesPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalSalidasUnidades = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(costo), 0.0) AS salidas_unidades FROM "
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
                totalSalidasUnidades = rs.getDouble("salidas_unidades");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 595\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasUnidadesPReparacion " + totalSalidasUnidades, 595, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalSalidasUnidades;
    }
    
    public int obtenerUltimoIdSalidaUnidadCCanceladas() throws SQLException{
        int maxIdSalidaUnidad = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_salida_unidad) AS max_salida FROM salida_unidad;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdSalidaUnidad = rs.getInt("max_salida");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 596\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdSalidaUnidadCCanceladas " + maxIdSalidaUnidad, 596, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdSalidaUnidad;
    }
    
    public boolean reestablecerAutoincrementSalidaUnidad(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE salida_unidad AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 597\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementSalidaUnidad " + index, 597, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarSalidaUnidad() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdSalidaUnidad = this.obtenerUltimoIdSalidaUnidadCCanceladas();
        return this.reestablecerAutoincrementSalidaUnidad(maxIdSalidaUnidad + 1);
    }
    
}
