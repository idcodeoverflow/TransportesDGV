/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import almacendgv.UserHome;
import beans.SistemaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;
import support.DBConnection;
import static utilidades.FinallyHandler.closeQuietly;

/**
 *
 * @author David Israel
 */
public class SistemaDAO {
    
    public SistemaDAO() {
        
    }
    
    public void modificarControlSistema(SistemaDTO sistema) throws SQLException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE sistema SET notificar_nueva_orden = ?, notificar_fin_orden = ? WHERE id_sistema = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, sistema.isNotificarNuevaOrden());
            pstmt.setBoolean(2, sistema.isNotificarFinOrden());
            pstmt.setInt(3, sistema.getIdSistema());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1378\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(sistema.toString(), 1378, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public SistemaDTO obtenerControlSistema(int idSistema, boolean abrir, boolean cerrar) throws SQLException {
        SistemaDTO sistema = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_sistema, notificar_nueva_orden, notificar_fin_orden FROM "
                + "sistema WHERE id_sistema = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idSistema);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sistema = new SistemaDTO(rs.getInt("id_sistema"), 
                        rs.getBoolean("notificar_nueva_orden"), 
                        rs.getBoolean("notificar_fin_orden"));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 1379\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(sistema.toString(), 1379, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return sistema;
    }
    
    public List<SistemaDTO> obtenerControlesSistema(boolean abrir, boolean cerrar) throws SQLException {
        List<SistemaDTO> sistemas = null;
        SistemaDTO sistema = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_sistema, notificar_nueva_orden, notificar_fin_orden FROM sistema;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sistema = new SistemaDTO(rs.getInt("id_sistema"), 
                        rs.getBoolean("notificar_nueva_orden"), 
                        rs.getBoolean("notificar_fin_orden"));
                
                sistemas.add(new SistemaDTO(rs.getInt("id_sistema"), 
                        rs.getBoolean("notificar_nueva_orden"), 
                        rs.getBoolean("notificar_fin_orden")));
            }
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 1380\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(sistema.toString(), 1380, UserHome.getUsuario(), ex);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return sistemas;
    }
    
    
}
