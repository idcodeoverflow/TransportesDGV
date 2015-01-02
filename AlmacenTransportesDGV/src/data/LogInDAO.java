/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static utilidades.FinallyHandler.closeQuietly;
import almacendgv.UserHome;
import beans.LogInDTO;
import beans.UsuarioDTO;
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
public final class LogInDAO {
    
    /**
     * Constructor por Default.
     */
    public LogInDAO(){}
    
    /**
     * Valida si la informacion de acceso a la aplicacion es valida de acuerdo
     * a los registros encontrados en la base de datos, regreso true si es
     * valida la informacion o false en caso contrario.
     * @param login Objeto LogInDTO con la informacion de inicio.
     * @return true si es valido, false si no es valido.
     * @throws SQLException  
     */
    public int esValido(LogInDTO login) throws SQLException{
        UsuarioDTO usuario = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String query = "SELECT numero_usuario FROM usuario WHERE nombre = ? "
                + "AND passwd = PASSWORD(?) AND status = ?;";
        
        try{
            DBConnection.createConnection();
            conn = DBConnection.getConn();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, login.getUser());
            pstmt.setString(2, login.getPassword());
            pstmt.setBoolean(3, true);
            rs = pstmt.executeQuery();
            usuario = new UsuarioDTO();
            while (rs.next()) {
                usuario.setNumeroUsuario(rs.getInt("numero_usuario"));
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Código error: 498\n" + e.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            //cachar excepcion en caso de obtener un usuario nulo
            try{
                ErrorLogger.scribirLog(usuario.toString(), 498, UserHome.getUsuario(), e);
            } catch(NullPointerException ex){}
        } finally {
            closeQuietly(conn);
            closeQuietly(pstmt);
        }
        return ((usuario != null) ? usuario.getNumeroUsuario() : -1);
    }
    
}
