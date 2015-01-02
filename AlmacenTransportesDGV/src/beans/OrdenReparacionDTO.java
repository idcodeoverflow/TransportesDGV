/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Timestamp;
import java.util.List;
import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class OrdenReparacionDTO {
    
    private int numeroOrden;
    private Timestamp fechaEntrada;
    private Timestamp fechaSalida;
    private boolean status;
    private OperadorDTO operador;
    private UsuarioDTO usuario;
    private List<SalidaUnidadDTO> salidasAlmacen;
    private List<CargoDirectoDTO> cargosDirectos;
    private UnidadTransporteDTO tracto;
    private UnidadTransporteDTO plana;

    public OrdenReparacionDTO() {
    }

    public OrdenReparacionDTO(int numeroOrden, Timestamp fechaEntrada, Timestamp fechaSalida, boolean status, OperadorDTO operador, UsuarioDTO usuario, List<SalidaUnidadDTO> salidasAlmacen) {
        this.numeroOrden = numeroOrden;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.status = status;
        this.operador = operador;
        this.usuario = usuario;
        this.salidasAlmacen = salidasAlmacen;
    }

    public OrdenReparacionDTO(int numeroOrden, Timestamp fechaEntrada, Timestamp fechaSalida, boolean status, OperadorDTO operador, UsuarioDTO usuario, List<SalidaUnidadDTO> salidasAlmacen, List<CargoDirectoDTO> cargosDirectos, UnidadTransporteDTO tracto, UnidadTransporteDTO plana) {
        this.numeroOrden = numeroOrden;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.status = status;
        this.operador = operador;
        this.usuario = usuario;
        this.salidasAlmacen = salidasAlmacen;
        this.cargosDirectos = cargosDirectos;
        this.tracto = tracto;
        this.plana = plana;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Timestamp getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Timestamp fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Timestamp getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Timestamp fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public OperadorDTO getOperador() {
        return operador;
    }

    public void setOperador(OperadorDTO operador) {
        this.operador = operador;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public List<SalidaUnidadDTO> getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(List<SalidaUnidadDTO> salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public List<CargoDirectoDTO> getCargosDirectos() {
        return cargosDirectos;
    }

    public void setCargosDirectos(List<CargoDirectoDTO> cargosDirectos) {
        this.cargosDirectos = cargosDirectos;
    }

    public UnidadTransporteDTO getTracto() {
        return tracto;
    }

    public void setTracto(UnidadTransporteDTO tracto) {
        this.tracto = tracto;
    }

    public UnidadTransporteDTO getPlana() {
        return plana;
    }

    public void setPlana(UnidadTransporteDTO plana) {
        this.plana = plana;
    }
 
    @Override
    public String toString(){
        try {
            String cadena = "{OrdenReparacionDTO}[(numeroOrden:" + this.numeroOrden + 
                    "),(fechaEntrada:" + nullCheck(this.fechaEntrada) + "),(fechaSalida:" + 
                    nullCheck(this.fechaSalida) + "),(operador:" + nullCheck(this.operador) + 
                    "),(tracto:" + nullCheck(this.tracto) + "),(plana:" + nullCheck(this.plana) + 
                    "),(usuario:" + nullCheck(this.usuario) + "),(status:" + this.status + "),(cargosDirectos:";
            boolean control = false;
            if(this.cargosDirectos != null){
                for(CargoDirectoDTO cargoDirecto : this.cargosDirectos){
                    cadena += ((control) ? "," : "") + nullCheck(cargoDirecto);
                    control = true;
                }
            }
            cadena += "),(salidasAlmacen:";
            control = false;
            if(this.salidasAlmacen != null){
                for(SalidaAlmacenDTO salidaAlmacen : this.salidasAlmacen){
                    cadena += ((control) ? "," : "") + nullCheck(salidaAlmacen);
                    control = true;
                }
            }
            cadena += ")]";
            return cadena;
        } catch (NullPointerException ex) {
            return null;
        }
    }
       
}
