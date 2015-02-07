/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import beans.EntradaAlmacenDTO;
import beans.SalidaUnidadDTO;
import data.EntradaAlmacenDAO;
import data.SalidaUnidadDAO;
import excelutils.ExcelImage;
import excelutils.ExcelStyles;
import java.awt.Desktop;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author David
 */
public class TransportExitReport extends ExcelReport{
    
    private Timestamp fechaInicio;
    private Timestamp fechaFin;

    public TransportExitReport(javax.swing.JFrame form){
        super(form);
    }
    
    public TransportExitReport(){
        super(null);
    }
    
    @Override
    public void generarReporte() {
        String mensajeError = "";
        Row fila;
        int nFila = 1;
        int max1 = 11;
        int max2 = 0;
        int max3 = 11;
        int max4 = 11;
        int max5 = 16;
        int max6 = 0;
        int max7 = 11;
        int max8 = 11;
        int max9 = 11;
        int max10 = 11;
        
        DecimalFormat formatD = new DecimalFormat("0.00");
        List<SalidaUnidadDTO> salidas = null;
        SalidaUnidadDAO accesoSalidas = new SalidaUnidadDAO();
        LazyQueryBO lazyQ = new LazyQueryBO();
        
        fila = sheet.createRow(0);
        fila.setHeightInPoints(70);
        Cell cel = fila.createCell(0);
        cel.setCellValue("Salidas de Almacén a Transporte");
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));
        cel.setCellStyle(ExcelStyles.titleStyle(book, "Reporte"));

        ExcelStyles.titleStyle(book, "Reporte");
        ExcelImage ei = new ExcelImage(0,8);
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
        celda.setCellValue("Clave");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(5);
        celda.setCellValue("Clave Refacción");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(6);
        celda.setCellValue("Refaccion");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(7);
        celda.setCellValue("Cantidad");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(8);
        celda.setCellValue("Precio Unitario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(9);
        celda.setCellValue("Total");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(10);
        celda.setCellValue("Usuario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        
        try {
            salidas = accesoSalidas.obtenerSalidasUnidadSinCanceladas(true, true, false);
            lazyQ.startLazyQuery();
            for (SalidaUnidadDTO salida : salidas) {
                fila = sheet.createRow(nFila++);


                Cell celdaT = fila.createCell(0);
                celdaT.setCellValue(salida.getIdSalidaUnidad());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(1);
                celdaT.setCellValue(salida.getFechaRegistro().toString());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(salida.getFechaRegistro().toString().length() > max2){
                    max2 = salida.getFechaRegistro().toString().length();
                }
                
                celdaT = fila.createCell(2);
                celdaT.setCellValue(salida.getOrdenReparacion().getNumeroOrden());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(3);
                celdaT.setCellValue(salida.getTransporte().getClave());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
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
                celdaT.setCellValue(salida.getRefaccion().getClaveRefaccion());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
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
            JOptionPane.showMessageDialog(null, "Código error: 2117\n" + ex.getMessage(),
                    "Error al obtener inventarios de la BD!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2121, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2118\n" + ex.getMessage(),
                    "Error al obtener inventarios!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2122, UserHome.getUsuario(), ex);
        }
        
    }
    
}
