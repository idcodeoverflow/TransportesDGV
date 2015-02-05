/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bussines;

import java.sql.Timestamp;

/**
 *
 * @author David
 */
public class InventaryReport extends ExcelReport {
    
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private boolean soloBajoStock;
    
    public InventaryReport(javax.swing.JFrame form){
        super(form);
        soloBajoStock = false;
    }

    private InventaryReport(){
        super(null);
    }
    
    @Override
    public void generarReporte() {
        
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
