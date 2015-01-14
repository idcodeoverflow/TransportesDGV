/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.EntradaAlmacenDTO;
import beans.RefaccionDTO;
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
public class RefaccionDAO {
    
    public void agregarRefaccion(RefaccionDTO refaccion) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "INSERT INTO refaccion(clave_refaccion, nombre, punto_reorden,"
                + " maximo, minimo, notificacion, status, id_familia_refaccion) VALUES(?,?,?,?,?,?,?,?);";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, refaccion.getClaveRefaccion());
            pstmt.setString(2, refaccion.getNombre());
            pstmt.setInt(3, refaccion.getPuntoReorden());
            pstmt.setInt(4, refaccion.getMaximo());
            pstmt.setInt(5, refaccion.getMinimo());
            pstmt.setBoolean(6, true);
            pstmt.setBoolean(7, true);
            pstmt.setInt(8, refaccion.getFamilia().getIdFamiliaRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 541\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(refaccion.toString(), 541, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void modificarRefaccion(RefaccionDTO refaccion) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE refaccion SET clave_refaccion = ?, nombre = ?, "
                + "punto_reorden = ?, maximo = ?, minimo = ?, id_familia_refaccion = ?, notificacion = ? "
                + "WHERE clave_refaccion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, refaccion.getClaveRefaccion());
            pstmt.setString(2, refaccion.getNombre());
            pstmt.setInt(3, refaccion.getPuntoReorden());
            pstmt.setInt(4, refaccion.getMaximo());
            pstmt.setInt(5, refaccion.getMinimo());
            pstmt.setInt(6, refaccion.getFamilia().getIdFamiliaRefaccion());
            pstmt.setBoolean(7, refaccion.isNotificacion());
            pstmt.setString(8, refaccion.getClaveRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 542\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(refaccion.toString(), 542, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public void eliminarRefaccion(RefaccionDTO refaccion) throws SQLException{
        PreparedStatement pstmt = null;
        Connection conn = null;
        String query = "UPDATE refaccion SET status = ? WHERE clave_refaccion = ?;";
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, false);
            pstmt.setString(2, refaccion.getClaveRefaccion());
            pstmt.executeUpdate();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 543\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(refaccion.toString(), 543, UserHome.getUsuario(), ex);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
    }
    
    public RefaccionDTO obtenerRefaccion(String claveRefaccion, boolean abrir, boolean cerrar) throws SQLException{
        RefaccionDTO refaccion = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT clave_refaccion, nombre, punto_reorden, maximo, minimo, notificacion, status,"
                + " id_familia_refaccion FROM refaccion WHERE "
                + "clave_refaccion = ?";// AND status = ?;";//Se elimino el filtrado de registros activos
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, claveRefaccion);
            //pst.setBoolean(2, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                refaccion = new RefaccionDTO();
                refaccion.setClaveRefaccion(rs.getString("clave_refaccion"));
                refaccion.setNombre(rs.getString("nombre"));
                refaccion.setPuntoReorden(rs.getInt("punto_reorden"));
                refaccion.setMaximo(rs.getInt("maximo"));
                refaccion.setMinimo(rs.getInt("minimo"));
                refaccion.setNotificacion(rs.getBoolean("notificacion"));
                refaccion.setStatus(rs.getBoolean("status"));
                refaccion.setFamilia(new FamiliaRefaccionDAO().obtenerFamilia(rs.getInt("id_familia_refaccion"), false, false));
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 544\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(refaccion.toString(), 544, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return refaccion;
    }
    
    public List<RefaccionDTO> obtenerRefacciones() throws SQLException{
        List<RefaccionDTO> refacciones = null;
        RefaccionDTO refaccion = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT clave_refaccion, nombre, punto_reorden, maximo, minimo, notificacion, status,"
                + " id_familia_refaccion FROM refaccion WHERE status = ? ORDER BY clave_refaccion ASC;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            rs = pstmt.executeQuery();
            refacciones = new ArrayList<RefaccionDTO>();
            while (rs.next()) {
                refaccion = new RefaccionDTO(
                rs.getString("clave_refaccion"),
                rs.getString("nombre"),
                rs.getInt("punto_reorden"),
                rs.getInt("maximo"),
                rs.getInt("minimo"),
                rs.getBoolean("notificacion"),
                rs.getBoolean("status"),
                new FamiliaRefaccionDAO().obtenerFamilia(rs.getInt("id_familia_refaccion"), false, false));
                refacciones.add(refaccion);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 545\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(refaccion.toString(), 545, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(refacciones != null){
            return refacciones;
        }
        return null;
    }
    
    public double obtenerExistenciaRefaccion(String claveRefaccion, boolean abrir, boolean cerrar) throws SQLException{
        double existencia = 0.00;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT IFNULL((SELECT SUM(entradas.cantidad) AS inventario FROM "
                + "(SELECT cantidad FROM entrada_almacen WHERE clave_refaccion = ?  "
                + "AND status = ?) AS entradas),0.0) - "
                
                + "IFNULL((SELECT SUM(salidas_especial.cantidad) AS "
                + "inventario FROM (SELECT cantidad FROM salida_especial WHERE clave_refaccion = "
                + "?  AND status = ?) AS salidas_especial),0.0) - "
                
                + "IFNULL((SELECT SUM(salidas_unidad.cantidad) AS "
                + "inventario FROM (SELECT cantidad FROM salida_unidad WHERE clave_refaccion = "
                + "?  AND status = ?) AS salidas_unidad),0.0) - "
                
                + "IFNULL((SELECT SUM(salidas_operador.cantidad) AS "
                + "inventario FROM (SELECT cantidad FROM salida_operador WHERE clave_refaccion = "
                + "?  AND status = ?) AS salidas_operador),0.0) AS existencia;";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, claveRefaccion);
            pstmt.setBoolean(2, true);
            pstmt.setString(3, claveRefaccion);
            pstmt.setBoolean(4, true);
            pstmt.setString(5, claveRefaccion);
            pstmt.setBoolean(6, true);
            pstmt.setString(7, claveRefaccion);
            pstmt.setBoolean(8, true);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existencia = rs.getDouble("existencia");
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 546\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("clave_refaccion " + claveRefaccion + " existencia " + existencia, 546, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
                closeQuietly(pstmt);
            }
        }
        return existencia;
    }
    
    public double obtenerPrecioRefaccion(String claveRefaccion, boolean abrir, boolean cerrar) throws SQLException{
        double precio = 0.00;
        double existenciaPieza = 0.00;
        double temporalExistenciaPieza = 0.00;
        List<EntradaAlmacenDTO> entradasAlmacen = new ArrayList<EntradaAlmacenDTO>();
        EntradaAlmacenDAO accesoAlmacen = new EntradaAlmacenDAO();
        EntradaAlmacenDTO entradaAlmacenPendiente = new EntradaAlmacenDTO();
        Connection conn = null;
        String query = "";
        try{
            if(abrir) {
                DBConnection.createConnection();
            }
            conn = DBConnection.getConn();
            entradasAlmacen = accesoAlmacen.obtenerEntradasAlmacenPRefaccionSinCanceladas(claveRefaccion, true, false, false);//obtener salidas de almacen
            existenciaPieza = this.obtenerExistenciaRefaccion(claveRefaccion, false, false);//obtener la existencia de la pieza
            temporalExistenciaPieza = existenciaPieza;
            entradaAlmacenPendiente.setCantidad(0.00);
            entradaAlmacenPendiente.setPrecioUnitario(0.00);
            entradaAlmacenPendiente.setMonto(0.00);
            
            for(EntradaAlmacenDTO entradaAlmacen : entradasAlmacen){
                if(temporalExistenciaPieza - entradaAlmacen.getCantidad() <= 0.00){
                    entradaAlmacenPendiente = entradaAlmacen;
                    break;
                } else {
                    temporalExistenciaPieza -= entradaAlmacen.getCantidad();
                    precio += entradaAlmacen.getMonto();
                }
            }
            
            precio += temporalExistenciaPieza * ((entradaAlmacenPendiente.getCantidad() != 0.00) ? (entradaAlmacenPendiente.getMonto() / entradaAlmacenPendiente.getCantidad()) : 0.00);
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 547\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("clave_refaccion " + claveRefaccion + " precio " + precio, 547, UserHome.getUsuario(), e);
        } finally {
            if(cerrar){
                closeQuietly(conn);
            }
        }
        return (double)((existenciaPieza != 0.00) ? (precio / existenciaPieza) : 0.00);
    }

    public List<RefaccionDTO> buscarRefaccion(RefaccionDTO refaccionBuscar) throws SQLException{
        List<RefaccionDTO> refacciones = null;
        RefaccionDTO refaccion = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT clave_refaccion, nombre, punto_reorden, maximo, minimo, notificacion, status,"
                + " id_familia_refaccion FROM refaccion WHERE status = ? AND nombre LIKE CONCAT('%',?,'%')" 
                + " ORDER BY clave_refaccion ASC;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, refaccionBuscar.getNombre());
            rs = pstmt.executeQuery();
            refacciones = new ArrayList<RefaccionDTO>();
            while (rs.next()) {
                refaccion = new RefaccionDTO(
                rs.getString("clave_refaccion"),
                rs.getString("nombre"),
                rs.getInt("punto_reorden"),
                rs.getInt("maximo"),
                rs.getInt("minimo"),
                rs.getBoolean("notificacion"),
                rs.getBoolean("status"),
                new FamiliaRefaccionDAO().obtenerFamilia(rs.getInt("id_familia_refaccion"), false, false));
                refacciones.add(refaccion);
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 548\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog(refaccion.toString(), 548, UserHome.getUsuario(), e);
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        if(refacciones != null){
            return refacciones;
        }
        return null;
    }
    
}
