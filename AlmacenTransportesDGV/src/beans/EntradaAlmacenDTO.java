/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Timestamp;
import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class EntradaAlmacenDTO {
    
    private int numeroEntrada;
    private Timestamp fechaRegistro;
    private double cantidad;
    private double precioUnitario;
    private double subtotal;
    private double iva;
    private double monto;
    private boolean status;
    private RefaccionDTO refaccion;
    private FacturaDTO factura;
    private UsuarioDTO usuario;
    
    public EntradaAlmacenDTO(){}

    public EntradaAlmacenDTO(int numeroEntrada, Timestamp fechaRegistro, double cantidad, double precioUnitario, double subtotal, double iva, double monto, boolean status, RefaccionDTO refaccion, FacturaDTO factura, UsuarioDTO usuario) {
        this.numeroEntrada = numeroEntrada;
        this.fechaRegistro = fechaRegistro;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.iva = iva;
        this.monto = monto;
        this.status = status;
        this.refaccion = refaccion;
        this.factura = factura;
        this.usuario = usuario;
    }

    public int getNumeroEntrada() {
        return numeroEntrada;
    }

    public void setNumeroEntrada(int numeroEntrada) {
        this.numeroEntrada = numeroEntrada;
    }

    public Timestamp getFechaRegistro(){
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }
    
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RefaccionDTO getRefaccion() {
        return refaccion;
    }

    public void setRefaccion(RefaccionDTO refaccion) {
        this.refaccion = refaccion;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }
    
    public UsuarioDTO getUsuario(){
        return usuario;
    }
    
    public void setUsuario(UsuarioDTO usuario){
        this.usuario = usuario;
    }
    
    @Override
    public String toString(){
        try {
            return "{EntradaAlmacenDTO}[(numeroEntrada:" + this.numeroEntrada + "),(fechaRegistro:" + 
                    nullCheck(this.fechaRegistro) + "),(factura:" + nullCheck(this.factura) + 
                    "),(refaccion:" + nullCheck(this.refaccion) + "),(usuario:" + nullCheck(this.usuario) + 
                    "),(cantidad:" + this.cantidad + "),(iva:" + this.iva + "),(monto:" + this.monto + 
                    "),(subtotal:" + this.subtotal + ")," + "(precioUnitario:" + this.precioUnitario + 
                    ")," + "(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
