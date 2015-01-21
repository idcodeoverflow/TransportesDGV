/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.*;
import almacendgv.UserHome;
import beans.CargoTallerDTO;
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
public class CargoTallerDAO extends CargoDirectoDAO {
    
    public boolean agregarCargoTaller(CargoTallerDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO cargo_taller(id_cargo_taller, "
                + "fecha_registro, precio_unitario, cantidad, subtotal, iva, total, status,"
                + "clave_refaccion, id_proveedor, folio, numero_usuario, numero_orden, clave) "
                + "VALUES(NULL,NOW(),?,?,?,?,?,?,?,?,?,?,?);";
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
            pstmt.setString(12, cargo.getUnidad().getClave());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2029\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nGrave: Consulte al administrador\nde"
                    + " la base de datos.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 2029, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public void modificarCargoTaller(CargoTallerDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_taller SET "
                + "fecha_registro = ?, precio_unitario = ?, cantidad = ?,"
                + " subtotal = ?, iva = ?, total = ?, clave_refaccion = ?, "
                + "id_proveedor = ?, folio = ? WHERE id_cargo_taller = ?;";
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
            pstmt.setInt(10, cargo.getIdCargoTaller());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2030\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 2030, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarCargoTaller(CargoTallerDTO cargo) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE cargo_taller SET fecha_registro = ?, status = ? WHERE "
                + " id_cargo_taller = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, cargo.getFechaRegistro());
            pstmt.setBoolean(2, false);
            pstmt.setInt(3, cargo.getIdCargoTaller());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2031\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 2031, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public CargoTallerDTO obtenerCargoTaller(int idCargoTaller, boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        CargoTallerDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_taller, fecha_registro, precio_unitario, cantidad, "
                + "subtotal, iva, total, status, clave_refaccion, id_proveedor, "
                + "folio, numero_usuario, numero_orden, clave FROM cargo_taller WHERE "
                + "id_cargo_taller = ?;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idCargoTaller);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cargo = new CargoTallerDTO(
                        rs.getInt("id_cargo_taller"),
                        null,//unidad transporte
                        0,//numero cargo directo
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"),
                        rs.getDouble("total"),
                        rs.getBoolean("status"),
                        null,//refaccion
                        null,//factura
                        null,//usuario
                        null//orden reparacion
                        );
                if(persistence){
                    cargo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    cargo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    cargo.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    cargo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                    cargo.setUnidad(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                }
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2032\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 2032, UserHome.getUsuario(), e);
        } finally {
            if(cerrar) {
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return cargo;
    }
    
    public List<CargoTallerDTO> obtenerCargosTaller(boolean persistence) throws SQLException{
        List<CargoTallerDTO> cargos = null;
        CargoTallerDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_taller, fecha_registro, precio_unitario, cantidad, "
                + "subtotal, iva, total, status, clave_refaccion, id_proveedor, "
                + "folio, numero_usuario, numero_orden, clave  FROM cargo_taller WHERE "
                + "status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoTallerDTO>();
            while (rs.next()) {
               cargo = new CargoTallerDTO(
                        rs.getInt("id_cargo_taller"),
                        null,//unidad transporte
                        0,//numero cargo directo
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"),
                        rs.getDouble("total"),
                        rs.getBoolean("status"),
                        null,//refaccion
                        null,//factura
                        null,//usuario
                        null//orden reparacion
                        );
                if(persistence){
                    cargo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    cargo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    cargo.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    cargo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                    cargo.setUnidad(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                }
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2033\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(cargo.toString(), 2033, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(cargos != null){
            return cargos;
        }
        return null;
    }
    
    public List<CargoTallerDTO> obtenerCargosTallerPFactura(FacturaDTO factura, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<CargoTallerDTO> cargos = null;
        CargoTallerDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_taller, fecha_registro, precio_unitario, cantidad, "
                + "subtotal, iva, total, status, clave_refaccion, id_proveedor, "
                + "folio, numero_usuario, numero_orden, clave FROM cargo_taller WHERE"
                + " id_proveedor = ? AND folio = ? AND status = ?;";
        
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
            cargos = new ArrayList<CargoTallerDTO>();
            while (rs.next()) {
                cargo = new CargoTallerDTO(
                        rs.getInt("id_cargo_taller"),
                        null,//unidad transporte
                        0,//numero cargo directo
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"),
                        rs.getDouble("total"),
                        rs.getBoolean("status"),
                        null,//refaccion
                        null,//factura
                        null,//usuario
                        null//orden reparacion
                        );
                if(persistence){
                    cargo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    cargo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    cargo.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    cargo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                    cargo.setUnidad(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                }
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2034\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(factura.toString(), 2034, UserHome.getUsuario(), e);
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
    
    public List<CargoTallerDTO> obtenerCargosTallerPReparacion(OrdenReparacionDTO ordenReparacion, 
            boolean persistence, boolean abrir, boolean cerrar) throws SQLException{
        List<CargoTallerDTO> cargos = null;
        CargoTallerDTO cargo = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT id_cargo_taller, fecha_registro, precio_unitario, cantidad, "
                + "subtotal, iva, total, status, clave_refaccion, id_proveedor, "
                + "folio, numero_usuario, numero_orden, clave FROM cargo_taller WHERE "
                + "numero_orden = ? AND status = ?;";
        
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ordenReparacion.getNumeroOrden());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            cargos = new ArrayList<CargoTallerDTO>();
            while (rs.next()) {
                cargo = new CargoTallerDTO(
                        rs.getInt("id_cargo_taller"),
                        null,//unidad transporte
                        0,//numero cargo directo
                        rs.getTimestamp("fecha_registro"),
                        rs.getDouble("precio_unitario"),
                        rs.getInt("cantidad"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("iva"),
                        rs.getDouble("total"),
                        rs.getBoolean("status"),
                        null,//refaccion
                        null,//factura
                        null,//usuario
                        null//orden reparacion
                        );
                if(persistence){
                    cargo.setFactura(new FacturaDAO().obtenerFactura(rs.getString("folio"), rs.getInt("id_proveedor"), true, false, false));
                    cargo.setOrdenReparacion(new OrdenReparacionDAO().obtenerOrdenReparacion(rs.getInt("numero_orden"), true, false, false));
                    cargo.setRefaccion(new RefaccionDAO().obtenerRefaccion(rs.getString("clave_refaccion"), false, false));
                    cargo.setUsuario(new UsuarioDAO().obtenerUsuario(rs.getInt("numero_usuario"), false, false));
                    cargo.setUnidad(new UnidadTransporteDAO().obtenerUnidad(rs.getString("clave"), true, false, false));
                }
                cargos.add(cargo);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2035\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 2035, UserHome.getUsuario(), e);
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
    
    public double obtenerTotalCargosTallerPReparacion(OrdenReparacionDTO ordenReparacion, boolean abrir, boolean cerrar) throws SQLException{
        double totalCargosEspeciales = 0.0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL(SUM(total), 0.0) AS cargos_taller FROM "
                + "cargo_taller WHERE numero_orden = ? AND status = ?;";
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
                totalCargosEspeciales = rs.getDouble("cargos_taller");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2036\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(ordenReparacion.toString(), 2036, UserHome.getUsuario(), e);
            return 0.0;
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return totalCargosEspeciales;
    }
    
    public int obtenerUltimoIdCargoTallerCCanceladas() throws SQLException{
        int maxIdCargoEspecial = 0;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT MAX(id_cargo_taller) AS max_cargo FROM cargo_taller;";
        try{
            DBConnection.createConnection();
            
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                maxIdCargoEspecial = rs.getInt("max_cargo");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 2037\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("obtenerUltimoIdCargoEspecialCCanceladas " + maxIdCargoEspecial, 2037, UserHome.getUsuario(), e);
            return 0;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return maxIdCargoEspecial;
    }
    
    public boolean reestablecerAutoincrementCargoTaller(int index) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "ALTER TABLE cargo_taller AUTO_INCREMENT = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2038\n" + ex.getMessage(),
                    "Error en acceso a datos!!!\nError al restablecer el index autoincrementable de"
                    + "la tabla.", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("reestablecerAutoincrementCargoEspecial " + index, 2038, UserHome.getUsuario(), ex);
            return false;
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return true;
    }
    
    public boolean repararErrorAgregarCargoTaller() throws SQLException{
        JOptionPane.showMessageDialog(null, "Se intentará reparar el error generado, de forma automática.", 
                "Se intentará reparar el error", JOptionPane.INFORMATION_MESSAGE);
        int maxIdCargoTaller = this.obtenerUltimoIdCargoTallerCCanceladas();
        return this.reestablecerAutoincrementCargoTaller(maxIdCargoTaller + 1);
    }
    
}
