/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.CargoEspecialDTO;
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
public class CargoEspecialDAO extends CargoDirectoDAO {
    
    public boolean agregarCargoEspecial(CargoEspecialDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO cargo_especial(id_cargo_especial, nombre_beneficiario,"
                + "fecha_registro, precio_unitario, cantidad, subtotal, iva, total, status,"
                + "clave_refaccion, id_proveedor, folio, numero_usuario, numero_orden) "
                + "VALUES(NULL,?,NOW(),?,?,?,?,?,?,?,?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cargo.getNombreBeneficiario());
            pstmt.setDouble(2, cargo.getPrecioUnitario());
            pstmt.setDouble(3, cargo.getCantidad());
            pstmt.setDouble(4, cargo.getSubtotal());
            pstmt.setDouble(5, cargo.getIva());
            pstmt.setDouble(6, cargo.getTotal());
            pstmt.setBoolean(7, true);
            pstmt.setString(8, cargo.getRefaccion().getClaveRefaccion());
            pstmt.setInt(9, cargo.getFactura().getProveedor().getIdProveedor());
            pstmt.setString(10, cargo.getFactura().getFolio());
            pstmt.setInt(11, cargo.getUsuario().getNumeroUsuario());
            pstmt.setInt(12, cargo.getOrdenReparacion().getNumeroOrden());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 427\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nGrave: Consulte al administrador\nde"
                    + " la base de datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 427, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public void modificarCargoEspecial(CargoEspecialDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_especial SET nombre_beneficiario = ? WHERE "
                + "id_cargo_especial = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cargo.getNombreBeneficiario());
            pstmt.setInt(2, cargo.getIdCargoEspecial());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 428\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 428, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarCargoEspecial(CargoEspecialDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_directo SET fecha_registro = ?, status = ? WHERE numero_cargo_directo "
                + "IN (SELECT numero_cargo_directo FROM cargo_especial WHERE id_cargo_especial = ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, cargo.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, cargo.getIdCargoEspecial());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 429\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 429, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public CargoEspecialDTO obtenerCargoEspecial(int idCargoEspecial) throws SQLException{
        CargoEspecialDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_especial, nombre_beneficiario, numero_cargo_directo FROM cargo_especial WHERE "
                + "id_cargo_especial = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idCargoEspecial);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cargo = new CargoEspecialDTO(rs.getInt("id_cargo_especial"), 
                        rs.getString("nombre_beneficiario"), 
                        new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 430\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 430, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return cargo;
    }
    
    public List<CargoEspecialDTO> obtenerCargosEspeciales() throws SQLException{
        List<CargoEspecialDTO> cargos = null;
        CargoEspecialDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_especial, nombre_beneficiario, numero_cargo_directo "
                + "FROM cargo_especial WHERE (SELECT status FROM cargo_directo WHERE "
                + "cargo_directo.numero_cargo_directo = cargo_especial.numero_cargo_directo) = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoEspecialDTO>();
            while (rs.next()) {
                cargo = new CargoEspecialDTO(
                rs.getInt("id_cargo_especial"),
                rs.getString("nombre_beneficiario"),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 431\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 431, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public List<CargoEspecialDTO> obtenerCargosEspecialesPFactura(FacturaDTO factura, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<CargoEspecialDTO> cargos = null;
        CargoEspecialDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_especial, nombre_beneficiario, numero_cargo_directo "
                + "FROM cargo_especial WHERE numero_cargo_directo IN (SELECT numero_cargo_directo "
                + "FROM cargo_directo WHERE id_proveedor = ? AND folio = ? AND status = ?);";
        
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
            cargos = new ArrayList<CargoEspecialDTO>();
            while (rs.next()) {
                cargo = new CargoEspecialDTO(
                rs.getInt("id_cargo_especial"),
                rs.getString("nombre_beneficiario"),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 432\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 432, UserHome.getUsuario(), e);
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
    
    public List<CargoEspecialDTO> obtenerCargosEspecialesPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<CargoEspecialDTO> cargos = null;
        CargoEspecialDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_especial, nombre_beneficiario, numero_cargo_directo "
                + "FROM cargo_especial WHERE numero_cargo_directo IN (SELECT numero_cargo_directo "
                + "FROM cargo_directo WHERE numero_orden = ? AND status = ?);";
        
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoEspecialDTO>();
            while (rs.next()) {
                cargo = new CargoEspecialDTO(
                rs.getInt("id_cargo_especial"),
                rs.getString("nombre_beneficiario"),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 433\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 433, UserHome.getUsuario(), e);
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
    
    public double obtenerTotalCargosEspecialesPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalCargosEspeciales = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(total), 0.0) AS cargos_especiales FROM "
                + "cargo_directo WHERE numero_orden = ? AND status = ? AND numero_cargo_directo "
                + "IN(SELECT numero_cargo_directo FROM cargo_especial);";
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
                totalCargosEspeciales = rs.getDouble("cargos_especiales");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 434\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 434, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalCargosEspeciales;
    }
    
    public int obtenerUltimoIdCargoEspecialCCanceladas() throws SQLException{
        int maxIdCargoEspecial = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_cargo_especial) AS max_cargo FROM cargo_especial;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdCargoEspecial = rs.getInt("max_cargo");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 435\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdCargoEspecialCCanceladas " + maxIdCargoEspecial, 435, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdCargoEspecial;
    }
    
    public boolean reestablecerAutoincrementCargoEspecial(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE cargo_especial AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 436\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementCargoEspecial " + index, 436, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarCargoEspecial() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdCargoEspecial = this.obtenerUltimoIdCargoEspecialCCanceladas();
        return this.reestablecerAutoincrementCargoEspecial(maxIdCargoEspecial + 1);
    }
    
}
