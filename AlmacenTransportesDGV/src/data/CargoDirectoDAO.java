/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.OrdenReparacionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import logger.ErrorLogger;
import support.DBConnection;

/**
 *
 * @author David Israel
 */
public class CargoDirectoDAO {
    
    public double obtenerTotalCargosDirectosTractoPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String queryCargoUnidad = "SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_unidad WHERE numero_orden = ? AND "
                + "status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != ?);";
        double totalCargosTracto = 0.0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            
            //Cargo Unidad
            pstmt = conn.prepareStatement(queryCargoUnidad);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCargosTracto += rs.getDouble("total_cargos");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 422\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 422, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalCargosTracto;
    }
    
    public double obtenerTotalCargosDirectosPlanaPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String queryCargoUnidad = "SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_unidad WHERE numero_orden = ? AND "
                + "status = ? AND clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = ?);";
        double totalCargosTracto = 0.0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            //Cargo Unidad
            pstmt = conn.prepareStatement(queryCargoUnidad);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCargosTracto += rs.getDouble("total_cargos");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 423\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 423, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        
        return totalCargosTracto;
    }
    
}

