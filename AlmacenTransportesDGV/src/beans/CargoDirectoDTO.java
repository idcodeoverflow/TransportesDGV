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
public class CargoDirectoDTO {
 
    private int numeroCargoDirecto;
    private Timestamp fechaRegistro;
    private double precioUnitario;
    private double cantidad;
    private double subtotal;
    private double iva;
    private double total;
    private boolean status;
    private RefaccionDTO refaccion;
    private FacturaDTO factura;
    private UsuarioDTO usuario;
    private OrdenReparacionDTO ordenReparacion;
    
    public CargoDirectoDTO(){}

    public CargoDirectoDTO(int numeroCargoDirecto, Timestamp fechaRegistro, double precioUnitario, double cantidad, double subtotal, double iva, double total, boolean status, RefaccionDTO refaccion, FacturaDTO factura, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion) {
        this.numeroCargoDirecto = numeroCargoDirecto;
        this.fechaRegistro = fechaRegistro;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.status = status;
        this.refaccion = refaccion;
        this.factura = factura;
        this.usuario = usuario;
        this.ordenReparacion = ordenReparacion;
    }

    public int getNumeroCargoDirecto() {
        return numeroCargoDirecto;
    }

    public void setNumeroCargoDirecto(int numeroCargoDirecto) {
        this.numeroCargoDirecto = numeroCargoDirecto;
    }
    
    public Timestamp getFechaRegistro(){
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public OrdenReparacionDTO getOrdenReparacion() {
        return ordenReparacion;
    }

    public void setOrdenReparacion(OrdenReparacionDTO ordenReparacion) {
        this.ordenReparacion = ordenReparacion;
    }
    
    @Override
    public String toString(){
        try{
            return "{CargoDirectoDTO}[(idCargoDirecto:" + this.numeroCargoDirecto + "),"
                    + "(fechaRegistro:" + nullCheck(this.fechaRegistro) + "),(factura:" + nullCheck(this.factura) + "),"
                    + "(cantidad:" + this.cantidad + "),(iva:" + this.iva + "),(ordenReparacion:" + nullCheck(this.ordenReparacion) + "),"
                    + "(precioUnitario:" + this.precioUnitario + "),(refaccion:" + nullCheck(this.refaccion) + "),(status:" + this.status + "),"
                    + "(subtotal:" + this.subtotal + "),(total:" + this.total + "),(usuario:" + nullCheck(this.usuario) + ")]";
       } catch (NullPointerException ex) {
           return null;
       }
    }
    
}
