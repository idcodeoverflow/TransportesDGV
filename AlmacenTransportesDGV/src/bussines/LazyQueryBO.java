/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import data.LazyQueryDAO;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author David Israel
 */
public class LazyQueryBO {
    
    private LazyQueryDAO lazyQ;
    
    public LazyQueryBO(){
        lazyQ = new LazyQueryDAO();
    }
    
    public void startLazyQuery(){
        lazyQ.startLazyQuery();
    }
    
    public void endLazyQuery(){
        try {
            lazyQ.endLazyQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error 300: \n" + ex.getMessage()
                    + "\nError al cerrar conexión segura a BD.",
                    "Error!!!", JOptionPane.ERROR_MESSAGE); 
            logger.ErrorLogger.scribirLog("endLazyQueryBO", 300, UserHome.getUsuario(), ex);
        }
    }
    
}
