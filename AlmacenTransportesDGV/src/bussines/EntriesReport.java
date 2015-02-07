/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import beans.EntradaAlmacenDTO;
import data.EntradaAlmacenDAO;
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
public class EntriesReport extends ExcelReport{
    
    private Timestamp fechaInicio;
    private Timestamp fechaFin;

    public EntriesReport(javax.swing.JFrame form){
        super(form);
    }
    
    public EntriesReport(){
        super(null);
    }
    
    @Override
    public void generarReporte() {
        String mensajeError = "";
        Row fila;
        int nFila = 1;
        int max1 = 11;
        int max2 = 0;
        int max3 = 0;
        int max4 = 11;
        int max5 = 16;
        int max6 = 0;
        int max7 = 11;
        int max8 = 11;
        int max9 = 11;
        int max10 = 11;
        int max11 = 11;
        
        DateFormat formatoFecha = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DecimalFormat formatD = new DecimalFormat("0.00");
        List<EntradaAlmacenDTO> entradas = null;
        EntradaAlmacenDAO accesoEntradas = new EntradaAlmacenDAO();
        LazyQueryBO lazyQ = new LazyQueryBO();
        double existencia = 0.00;
        
        fila = sheet.createRow(0);
        fila.setHeightInPoints(70);
        Cell cel = fila.createCell(0);
        cel.setCellValue("Entradas de Almacén");
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));
        cel.setCellStyle(ExcelStyles.titleStyle(book, "Reporte"));

        ExcelStyles.titleStyle(book, "Reporte");
        ExcelImage ei = new ExcelImage(0,8);
        ei.insertImage("/icons/Logo Efectivo Negro Chico.png", "Reporte", book, fStream);
        fila = sheet.createRow(nFila++);
        
        Cell celda = fila.createCell(0);
        celda.setCellValue("N° Entrada");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(1);
        celda.setCellValue("Fecha");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(2);
        celda.setCellValue("Proveedor");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(3);
        celda.setCellValue("Factura");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(4);
        celda.setCellValue("Clave Refaccion");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(5);
        celda.setCellValue("Refaccion");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(6);
        celda.setCellValue("Cantidad");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(7);
        celda.setCellValue("Precio Unitario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(8);
        celda.setCellValue("Subtotal");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(9);
        celda.setCellValue("Iva");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(10);
        celda.setCellValue("Total");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        
        try {
            entradas = accesoEntradas.obtenerEntradasAlmacen(true);
            lazyQ.startLazyQuery();
            for (EntradaAlmacenDTO entrada : entradas) {
                fila = sheet.createRow(nFila++);

                mensajeError = entrada.toString() + "_existencia_" + existencia;

                Cell celdaT = fila.createCell(0);
                celdaT.setCellValue(entrada.getNumeroEntrada());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                celdaT = fila.createCell(1);
                celdaT.setCellValue(formatoFecha.format(new Date(entrada.getFechaRegistro().getTime())));
                celdaT.setCellStyle(ExcelStyles.dateStyle(book));
                if(entrada.getFechaRegistro().toString().length() > max2){
                    max2 = entrada.getFechaRegistro().toString().length();
                }
                celdaT = fila.createCell(2);
                celdaT.setCellValue(entrada.getFactura().getProveedor().getNombre());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(entrada.getFactura().getProveedor().getNombre().length() > max3){
                    max3 = entrada.getFactura().getProveedor().getNombre().length();
                }
                celdaT = fila.createCell(3);
                celdaT.setCellValue(entrada.getFactura().getFolio());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(entrada.getFactura().getFolio().length() > max4){
                    max4 = entrada.getFactura().getFolio().length();
                }
                celdaT = fila.createCell(4);
                celdaT.setCellValue(entrada.getRefaccion().getClaveRefaccion());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                celdaT = fila.createCell(5);
                celdaT.setCellValue(entrada.getRefaccion().getNombre());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(entrada.getRefaccion().getNombre().length() > max6){
                    max6 = entrada.getRefaccion().getNombre().length();
                }
                celdaT = fila.createCell(6);
                celdaT.setCellValue(entrada.getCantidad());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                celdaT = fila.createCell(7);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(entrada.getPrecioUnitario())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                celdaT = fila.createCell(8);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(entrada.getSubtotal())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                celdaT = fila.createCell(9);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(entrada.getIva())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                celdaT = fila.createCell(10);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(entrada.getMonto())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
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
            sheet.setColumnWidth(10, 256 * max11);

            book.write(fStream);
            fStream.close();
            Desktop.getDesktop().open(file);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2117\n" + ex.getMessage(),
                    "Error al obtener inventarios de la BD!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2117, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2118\n" + ex.getMessage(),
                    "Error al obtener inventarios!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2118, UserHome.getUsuario(), ex);
        }
        
    }
    
}
