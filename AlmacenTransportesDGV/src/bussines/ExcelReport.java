/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author David
 */
public abstract class ExcelReport {
    
    protected String path;
    protected String fileName;
    protected File file;
    protected Workbook book;
    protected Sheet sheet;
    protected FileOutputStream fStream;
    
    public ExcelReport(javax.swing.JFrame form){
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.showSaveDialog(form);
            file = jfc.getSelectedFile();
            if (file != null) {
                file = new File(file.getAbsolutePath() + "\\" + file.getName() + ".xls");
                
                if(file.exists()){
                    file.delete(); 
                }
                
                file.createNewFile();
                book = new HSSFWorkbook();
                fStream = new FileOutputStream(file);
                sheet = book.createSheet("Reporte");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Código error 2114: \n" + ex.getMessage()
                    + "\nError al escrbir en archivo.",
                    "Error!!!", JOptionPane.ERROR_MESSAGE); 
            logger.ErrorLogger.scribirLog("InventaryReportBO()", 2114, UserHome.getUsuario(), ex);
        }
    }
    
    public abstract void generarReporte();
    
}
