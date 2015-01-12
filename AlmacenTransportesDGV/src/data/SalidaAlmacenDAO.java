/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.SalidaAlmacenDTO;
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
public class SalidaAlmacenDAO {
    
    public List<SalidaAlmacenDTO> obtenerSalidasAlmacenPReparacionSinCanceladas(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        OrdenReparacionDAO accesoOrdenReparacion = new OrdenReparacionDAO();
        RefaccionDAO accesoReparacion = new RefaccionDAO();
        UsuarioDAO accesoUsuario = new UsuarioDAO();
        List<SalidaAlmacenDTO> salidasAlmacen = null;
        SalidaAlmacenDTO salidaAlmacen = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_salida, costo, status, cantidad, fecha_registro, "
                + "clave_refaccion, numero_usuario, numero_orden, tipo FROM salida_almacen "
                + "WHERE numero_orden = ? AND status = ?;";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            salidasAlmacen = new ArrayList<SalidaAlmacenDTO>();
            while (rs.next()) {
                salidaAlmacen = new SalidaAlmacenDTO(
                rs.getInt("numero_salida"),
                rs.getDouble("costo"),
                rs.getBoolean("status"),
                rs.getDouble("cantidad"),
                rs.getTimestamp("fecha_registro"),
                null,//refaccion
                null,//usuario
                null,//orden de reparacion
                rs.getInt("tipo"));
                if(persistence){
                    salidaAlmacen.setOrdenReparacion(accesoOrdenReparacion.obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    salidaAlmacen.setRefaccion(accesoReparacion.obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    salidaAlmacen.setUsuario(accesoUsuario.obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                
                salidasAlmacen.add(salidaAlmacen);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 554\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaAlmacen.toString(), 554, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return salidasAlmacen;
    }
    
    public double obtenerTotalSalidasTractoPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String querySalidaEspecial = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_especial WHERE numero_orden = ? "
                + "AND status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != ?);";
        String querySalidaOperador = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_operador WHERE numero_orden = ? "
                + "AND status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != ?);";
        String querySalidaUnidad = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_unidad WHERE numero_orden = ? "
                + "AND status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != ?);";
        double totalSalidasTracto = 0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            //Salida Especial
            pstmt = conn.prepareStatement(querySalidaEspecial);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalSalidasTracto = rs.getDouble("total_salidas");
            }
            //Salida Operador
            pstmt = conn.prepareStatement(querySalidaOperador);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalSalidasTracto += rs.getDouble("total_salidas");
            }
            //Salida Unidad
            pstmt = conn.prepareStatement(querySalidaUnidad);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalSalidasTracto += rs.getDouble("total_salidas");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 556\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasTractoPReparacion " + totalSalidasTracto, 556, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalSalidasTracto;
    }
    
    public double obtenerTotalSalidasPlanaPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String querySalidaEspecial = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_especial WHERE numero_orden = ? "
                + "AND status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = ?);";
        String querySalidaOperador = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_operador WHERE numero_orden = ? "
                + "AND status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = ?);";
        String querySalidaUnidad = "SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_unidad WHERE numero_orden = ? "
                + "AND status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = ?);";
        double totalSalidasPlana = 0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            //Salida Especial
            pstmt = conn.prepareStatement(querySalidaEspecial);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalSalidasPlana = rs.getDouble("total_salidas");
            }
            //Salida Operador
            pstmt = conn.prepareStatement(querySalidaOperador);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalSalidasPlana += rs.getDouble("total_salidas");
            }
            //Salida Unidad
            pstmt = conn.prepareStatement(querySalidaUnidad);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalSalidasPlana += rs.getDouble("total_salidas");
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 557\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerTotalSalidasPlanaPReparacion " + totalSalidasPlana, 557, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalSalidasPlana;
    }
    
}
