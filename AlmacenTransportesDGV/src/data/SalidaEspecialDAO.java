/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.RefaccionDTO;
import beans.SalidaEspecialDTO;
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
public class SalidaEspecialDAO extends SalidaAlmacenDAO {
    
    public boolean agregarSalidaEspecial(SalidaEspecialDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO salida_especial(id_salida_especial, nombre_beneficiario, costo, status, "
                + "cantidad, fecha_registro, clave_refaccion, numero_usuario, "
                + "numero_orden, tipo) VALUES(NULL,?,?,?,?,NOW(),?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, salida.getNombreBeneficiario());
            pstmt.setDouble(2, salida.getCosto());
            pstmt.setBoolean(3, true);
            pstmt.setDouble(4, salida.getCantidad());
            pstmt.setString(5, salida.getRefaccion().getClaveRefaccion());
            pstmt.setInt(6, salida.getUsuario().getNumeroUsuario());
            pstmt.setInt(7, salida.getOrdenReparacion().getNumeroOrden());
            pstmt.setInt(8, salida.getTipo());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 570\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 570, UserHome.getUsuario(), ex);
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
    public void modificarSalidaEspecial(SalidaEspecialDTO salida) throws SQLException{
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
    
    public void eliminarSalidaEspecial(SalidaEspecialDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE salida_almacen SET status = ?, fecha_registro = ? "
                + "WHERE id_salida_especial = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, salida.getFechaRegistro());
            pstmt.setInt(3, salida.getIdSalidaEspecial());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 571\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 571, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public SalidaEspecialDTO obtenerSalidaEspecial(int idSalidaEspecial, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaEspecialDTO salidaEspecial = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_especial, nombre_beneficiario, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_especial "
                + "WHERE id_salida_especial = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSalidaEspecial);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaEspecial = new SalidaEspecialDTO(rs.getInt("id_salida_especial"), 
                        rs.getString("nombre_beneficiario"), 
                        null);//salida almacen
                salidaEspecial.setCantidad(rs.getDouble("cantidad"));
                salidaEspecial.setCosto(rs.getDouble("costo"));
                salidaEspecial.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaEspecial.setNumeroSalida(rs.getInt("numero_salida"));
                salidaEspecial.setOrdenReparacion(null);
                salidaEspecial.setRefaccion(null);
                salidaEspecial.setStatus(rs.getBoolean("status"));
                salidaEspecial.setTipo(rs.getInt("tipo"));
                salidaEspecial.setUsuario(null);
                if(persistence){
                    salidaEspecial.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaEspecial.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaEspecial.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 572\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaEspecial.toString(), 572, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaEspecial;
    }
    /*
    public SalidaEspecialDTO obtenerSalidaEspecialPSalidaAlmacen(int numeroSalida, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaEspecialDTO salidaEspecial = null;
        SalidaAlmacenDAO accesoSalida = new SalidaAlmacenDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_especial, nombre_beneficiario, numero_salida FROM "
                + "salida_especial WHERE numero_salida = ?;";
        
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroSalida);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaEspecial = new SalidaEspecialDTO(rs.getInt("id_salida_especial"), 
                        rs.getString("nombre_beneficiario"), 
                        accesoSalida.obtenerSalidaAlmacen(rs.getInt("numero_salida"), persistence, false, false));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 573\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaEspecial.toString(), 573, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaEspecial;
    }
    */
    public List<SalidaEspecialDTO> obtenerSalidasEspeciales(boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaEspecialDTO> salidasEspeciales = null;
        SalidaEspecialDTO salidaEspecial = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_especial, nombre_beneficiario, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_especial;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            salidasEspeciales = new ArrayList<SalidaEspecialDTO>();
            while (rs.next()) {
                salidaEspecial = new SalidaEspecialDTO(rs.getInt("id_salida_especial"), 
                        rs.getString("nombre_beneficiario"), 
                        null);//salida almacen
                salidaEspecial.setCantidad(rs.getDouble("cantidad"));
                salidaEspecial.setCosto(rs.getDouble("costo"));
                salidaEspecial.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaEspecial.setNumeroSalida(rs.getInt("numero_salida"));
                salidaEspecial.setOrdenReparacion(null);
                salidaEspecial.setRefaccion(null);
                salidaEspecial.setStatus(rs.getBoolean("status"));
                salidaEspecial.setTipo(rs.getInt("tipo"));
                salidaEspecial.setUsuario(null);
                if(persistence){
                    salidaEspecial.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaEspecial.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaEspecial.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasEspeciales.add(salidaEspecial);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 574\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaEspecial.toString(), 574, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasEspeciales;
    }
    
    public List<SalidaEspecialDTO> obtenerSalidasEspecialesSinCanceladas(boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaEspecialDTO> salidasEspeciales = null;
        SalidaEspecialDTO salidaEspecial = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_especial, nombre_beneficiario, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_especial WHERE status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            salidasEspeciales = new ArrayList<SalidaEspecialDTO>();
            while (rs.next()) {
                salidaEspecial = new SalidaEspecialDTO(rs.getInt("id_salida_especial"), 
                        rs.getString("nombre_beneficiario"), 
                        null);//salida almacen
                salidaEspecial.setCantidad(rs.getDouble("cantidad"));
                salidaEspecial.setCosto(rs.getDouble("costo"));
                salidaEspecial.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaEspecial.setNumeroSalida(rs.getInt("numero_salida"));
                salidaEspecial.setOrdenReparacion(null);
                salidaEspecial.setRefaccion(null);
                salidaEspecial.setStatus(rs.getBoolean("status"));
                salidaEspecial.setTipo(rs.getInt("tipo"));
                salidaEspecial.setUsuario(null);
                if(persistence){
                    salidaEspecial.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaEspecial.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaEspecial.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasEspeciales.add(salidaEspecial);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2000\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaEspecial.toString(), 2000, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasEspeciales;
    }
    
    public List<SalidaEspecialDTO> obtenerSalidasEspecialesPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaEspecialDTO> salidasEspeciales = null;
        SalidaEspecialDTO salidaEspecial = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_especial, nombre_beneficiario, costo, "
                + "status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo "
                + "FROM salida_especial WHERE numero_orden = ? AND status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasEspeciales = new ArrayList<SalidaEspecialDTO>();
            while (rs.next()) {
                salidaEspecial = new SalidaEspecialDTO(rs.getInt("id_salida_especial"), 
                        rs.getString("nombre_beneficiario"), 
                        null);//salida almacen
                salidaEspecial.setCantidad(rs.getDouble("cantidad"));
                salidaEspecial.setCosto(rs.getDouble("costo"));
                salidaEspecial.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaEspecial.setNumeroSalida(rs.getInt("numero_salida"));
                salidaEspecial.setOrdenReparacion(null);
                salidaEspecial.setRefaccion(null);
                salidaEspecial.setStatus(rs.getBoolean("status"));
                salidaEspecial.setTipo(rs.getInt("tipo"));
                salidaEspecial.setUsuario(null);
                if(persistence){
                    salidaEspecial.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaEspecial.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaEspecial.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasEspeciales.add(salidaEspecial);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 575\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaEspecial.toString(), 575, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasEspeciales;
    }
    
    public List<SalidaEspecialDTO> obtenerSalidasEspecialesPRefaccionSinCanceladas(RefaccionDTO refaccion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<SalidaEspecialDTO> salidasEspeciales = null;
        SalidaEspecialDTO salidaEspecial = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_especial, nombre_beneficiario, costo, "
                + "status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo "
                + "FROM salida_especial WHERE clave_refaccion = ? AND status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, refaccion.getClaveRefaccion());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasEspeciales = new ArrayList<SalidaEspecialDTO>();
            while (rs.next()) {
                salidaEspecial = new SalidaEspecialDTO(rs.getInt("id_salida_especial"), 
                        rs.getString("nombre_beneficiario"), 
                        null);//salida almacen
                salidaEspecial.setCantidad(rs.getDouble("cantidad"));
                salidaEspecial.setCosto(rs.getDouble("costo"));
                salidaEspecial.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                salidaEspecial.setNumeroSalida(rs.getInt("numero_salida"));
                salidaEspecial.setOrdenReparacion(null);
                salidaEspecial.setRefaccion(null);
                salidaEspecial.setStatus(rs.getBoolean("status"));
                salidaEspecial.setTipo(rs.getInt("tipo"));
                salidaEspecial.setUsuario(null);
                if(persistence){
                    salidaEspecial.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaEspecial.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaEspecial.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                salidasEspeciales.add(salidaEspecial);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2003\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaEspecial.toString(), 2003, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasEspeciales;
    }
    
    public double obtenerTotalSalidasEspecialesPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalSalidasEspeciales = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(costo), 0.0) AS salidas_especiales FROM "
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
                totalSalidasEspeciales = rs.getDouble("salidas_especiales");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 576\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasEspecialesPReparacion " + totalSalidasEspeciales, 576, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalSalidasEspeciales;
    }
    
    public int obtenerUltimoIdSalidaEspecialCCanceladas() throws SQLException{
        int maxIdSalidaEspecial = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_salida_especial) AS max_salida FROM salida_especial;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdSalidaEspecial = rs.getInt("max_salida");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 577\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdSalidaEspecialCCanceladas " + maxIdSalidaEspecial, 577, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdSalidaEspecial;
    }
    
    public boolean reestablecerAutoincrementSalidaEspecial(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE salida_especial AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 578\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementSalidaEspecial " + index, 578, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarSalidaEspecial() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdSalidaEspecial = this.obtenerUltimoIdSalidaEspecialCCanceladas();
        return this.reestablecerAutoincrementSalidaEspecial(maxIdSalidaEspecial + 1);
    }
    
}
