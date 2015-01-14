/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import beans.RefaccionDTO;
import beans.SalidaAlmacenDTO;
import beans.SalidaEspecialDTO;
import beans.SalidaOperadorDTO;
import beans.SalidaUnidadDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;
import support.DBConnection;

/**
 *
 * @author David Israel
 */
public class SalidaAlmacenDAO{
    
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
    
    public SalidaAlmacenDTO obtenerUltimaSalidaAlmacenPRefaccion(RefaccionDTO refaccion, boolean persistence, boolean abrir, boolean cerrar){
        SalidaAlmacenDTO salidaAlmacen = new SalidaAlmacenDTO();
        SalidaUnidadDAO accesoSalidaUnidad = new SalidaUnidadDAO();
        SalidaOperadorDAO accesoSalidaOperador = new SalidaOperadorDAO();
        SalidaEspecialDAO accesoSalidaEspecial = new SalidaEspecialDAO();
        try {
            
            List<SalidaUnidadDTO> salidasUnidad = accesoSalidaUnidad.obtenerSalidasUnidadPRefaccionSinCanceladas(refaccion, false, true, false);
            
            for(SalidaAlmacenDTO salida : salidasUnidad){
                if(null != salidaAlmacen || !salida.getFechaRegistro().after(salidaAlmacen.getFechaRegistro())){
                    salidaAlmacen = salida;
                }
            }
            
            List<SalidaOperadorDTO> salidasOperador = accesoSalidaOperador.obtenerSalidasOperadorPRefaccionSinCanceladas(refaccion, false, false, false);
            
            for(SalidaAlmacenDTO salida : salidasOperador){
                if(null != salidaAlmacen || !salida.getFechaRegistro().after(salidaAlmacen.getFechaRegistro())){
                    salidaAlmacen = salida;
                }
            }
            
            List<SalidaEspecialDTO> salidasEspeciales = accesoSalidaEspecial.obtenerSalidasEspecialesPRefaccionSinCanceladas(refaccion, false, false, false);
            
            for(SalidaAlmacenDTO salida : salidasEspeciales){
                if(null != salidaAlmacen || !salida.getFechaRegistro().after(salidaAlmacen.getFechaRegistro())){
                    salidaAlmacen = salida;
                }
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2006\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(salidaAlmacen.toString(), 2006, UserHome.getUsuario(), ex);
        }
        return salidaAlmacen;
    }

}
