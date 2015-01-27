/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logger;

import almacendgv.UserHome;
import beans.UsuarioDTO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import mailer.AccountAddress;
import mailer.MailData;
import mailer.Mailer;
import static utilidades.DataChecker.*;

/**
 *
 * @author David Israel
 */
public class ErrorLogger implements ExceptionLogger {

    private static String loggerPath = System.getProperty("user.home") + "\\logs\\"; //obtener acceso a la carpeta personal del usuario
    private static String loggerName = "";
    
    {
        //generar nombre del archivo de registro de errores
        try{
        String dateElements[] = new java.util.Date().toString().split(" ");
        loggerName = dateElements[1] + "-" + dateElements[2] + "-" + dateElements[5] + ".log";
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1362\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:initializationSegment", 1362, UserHome.getUsuario(), ex);
        }
        
        //regenerar rutas si es que estas se han modificado o eliminado
        try {
            generarRuta();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1363\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:initializationSegment", 1363, UserHome.getUsuario(), ex);
        }
    }
    
    public ErrorLogger(){
        
    }
    
    @Override
    public boolean crearNuevoLog() {
        try {
            generarRuta();
            File file = new File(loggerPath + loggerName);
            return file.createNewFile();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1364\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:crearNuevoLog()", 1364, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1365\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:crearNuevoLog()", 1365, UserHome.getUsuario(), ex);
        }
        return false;
    }

    @Override
    public boolean existeLog(String name) {
        String path = loggerPath + name;
        File file = new File(path);
        return file.exists();
    }

    @Override
    public boolean borrarLog(String name) {
        String path = loggerPath + name;
        File file = new File(path);
        return file.delete();
    }

    @Override
    public boolean existePath() {
        return new File(loggerPath).isDirectory();
    }
    
    @Override
    public boolean generarRuta() throws IOException{
        if(!existePath()) {
            return new File(loggerPath).mkdir();
        }
        return false;
    }
    
    public static boolean scribirLog(String message, int codigo, UsuarioDTO usuario, Exception ex) {
        try{
            final AccountAddress account = new AccountAddress("mailer.transportesdgv@gmail.com","transportesdg");
            final Mailer mailer = new Mailer(account);
            ErrorLogger errorLogger = new ErrorLogger();
            String path = errorLogger.loggerPath + errorLogger.loggerName;
            File file = new File(path);
            FileWriter fWriter = new FileWriter(file, true);
            BufferedWriter bf = new BufferedWriter(fWriter);
            
            if(errorLogger.isEmptyLoggerName() || errorLogger.isEmptyLoggerPath()){
                JOptionPane.showMessageDialog(null, "No hay una ruta o nombre definido"
                        + "\npara el log de errores.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            /*if(!errorLogger.existeLog(errorLogger.loggerName)){
                errorLogger.crearNuevoLog();
                //System.out.println("No existe");
            }*/
            try (PrintWriter pWriter = new PrintWriter(bf)) {
                if(file.length() > 5){
                    pWriter.println();
                }
                pWriter.append(new java.util.Date() + " | " + "Código: " + codigo + " usuario: " + nullCheck(usuario) + " mensaje: " + message + " Excepción: " + ex);
            }
            bf.close();
            fWriter.close();
            mailer.send("Ocurrió un error en el sistema del almacen del taller. "
                    + "Usuario: " + nullCheck(UserHome.getUsuario()), loggerPath, loggerName, MailData.SOPORTE);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Código error: 1359\n"
                        + "Error al escribir en el log." + e, "Error!!!",
                        JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public String getLoggerPath(){
        return loggerPath;
    }
    
    public void setLoggerPath(String loggerPath){
        this.loggerPath = loggerPath;
    }
    
    public String getLoggerName(){
        return loggerName;
    }
    
    public void setLoggerName(String loggerName){
        this.loggerName = loggerName;
    }
    
    private boolean isEmptyLoggerName(){
        return "".equals(loggerName);
    }
    
    private boolean isEmptyLoggerPath(){
        return "".equals(loggerPath);
    }
}
