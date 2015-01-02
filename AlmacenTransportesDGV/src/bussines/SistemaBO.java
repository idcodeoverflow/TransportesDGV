/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bussines;

import almacendgv.UserHome;
import beans.SistemaDTO;
import data.SistemaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author David Israel
 */
public class SistemaBO {
    
    public SistemaBO(){
    }
    
    public void modificarControlSistema(SistemaDTO sistema){
        try {
            new SistemaDAO().modificarControlSistema(sistema);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: \n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog("SistemaBO.modificarControlSistema()", 0, UserHome.getUsuario(), ex);
        }
    }
    
    public SistemaDTO obtenerControlSistema(int idSistema, boolean abrir, boolean cerrar){
        try {
            return new SistemaDAO().obtenerControlSistema(idSistema, abrir, cerrar);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: \n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog("SistemaBO.obtenerControlSistema()", 0, UserHome.getUsuario(), ex);
        }
        return null;
    }
    
    public List<SistemaDTO> obtenerControlesSistema(boolean abrir, boolean cerrar){
        try {
            return new SistemaDAO().obtenerControlesSistema(abrir, cerrar);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: \n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog("SistemaBO.obtenerControlesSistema()", 0, UserHome.getUsuario(), ex);
        }
        return null;
    }
    
}
