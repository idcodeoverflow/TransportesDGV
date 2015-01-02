/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.RefaccionDTO;
import beans.SalidaAlmacenDTO;
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
public class SalidaAlmacenDAO {
    
    public boolean agregarSalidaAlmacen(SalidaAlmacenDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO salida_almacen(numero_salida, costo, status, "
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
            JOptionPane.showMessageDialog(null, "Código error: 549\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 549, UserHome.getUsuario(), ex);
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
    public void modificarSalidaAlmacen(SalidaAlmacenDTO salida) throws SQLException{
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
    
    public void eliminarSalidaAlmacen(SalidaAlmacenDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE salida_almacen SET status = ?, fecha_registro = ? WHERE numero_salida = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, salida.getFechaRegistro());
            pstmt.setInt(3, salida.getNumeroSalida());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 550\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 550, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public SalidaAlmacenDTO obtenerSalidaAlmacen(int numeroSalida, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaAlmacenDTO salidaAlmacen = null;
        OrdenReparacionDAO accesoOrdenReparacion = new OrdenReparacionDAO();
        RefaccionDAO accesoReparacion = new RefaccionDAO();
        UsuarioDAO accesoUsuario = new UsuarioDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_salida, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_almacen "
                + "WHERE numero_salida = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroSalida);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaAlmacen = new SalidaAlmacenDTO();
                salidaAlmacen.setCantidad(rs.getDouble("cantidad"));
                salidaAlmacen.setCosto(rs.getDouble("costo"));
                salidaAlmacen.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaAlmacen.setNumeroSalida(rs.getInt("numero_salida"));
                salidaAlmacen.setOrdenReparacion(null);
                salidaAlmacen.setRefaccion(null);
                salidaAlmacen.setStatus(rs.getBoolean("status"));
                salidaAlmacen.setTipo(rs.getInt("tipo"));
                salidaAlmacen.setUsuario(null);
                if(persistence){
                    salidaAlmacen.setOrdenReparacion(accesoOrdenReparacion.obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaAlmacen.setRefaccion(accesoReparacion.obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaAlmacen.setUsuario(accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 551\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaAlmacen.toString(), 551, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaAlmacen;
    }
    
    public int obtenerUltimoNSalidaAlmacenPRefaccion(RefaccionDTO refaccionB, 
            boolean abrir, boolean cerrar) throws SQLException{
        int ultimoNumeroSalida = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pst = null;
        String query = "SELECT MAX(numero_salida) AS max_salida FROM salida_almacen WHERE "
                + "clave_refaccion = ? AND status = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pst = conn.prepareStatement(query);
            pst.setString(1, refaccionB.getClaveRefaccion());
            pst.setBoolean(2, true);
            rs = pst.executeQuery();
            while (rs.next()) {
                ultimoNumeroSalida = rs.getInt("max_salida");
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 552\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoNSalidaAlmacenPRefaccion " + ultimoNumeroSalida, 552, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pst);
            }
        }
        
        return ultimoNumeroSalida;
    }
    
    public List<SalidaAlmacenDTO> obtenerSalidasAlmacen(boolean persistence, boolean abrir, 
            boolean cerrar) throws SQLException{
        OrdenReparacionDAO accesoOrdenReparacion = new OrdenReparacionDAO();
        RefaccionDAO accesoReparacion = new RefaccionDAO();
        UsuarioDAO accesoUsuario = new UsuarioDAO();
        List<SalidaAlmacenDTO> salidasAlmacen = null;
        SalidaAlmacenDTO salidaAlmacen = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pst = null;
        String query = "SELECT numero_salida, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_almacen "
                + "ORDER BY numero_salida DESC;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            salidasAlmacen = new ArrayList<SalidaAlmacenDTO>();
            while (rs.next()) {
                salidaAlmacen = new SalidaAlmacenDTO(
                rs.getInt("numero_salida"),
                rs.getDouble("costo"),
                rs.getBoolean("status"),
                rs.getDouble("cantidad"),
                rs.getTimestamp("fecha_registro"),
                null,//refaccion
                null,//usuario
                null,//orden de reparacion
                rs.getInt("tipo"));
                
                if(persistence){
                    salidaAlmacen.setOrdenReparacion(accesoOrdenReparacion.obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaAlmacen.setRefaccion(accesoReparacion.obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaAlmacen.setUsuario(accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
                salidasAlmacen.add(salidaAlmacen);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 553\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaAlmacen.toString(), 553, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pst);
            }
        }
        
        return salidasAlmacen;
    }
    
    public List<SalidaAlmacenDTO> obtenerSalidasAlmacenPReparacionSinCanceladas(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        OrdenReparacionDAO accesoOrdenReparacion = new OrdenReparacionDAO();
        RefaccionDAO accesoReparacion = new RefaccionDAO();
        UsuarioDAO accesoUsuario = new UsuarioDAO();
        List<SalidaAlmacenDTO> salidasAlmacen = null;
        SalidaAlmacenDTO salidaAlmacen = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_salida, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_almacen "
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
            salidasAlmacen = new ArrayList<SalidaAlmacenDTO>();
            while (rs.next()) {
                salidaAlmacen = new SalidaAlmacenDTO(
                rs.getInt("numero_salida"),
                rs.getDouble("costo"),
                rs.getBoolean("status"),
                rs.getDouble("cantidad"),
                rs.getTimestamp("fecha_registro"),
                null,//refaccion
                null,//usuario
                null,//orden de reparacion
                rs.getInt("tipo"));
                if(persistence){
                    salidaAlmacen.setOrdenReparacion(accesoOrdenReparacion.obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaAlmacen.setRefaccion(accesoReparacion.obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaAlmacen.setUsuario(accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
                salidasAlmacen.add(salidaAlmacen);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 554\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaAlmacen.toString(), 554, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasAlmacen;
    }
    
    public List<SalidaAlmacenDTO> obtenerSalidasAlmacenPRefaccionSinCanceladas(String claveRefaccion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        OrdenReparacionDAO accesoOrdenReparacion = new OrdenReparacionDAO();
        RefaccionDAO accesoReparacion = new RefaccionDAO();
        UsuarioDAO accesoUsuario = new UsuarioDAO();
        List<SalidaAlmacenDTO> salidasAlmacen = null;
        SalidaAlmacenDTO salidaAlmacen = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_salida, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_almacen "
                + "WHERE clave_refaccion = ? AND status = ? ORDER BY fecha_registro DESC;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, claveRefaccion);
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasAlmacen = new ArrayList<SalidaAlmacenDTO>();
            while (rs.next()) {
                salidaAlmacen = new SalidaAlmacenDTO(
                rs.getInt("numero_salida"),
                rs.getDouble("costo"),
                rs.getBoolean("status"),
                rs.getDouble("cantidad"),
                rs.getTimestamp("fecha_registro"),
                null,//refaccion
                null,//usuario
                null,//orden de reparacion
                rs.getInt("tipo"));
                if(persistence){
                    salidaAlmacen.setOrdenReparacion(accesoOrdenReparacion.obtenerOrdenReparacion(rs.getInt("numero_salida"), true, false, false));
                    salidaAlmacen.setRefaccion(accesoReparacion.obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaAlmacen.setUsuario(accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
                salidasAlmacen.add(salidaAlmacen);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 555\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaAlmacen.toString(), 555, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasAlmacen;
    }
    
    public double obtenerTotalSalidasTractoPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_almacen WHERE numero_orden = ? AND status = ? AND numero_salida "
                + "IN(SELECT numero_salida FROM salida_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != ?));";
        double totalSalidasTracto = 0;
        
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
                totalSalidasTracto = rs.getDouble("total_salidas");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 556\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasTractoPReparacion " + totalSalidasTracto, 556, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalSalidasTracto;
    }
    
    public double obtenerTotalSalidasPlanaPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_almacen WHERE numero_orden = ? AND status = ? AND numero_salida "
                + "IN(SELECT numero_salida FROM salida_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = ?));";
        double totalSalidasPlana = 0;
        
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
                totalSalidasPlana = rs.getDouble("total_salidas");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 557\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasPlanaPReparacion " + totalSalidasPlana, 557, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalSalidasPlana;
    }
    
    public int obtenerUltimaSalidaAlmacen() throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_salida) AS ultima_salida FROM salida_almacen;";
        int numeroSalida = 0;
        
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroSalida = rs.getInt("ultima_salida");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 558\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimaSalidaAlmacen " + numeroSalida, 558, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        
        return numeroSalida;
    }
    
    /**
     * No usar esta función solo que se desee borrar la información de forma
     * permanente de la base de datos.
     * @param salidaAlmacen Salida de Almacen que se desea borrar.
     * @return true si el borrado fue correcto, false en caso contrario.
     * @throws SQLException 
     */
    public boolean borrarRegistroSalidaAlmacen(SalidaAlmacenDTO salidaAlmacen) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "DELETE FROM salida_almacen WHERE numero_salida = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, salidaAlmacen.getNumeroSalida());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 559\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al borrar de forma permanente "
                    + "la información de la Base de Datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaAlmacen.toString(), 559, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean reestablecerAutoincrementSalidaAlmacen(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE salida_almacen AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 560\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementSalidaAlmacen " + index, 560, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorClasificacionSalidaAlmacen(SalidaAlmacenDTO salida, int index) throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        return this.borrarRegistroSalidaAlmacen(salida) && this.reestablecerAutoincrementSalidaAlmacen(index);
    }
    
}
