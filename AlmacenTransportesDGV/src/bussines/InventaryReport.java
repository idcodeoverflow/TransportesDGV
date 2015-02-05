/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import almacendgv.UserHome;
import beans.RefaccionDTO;
import data.RefaccionDAO;
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
        DecimalFormat formatD = new DecimalFormat("0.00");
        List<RefaccionDTO> refacciones = null;
        RefaccionDAO accesoRefaccion = new RefaccionDAO();
        LazyQueryBO lazyQ = new LazyQueryBO();
        double existencia = 0.00;
        fila = sheet.createRow(nFila++);
        Cell celda = fila.createCell(0);
        celda.setCellValue("Clave Refacción");
        celda = fila.createCell(1);
        celda.setCellValue("Nombre");
        celda = fila.createCell(2);
        celda.setCellValue("Máximo");
        celda = fila.createCell(3);
        celda.setCellValue("Mínimo");
        celda = fila.createCell(4);
        celda.setCellValue("Punto Re-orden");
        celda = fila.createCell(5);
        celda.setCellValue("Existencia");
        celda = fila.createCell(6);
        celda.setCellValue("PrecioUnitario");
        if (soloBajoStock) {
            try {
                refacciones = accesoRefaccion.obtenerRefacciones();
                lazyQ.startLazyQuery();
                for (RefaccionDTO refaccion : refacciones) {
                    fila = sheet.createRow(nFila++);
                    existencia = accesoRefaccion.obtenerExistenciaRefaccion(refaccion.getClaveRefaccion(), false, false);
                    mensajeError = refaccion.toString() + "_existencia_" + existencia;
                    if (existencia <= refaccion.getPuntoReorden()) {
                        Cell celdaT = fila.createCell(0);
                        celdaT.setCellValue(refaccion.getClaveRefaccion());
                        celdaT = fila.createCell(1);
                        celdaT.setCellValue(refaccion.getNombre());
                        celdaT = fila.createCell(2);
                        celdaT.setCellValue(refaccion.getMaximo());
                        celdaT = fila.createCell(3);
                        celdaT.setCellValue(refaccion.getMinimo());
                        celdaT = fila.createCell(4);
                        celdaT.setCellValue(refaccion.getPuntoReorden());
                        celdaT = fila.createCell(5);
                        celdaT.setCellValue(existencia);
                        celdaT = fila.createCell(6);
                        celdaT.setCellValue(formatD.format(accesoRefaccion.obtenerPrecioRefaccion(refaccion.getClaveRefaccion(), false, false)));
                    }
                }
                lazyQ.endLazyQuery();
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
                    celdaT = fila.createCell(1);
                    celdaT.setCellValue(refaccion.getNombre());
                    celdaT = fila.createCell(2);
                    celdaT.setCellValue(refaccion.getMaximo());
                    celdaT = fila.createCell(3);
                    celdaT.setCellValue(refaccion.getMinimo());
                    celdaT = fila.createCell(4);
                    celdaT.setCellValue(refaccion.getPuntoReorden());
                    celdaT = fila.createCell(5);
                    celdaT.setCellValue(existencia);
                    celdaT = fila.createCell(6);
                    celdaT.setCellValue(formatD.format(accesoRefaccion.obtenerPrecioRefaccion(refaccion.getClaveRefaccion(), false, false)));
                }
                lazyQ.endLazyQuery();
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
