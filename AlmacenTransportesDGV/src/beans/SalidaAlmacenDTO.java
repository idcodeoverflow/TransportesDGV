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
public class SalidaAlmacenDTO {
    
    private int numeroSalida;
    private double costo;
    private boolean status;
    private double cantidad;
    private Timestamp fechaRegistro;
    private RefaccionDTO refaccion;
    private UsuarioDTO usuario;
    private OrdenReparacionDTO ordenReparacion;
    private int tipo;

    public SalidaAlmacenDTO() {
    }

    public SalidaAlmacenDTO(int numeroSalida, double costo, boolean status, double cantidad, Timestamp fechaRegistro, RefaccionDTO refaccion, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion, int tipo) {
        this.numeroSalida = numeroSalida;
        this.costo = costo;
        this.status = status;
        this.cantidad = cantidad;
        this.fechaRegistro = fechaRegistro;
        this.refaccion = refaccion;
        this.usuario = usuario;
        this.ordenReparacion = ordenReparacion;
        this.tipo = tipo;
    }

    public int getNumeroSalida() {
        return numeroSalida;
    }

    public void setNumeroSalida(int numeroSalida) {
        this.numeroSalida = numeroSalida;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    public Timestamp getFechaRegistro(){
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public RefaccionDTO getRefaccion() {
        return refaccion;
    }

    public void setRefaccion(RefaccionDTO refaccion) {
        this.refaccion = refaccion;
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
    
    public int getTipo(){
        return tipo;
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    
    @Override
    public String toString(){
        try {
            return "{SalidaAlmacenDTO}[(numeroSalida:" + this.numeroSalida + "),(fechaRegistro:" + 
                    nullCheck(this.fechaRegistro) + "),(refaccion:" + nullCheck(this.refaccion) + 
                    "),(ordenReparacion:" + nullCheck(this.ordenReparacion) + "),(usuario:" + 
                    nullCheck(this.usuario) + "),(costo:" + this.costo + "),(cantidad:" + 
                    this.cantidad + "),(tipo:" + this.tipo + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
