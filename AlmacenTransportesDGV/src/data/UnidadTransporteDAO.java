/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.UnidadTransporteDTO;
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
public class UnidadTransporteDAO {
 
    /**
     * Constructor por Default.
     */
    public UnidadTransporteDAO(){}
    
    /**
     * Crea un nuevo registro en la base de datos con la informacion de un 
     * Objeto del tipo UnidadDTO.
     * @param unidad Objeto UnidadDTO con la informacion a almacenar.
     * @throws SQLException  
     */
    public void altaUnidad(UnidadTransporteDTO unidad) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "INSERT INTO unidad_transporte(clave, numero_unidad, placas, modelo, "
                + "fecha_inicio, fecha_fin, status, id_tipo, id_marca, numero_usuario) "
                + "VALUES(?, ?, ?, ?, NOW(), null, ?, ?, ?, ?);";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, unidad.getClave());
            pstmt.setString(2, unidad.getNumeroUnidad());
            pstmt.setString(3, unidad.getPlacas());
            pstmt.setString(4, unidad.getModelo());
            pstmt.setBoolean(5, true);
            pstmt.setInt(6, unidad.getTipoUnidad().getIdTipo());
            pstmt.setInt(7, unidad.getMarca().getIdMarca());
            pstmt.setInt(8, unidad.getUsuario().getNumeroUsuario());
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 619\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(unidad.toString(), 619, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * Modifica un registro existente en la base de datos con la informacion de
     * un Objeto del tipo UnidadDTO en base al Numero de Unidad.
     * @param unidad Objeto del tipo UnidadDTO.
     * @throws SQLException  
     */
    public void modificarUnidad(UnidadTransporteDTO unidad) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "UPDATE unidad_transporte SET numero_unidad = ?, "
                + "placas = ?, modelo = ?, fecha_inicio = ?, fecha_fin = ?, "
                + "status = ?, id_tipo = ?, id_marca = ? WHERE clave = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            //pst.setString(1, unidad.getClave());
            pstmt.setString(1, unidad.getNumeroUnidad());
            pstmt.setString(2, unidad.getPlacas());
            pstmt.setString(3, unidad.getModelo());
            pstmt.setTimestamp(4, unidad.getFechaInicio());
            pstmt.setTimestamp(5, unidad.getFechaFin());
            pstmt.setBoolean(6, true);
            pstmt.setInt(7, unidad.getTipoUnidad().getIdTipo());
            pstmt.setInt(8, unidad.getMarca().getIdMarca());
            pstmt.setString(9, unidad.getClave());
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 620\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(unidad.toString(), 620, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * Da de baja un registro existente en la base de datos con la informacion 
     * de un Objeto del tipo UnidadDTO en base al Numero de Unidad.
     * @param unidad Objeto del tipo UnidadDTO.
     * @throws SQLException  
     */
    public void eliminarUnidad(UnidadTransporteDTO unidad) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "UPDATE unidad_transporte SET fecha_inicio = ?, fecha_fin = NOW(), "
                + "status = ? WHERE clave = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, unidad.getFechaInicio());
            pstmt.setBoolean(2, false);
            pstmt.setString(3, unidad.getClave());
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 621\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(unidad.toString(), 621, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    /**
     * Obtiene un registro existente en la base de datos con la informacion de
     * un Objeto del tipo UnidadDTO, en base al Numero de unidad, o null en caso
     * de no encontrar informacion.
     * @param numeroUnidad int con el Numero de unidad a buscar.
     * @param persistence si es true obtiene datos de los objetos internos.
     * @return Objeto del tipo UnidadDTO con la informacion.
     * @throws SQLException  
     */
    public UnidadTransporteDTO obtenerUnidad(String numeroUnidad, boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        UnidadTransporteDTO unidad = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT clave, numero_unidad, placas, modelo, fecha_inicio, "
                + "fecha_fin, status, id_tipo, id_marca, numero_usuario FROM "
                + "unidad_transporte WHERE clave = ?";// AND status = ?;";//Se elimino el filtrado de registros activos
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, numeroUnidad);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                unidad = new UnidadTransporteDTO();
                unidad.setClave(rs.getString("clave"));
                unidad.setNumeroUnidad(rs.getString("numero_unidad"));
                unidad.setPlacas(rs.getString("placas"));
                unidad.setModelo(rs.getString("modelo"));
                unidad.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                unidad.setFechaFin(rs.getTimestamp("fecha_fin"));
                unidad.setStatus(rs.getBoolean("status"));
                if(persistence){
                    unidad.setTipoUnidad(new TipoUnidadDAO().obtenerTipoUnidad(rs.getInt("id_tipo"), false, false));
                    unidad.setMarca(new MarcaUnidadDAO().obtenerMarcaUnidad(rs.getInt("id_marca"), false, false));
                    unidad.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                unidad.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 622\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(unidad.toString(), 622, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return unidad;
    }
    
    /**
     * Obtiene la informacion de todos los registros existentes en la base de
     * datos con la informacion de los Objetos del tipo UnidadDTO, y los 
     * devuelve en un Objeto del tipo List<UnidadDTO>, o null en caso de no
     * encontrar informacion.
     * @param persistence si es true obtiene datos de los objetos internos.
     * @return Objeto List<UnidadDTO> con la informacion.
     * @throws SQLException  
     */
    public List<UnidadTransporteDTO> obtenerUnidades(boolean persistence) throws SQLException{
        List<UnidadTransporteDTO> unidades = null;
        UnidadTransporteDTO unidad = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT clave, numero_unidad, placas, modelo, fecha_inicio, fecha_fin,"
                + "status, id_tipo, id_marca, numero_usuario FROM unidad_transporte WHERE status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            unidades = new ArrayList<UnidadTransporteDTO>();
            while (rs.next()) {
                unidad = new UnidadTransporteDTO(
                rs.getString("clave"),
                rs.getString("numero_unidad"),
                rs.getString("placas"),
                rs.getString("modelo"),
                rs.getTimestamp("fecha_inicio"),
                rs.getTimestamp("fecha_fin"),
                rs.getBoolean("status"),
                null,//tipo
                null,//marca
                null);//usuario
                if(persistence){
                    unidad.setTipoUnidad(new TipoUnidadDAO().
                            obtenerTipoUnidad(rs.getInt("id_tipo"), false, false));
                    unidad.setMarca(new MarcaUnidadDAO().
                            obtenerMarcaUnidad(rs.getInt("id_marca"), false, false));
                    unidad.setUsuario(new UsuarioDAO().
                            obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                unidades.add(unidad);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 623\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(unidad.toString(), 623, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(unidades != null){
            return unidades;
        }
        return null;
    }
    
    public boolean hayReparacionesPendientes(UnidadTransporteDTO transporte, boolean abrir, boolean cerrar) throws SQLException{
        
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_orden FROM orden_reparacion WHERE fecha_salida "
                + "IS NULL AND status = ? AND numero_orden IN(SELECT numero_orden FROM "
                + "transporte_reparacion WHERE clave = ? AND status = ?);";
        
        try{
            
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, transporte.getClave());
            pstmt.setBoolean(3, true);
            rs = pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 624\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(transporte.toString(), 624, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return false;
    }
    
}
