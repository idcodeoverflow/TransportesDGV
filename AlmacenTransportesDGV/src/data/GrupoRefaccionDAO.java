/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.GrupoRefaccionDTO;
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
public class GrupoRefaccionDAO {
    
    public void agregarGrupoRefaccion(GrupoRefaccionDTO grupo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO grupo_refaccion(id_grupo_refaccion, nombre, status) VALUES(NULL, ?, ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, grupo.getNombre());
            pstmt.setBoolean(2, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 493\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(grupo.toString(), 493, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarGrupoRefaccion(GrupoRefaccionDTO grupo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE grupo_refaccion SET nombre = ? WHERE id_grupo_refaccion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, grupo.getNombre());
            pstmt.setInt(2, grupo.getIdGrupoRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 494\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(grupo.toString(), 494, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarGrupoRefaccion(GrupoRefaccionDTO grupo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE grupo_refaccion SET status = ? WHERE id_grupo_refaccion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, grupo.getIdGrupoRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 495\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(grupo.toString(), 495, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public GrupoRefaccionDTO obtenerGrupoRefaccion(int idGrupoRefaccion, boolean abrir, boolean cerrar) throws SQLException{
        GrupoRefaccionDTO grupo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pst = null;
        String query = "SELECT id_grupo_refaccion, nombre, status FROM grupo_refaccion WHERE "
                + "id_grupo_refaccion = ?;";
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pst = conn.prepareStatement(query);
            pst.setInt(1, idGrupoRefaccion);
            rs = pst.executeQuery();
            grupo = new GrupoRefaccionDTO();
            while (rs.next()) {
                grupo.setIdGrupoRefaccion(rs.getInt("id_grupo_refaccion"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 496\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(grupo.toString(), 496, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pst);
            }
        }
        return grupo;
    }
    
    public List<GrupoRefaccionDTO> obtenerGrupoRefacciones() throws SQLException{
        List<GrupoRefaccionDTO> grupos = null;
        GrupoRefaccionDTO grupo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_grupo_refaccion, nombre, status FROM grupo_refaccion WHERE "
                + "status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            grupos = new ArrayList<GrupoRefaccionDTO>();
            while (rs.next()) {
                grupo = new GrupoRefaccionDTO(
                rs.getInt("id_grupo_refaccion"),
                rs.getString("nombre"),
                rs.getBoolean("status"));
                grupos.add(grupo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 497\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(grupo.toString(), 497, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(grupos != null){
            return grupos;
        }
        return null;
    }
}
