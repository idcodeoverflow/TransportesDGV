/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilidades;

import almacendgv.UserHome;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public final class FinallyHandler {
    
    public static void closeQuietly(Connection conn){
        try{
            conn.close();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 1369\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("FinallyHandler:closeQuietly()", 1369, UserHome.getUsuario(), ex);
        }
    }
    
    public static void closeQuietly(PreparedStatement pstmt){
        try{
            pstmt.close();
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Código error: 1670\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("FinallyHandler:closeQuietly()", 1670, UserHome.getUsuario(), ex);
        }
    }
    
}
