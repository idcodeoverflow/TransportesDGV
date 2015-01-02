/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mailer;

import almacendgv.UserHome;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import logger.ErrorLogger;

/**
 *
 * @author David Israel
 */
public class MailData {
    
    private final String mailerPath = System.getProperty("user.home") + "\\remex_mail\\"; //obtener acceso a la carpeta personal del usuario
    private final String mailerName = "lista_mail.txt";
    private final String developerAddress = "soporte.txt";
    private final String reportName = "reporte.pdf";
    public static final int SOPORTE = 1;
    public static final int NOTIFICACION = 2;
    
    
    {
        //Regenerar rutas de los archivos de configuracion de destinatarios, en caso de que se hayan modificado o eliminado
        try {
            generarRuta();
            generarArchivos();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1362\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:initializationSegment", 1362, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1362\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("ErrorLogger:initializationSegment", 1362, UserHome.getUsuario(), ex);
        }
    }
    
    public MailData(){
        
    }
    
    public String getReportName(){
        return reportName;
    }
    
    public String getRelativeMailerPath(){
        return mailerPath;
    }
    
    public String getPathMailer(){
        return mailerPath + mailerName;
    }
    
    public String getPathSoporte(){
        return mailerPath + developerAddress;
    }
    
    public boolean existePath() {
        return new File(mailerPath).isDirectory();
    }
    
    public boolean existeArchivo(){
        return new File(mailerPath + mailerName).exists();
    }
    
    public boolean existeArchivoSoporte(){
        return new File(mailerPath + developerAddress).exists();
    }
    
    public boolean generarRuta() throws IOException{
        if(!existePath()) {
            return new File(mailerPath).mkdir();
        }
        return false;
    }
    
    public boolean generarArchivos() throws IOException{
        if(!existeArchivo()){
            return new File(mailerPath + mailerName).createNewFile();
        }
        if(!existeArchivoSoporte()){
            return new File(mailerPath + developerAddress).createNewFile();
        }
        return true;
    }
    
    public List<String> getMailAddresses(int tipo){
        List<String> mails = new ArrayList<String>();
        try{
            File file = new File(mailerPath + ((tipo == NOTIFICACION) ? mailerName : developerAddress));
            Scanner sc = new Scanner(new FileReader(file));
            while(sc.hasNext()){
                mails.add(sc.nextLine());
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1360\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("MailData:getMailAddresses()", 1360, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 1361\n" + ex.getMessage(),
                    "Error en acceso a datos!!!", JOptionPane.ERROR_MESSAGE);
            ErrorLogger.scribirLog("MailData:getMailAddresses()", 1361, UserHome.getUsuario(), ex);
        }
        return mails;
    }
        
}
