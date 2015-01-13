/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import almacendgv.UserHome;
import beans.MarcaMotorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;
import support.DBConnection;
import static utilidades.FinallyHandler.closeQuietly;

/**
 *
 * @author David
 */
public class MarcaMotorDAO {
    
    public MarcaMotorDAO(){}
    
    public void agregarMarcaMotor(MarcaMotorDTO marcaMotor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO marca_motor(id_marca_motor, nombre, status) "
                + "VALUES(NULL,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, marcaMotor.getNombre());
            pstmt.setBoolean(2, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2007\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marcaMotor.toString(), 2007, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    } 
    
    public void modificarMarcaMotor(MarcaMotorDTO marcaMotor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE marca_motor SET nombre = ? WHERE id_marca_unidad = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, marcaMotor.getNombre());
            pstmt.setInt(2, marcaMotor.getIdMarcaMotor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2008\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marcaMotor.toString(), 2008, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarMarcaMotor(MarcaMotorDTO marcaMotor) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE marca_motor SET status = ? WHERE id_marca_unidad = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, marcaMotor.getIdMarcaMotor());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2009\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marcaMotor.toString(), 2009, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public MarcaMotorDTO obtenerMarcaMotor(int idMarca, boolean abrir, boolean cerrar) throws SQLException{
        MarcaMotorDTO marcaMotor = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String query = "SELECT id_marca_motor, nombre, status FROM marca_motor WHERE id_marca_motor = ?;";
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, marcaMotor.getIdMarcaMotor());
            rs = pstmt.executeQuery();
            while(rs.next()){
                marcaMotor = new MarcaMotorDTO(rs.getInt("id_marca_motor"), 
                        rs.getString("nombre"), 
                        rs.getBoolean("status"));
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2010\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marcaMotor.toString(), 2010, UserHome.getUsuario(), ex);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return marcaMotor;
    }
    
    public List<MarcaMotorDTO> obtenerMarcasMotores() throws SQLException{
        List<MarcaMotorDTO> marcasMotores = new ArrayList<MarcaMotorDTO>();
        MarcaMotorDTO marcaMotor = null;
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String query = "SELECT id_marca_motor, nombre, status FROM marca_motor WHERE id_marca_motor = ? AMD status = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, marcaMotor.getIdMarcaMotor());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            while(rs.next()){
                marcaMotor = new MarcaMotorDTO(rs.getInt("id_marca_motor"), 
                        rs.getString("nombre"), 
                        rs.getBoolean("status"));
                marcasMotores.add(marcaMotor);
            }
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2011\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(marcaMotor.toString(), 2011, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return marcasMotores;
    }
    
}
