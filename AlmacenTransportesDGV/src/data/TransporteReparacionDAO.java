/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.TransporteReparacionDTO;
import beans.UnidadTransporteDTO;
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
public class TransporteReparacionDAO {
    
    public void agregarTransporteAReparacion(TransporteReparacionDTO reparacion) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO transporte_reparacion(clave, numero_orden, descripcion, fecha_alta, fecha_baja, "
                + "kilometraje, status, numero_usuario, numero_usuario_baja) VALUES(?,?,?,NOW(),NULL,?,?,?,NULL);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, reparacion.getTransporte().getClave());
            pstmt.setInt(2, reparacion.getOrdenReparacion().getNumeroOrden());
            pstmt.setString(3, reparacion.getDescripcion());
            pstmt.setInt(4, reparacion.getKilometraje());
            pstmt.setBoolean(5, true);
            pstmt.setInt(6, reparacion.getUsuario().getNumeroUsuario());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 614\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(reparacion.toString(), 614, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarReparacionTransporte(TransporteReparacionDTO reparacion) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE transporte_reparacion SET status = ?, fecha_alta = ?, "
                + "fecha_baja = NOW(), numero_usuario_baja = ? WHERE clave = ? AND numero_orden = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, reparacion.getFechaAlta());
            pstmt.setInt(3, reparacion.getUsuario().getNumeroUsuario());
            pstmt.setString(4, reparacion.getTransporte().getClave());
            pstmt.setInt(5, reparacion.getOrdenReparacion().getNumeroOrden());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 615\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(reparacion.toString(), 615, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarReparacionTransportePOrden(OrdenReparacionDTO ordenReparacion, UsuarioDTO usuarioBaja) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE transporte_reparacion SET status = ?, fecha_alta = ?, "
                + "fecha_baja = NOW(), numero_usuario_baja = ? WHERE numero_orden = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setTimestamp(2, ordenReparacion.getFechaEntrada());
            pstmt.setInt(3, usuarioBaja.getNumeroUsuario());
            pstmt.setInt(4, ordenReparacion.getNumeroOrden());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 616\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 616, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public TransporteReparacionDTO obtenerTransporteReparacion(String clave, int numeroOrden, boolean abrir, boolean cerrar) throws SQLException{
        TransporteReparacionDTO transporteReparacion = new TransporteReparacionDTO();
        UnidadTransporteDTO transporte = null;
        OrdenReparacionDTO ordenReparacion = null;
        UsuarioDTO usuario = null;
        UsuarioDTO usuarioBaja = new UsuarioDTO();
        UnidadTransporteDAO accesoTransporte = new UnidadTransporteDAO();
        OrdenReparacionDAO accesoReparacion = new OrdenReparacionDAO();
        UsuarioDAO accesoUsuario = new UsuarioDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT clave, numero_orden, descripcion, fecha_alta, fecha_baja, kilometraje, "
                + "status, numero_usuario, numero_usuario_baja FROM "
                + "transporte_reparacion WHERE clave = ? AND numero_orden = ? AND "
                + "status = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, clave);
            pstmt.setInt(2, numeroOrden);
            pstmt.setBoolean(3, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ordenReparacion = accesoReparacion.obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false);
                transporte = accesoTransporte.obtenerUnidad(rs.getString("clave"), true, false, false);
                usuario = accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario"), false, false);
                //usuarioBaja = accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario_baja"), false, false);Por el momento no se pueden obtener datos si estan dados 
                transporteReparacion.setDescripcion(rs.getString("descripcion"));
                transporteReparacion.setTransporte(transporte);
                transporteReparacion.setOrdenReparacion(ordenReparacion);
                transporteReparacion.setUsuario(usuario);
                //transporteReparacion.setUsuarioBaja(usuarioBaja);
                transporteReparacion.setFechaAlta(rs.getTimestamp("fecha_alta"));
                transporteReparacion.setFechaBaja(rs.getTimestamp("fecha_baja"));
                transporteReparacion.setKilometraje(rs.getInt("kilometraje"));
                transporteReparacion.setStatus(rs.getBoolean("status"));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 617\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(transporteReparacion.toString(), 617, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return transporteReparacion;
    }
    
    public void eliminarUnidadTransporteReparacion(String clave, int numeroOrden, UsuarioDTO usuarioBaja, boolean abrir, boolean cerrar) throws SQLException{
        TransporteReparacionDTO transporteReparacion = new TransporteReparacionDTO();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "UPDATE transporte_reparacion SET status = FALSE, fecha_alta = ?, fecha_baja = NOW(), numero_usuario_baja = ? WHERE "
                + "clave = ? AND numero_orden = ? AND status = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            
            transporteReparacion = this.obtenerTransporteReparacion(clave, numeroOrden, true, true);
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, transporteReparacion.getFechaAlta());
            pstmt.setInt(2, usuarioBaja.getNumeroUsuario());
            pstmt.setString(3, clave);
            pstmt.setInt(4, numeroOrden);
            pstmt.setBoolean(5, true);
            pstmt.executeUpdate();
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 1227\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(transporteReparacion.toString(), 1227, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
    }
    
    public List<TransporteReparacionDTO> obtenerTransportesPReparacion(OrdenReparacionDTO reparacion, 
            boolean persistence, boolean dPersistence, boolean abrir, boolean cerrar) throws SQLException{
        
        List<TransporteReparacionDTO> transportes = null;
        TransporteReparacionDTO transporte = null;
        UnidadTransporteDAO accesoTransporte = new UnidadTransporteDAO();
        OrdenReparacionDAO accesoReparacion = new OrdenReparacionDAO();
        UsuarioDAO accesoUsuario = new UsuarioDAO();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT clave, numero_orden, descripcion, fecha_alta, fecha_baja, kilometraje, "
                + "status, numero_usuario, numero_usuario_baja FROM transporte_reparacion "
                + "WHERE numero_orden = ? AND status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            transportes = new ArrayList<TransporteReparacionDTO>();
            while (rs.next()) {
                transporte = new TransporteReparacionDTO(
                    null, //transporte
                    null, //orden reparacion
                    null, //usuario    
                    null, //usuario baja    
                    rs.getString("descripcion"),
                    rs.getTimestamp("fecha_alta"),
                    rs.getTimestamp("fecha_baja"),
                    rs.getInt("kilometraje"),
                    rs.getBoolean("status"));
                
                if(persistence){
                    transporte.setTransporte(accesoTransporte.obtenerUnidad(rs.getString("clave"), true, false, false));
                }
                if(dPersistence){
                    transporte.setOrdenReparacion(accesoReparacion.obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                }
                
                if(persistence || dPersistence){
                    transporte.setUsuario(accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
                transportes.add(transporte);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 618\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(reparacion.toString(), 618, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(transportes != null){
            return transportes;
        }
        return null;
    }
}
