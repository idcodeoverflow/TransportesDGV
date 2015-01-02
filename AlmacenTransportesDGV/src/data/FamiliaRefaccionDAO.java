/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.FamiliaRefaccionDTO;
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
public class FamiliaRefaccionDAO {
    
    public void agregarFamiliaRefaccion(FamiliaRefaccionDTO familia) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO familia_refaccion(id_familia_refaccion, nombre, status, id_grupo_refaccion) VALUES(NULL, ?, ?, ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, familia.getNombre());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, familia.getGrupo().getIdGrupoRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 488\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(familia.toString(), 488, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarFamiliaRefaccion(FamiliaRefaccionDTO familia) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE familia_refaccion SET nombre = ?, id_grupo_refaccion = ? WHERE id_familia_refaccion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, familia.getNombre());
            pstmt.setInt(2, familia.getGrupo().getIdGrupoRefaccion());
            pstmt.setInt(3, familia.getIdFamiliaRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 489\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(familia.toString(), 489, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarFamiliaRefaccion(FamiliaRefaccionDTO familia) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE familia_refaccion SET status = ? WHERE id_familia_refaccion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, familia.getIdFamiliaRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 490\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
           ErrorLogger.scribirLog(familia.toString(), 490, UserHome.getUsuario(), ex);

        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public FamiliaRefaccionDTO obtenerFamilia(int idFamilia, boolean abrir, boolean cerrar) throws SQLException{
        FamiliaRefaccionDTO grupo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_familia_refaccion, nombre, status, id_grupo_refaccion FROM familia_refaccion WHERE "
                + "id_familia_refaccion = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idFamilia);
            rs = pstmt.executeQuery();
            grupo = new FamiliaRefaccionDTO();
            while (rs.next()) {
                grupo.setIdFamiliaRefaccion(rs.getInt("id_familia_refaccion"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setStatus(rs.getBoolean("status"));
                grupo.setGrupo(new GrupoRefaccionDAO().obtenerGrupoRefaccion(rs.getInt("id_grupo_refaccion"), false, false));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 491\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(grupo.toString(), 491, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return grupo;
    }
    
    public List<FamiliaRefaccionDTO> obtenerFamiliasRefacciones() throws SQLException{
        List<FamiliaRefaccionDTO> familias = null;
        FamiliaRefaccionDTO familia = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_familia_refaccion, nombre, status, id_grupo_refaccion FROM familia_refaccion WHERE "
                + "status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            familias = new ArrayList<FamiliaRefaccionDTO>();
            while (rs.next()) {
                familia = new FamiliaRefaccionDTO(
                rs.getInt("id_familia_refaccion"),
                rs.getString("nombre"),
                rs.getBoolean("status"),
                new GrupoRefaccionDAO().obtenerGrupoRefaccion(rs.getInt("id_grupo_refaccion"), false, false));
                familias.add(familia);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 492\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(familia.toString(), 492, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(familias != null){
            return familias;
        }
        return null;
    }
}
