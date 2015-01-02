/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.UsuarioDTO;
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
public class UsuarioDAO {
    
    /**
     * Constructor por Default.
     */
    public UsuarioDAO(){}
    
    /**
     * Crea un nuevo registro en la base de datos con la informacion de un
     * Objeto del tipo UsuarioDTO.
     * @param usuario Objeto UsuarioDTO con la informacion a guardar.
     * @throws SQLException  
     */
    public void altaUsuario(UsuarioDTO usuario) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "INSERT INTO usuario(numero_usuario, nombre, apellidos, "
                + "passwd, fecha_ingreso, fecha_egreso, privilegio, status) "
                + "VALUES(null,?,?,PASSWORD(?),NOW(),NULL,?,?);";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getPasswd());
            pstmt.setInt(4, usuario.getPrivilegio());
            pstmt.setBoolean(5, true);
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 625\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(usuario.toString(), 625, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * Modifica la informacion existente en un registro de la base de datos
     * con los datos de un Objeto UsuarioDTO, en base al Numero de usuario.
     * @param usuario Objeto UsuarioDTO con la informacion.
     * @throws SQLException  
     */
    public void modificarUsuario(UsuarioDTO usuario) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "UPDATE usuario SET nombre = ?, apellidos = ?, passwd = PASSWORD(?), "
                + "fecha_ingreso = ?, fecha_egreso = ?, privilegio = ?, status = ?"
                + " WHERE numero_usuario = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getPasswd());
            pstmt.setTimestamp(4, usuario.getFechaIngreso());
            pstmt.setTimestamp(5, usuario.getFechaEgreso());
            pstmt.setInt(6, usuario.getPrivilegio());
            pstmt.setBoolean(7, true);
            pstmt.setInt(8, usuario.getNumeroUsuario());
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 626\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(usuario.toString(), 626, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * Da de baja un registro en la base de datos con la informacion de un
     * Objeto del tipo UsuarioDTO, en base al Numero de usuario.
     * @param usuario Objeto del tipo UsuarioDTO con la informacion.
     * @throws SQLException  
     */
    public void eliminarUsuario(UsuarioDTO usuario) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "UPDATE usuario SET fecha_ingreso = ?, fecha_egreso = NOW(), "
                + "status = ? WHERE numero_usuario = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, usuario.getFechaIngreso());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3,usuario.getNumeroUsuario());
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 627\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(usuario.toString(), 627, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * Obtiene la informacion de un registro existente en la base de datos, de
     * acuerdo a un Numero de usuario, y lo devuelve en un Objeto UsuarioDTO, en
     * caso de no encontrar datos regresa null.
     * @param numeroUsuario int con el Numero de usuario a buscar.
     * @return Objeto UsuarioDTO con la informacion.
     * @throws SQLException  
     */
    public UsuarioDTO obtenerUsuario(int numeroUsuario, boolean abrir, boolean cerrar) throws SQLException{
        UsuarioDTO usuario = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_usuario, nombre, apellidos, passwd,"
                + "fecha_ingreso, fecha_egreso, privilegio, status FROM usuario WHERE "
                + "numero_usuario = ?";// AND status = ?;";//Se elimino el filtrado de registros activos
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroUsuario);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            usuario = new UsuarioDTO();
            while (rs.next()) {
                usuario.setNumeroUsuario(rs.getInt("numero_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setPasswd(rs.getString("passwd"));
                usuario.setFechaIngreso(rs.getTimestamp("fecha_ingreso"));
                usuario.setFechaEgreso(rs.getTimestamp("fecha_egreso"));
                usuario.setPrivilegio(rs.getInt("privilegio"));
                usuario.setStatus(rs.getBoolean("status"));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 628\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            //cachar excepcion en casa de que el usuario sea nulo
            try{
                ErrorLogger.scribirLog(usuario.toString(), 628, UserHome.getUsuario(), e);
            } catch (NullPointerException ex){}
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return usuario;
    }
    
    /**
     * Obtiene la informacion de los registros existentes en la base de datos
     * con la informacion de los objetos UsuarioDTO, y los devuelve en un
     * Objeto del tipo List<UsuarioDTO>, en caso de no encontrar datos
     * regresa null.
     * @return Objeto List<UsuarioDTO> con la informacion.
     * @throws SQLException  
     */
    public List<UsuarioDTO> obtenerUsuarios() throws SQLException{
        List<UsuarioDTO> usuarios = null;
        UsuarioDTO usuario = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_usuario, nombre, apellidos, passwd,"
                + "fecha_ingreso, fecha_egreso, privilegio, status FROM usuario WHERE "
                + "status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            usuarios = new ArrayList<UsuarioDTO>();
            while (rs.next()) {
                usuario = new UsuarioDTO(
                rs.getInt("numero_usuario"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("passwd"),
                rs.getTimestamp("fecha_ingreso"),
                rs.getTimestamp("fecha_egreso"),
                rs.getInt("privilegio"),
                rs.getBoolean("status"));
                usuarios.add(usuario);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 629\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(usuario.toString(), 629, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(usuarios != null){
            return usuarios;
        }
        return null;
    }
    
    public int obtenerUltimoNumeroUsuarioCCanceladas() throws SQLException{
        int maxNumeroUsuario = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_usuario) AS max_usuario FROM usuario;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxNumeroUsuario = rs.getInt("max_contacto");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 630\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoNumeroUsuarioCCanceladas " + maxNumeroUsuario, 630, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxNumeroUsuario;
    }
    
    public boolean reestablecerAutoincrementUsuario(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE usuario AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 631\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementUsuario " + index, 631, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarUsuario() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxNumeroUsuario = this.obtenerUltimoNumeroUsuarioCCanceladas();
        return this.reestablecerAutoincrementUsuario(maxNumeroUsuario + 1);
    }
    
}
