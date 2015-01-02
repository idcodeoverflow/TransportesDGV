/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.TipoUnidadDTO;
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
public class TipoUnidadDAO {
 
    public void agregarTipoUnidad(TipoUnidadDTO tipo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO tipo_unidad(id_tipo, nombre, status) VALUES(NULL, ?, ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, tipo.getNombre());
            pstmt.setBoolean(2, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 598\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(tipo.toString(), 598, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarTipoUnidad(TipoUnidadDTO tipo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE tipo_unidad WHERE id_tipo = ? SET nombre = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, tipo.getIdTipo());
            pstmt.setString(2, tipo.getNombre());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 632\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(tipo.toString(), 632, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarTipoUnidad(TipoUnidadDTO tipo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE tipo_unidad WHERE id_tipo SET status = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, tipo.getIdTipo());
            pstmt.setBoolean(2, false);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 599\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(tipo.toString(), 599, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public TipoUnidadDTO obtenerTipoUnidad(int idTipo, boolean abrir, boolean cerrar) throws SQLException{
        TipoUnidadDTO tipo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_tipo, nombre, status FROM tipo_unidad WHERE "
                + "id_tipo = ?";// AND status = ?;";//Se elimino el filtrado de registros activos
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idTipo);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            tipo = new TipoUnidadDTO();
            while (rs.next()) {
                tipo.setIdTipo(rs.getInt("id_tipo"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 600\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(tipo.toString(), 600, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(tipo != null){
            return tipo;
        }
        return null;
    }
    
    public List<TipoUnidadDTO> obtenerTiposUnidades() throws SQLException{
        List<TipoUnidadDTO> tipos = null;
        TipoUnidadDTO tipo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_tipo, nombre, status FROM tipo_unidad WHERE status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            tipos = new ArrayList<TipoUnidadDTO>();
            while (rs.next()) {
                tipo = new TipoUnidadDTO(
                rs.getInt("id_tipo"),
                rs.getString("nombre"),
                rs.getBoolean("status"));
                tipos.add(tipo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 601\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(tipo.toString(), 601, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(tipos != null){
            return tipos;
        }
        return null;
    }
}
