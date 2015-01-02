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
public class UnidadTransporteDTO {
    
    private String clave;
    private String numeroUnidad;
    private String placas;
    private String modelo;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private boolean status;
    private TipoUnidadDTO tipoUnidad;
    private MarcaUnidadDTO marca;
    private UsuarioDTO usuario;

    public UnidadTransporteDTO() {
    }

    public UnidadTransporteDTO(String clave, String numeroUnidad, String placas, String modelo, Timestamp fechaInicio, Timestamp fechaFin, boolean status, TipoUnidadDTO tipoUnidad, MarcaUnidadDTO marca, UsuarioDTO usuario) {
        this.clave = clave;
        this.numeroUnidad = numeroUnidad;
        this.placas = placas;
        this.modelo = modelo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.status = status;
        this.tipoUnidad = tipoUnidad;
        this.marca = marca;
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getNumeroUnidad(){
        return this.numeroUnidad;
    }
    
    public void setNumeroUnidad(String numeroUnidad){
        this.numeroUnidad = numeroUnidad;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TipoUnidadDTO getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(TipoUnidadDTO tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public MarcaUnidadDTO getMarca() {
        return marca;
    }

    public void setMarca(MarcaUnidadDTO marca) {
        this.marca = marca;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public String toString(){
        try {
            return "{UnidadTransporteDTO}[(clave:" + nullCheck(this.clave) + "),(numeroUnidad:" + 
                    nullCheck(this.numeroUnidad) + "),(fechaInicio:" + nullCheck(this.fechaInicio) + 
                    "),(fechaFin:" + nullCheck(this.fechaFin) + "),(placas:" + nullCheck(this.placas) + 
                    "),(modelo:" + nullCheck(this.modelo) + "),(usuario:" + nullCheck(this.usuario) + 
                    "),(tipoUnidad:" + nullCheck(this.tipoUnidad) + "),(marca:" + nullCheck(this.marca) + 
                    "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
