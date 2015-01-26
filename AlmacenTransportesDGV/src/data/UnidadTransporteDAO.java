/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.UnidadTransporteDTO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
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
    
    public void guardarImagenTransporte(String ruta, UnidadTransporteDTO unidad) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        FileInputStream fis = null;
        File file = null;
        String query = "UPDATE unidad_transporte SET imagen = ? WHERE clave = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            file = new File(ruta);
            fis = new FileInputStream(file);
            pstmt = conn.prepareStatement(query);
            pstmt.setBinaryStream(1, fis, (int)file.length());
            pstmt.setString(2, unidad.getClave());
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2012\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(unidad.toString(), 2012, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }    
    
    public Image getImagenUnidadTransporte(UnidadTransporteDTO unidadTransporte) throws SQLException {
        Image imagen = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        Blob blob = null;
        byte []data = null;
        BufferedImage bfImage = null;
        ResultSet rs = null;
        String query = "SELECT imagen FROM unidad_transporte WHERE clave = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, unidadTransporte.getClave());
            rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getBlob("imagen") == null){
                    break;
                }
                blob = rs.getBlob("imagen");
                data = blob.getBytes(1, (int)blob.length());
                bfImage = ImageIO.read(new ByteArrayInputStream(data));
                imagen = bfImage;
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2013\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(unidadTransporte.toString(), 2013, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return imagen;
    }
    
    /**
     * Crea un nuevo registro en la base de datos con la informacion de un 
     * Objeto del tipo UnidadDTO.
     * @param unidad Objeto UnidadDTO con la informacion a almacenar.
     * @throws SQLException  
     */
    public void altaUnidad(UnidadTransporteDTO unidad) throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "INSERT INTO unidad_transporte(clave, no_economico, vin, placas, "
                + "modelo, color, modelo_motor, no_serie_motor, cpl, imagen, "
                + "fecha_inicio, fecha_fin, status, id_tipo, id_marca, id_marca_motor, "
                + "numero_usuario) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, null, NOW(), null, ?, ?, ?, ?, ?);";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, unidad.getClave());
            pstmt.setString(2, unidad.getNoEconomico());
            pstmt.setString(3, unidad.getVin());
            pstmt.setString(4, unidad.getPlacas());
            pstmt.setString(5, unidad.getModelo());
            pstmt.setString(6, unidad.getColor());
            pstmt.setString(7, unidad.getModeloMotor());
            pstmt.setString(8, unidad.getNoSerieMotor());
            pstmt.setString(9, unidad.getCpl());
            pstmt.setBoolean(10, true);
            pstmt.setInt(11, unidad.getTipoUnidad().getIdTipo());
            pstmt.setInt(12, unidad.getMarcaUnidad().getIdMarca());
            pstmt.setInt(13, unidad.getMarcaMotor().getIdMarcaMotor());
            pstmt.setInt(14, unidad.getUsuario().getNumeroUsuario());
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
        String query = "UPDATE unidad_transporte SET no_economico = ?, vin = ?, "
                + "placas = ?, modelo = ?, color = ?, modelo_motor = ?, no_serie_motor = ?,"
                + "cpl = ?, fecha_inicio = ?, fecha_fin = ?, status = ?, id_tipo = ?, "
                + "id_marca = ?, id_marca_motor = ?, numero_usuario = ? WHERE clave = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, unidad.getNoEconomico());
            pstmt.setString(2, unidad.getVin());
            pstmt.setString(3, unidad.getPlacas());
            pstmt.setString(4, unidad.getModelo());
            pstmt.setString(5, unidad.getColor());
            pstmt.setString(6, unidad.getModeloMotor());
            pstmt.setString(7, unidad.getNoSerieMotor());
            pstmt.setString(8, unidad.getCpl());
            pstmt.setTimestamp(9, unidad.getFechaInicio());
            pstmt.setTimestamp(10, unidad.getFechaFin());
            pstmt.setBoolean(11, true);
            pstmt.setInt(12, unidad.getTipoUnidad().getIdTipo());
            pstmt.setInt(13, unidad.getMarcaUnidad().getIdMarca());
            pstmt.setInt(14, unidad.getMarcaMotor().getIdMarcaMotor());
            pstmt.setInt(15, unidad.getUsuario().getNumeroUsuario());
            pstmt.setString(16, unidad.getClave());
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
        String query = "SELECT clave, no_economico, vin, placas, modelo, color, "
                + "modelo_motor, no_serie_motor, cpl, fecha_inicio, fecha_fin, "
                + "status, id_tipo, id_marca, id_marca_motor, numero_usuario FROM "
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
                unidad.setNoEconomico(rs.getString("no_economico"));
                unidad.setVin(rs.getString("vin"));
                unidad.setPlacas(rs.getString("placas"));
                unidad.setModelo(rs.getString("modelo"));
                unidad.setColor(rs.getString("color"));
                unidad.setModeloMotor(rs.getString("modelo_motor"));
                unidad.setNoSerieMotor(rs.getString("no_serie_motor"));
                unidad.setCpl(rs.getString("cpl"));
                unidad.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                unidad.setFechaFin(rs.getTimestamp("fecha_fin"));
                unidad.setStatus(rs.getBoolean("status"));
                if(persistence){
                    unidad.setTipoUnidad(new TipoUnidadDAO().obtenerTipoUnidad(rs.getInt("id_tipo"), false, false));
                    unidad.setMarcaUnidad(new MarcaUnidadDAO().obtenerMarcaUnidad(rs.getInt("id_marca"), false, false));
                    unidad.setMarcaMotor(new MarcaMotorDAO().obtenerMarcaMotor(rs.getInt("id_marca_motor"), false, false));
                    unidad.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
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
        String query = "SELECT clave, no_economico, vin, placas, modelo, color, "
                + "modelo_motor, no_serie_motor, cpl, fecha_inicio, fecha_fin, "
                + "status, id_tipo, id_marca, id_marca_motor, numero_usuario FROM "
                + "unidad_transporte WHERE status = ?";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            unidades = new ArrayList<UnidadTransporteDTO>();
            while (rs.next()) {
                unidad = new UnidadTransporteDTO();
                unidad.setClave(rs.getString("clave"));
                unidad.setNoEconomico(rs.getString("no_economico"));
                unidad.setVin(rs.getString("vin"));
                unidad.setPlacas(rs.getString("placas"));
                unidad.setModelo(rs.getString("modelo"));
                unidad.setColor(rs.getString("color"));
                unidad.setModeloMotor(rs.getString("modelo_motor"));
                unidad.setNoSerieMotor(rs.getString("no_serie_motor"));
                unidad.setCpl(rs.getString("cpl"));
                unidad.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                unidad.setFechaFin(rs.getTimestamp("fecha_fin"));
                unidad.setStatus(rs.getBoolean("status"));
                if(persistence){
                    unidad.setTipoUnidad(new TipoUnidadDAO().obtenerTipoUnidad(rs.getInt("id_tipo"), false, false));
                    unidad.setMarcaUnidad(new MarcaUnidadDAO().obtenerMarcaUnidad(rs.getInt("id_marca"), false, false));
                    unidad.setMarcaMotor(new MarcaMotorDAO().obtenerMarcaMotor(rs.getInt("id_marca_motor"), false, false));
                    unidad.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
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
