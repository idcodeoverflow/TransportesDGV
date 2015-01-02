/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.CargoOperadorDTO;
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
public class CargoOperadorDAO extends CargoDirectoDAO {
    
    public boolean agregarCargoOperador(CargoOperadorDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO cargo_operador(id_cargo_operador, numero_cargo_directo, "
                + "numero_operador) VALUES(NULL,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cargo.getNumeroCargoDirecto());
            pstmt.setInt(2, cargo.getOperador().getNumeroOperador());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 437\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nGrave: Consulte al administrador\nde"
                    + " la base de datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 437, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public void modificarCargoOperador(CargoOperadorDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_operador SET numero_operador = ? WHERE "
                + "id_cargo_operador = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cargo.getOperador().getNumeroOperador());
            pstmt.setInt(2, cargo.getIdCargoOperador());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 438\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 438, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarCargoOperador(CargoOperadorDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_directo SET fecha_registro = ?, status = ? WHERE numero_cargo_directo "
                + "IN (SELECT numero_cargo_directo FROM cargo_operador WHERE id_cargo_operador = ?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, cargo.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, cargo.getIdCargoOperador());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 439\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 439, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public CargoOperadorDTO obtenerCargoOperador(int idCargoOperador) throws SQLException{
        CargoOperadorDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_operador, numero_cargo_directo, numero_operador FROM cargo_operador WHERE "
                + "id_cargo_operador = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idCargoOperador);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cargo = new CargoOperadorDTO(rs.getInt("id_cargo_operador"), 
                        new OperadorDAO().obtenerOperador(rs.getInt("numero_operador"), false, false),
                        new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 440\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 440, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return cargo;
    }
    
    public List<CargoOperadorDTO> obtenerCargosOperadores() throws SQLException{
        List<CargoOperadorDTO> cargos = null;
        CargoOperadorDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_operador, numero_cargo_directo, numero_operador "
                + "FROM cargo_operador WHERE (SELECT status FROM cargo_directo WHERE "
                + "cargo_directo.numero_cargo_directo = cargo_operador.numero_cargo_directo) = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoOperadorDTO>();
            while (rs.next()) {
                cargo = new CargoOperadorDTO(
                rs.getInt("id_cargo_operador"),
                new OperadorDAO().obtenerOperador(rs.getInt("numero_operador"), false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 441\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 441, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public List<CargoOperadorDTO> obtenerCargosOperadoresPFactura(FacturaDTO factura, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<CargoOperadorDTO> cargos = null;
        CargoOperadorDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_operador, numero_cargo_directo, numero_operador "
                + "FROM cargo_operador WHERE numero_cargo_directo IN "
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
            cargos = new ArrayList<CargoOperadorDTO>();
            while (rs.next()) {
                cargo = new CargoOperadorDTO(
                rs.getInt("id_cargo_operador"),
                new OperadorDAO().obtenerOperador(rs.getInt("numero_operador"), false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 442\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString() + factura.toString(), 442, UserHome.getUsuario(), e);
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
    
    public List<CargoOperadorDTO> obtenerCargosOperadoresPReparacion(OrdenReparacionDTO reparacion, 
            boolean abrir, boolean cerrar) throws SQLException{
        List<CargoOperadorDTO> cargos = null;
        CargoOperadorDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_operador, numero_cargo_directo, numero_operador "
                + "FROM cargo_operador WHERE numero_cargo_directo IN "
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
            cargos = new ArrayList<CargoOperadorDTO>();
            while (rs.next()) {
                cargo = new CargoOperadorDTO(
                rs.getInt("id_cargo_operador"),
                new OperadorDAO().obtenerOperador(rs.getInt("numero_operador"), false, false),
                new CargoDirectoDAO().obtenerCargoDirecto(rs.getInt("numero_cargo_directo"), true, false, false));
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 443\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(reparacion.toString(), 443, UserHome.getUsuario(), e);
        } finally {
            if(cerrar) {
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public double obtenerTotalCargosOperadorPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalCargosOperador = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(total), 0.0) AS cargos_operador FROM "
                + "cargo_directo WHERE numero_orden = ? AND status = ? AND numero_cargo_directo "
                + "IN(SELECT numero_cargo_directo FROM cargo_operador);";
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
                totalCargosOperador = rs.getDouble("cargos_operador");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 444\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 444, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalCargosOperador;
    }
    
    public int obtenerUltimoIdCargoOperadorCCanceladas() throws SQLException{
        int maxIdCargoOperador = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_cargo_operador) AS max_cargo FROM cargo_operador;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdCargoOperador = rs.getInt("max_cargo");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 445\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdCargoOperadorCCanceladas " + maxIdCargoOperador, 445, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdCargoOperador;
    }
    
    public boolean reestablecerAutoincrementCargoOperador(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE cargo_operador AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 446\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementCargoOperador " + index, 446, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarCargoOperador() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdCargoOperador = this.obtenerUltimoIdCargoOperadorCCanceladas();
        return this.reestablecerAutoincrementCargoOperador(maxIdCargoOperador + 1);
    }
    
}
