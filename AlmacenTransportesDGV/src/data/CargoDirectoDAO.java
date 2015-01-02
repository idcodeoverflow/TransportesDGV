/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.CargoDirectoDTO;
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
public class CargoDirectoDAO {
    
    public boolean agregarCargoDirecto(CargoDirectoDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO cargo_directo(numero_cargo_directo, fecha_registro, "
                + "precio_unitario, cantidad, subtotal, iva, total, status, "
                + "clave_refaccion, id_proveedor, folio, numero_usuario, "
                + "numero_orden) VALUES(NULL,NOW(),?,?,?,?,?,?,?,?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, cargo.getPrecioUnitario());
            pstmt.setDouble(2, cargo.getCantidad());
            pstmt.setDouble(3, cargo.getSubtotal());
            pstmt.setDouble(4, cargo.getIva());
            pstmt.setDouble(5, cargo.getTotal());
            pstmt.setBoolean(6, true);
            pstmt.setString(7, cargo.getRefaccion().getClaveRefaccion());
            pstmt.setInt(8, cargo.getFactura().getProveedor().getIdProveedor());
            pstmt.setString(9, cargo.getFactura().getFolio());
            pstmt.setInt(10, cargo.getUsuario().getNumeroUsuario());
            pstmt.setInt(11, cargo.getOrdenReparacion().getNumeroOrden());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 417\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nGrave: Consulte al administrador\nde"
                    + " la base de datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 417, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public void modificarCargoDirecto(CargoDirectoDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_directo SET fecha_registro = ?, precio_unitario = ?, cantidad = ?,"
                + " subtotal = ?, iva = ?, total = ?, clave_refaccion = ?, "
                + "id_proveedor = ?, folio = ? WHERE numero_cargo_directo = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, cargo.getFechaRegistro());
            pstmt.setDouble(2, cargo.getPrecioUnitario());
            pstmt.setDouble(3, cargo.getCantidad());
            pstmt.setDouble(4, cargo.getSubtotal());
            pstmt.setDouble(5, cargo.getIva());
            pstmt.setDouble(6, cargo.getTotal());
            pstmt.setString(7, cargo.getRefaccion().getClaveRefaccion());
            pstmt.setInt(8, cargo.getFactura().getProveedor().getIdProveedor());
            pstmt.setString(9, cargo.getFactura().getFolio());
            pstmt.setInt(10, cargo.getNumeroCargoDirecto());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 418\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 418, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarCargoDirecto(CargoDirectoDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_directo SET fecha_registro = ?, status = ? WHERE numero_cargo_directo = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, cargo.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, cargo.getNumeroCargoDirecto());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 419\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 419, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public CargoDirectoDTO obtenerCargoDirecto(int numeroCargoDirecto, boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        CargoDirectoDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_cargo_directo, fecha_registro, precio_unitario, cantidad, "
                + "subtotal, iva, total, status, clave_refaccion, id_proveedor, "
                + "folio, numero_usuario, numero_orden  FROM cargo_directo WHERE "
                + "numero_cargo_directo = ?";// AND status = ?;";//se elimino el filtrado por registros activos
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, numeroCargoDirecto);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            cargo = new CargoDirectoDTO();
            while (rs.next()) {
                cargo.setCantidad(rs.getDouble("cantidad"));
                cargo.setFactura(null);
                cargo.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                cargo.setIva(rs.getDouble("iva"));
                cargo.setNumeroCargoDirecto(rs.getInt("numero_cargo_directo"));
                cargo.setOrdenReparacion(null);
                cargo.setPrecioUnitario(rs.getDouble("precio_unitario"));
                cargo.setRefaccion(null);
                cargo.setStatus(rs.getBoolean("status"));
                cargo.setSubtotal(rs.getDouble("subtotal"));
                cargo.setTotal(rs.getDouble("total"));
                cargo.setUsuario(null);
                if(persistence){
                    cargo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    cargo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    cargo.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    cargo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 420\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 420, UserHome.getUsuario(), e);
        } finally {
            if(cerrar) {
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return cargo;
    }
    
    public List<CargoDirectoDTO> obtenerCargosDirectos(boolean persistence) throws SQLException{
        List<CargoDirectoDTO> cargos = null;
        CargoDirectoDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_cargo_directo, fecha_registro, precio_unitario, cantidad, "
                + "subtotal, iva, total, status, clave_refaccion, id_proveedor, "
                + "folio, numero_usuario, numero_orden  FROM cargo_directo WHERE "
                + "status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoDirectoDTO>();
            while (rs.next()) {
                cargo = new CargoDirectoDTO(
                rs.getInt("numero_cargo_directo"),
                rs.getTimestamp("fecha_registro"),
                rs.getDouble("precio_unitario"),
                rs.getDouble("cantidad"),
                rs.getDouble("subtotal"),
                rs.getDouble("iva"),
                rs.getDouble("total"),
                rs.getBoolean("status"),
                null,
                null,
                null,
                null);
                if(persistence){
                    cargo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    cargo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    cargo.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    cargo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                }
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 421\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 421, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public double obtenerTotalCargosDirectosTractoPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_directo WHERE numero_orden = ? AND status = ? AND numero_cargo_directo "
                + "IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != ?));";
        double totalCargosTracto = 0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCargosTracto = rs.getDouble("total_cargos");
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
        String query = "SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_directo WHERE numero_orden = ? AND status = ? AND numero_cargo_directo "
                + "IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = ?));";
        double totalCargosTracto = 0;
        
        try{
            if(abrir){
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            pstmt.setInt(3, 2);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                totalCargosTracto = rs.getDouble("total_cargos");
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
    
    public int obtenerUltimoCargoDirecto() throws SQLException{
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(numero_cargo_directo) AS ultimo_cargo FROM cargo_directo;";
        int numeroSalida = 0;
        
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                numeroSalida = rs.getInt("ultimo_cargo");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 424\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoCargoDirecto", 424, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        
        return numeroSalida;
    }
    
    /**
     * No usar esta función solo que se desee borrar la información de forma
     * permanente de la base de datos.
     * @param cargoDirecto Cargo Directo que se desea borrar.
     * @return true si el borrado fue correcto, false en caso contrario.
     * @throws SQLException 
     */
    public boolean borrarRegistroCargoDirecto(CargoDirectoDTO cargoDirecto) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "DELETE FROM cargo_directo WHERE numero_cargo_directo = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cargoDirecto.getNumeroCargoDirecto());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 425\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al borrar de forma permanente "
                    + "la información de la Base de Datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargoDirecto.toString(), 425, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean reestablecerAutoincrementCargoDirecto(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE cargo_directo AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 426\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementCargoDirecto " + index, 426, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorClasificacionCargoDirecto(CargoDirectoDTO cargo, int index) throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        return this.borrarRegistroCargoDirecto(cargo) && this.reestablecerAutoincrementCargoDirecto(index);
    }
    
}

