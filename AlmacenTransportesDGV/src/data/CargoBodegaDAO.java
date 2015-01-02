/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.CargoBodegaDTO;
import beans.FacturaDTO;
import beans.OrdenReparacionDTO;
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
public class CargoBodegaDAO extends CargoDirectoDAO {
    
    public boolean agregarCargoBodega(CargoBodegaDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO cargo_bodega(id_cargo_bodega, numero_cargo_directo, "
                + "id_bodega) VALUES(NULL,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cargo.getNumeroCargoDirecto());
            pstmt.setInt(2, cargo.getBodega().getIdBodega());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 407\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nGrave: Consulte al administrador\nde"
                    + " la base de datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 407, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public void modificarCargoBodega(CargoBodegaDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_bodega SET numero_bodega = ? WHERE id_cargo_bodega = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cargo.getBodega().getIdBodega());
            pstmt.setInt(2, cargo.getIdCargoBodega());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 408\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 408, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarCargoBodega(CargoBodegaDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_directo SET fecha_registro = ?, status = ? WHERE numero_cargo_directo "
                + "IN (SELECT numero_cargo_directo FROM cargo_bodega WHERE id_cargo_bodega = ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, cargo.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, cargo.getIdCargoBodega());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 409\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 409, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public CargoBodegaDTO obtenerCargoBodega(int idCargoBodega) throws SQLException{
        CargoBodegaDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_bodega, numero_cargo_directo, id_bodega FROM cargo_bodega WHERE "
                + "id_cargo_bodega = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idCargoBodega);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cargo = new CargoBodegaDTO(rs.getInt("id_cargo_bodega"), 
                        new BodegaReciclajeDAO().obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false), new CargoDirectoDAO().
                        obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 410\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 410, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return cargo;
    }
    
    public List<CargoBodegaDTO> obtenerCargosBodega() throws SQLException{
        List<CargoBodegaDTO> cargos = null;
        CargoBodegaDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_bodega, numero_cargo_directo, id_bodega "
                + "FROM cargo_bodega WHERE (SELECT status FROM cargo_directo WHERE "
                + "cargo_directo.numero_cargo_directo = cargo_bodega.numero_cargo_directo) = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoBodegaDTO>();
            while (rs.next()) {
                cargo = new CargoBodegaDTO(
                rs.getInt("id_cargo_bodega"),
                new BodegaReciclajeDAO().obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 411\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 411, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }

    public List<CargoBodegaDTO> obtenerCargosBodegasPFactura(FacturaDTO factura, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<CargoBodegaDTO> cargos = null;
        CargoBodegaDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_bodega, numero_cargo_directo, id_bodega "
                + "FROM cargo_bodega WHERE numero_cargo_directo IN "
                + "(SELECT numero_cargo_directo FROM cargo_directo WHERE "
                + "id_proveedor = ? AND folio = ? AND status = ?);";
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, factura.getProveedor().getIdProveedor());
            pstmt.setString(2, factura.getFolio());
            pstmt.setBoolean(3, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoBodegaDTO>();
            while (rs.next()) {
                cargo = new CargoBodegaDTO(
                rs.getInt("id_cargo_bodega"),
                new BodegaReciclajeDAO().obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 412\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 412, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public List<CargoBodegaDTO> obtenerCargosBodegasPReparacion(OrdenReparacionDTO reparacion, boolean abrir, boolean cerrar) throws SQLException{
        List<CargoBodegaDTO> cargos = null;
        CargoBodegaDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_bodega, numero_cargo_directo, id_bodega "
                + "FROM cargo_bodega WHERE numero_cargo_directo IN "
                + "(SELECT numero_cargo_directo FROM cargo_directo WHERE "
                + "numero_orden = ? AND status = ?);";
        
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoBodegaDTO>();
            while (rs.next()) {
                cargo = new CargoBodegaDTO(
                rs.getInt("id_cargo_bodega"),
                new BodegaReciclajeDAO().obtenerBodegaReciclaje(rs.getInt("id_bodega"), false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 413\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 413, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public double obtenerTotalCargosBodegaPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalCargosBodega = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(total), 0.0) AS cargos_bodegas FROM "
                + "cargo_directo WHERE numero_orden = ? AND status = ? AND numero_cargo_directo "
                + "IN(SELECT numero_cargo_directo FROM cargo_bodega);";
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCargosBodega = rs.getDouble("cargos_bodegas");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 414\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 414, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalCargosBodega;
    }
    
    public int obtenerUltimoIdCargoBodegaCCanceladas() throws SQLException{
        int maxIdCargoBodega = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_cargo_bodega) AS max_cargo FROM cargo_bodega;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdCargoBodega = rs.getInt("max_cargo");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 415\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdCargoBodegaCCanceladas " + maxIdCargoBodega, 415, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdCargoBodega;
    }
    
    public boolean reestablecerAutoincrementCargoBodega(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE cargo_bodega AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 416\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementCargoBodega " + index, 416, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarCargoBodega() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdCargoBodega = this.obtenerUltimoIdCargoBodegaCCanceladas();
        return this.reestablecerAutoincrementCargoBodega(maxIdCargoBodega + 1);
    }
    
}
