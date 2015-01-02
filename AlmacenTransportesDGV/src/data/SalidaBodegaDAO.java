/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.SalidaBodegaDTO;
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
public class SalidaBodegaDAO extends SalidaAlmacenDAO {
    
    public boolean agregarSalidaBodega(SalidaBodegaDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO salida_bodega(id_salida_bodega, id_bodega, numero_salida) VALUES(NULL,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, salida.getBodega().getIdBodega());
            pstmt.setInt(2, salida.getNumeroSalida());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 561\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 561, UserHome.getUsuario(), ex);
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
    public void modificarSalidaBodega(SalidaBodegaDTO salida) throws SQLException{
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
    
    public void eliminarSalidaBodega(SalidaBodegaDTO salida) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE salida_almacen SET status = ?, fecha_registro = ? "
                + "WHERE numero_salida = (SELECT numero_salida FROM salida_bodega "
                + "WHERE id_salida_bodega = ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, salida.getFechaRegistro());
            pstmt.setInt(3, salida.getIdSalidaBodega());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 562\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salida.toString(), 562, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public SalidaBodegaDTO obtenerSalidaBodega(int idSalidaBodega, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaBodegaDTO salidaBodega = null;
        SalidaAlmacenDAO accesoSalida = new SalidaAlmacenDAO();
        BodegaReciclajeDAO accesoBodega = new BodegaReciclajeDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_bodega, id_bodega, numero_salida FROM "
                + "salida_bodega WHERE id_salida_bodega = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSalidaBodega);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaBodega = new SalidaBodegaDTO(rs.getInt("id_salida_bodega"), 
                        accesoBodega.obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false), 
                        accesoSalida.obtenerSalidaAlmacen(rs.getInt("numero_salida"), persistence, false, false));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 563\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaBodega.toString(), 563, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaBodega;
    }
    
    public SalidaBodegaDTO obtenerSalidaBodegaPSalidaAlmacen(int numeroSalida, boolean persistence, 
            boolean abrir, boolean cerrar) throws SQLException{
        SalidaBodegaDTO salidaBodega = null;
        SalidaAlmacenDAO accesoSalida = new SalidaAlmacenDAO();
        BodegaReciclajeDAO accesoBodega = new BodegaReciclajeDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_bodega, id_bodega, numero_salida FROM "
                + "salida_bodega WHERE numero_salida = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroSalida);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salidaBodega = new SalidaBodegaDTO(rs.getInt("id_salida_bodega"), 
                        accesoBodega.obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false), 
                        accesoSalida.obtenerSalidaAlmacen(rs.getInt("numero_salida"), persistence, false, false));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 564\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaBodega.toString(), 564, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidaBodega;
    }
    
    public List<SalidaBodegaDTO> obtenerSalidasBodega(boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        SalidaAlmacenDAO accesoSalida = new SalidaAlmacenDAO();
        BodegaReciclajeDAO accesoBodega = new BodegaReciclajeDAO();
        List<SalidaBodegaDTO> salidasBodega = null;
        SalidaBodegaDTO salidaBodega = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_bodega, id_bodega, numero_salida FROM salida_bodega;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            salidasBodega = new ArrayList<SalidaBodegaDTO>();
            while (rs.next()) {
                salidaBodega = new SalidaBodegaDTO(rs.getInt("id_salida_bodega"), 
                        accesoBodega.obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false), 
                        accesoSalida.obtenerSalidaAlmacen(rs.getInt("numero_salida"), persistence, false, false));
                
                salidasBodega.add(salidaBodega);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 565\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaBodega.toString(), 565, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasBodega;
    }
    
    public List<SalidaBodegaDTO> obtenerSalidasBodegaPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        SalidaAlmacenDAO accesoSalida = new SalidaAlmacenDAO();
        BodegaReciclajeDAO accesoBodega = new BodegaReciclajeDAO();
        List<SalidaBodegaDTO> salidasBodega = null;
        SalidaBodegaDTO salidaBodega = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_salida_bodega, id_bodega, numero_salida FROM "
                + "salida_bodega WHERE numero_salida IN (SELECT numero_salida FROM "
                + "salida_almacen WHERE numero_orden = ? AND status = ?);";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasBodega = new ArrayList<SalidaBodegaDTO>();
            while (rs.next()) {
                salidaBodega = new SalidaBodegaDTO(rs.getInt("id_salida_bodega"), 
                accesoBodega.obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false), 
                accesoSalida.obtenerSalidaAlmacen(rs.getInt("numero_salida"), persistence, false, false));
                
                salidasBodega.add(salidaBodega);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 566\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaBodega.toString(), 566, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasBodega;
    }
    
    public double obtenerTotalSalidasBodegaPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalSalidasBodega = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(costo), 0.0) AS salidas_bodega FROM "
                + "salida_almacen WHERE numero_orden = ? AND status = ? AND numero_salida "
                + "IN(SELECT numero_salida FROM salida_bodega);";
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
                totalSalidasBodega = rs.getDouble("salidas_bodega");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 567\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 567, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalSalidasBodega;
    }
    
    public int obtenerUltimoIdSalidaBodegaCCanceladas() throws SQLException{
        int maxIdSalidaBodega = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_salida_bodega) AS max_salida FROM salida_bodega;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdSalidaBodega = rs.getInt("max_salida");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 568\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdSalidaBodegaCCanceladas " + maxIdSalidaBodega, 568, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdSalidaBodega;
    }
    
    public boolean reestablecerAutoincrementSalidaBodega(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE salida_bodega AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 569\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementSalidaBodega " + index, 569, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarSalidaBodega() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdSalidaBodega = this.obtenerUltimoIdSalidaBodegaCCanceladas();
        return this.reestablecerAutoincrementSalidaBodega(maxIdSalidaBodega + 1);
    }
    
}
