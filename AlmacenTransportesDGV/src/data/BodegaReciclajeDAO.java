/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.BodegaReciclajeDTO;
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
public class BodegaReciclajeDAO {
    
    public void agregarBodegaReciclaje(BodegaReciclajeDTO bodega) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO bodega_reciclaje(id_bodega, nombre, status) VALUES (NULL, ?, ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, bodega.getNombre());
            pstmt.setBoolean(2, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 400\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(bodega.toString(), 400, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarBodegaReciclaje(BodegaReciclajeDTO bodega) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE bodega_reciclaje SET nombre = ? WHERE id_bodega = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, bodega.getNombre());
            pstmt.setInt(2, bodega.getIdBodega());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 401\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(bodega.toString(), 401, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarBodegaReciclaje(BodegaReciclajeDTO bodega) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE bodega_reciclaje SET status = ? WHERE id_bodega = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, bodega.getIdBodega());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 402\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(bodega.toString(), 402, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public BodegaReciclajeDTO obtenerBodegaReciclaje(int idBodega, boolean abrir, boolean cerrar) throws SQLException{
        BodegaReciclajeDTO bodega = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_bodega, nombre, status FROM bodega_reciclaje WHERE "
                + "id_bodega = ?";// AND status = ?;";//Se elimino el filtrado por registros activos
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idBodega);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            bodega = new BodegaReciclajeDTO();
            while (rs.next()) {
                bodega.setIdBodega(rs.getInt("id_bodega"));
                bodega.setNombre(rs.getString("nombre"));
                bodega.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 403\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(bodega.toString(), 403, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return bodega;
    }
    
    public List<BodegaReciclajeDTO> obtenerBodegasReciclaje() throws SQLException{
        List<BodegaReciclajeDTO> bodegas = null;
        BodegaReciclajeDTO bodega = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_bodega, nombre, status FROM bodega_reciclaje WHERE "
                + "status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            bodegas = new ArrayList<BodegaReciclajeDTO>();
            while (rs.next()) {
                bodega = new BodegaReciclajeDTO(
                rs.getInt("id_bodega"),
                rs.getString("nombre"),
                rs.getBoolean("status"));
                bodegas.add(bodega);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 404\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(bodega.toString(), 404, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(bodegas != null){
            return bodegas;
        }
        return null;
    }
    
    public int obtenerUltimoIdBodegaCCanceladas() throws SQLException{
        int maxIdBodega = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_bodega) AS max_bodega FROM bodega_reciclaje;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdBodega = rs.getInt("max_bodega");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 405\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdBodegaCCanceladas", 405, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdBodega;
    }
    
    public boolean reestablecerAutoincrementBodegaReciclaje(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE bodega_reciclaje AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 406\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementBodegaReciclaje " + index, 406, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarBodegaReciclaje() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdBodega = this.obtenerUltimoIdBodegaCCanceladas();
        return this.reestablecerAutoincrementBodegaReciclaje(maxIdBodega + 1);
    }
    
}
