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
public class TransporteReparacionDTO {
    
    private UnidadTransporteDTO transporte;
    private OrdenReparacionDTO ordenReparacion;
    private UsuarioDTO usuario;
    private UsuarioDTO usuarioBaja;
    private String descripcion;
    private Timestamp fechaAlta;
    private Timestamp fechaBaja;
    private int kilometraje;
    private boolean status;

    public TransporteReparacionDTO() {
    }

    public TransporteReparacionDTO(UnidadTransporteDTO transporte, OrdenReparacionDTO ordenReparacion, 
            UsuarioDTO usuario, UsuarioDTO usuarioBaja, String descripcion, Timestamp fechaAlta, Timestamp fechaBaja, int kilometraje, boolean status) {
        this.transporte = transporte;
        this.ordenReparacion = ordenReparacion;
        this.usuario = usuario;
        this.usuarioBaja = usuarioBaja;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.kilometraje = kilometraje;
        this.status = status;
    }

    public UnidadTransporteDTO getTransporte() {
        return transporte;
    }

    public void setTransporte(UnidadTransporteDTO transporte) {
        this.transporte = transporte;
    }

    public OrdenReparacionDTO getOrdenReparacion() {
        return ordenReparacion;
    }

    public void setOrdenReparacion(OrdenReparacionDTO ordenReparacion) {
        this.ordenReparacion = ordenReparacion;
    }

    public UsuarioDTO getUsuario(){
        return usuario;
    }
    
    public void setUsuario(UsuarioDTO usuario){
        this.usuario = usuario;
    }
    
    public UsuarioDTO getUsuarioBaja(){
        return usuarioBaja;
    }
    
    public void setUsuarioBaja(UsuarioDTO usuarioBaja){
        this.usuarioBaja = usuarioBaja;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    public Timestamp getFechaAlta(){
        return fechaAlta;
    }
    
    public void setFechaAlta(Timestamp fechaAlta){
        this.fechaAlta = fechaAlta;
    }
    
    public Timestamp getFechaBaja(){
        return fechaBaja;
    }
    
    public void setFechaBaja(Timestamp fechaBaja){
        this.fechaBaja = fechaBaja;
    }
    
    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public String toString(){
        try {
            return "{TransporteReparacionDTO}[(ordenReparacion:" + nullCheck(this.ordenReparacion) + 
                    "),(transporte:" + nullCheck(this.transporte) + "),(descripcion:" + nullCheck(this.descripcion) + "),(fechaAlta:" +
                    nullCheck(this.fechaAlta) + "),(fechaBaja:" + nullCheck(this.fechaBaja) + 
                    "),(kilometraje:" + this.kilometraje + "),(usuario:" + nullCheck(this.usuario) + 
                    "),(usuarioBaja:" + nullCheck(this.usuarioBaja) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
