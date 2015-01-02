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
public class SalidaUnidadDTO extends SalidaAlmacenDTO {
    
    private int idSalidaUnidad;
    private UnidadTransporteDTO unidadTransporte;
    
    public SalidaUnidadDTO() {
        super();
    }

    public SalidaUnidadDTO(int idSalidaUnidad, UnidadTransporteDTO unidadTransporte, int numeroSalida, double costo, boolean status, double cantidad, Timestamp fechaRegistro, RefaccionDTO refaccion, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion, int tipo) {
        super(numeroSalida, costo, status, cantidad, fechaRegistro, refaccion, usuario, ordenReparacion, tipo);
        this.idSalidaUnidad = idSalidaUnidad;
        this.unidadTransporte = unidadTransporte;
    }
    
    public SalidaUnidadDTO(int idSalidaUnidad, UnidadTransporteDTO unidadTransporte, SalidaAlmacenDTO salidaAlmacen){
        super(salidaAlmacen.getNumeroSalida(), salidaAlmacen.getCosto(), salidaAlmacen.isStatus(), salidaAlmacen.getCantidad(), salidaAlmacen.getFechaRegistro(), salidaAlmacen.getRefaccion(), salidaAlmacen.getUsuario(), salidaAlmacen.getOrdenReparacion(), salidaAlmacen.getTipo());
        this.idSalidaUnidad = idSalidaUnidad;
        this.unidadTransporte = unidadTransporte;
    }

    public int getIdSalidaUnidad() {
        return idSalidaUnidad;
    }

    public void setIdSalidaUnidad(int idSalidaUnidad) {
        this.idSalidaUnidad = idSalidaUnidad;
    }

    public UnidadTransporteDTO getTransporte() {
        return unidadTransporte;
    }

    public void setTransporte(UnidadTransporteDTO unidadTransporte) {
        this.unidadTransporte = unidadTransporte;
    }
    
    @Override
    public String toString(){
        try {
            return "{SalidaUnidadDTO}[(idSalidaUnidad:" + this.idSalidaUnidad + "),(unidad:" + 
                    nullCheck(this.unidadTransporte) + "),(salidaAlmacen:" + nullCheck(super.toString()) + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
