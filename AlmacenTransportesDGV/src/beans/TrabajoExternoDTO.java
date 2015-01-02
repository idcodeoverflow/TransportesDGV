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
public class TrabajoExternoDTO {
    
    private int numeroTrabajoExterno;
    private Timestamp fechaRegistro;
    private String descripcion;
    private double cantidad;
    private double precioUnitario;
    private double subtotal;
    private double iva;
    private double monto;
    private boolean status;
    private FacturaDTO factura;
    private OrdenReparacionDTO ordenReparacion;
    private UnidadTransporteDTO unidadTransporte;
    private UsuarioDTO usuario;
    
    public TrabajoExternoDTO() {
    }

    public TrabajoExternoDTO(int numeroTrabajoExterno, Timestamp fechaRegistro, String descripcion, double cantidad, double precioUnitario, double subtotal, double iva, double monto, boolean status, FacturaDTO factura, OrdenReparacionDTO ordenReparacion, UnidadTransporteDTO unidadTransporte, UsuarioDTO usuario) {
        this.numeroTrabajoExterno = numeroTrabajoExterno;
        this.fechaRegistro = fechaRegistro;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.iva = iva;
        this.monto = monto;
        this.status = status;
        this.factura = factura;
        this.ordenReparacion = ordenReparacion;
        this.unidadTransporte = unidadTransporte;
        this.usuario = usuario;
    }

    public int getNumeroTrabajoExterno() {
        return numeroTrabajoExterno;
    }

    public void setNumeroTrabajoExterno(int numeroTrabajoExterno) {
        this.numeroTrabajoExterno = numeroTrabajoExterno;
    }
    
    public Timestamp getFechaRegistro(){
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }
    
    public OrdenReparacionDTO getOrdenReparacion(){
        return ordenReparacion;
    }
    
    public void setOrdenReparacion(OrdenReparacionDTO ordenReparacion){
        this.ordenReparacion = ordenReparacion;
    }
    
    public UnidadTransporteDTO getUnidadTransporte(){
        return unidadTransporte;
    }
    
    public void setUnidadTransporte(UnidadTransporteDTO unidadTransporte){
        this.unidadTransporte = unidadTransporte;
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
            return "{TrabajoExternoDTO}[(numeroTrabajoExterno:" + this.numeroTrabajoExterno + 
                    "),(fechaRegistro:" + nullCheck(this.fechaRegistro) + "),(cantidad:" + 
                    this.cantidad + "),(precioUnitario:" + this.precioUnitario + "),(iva:" + 
                    this.iva + "),(ordenReparacion:" + nullCheck(this.ordenReparacion) + "),(unidadTransporte:" + 
                    nullCheck(this.unidadTransporte) + "),(usuario:" + nullCheck(this.usuario) + 
                    "),(descripcion:" + nullCheck(this.descripcion) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
