/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Blob;
import java.sql.Timestamp;
import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class UnidadTransporteDTO {
    
    private String clave;
    private String noEconomico;
    private String vin;
    private String placas;
    private String modelo;
    private String color;
    private String modeloMotor;
    private String noSerieMotor;
    private String cpl;
    private Blob imagen;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private boolean status;
    private TipoUnidadDTO tipoUnidad;
    private MarcaUnidadDTO marcaUnidad;
    private MarcaMotorDTO marcaMotor;
    private UsuarioDTO usuario;

    public UnidadTransporteDTO(String clave, String noEconomico, String vin, String placas, String modelo, String color, String modeloMotor, String noSerieMotor, String cpl, Blob imagen, Timestamp fechaInicio, Timestamp fechaFin, boolean status, TipoUnidadDTO tipoUnidad, MarcaUnidadDTO marcaUnidad, MarcaMotorDTO marcaMotor, UsuarioDTO usuario) {
        this.clave = clave;
        this.noEconomico = noEconomico;
        this.vin = vin;
        this.placas = placas;
        this.modelo = modelo;
        this.color = color;
        this.modeloMotor = modeloMotor;
        this.noSerieMotor = noSerieMotor;
        this.cpl = cpl;
        this.imagen = imagen;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.status = status;
        this.tipoUnidad = tipoUnidad;
        this.marcaUnidad = marcaUnidad;
        this.marcaMotor = marcaMotor;
        this.usuario = usuario;
    }
    
    public UnidadTransporteDTO(){}

    public String getClave() {
        return clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNoEconomico() {
        return noEconomico;
    }

    public void setNoEconomico(String noEconomico) {
        this.noEconomico = noEconomico;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModeloMotor() {
        return modeloMotor;
    }

    public void setModeloMotor(String modeloMotor) {
        this.modeloMotor = modeloMotor;
    }

    public String getNoSerieMotor() {
        return noSerieMotor;
    }

    public void setNoSerieMotor(String noSerieMotor) {
        this.noSerieMotor = noSerieMotor;
    }

    public String getCpl() {
        return cpl;
    }

    public void setCpl(String cpl) {
        this.cpl = cpl;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
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

    public MarcaUnidadDTO getMarcaUnidad() {
        return marcaUnidad;
    }

    public void setMarcaUnidad(MarcaUnidadDTO marcaUnidad) {
        this.marcaUnidad = marcaUnidad;
    }

    public MarcaMotorDTO getMarcaMotor() {
        return marcaMotor;
    }

    public void setMarcaMotor(MarcaMotorDTO marcaMotor) {
        this.marcaMotor = marcaMotor;
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
            return "{UnidadTransporteDTO}[(clave:" + nullCheck(this.clave) + "),(nEconomico:" + 
                    nullCheck(this.noEconomico) + "),(vin:" + nullCheck(this.vin) + 
                    "),(placas:" + nullCheck(this.placas) + "),(modelo:" + nullCheck(this.modelo) + 
                    "),(color:" + nullCheck(this.color) + "),(modelo_motor:" + nullCheck(this.modeloMotor) + 
                    "),(noSerieMotor:" + nullCheck(this.noSerieMotor) + "),(cpl:" + nullCheck(this.cpl) + 
                    "),(imagen:" + nullCheck(this.imagen) + "),(fechaInicio:" + nullCheck(this.fechaInicio) + 
                    "),(fechaFin:" + nullCheck(this.fechaFin) +  
                    "),(status:" + this.status + "),(TipoUnidad:" + this.tipoUnidad + 
                    "),(marcaUnidad:" + this.marcaUnidad + "),(marcaMotor:" + this.marcaMotor + 
                    "),(usuario:" + this.usuario + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
