/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import beans.LogInDTO;
import data.LogInDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author David Israel
 */
public class LogInBO {
    
    /**
     * Constructor por Default.
     */
    public LogInBO(){}
    
    /**
     * Valida si la informacion de acceso a la aplicacion es valida de acuerdo
     * a los registros encontrados en la base de datos, regreso true si es
     * valida la informacion o false en caso contrario.
     * @param login Objeto LogInDTO con la informacion de inicio.
     * @return true si es valido, false si no es valido.
     */
    public int esValido(LogInDTO login){
        LogInDAO acceso = new LogInDAO();
        int n = 0;
        try {
            n = acceso.esValido(login);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error 301: \n" + ex.getMessage()
                    + "\nError de la BD al iniciar sesión.",
                    "Error!!!", JOptionPane.ERROR_MESSAGE); 
            logger.ErrorLogger.scribirLog(login.toString(), 301, UserHome.getUsuario(), ex);
            //Logger.getLogger(LogInBO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
}
