/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import beans.CargoTallerDTO;
import data.CargoTallerDAO;
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
public class CargoTallerReport extends ExcelReport{
    
    private Timestamp fechaInicio;
    private Timestamp fechaFin;

    public CargoTallerReport(javax.swing.JFrame form){
        super(form);
    }
    
    public CargoTallerReport(){
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
        int max11 = 11;
        int max12 = 11;
        int max13 = 11;
        int max14 = 11;
        
        DateFormat formatoFecha = DateFormat.getDateInstance(DateFormat.MEDIUM);
        DecimalFormat formatD = new DecimalFormat("0.00");
        List<CargoTallerDTO> cargos = null;
        CargoTallerDAO accesoCargos = new CargoTallerDAO();
        LazyQueryBO lazyQ = new LazyQueryBO();
        
        fila = sheet.createRow(0);
        fila.setHeightInPoints(70);
        Cell cel = fila.createCell(0);
        cel.setCellValue("Cargos Directos a Taller");
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$K$1"));
        cel.setCellStyle(ExcelStyles.titleStyle(book, "Reporte"));

        ExcelStyles.titleStyle(book, "Reporte");
        ExcelImage ei = new ExcelImage(0,11);
        ei.insertImage("/icons/Logo Efectivo Negro Chico.png", "Reporte", book, fStream);
        fila = sheet.createRow(nFila++);
        
        Cell celda = fila.createCell(0);
        celda.setCellValue("Id Cargo");
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
        celda = fila.createCell(4);
        celda.setCellValue("Proveedor");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(5);
        celda.setCellValue("Factura");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(6);
        celda.setCellValue("Clave Refacción");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(7);
        celda.setCellValue("Refacción");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(8);
        celda.setCellValue("Cantidad");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(9);
        celda.setCellValue("Precio Unitario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(10);
        celda.setCellValue("Subtotal");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(11);
        celda.setCellValue("Iva");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(12);
        celda.setCellValue("Total");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(13);
        celda.setCellValue("Usuario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        
        try {
            cargos = accesoCargos.obtenerCargosTaller(true);
            lazyQ.startLazyQuery();
            for (CargoTallerDTO cargo : cargos) {
                fila = sheet.createRow(nFila++);

                mensajeError = cargo.toString();

                Cell celdaT = fila.createCell(0);
                celdaT.setCellValue(cargo.getIdCargoTaller());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                celdaT = fila.createCell(1);
                celdaT.setCellValue(formatoFecha.format(new Date(cargo.getFechaRegistro().getTime())));
                celdaT.setCellStyle(ExcelStyles.dateStyle(book));
                if(cargo.getFechaRegistro().toString().length() > max2){
                    max2 = cargo.getFechaRegistro().toString().length();
                }
                
                celdaT = fila.createCell(2);
                celdaT.setCellValue(cargo.getOrdenReparacion().getNumeroOrden());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(3);
                celdaT.setCellValue(cargo.getUnidad().getClave());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(4);
                celdaT.setCellValue(cargo.getFactura().getProveedor().getNombre());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(cargo.getFactura().getProveedor().getNombre().length() > max4){
                    max4 = cargo.getFactura().getProveedor().getNombre().length();
                }
                celdaT = fila.createCell(5);
                celdaT.setCellValue(cargo.getFactura().getFolio());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(cargo.getFactura().getFolio().length() > max6){
                    max6 = cargo.getFactura().getFolio().length();
                }
                celdaT = fila.createCell(6);
                celdaT.setCellValue(cargo.getRefaccion().getClaveRefaccion());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if(cargo.getRefaccion().getNombre().length() > max7){
                    max7 = cargo.getRefaccion().getNombre().length();
                }
                
                celdaT = fila.createCell(7);
                celdaT.setCellValue(cargo.getRefaccion().getNombre());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                
                celdaT = fila.createCell(8);
                celdaT.setCellValue(cargo.getCantidad());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                celdaT = fila.createCell(9);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(cargo.getPrecioUnitario())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                celdaT = fila.createCell(10);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(cargo.getSubtotal())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                celdaT = fila.createCell(11);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(cargo.getIva())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                celdaT = fila.createCell(12);
                celdaT.setCellValue(Double.parseDouble(formatD.
                        format(cargo.getTotal())));
                celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                
                celdaT = fila.createCell(13);
                celdaT.setCellValue(cargo.getUsuario().getNombre() + " " + cargo.getUsuario().getApellidos());
                celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                if((cargo.getUsuario().getNombre() + " " + cargo.getUsuario().getApellidos()).length() > max14){
                    max14 = (cargo.getUsuario().getNombre() + " " + cargo.getUsuario().getApellidos()).length();
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
            sheet.setColumnWidth(10, 256 * max11);
            sheet.setColumnWidth(11, 256 * max12);
            sheet.setColumnWidth(12, 256 * max13);
            sheet.setColumnWidth(13, 256 * max14);

            
            book.write(fStream);
            fStream.close();
            Desktop.getDesktop().open(file);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2145\n" + ex.getMessage(),
                    "Error al obtener inventarios de la BD!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2145, UserHome.getUsuario(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Código error: 2146\n" + ex.getMessage(),
                    "Error al obtener inventarios!!!", JOptionPane.ERROR_MESSAGE);
            logger.ErrorLogger.scribirLog(mensajeError, 2146, UserHome.getUsuario(), ex);
        }
        
    }
    
}
