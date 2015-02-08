/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import beans.SalidaEspecialDTO;
import beans.SalidaUnidadDTO;
import data.SalidaEspecialDAO;
import data.SalidaUnidadDAO;
import excelutils.ExcelImage;
import excelutils.ExcelStyles;
import java.awt.Desktop;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author David
 */
public class SpecialExitReport extends ExcelReport{
    
    private Timestamp fechaInicio;
    private Timestamp fechaFin;

    public SpecialExitReport(javax.swing.JFrame form){
        super(form);
    }
    
    public SpecialExitReport(){
        super(null);
    }
    
    @Override
    public void generarReporte() {
        String mensajeError = "";
        Row fila;
        int nFila = 1;
        int max1 = 11;
        int max2 = 11;
        int max3 = 11;
        int max4 = 11;
        int max5 = 16;
        int max6 = 11;
        int max7 = 11;
        int max8 = 11;
        int max9 = 11;
        int max10 = 11;
        
        DateFormat formatoFecha = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DecimalFormat formatD = new DecimalFormat("0.00");
        List<SalidaEspecialDTO> salidas = null;
        SalidaEspecialDAO accesoSalidas = new SalidaEspecialDAO();
        LazyQueryBO lazyQ = new LazyQueryBO();
        
        fila = sheet.createRow(0);
        fila.setHeightInPoints(70);
        Cell cel = fila.createCell(0);
        cel.setCellValue("Salidas de Almacén Especiales");
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$G$1"));
        cel.setCellStyle(ExcelStyles.titleStyle(book, "Reporte"));

        ExcelStyles.titleStyle(book, "Reporte");
        ExcelImage ei = new ExcelImage(0,7);
        ei.insertImage("/icons/Logo Efectivo Negro Chico.png", "Reporte", book, fStream);
        fila = sheet.createRow(nFila++);
        
        Cell celda = fila.createCell(0);
        celda.setCellValue("Id Salida");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(1);
        celda.setCellValue("Fecha");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(2);
        celda.setCellValue("N° Orden");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(3);
        celda.setCellValue("Beneficiario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(4);
        celda.setCellValue("Clave Refacción");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(5);
        celda.setCellValue("Refacción");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(6);
        celda.setCellValue("Cantidad");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(7);
        celda.setCellValue("Precio Unitario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(8);
        celda.setCellValue("Total");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(9);
        celda.setCellValue("Usuario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        
        try {
            salidas = accesoSalidas.obtenerSalidasEspecialesSinCanceladas(true, true, false);
            lazyQ.startLazyQuery();
            for (SalidaEspecialDTO salida : salidas) {
                fila = sheet.createRow(nFila++);


                Cell celdaT = fila.createCell(0);
                celdaT.setCellValue(salida.getIdSalidaEspecial());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(1);
                celdaT.setCellValue(formatoFecha.format(new Date(salida.getFechaRegistro().getTime())));
                celdaT.setCellStyle(ExcelStyles.dateStyle(book));
                if(salida.getFechaRegistro().toString().length() > max2){
                    max2 = salida.getFechaRegistro().toString().length();
                }
                
                celdaT = fila.createCell(2);
                celdaT.setCellValue(salida.getOrdenReparacion().getNumeroOrden());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(3);
                celdaT.setCellValue(salida.getNombreBeneficiario());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(salida.getNombreBeneficiario().length() > max4){
                    max4 = salida.getNombreBeneficiario().length();
                }
                
                celdaT = fila.createCell(4);
                celdaT.setCellValue(salida.getRefaccion().getClaveRefaccion());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(5);
                celdaT.setCellValue(salida.getRefaccion().getNombre());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(salida.getRefaccion().getNombre().length() > max6){
                    max6 = salida.getRefaccion().getNombre().length();
                }
                
                celdaT = fila.createCell(6);
                celdaT.setCellValue(salida.getCantidad());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(7);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format((double)(salida.getCosto() / salida.getCantidad()))));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                
                celdaT = fila.createCell(8);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(salida.getCosto())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                
                celdaT = fila.createCell(9);
                celdaT.setCellValue(salida.getUsuario().getNombre() + " " + salida.getUsuario().getApellidos());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if((salida.getUsuario().getNombre() + " " + salida.getUsuario().getApellidos()).length() > max10){
                    max10 = (salida.getUsuario().getNombre() + " " + salida.getUsuario().getApellidos()).length();
                }
            }
            lazyQ.endLazyQuery();
            sheet.setColumnWidth(0, 256 * max1);
            sheet.setColumnWidth(1, 256 * max2);
            sheet.setColumnWidth(2, 256 * max3);
            sheet.setColumnWidth(3, 256 * max4);
            sheet.setColumnWidth(4, 256 * max5);
            sheet.setColumnWidth(5, 256 * max6);
            sheet.setColumnWidth(6, 256 * max7);
            sheet.setColumnWidth(7, 256 * max8);
            sheet.setColumnWidth(8, 256 * max9);
            sheet.setColumnWidth(9, 256 * max10);

            book.write(fStream);
            fStream.close();
            Desktop.getDesktop().open(file);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2125\n" + ex.getMessage(),
                    "Error al obtener inventarios de la BD!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2125, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2126\n" + ex.getMessage(),
                    "Error al obtener inventarios!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2126, UserHome.getUsuario(), ex);
        }
        
    }
    
}
