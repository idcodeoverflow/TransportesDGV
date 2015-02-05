/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import beans.RefaccionDTO;
import data.RefaccionDAO;
import excelutils.ExcelStyles;
import java.awt.Desktop;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JOptionPane;
import logger.ErrorLogger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author David
 */
public class InventaryReport extends ExcelReport {

    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private boolean soloBajoStock;

    public InventaryReport(javax.swing.JFrame form) {
        super(form);
        soloBajoStock = false;
    }

    private InventaryReport() {
        super(null);
    }

    @Override
    public void generarReporte() {
        String mensajeError = "";
        Row fila;
        int nFila = 0;
        int max1 = 20;
        int max2 = 0;
        int max3 = 11;
        int max4 = 11;
        int max5 = 11;
        int max6 = 11;
        int max7 = 16;
        
        DecimalFormat formatD = new DecimalFormat("0.00");
        List<RefaccionDTO> refacciones = null;
        RefaccionDAO accesoRefaccion = new RefaccionDAO();
        LazyQueryBO lazyQ = new LazyQueryBO();
        double existencia = 0.00;
        fila = sheet.createRow(nFila++);
        Cell celda = fila.createCell(0);
        celda.setCellValue("Clave Refacción");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(1);
        celda.setCellValue("Nombre");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(2);
        celda.setCellValue("Máximo");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(3);
        celda.setCellValue("Mínimo");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(4);
        celda.setCellValue("Punto Re-orden");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(5);
        celda.setCellValue("Existencia");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        celda = fila.createCell(6);
        celda.setCellValue("PrecioUnitario");
        celda.setCellStyle(ExcelStyles.headerStyle(book));
        if (soloBajoStock) {
            try {
                refacciones = accesoRefaccion.obtenerRefacciones();
                lazyQ.startLazyQuery();
                for (RefaccionDTO refaccion : refacciones) {
                    existencia = accesoRefaccion.obtenerExistenciaRefaccion(refaccion.getClaveRefaccion(), false, false);
                    mensajeError = refaccion.toString() + "_existencia_" + existencia;
                    if (existencia <= refaccion.getPuntoReorden()) {
                        fila = sheet.createRow(nFila++);
                        Cell celdaT = fila.createCell(0);
                        celdaT.setCellValue(refaccion.getClaveRefaccion());
                        celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                        celdaT = fila.createCell(1);
                        celdaT.setCellValue(refaccion.getNombre());
                        celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                        if(refaccion.getNombre().length() > max2){
                            max2 = refaccion.getNombre().length();
                        }
                        celdaT = fila.createCell(2);
                        celdaT.setCellValue(refaccion.getMaximo());
                        celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                        celdaT = fila.createCell(3);
                        celdaT.setCellValue(refaccion.getMinimo());
                        celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                        celdaT = fila.createCell(4);
                        celdaT.setCellValue(refaccion.getPuntoReorden());
                        celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                        celdaT = fila.createCell(5);
                        celdaT.setCellValue(existencia);
                        celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                        celdaT = fila.createCell(6);
                        celdaT.setCellValue(Double.parseDouble(formatD.
                                format(accesoRefaccion.obtenerPrecioRefaccion(refaccion.
                                        getClaveRefaccion(), false, false))));
                        celdaT.setCellStyle(ExcelStyles.contabilityStyle(book));
                    }
                }
                sheet.setColumnWidth(0, 256 * max1);
                sheet.setColumnWidth(1, 256 * max2);
                sheet.setColumnWidth(2, 256 * max3);
                sheet.setColumnWidth(3, 256 * max4);
                sheet.setColumnWidth(4, 256 * max5);
                sheet.setColumnWidth(5, 256 * max6);
                sheet.setColumnWidth(6, 256 * max7);
                lazyQ.endLazyQuery();
                book.write(fStream);
                fStream.close();
                Desktop.getDesktop().open(file);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código error: 2115\n" + ex.getMessage(),
                        "Error al obtener inventarios de la BD!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(mensajeError, 2115, UserHome.getUsuario(), ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Código error: 2116\n" + ex.getMessage(),
                        "Error al obtener inventarios!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(mensajeError, 2116, UserHome.getUsuario(), ex);
            }
        } else {
            try {
                refacciones = accesoRefaccion.obtenerRefacciones();
                lazyQ.startLazyQuery();
                for (RefaccionDTO refaccion : refacciones) {
                    fila = sheet.createRow(nFila++);
                    existencia = accesoRefaccion.obtenerExistenciaRefaccion(refaccion.getClaveRefaccion(), false, false);
                    mensajeError = refaccion.toString() + "_existencia_" + existencia;
                    
                    Cell celdaT = fila.createCell(0);
                    celdaT.setCellValue(refaccion.getClaveRefaccion());
                    celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                    celdaT = fila.createCell(1);
                    celdaT.setCellValue(refaccion.getNombre());
                    celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                    if(refaccion.getNombre().length() > max2){
                        max2 = refaccion.getNombre().length();
                    }
                    celdaT = fila.createCell(2);
                    celdaT.setCellValue(refaccion.getMaximo());
                    celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                    celdaT = fila.createCell(3);
                    celdaT.setCellValue(refaccion.getMinimo());
                    celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                    celdaT = fila.createCell(4);
                    celdaT.setCellValue(refaccion.getPuntoReorden());
                    celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                    celdaT = fila.createCell(5);
                    celdaT.setCellValue(existencia);
                    celdaT.setCellStyle(ExcelStyles.createBorderedStyle(book));
                    celdaT = fila.createCell(6);
                    celdaT.setCellValue(Double.parseDouble(formatD.
                            format(accesoRefaccion.obtenerPrecioRefaccion(refaccion.
                                    getClaveRefaccion(), false, false))));
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
                book.write(fStream);
                fStream.close();
                Desktop.getDesktop().open(file);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Código error: 2117\n" + ex.getMessage(),
                        "Error al obtener inventarios de la BD!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(mensajeError, 2117, UserHome.getUsuario(), ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Código error: 2118\n" + ex.getMessage(),
                        "Error al obtener inventarios!!!", JOptionPane.ERROR_MESSAGE);
                ErrorLogger.scribirLog(mensajeError, 2118, UserHome.getUsuario(), ex);
            }

        }
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isSoloBajoStock() {
        return soloBajoStock;
    }

    public void setSoloBajoStock(boolean soloBajoStock) {
        this.soloBajoStock = soloBajoStock;
    }
    
}
