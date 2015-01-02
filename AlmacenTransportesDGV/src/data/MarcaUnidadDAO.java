/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.MarcaUnidadDTO;
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
public class MarcaUnidadDAO {
    
    public void agregarMarcaUnidad(MarcaUnidadDTO marca) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO marca_unidad(id_marca, nombre, status) "
                + "VALUES(NULL, ?, ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, marca.getNombre());
            pstmt.setBoolean(2, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 499\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marca.toString(), 499, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarMarcaUnidad(MarcaUnidadDTO marca) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE marca_unidad SET nombre = ? WHERE id_marca = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, marca.getNombre());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 500\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marca.toString(), 500, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarMarcaUnidad(MarcaUnidadDTO marca) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE marca_unidad SET status = ? WHERE id_marca = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, marca.getIdMarca());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 501\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marca.toString(), 501, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public MarcaUnidadDTO obtenerMarcaUnidad(int idMarcaUnidad, boolean abrir, boolean cerrar) throws SQLException{
        MarcaUnidadDTO marca = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_marca, nombre, status FROM marca_unidad WHERE "
                + "id_marca = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idMarcaUnidad);
            rs = pstmt.executeQuery();
            marca = new MarcaUnidadDTO();
            while (rs.next()) {
                marca.setIdMarca(rs.getInt("id_marca"));
                marca.setNombre(rs.getString("nombre"));
                marca.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 502\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marca.toString(), 502, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return marca;
    }
    
    public List<MarcaUnidadDTO> obtenerMarcasUnidades() throws SQLException{
        List<MarcaUnidadDTO> marcas = null;
        MarcaUnidadDTO marca = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_marca, nombre, status FROM marca_unidad WHERE status = ? ORDER BY nombre;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            marcas = new ArrayList<MarcaUnidadDTO>();
            while (rs.next()) {
                marca = new MarcaUnidadDTO(
                rs.getInt("id_marca"),
                rs.getString("nombre"),
                rs.getBoolean("status"));
                marcas.add(marca);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 503\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marca.toString(), 503, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(marcas != null){
            return marcas;
        }
        return null;
    }
}
