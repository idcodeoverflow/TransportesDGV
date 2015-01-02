/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OperadorDTO;
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
public class OperadorDAO {
    
    public void agregarOperador(OperadorDTO operador) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO operador(numero_operador, nombre, apellidos, "
                + "fecha_ingreso, fecha_egreso, status) VALUES(NULL, ?, ?, NOW(), NULL, ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, operador.getNombre());
            pstmt.setString(2, operador.getApellidos());
            pstmt.setBoolean(3, true);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 504\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(operador.toString(), 504, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarOperador(OperadorDTO operador) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE operador SET nombre = ?, apellidos = ?, fecha_ingreso = ?, "
                + "fecha_egreso = ? WHERE numero_operador = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, operador.getNombre());
            pstmt.setString(2, operador.getApellidos());
            pstmt.setTimestamp(3, operador.getFechaIngreso());
            pstmt.setTimestamp(4, operador.getFechaEgreso());
            pstmt.setInt(5, operador.getNumeroOperador());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 505\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(operador.toString(), 505, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarOperador(OperadorDTO operador) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE operador SET fecha_ingreso = ?, fecha_egreso = NOW(), "
                + "status = ? WHERE numero_operador = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, operador.getFechaIngreso());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, operador.getNumeroOperador());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 506\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(operador.toString(), 506, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public OperadorDTO obtenerOperador(int numeroOperador, boolean abrir, boolean cerrar) throws SQLException{
        OperadorDTO operador = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_operador, nombre, apellidos, fecha_ingreso, "
                + "fecha_egreso, status FROM operador WHERE numero_operador = ?";/* AND "
                + "status = ?;";*///Se elimino el filtrado de registros activos
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroOperador);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            operador = new OperadorDTO();
            while (rs.next()) {
                operador.setNumeroOperador(rs.getInt("numero_operador"));
                operador.setNombre(rs.getString("nombre"));
                operador.setApellidos(rs.getString("apellidos"));
                operador.setFechaIngreso(rs.getTimestamp("fecha_ingreso"));
                operador.setFechaEgreso(rs.getTimestamp("fecha_egreso"));
                operador.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 507\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(operador.toString(), 507, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
       
        return operador;
    }
    
    public List<OperadorDTO> obtenerOperadores() throws SQLException{
        List<OperadorDTO> operadores = null;
        OperadorDTO operador = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_operador, nombre, apellidos, fecha_ingreso, "
                + "fecha_egreso, status FROM operador WHERE status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            operadores = new ArrayList<OperadorDTO>();
            while (rs.next()) {
                operador = new OperadorDTO(
                rs.getInt("numero_operador"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getTimestamp("fecha_ingreso"),
                rs.getTimestamp("fecha_egreso"),
                rs.getBoolean("status"));
                operadores.add(operador);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 508\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(operador.toString(), 508, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(operadores != null){
            return operadores;
        }
        return null;
    }
    
    public int obtenerUltimoNumeroOperadorCCanceladas() throws SQLException{
        int maxNumeroOperador = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_operador) AS max_operador FROM operador;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxNumeroOperador = rs.getInt("max_operador");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 509\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoNumeroOperadorCCanceladas " + maxNumeroOperador, 509, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxNumeroOperador;
    }
    
    public boolean reestablecerAutoincrementOperador(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE operador AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 510\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementOperador " + index, 510, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarOperador() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxNumeroOperador = this.obtenerUltimoNumeroOperadorCCanceladas();
        return this.reestablecerAutoincrementOperador(maxNumeroOperador + 1);
    }
    
    public List<OperadorDTO> buscarOperador(OperadorDTO operadorBuscar) throws SQLException{
        List<OperadorDTO> operadores = null;
        OperadorDTO operador = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_operador, nombre, apellidos, fecha_ingreso, fecha_egreso, status"
                + " FROM operador WHERE status = ? AND nombre LIKE CONCAT('%',?,'%')" 
                + " ORDER BY nombre, apellidos ASC;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, operadorBuscar.getNombre());
            rs = pstmt.executeQuery();
            operadores = new ArrayList<OperadorDTO>();
            while (rs.next()) {
                operador = new OperadorDTO(
                rs.getInt("numero_operador"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getTimestamp("fecha_ingreso"),
                rs.getTimestamp("fecha_egreso"),
                rs.getBoolean("status"));
                operadores.add(operador);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 634\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(operador.toString(), 634, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(operadores != null){
            return operadores;
        }
        return null;
    }
}
