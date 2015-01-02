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
public class FacturaDTO {
    
    private ProveedorDTO proveedor;
    private String folio;
    private Timestamp fechaRegistro;
    private Timestamp fechaPago;
    private double subtotal;
    private double iva;
    private double total;
    private boolean pagada;
    private boolean status;
    private UsuarioDTO usuario;

    public FacturaDTO() {
    }

    public FacturaDTO(ProveedorDTO proveedor, String folio, Timestamp fechaRegistro, Timestamp fechaPago, double subtotal, double iva, double total, boolean pagada, boolean status, UsuarioDTO usuario) {
        this.proveedor = proveedor;
        this.folio = folio;
        this.fechaRegistro = fechaRegistro;
        this.fechaPago = fechaPago;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.pagada = pagada;
        this.status = status;
        this.usuario = usuario;
    }

    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDTO idProveedor) {
        this.proveedor = idProveedor;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Timestamp getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Timestamp fechaPago) {
        this.fechaPago = fechaPago;
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

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString(){
        try{
            return "{FacturaDTO}[(folio:" + nullCheck(this.folio) + "),(proveedor:" + nullCheck(this.proveedor) + 
                    "),(fechaRegistro:" + nullCheck(this.fechaRegistro) + "),(fechaPago:" + nullCheck(this.fechaPago) + 
                    "),(usuario:" + nullCheck(this.usuario) + "),(subtotal:" + this.subtotal + "),(iva:" + this.iva + 
                    "),(total:" + this.total + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
