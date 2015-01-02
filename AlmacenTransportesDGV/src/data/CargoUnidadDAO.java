/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.CargoUnidadDTO;
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
public class CargoUnidadDAO extends CargoDirectoDAO {
    
    public boolean agregarCargoUnidad(CargoUnidadDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO cargo_unidad(id_cargo_unidad, numero_cargo_directo, "
                + "clave) VALUES(NULL,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cargo.getNumeroCargoDirecto());
            pstmt.setString(2, cargo.getUnidad().getClave());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 447\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nGrave: Consulte al administrador\nde"
                    + " la base de datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 447, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public void modificarCargoUnidad(CargoUnidadDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_unidad SET clave = ? WHERE "
                + "id_cargo_unidad = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cargo.getUnidad().getClave());
            pstmt.setInt(2, cargo.getIdCargoUnidad());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 448\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 448, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarCargoUnidad(CargoUnidadDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_directo SET fecha_registro = ?, status = ? WHERE numero_cargo_directo "
                + "IN (SELECT numero_cargo_directo FROM cargo_unidad WHERE id_cargo_unidad = ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, cargo.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, cargo.getIdCargoUnidad());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 449\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 449, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public CargoUnidadDTO obtenerCargoUnidad(int idCargoUnidad) throws SQLException{
        CargoUnidadDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_unidad, numero_cargo_directo, clave FROM cargo_unidad WHERE "
                + "id_cargo_unidad = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idCargoUnidad);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cargo = new CargoUnidadDTO(rs.getInt("id_cargo_unidad"), 
                        new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false),
                        new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 450\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 450, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return cargo;
    }
    
    public List<CargoUnidadDTO> obtenerCargosUnidades() throws SQLException{
        List<CargoUnidadDTO> cargos = null;
        CargoUnidadDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_unidad, numero_cargo_directo, clave "
                + "FROM cargo_unidad WHERE (SELECT status FROM cargo_directo WHERE "
                + "cargo_directo.numero_cargo_directo = cargo_unidad.numero_cargo_directo) = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoUnidadDTO>();
            while (rs.next()) {
                cargo = new CargoUnidadDTO(
                rs.getInt("id_cargo_unidad"),
                new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 451\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 451, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public List<CargoUnidadDTO> obtenerCargosUnidadesPFactura(FacturaDTO factura, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<CargoUnidadDTO> cargos = null;
        CargoUnidadDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_unidad, numero_cargo_directo, clave "
                + "FROM cargo_unidad WHERE numero_cargo_directo IN "
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
            cargos = new ArrayList<CargoUnidadDTO>();
            while (rs.next()) {
                cargo = new CargoUnidadDTO(
                rs.getInt("id_cargo_unidad"),
                new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 452\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 452, UserHome.getUsuario(), e);
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
    
    public List<CargoUnidadDTO> obtenerCargosUnidadesPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<CargoUnidadDTO> cargos = null;
        CargoUnidadDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_unidad, numero_cargo_directo, clave "
                + "FROM cargo_unidad WHERE numero_cargo_directo IN "
                + "(SELECT numero_cargo_directo FROM cargo_directo WHERE "
                + "numero_orden = ? AND status = ?);";
        
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoUnidadDTO>();
            while (rs.next()) {
                cargo = new CargoUnidadDTO(
                rs.getInt("id_cargo_unidad"),
                new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 453\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString() + ordenReparacion.toString(), 453, UserHome.getUsuario(), e);
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
    
    public double obtenerTotalCargosUnidadesPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalCargosUnidades = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(total), 0.0) AS cargos_unidades FROM "
                + "cargo_directo WHERE numero_orden = ? AND status = ? AND numero_cargo_directo "
                + "IN(SELECT numero_cargo_directo FROM cargo_unidad);";
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
                totalCargosUnidades = rs.getDouble("cargos_unidades");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 454\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 454, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalCargosUnidades;
    }
    
    public int obtenerUltimoIdCargoUnidadCCanceladas() throws SQLException{
        int maxIdCargoUnidad = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_cargo_unidad) AS max_cargo FROM cargo_unidad;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdCargoUnidad = rs.getInt("max_cargo");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 455\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdCargoUnidadCCanceladas " + maxIdCargoUnidad, 455, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdCargoUnidad;
    }
    
    public boolean reestablecerAutoincrementCargoUnidad(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE cargo_unidad AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 456\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementCargoUnidad " + index, 456, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarCargoUnidad() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdCargoUnidad = this.obtenerUltimoIdCargoUnidadCCanceladas();
        return this.reestablecerAutoincrementCargoUnidad(maxIdCargoUnidad + 1);
    }
    
}
