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
public class SalidaBodegaDTO extends SalidaAlmacenDTO {
    
    private int idSalidaBodega;
    private BodegaReciclajeDTO bodega;

    public SalidaBodegaDTO() {
        super();
    }

    public SalidaBodegaDTO(int idSalidaBodega, BodegaReciclajeDTO bodega, int numeroSalida, double costo, boolean status, double cantidad, Timestamp fechaRegistro, RefaccionDTO refaccion, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion, int tipo) {
        super(numeroSalida, costo, status, cantidad, fechaRegistro, refaccion, usuario, ordenReparacion, tipo);
        this.idSalidaBodega = idSalidaBodega;
        this.bodega = bodega;
    }

    public SalidaBodegaDTO(int idSalidaBodega, BodegaReciclajeDTO bodega, SalidaAlmacenDTO salidaAlmacen){
        super(salidaAlmacen.getNumeroSalida(), salidaAlmacen.getCosto(), salidaAlmacen.isStatus(), salidaAlmacen.getCantidad(), salidaAlmacen.getFechaRegistro(), salidaAlmacen.getRefaccion(), salidaAlmacen.getUsuario(), salidaAlmacen.getOrdenReparacion(), salidaAlmacen.getTipo());
        this.idSalidaBodega = idSalidaBodega;
        this.bodega = bodega;
    }
    
    public int getIdSalidaBodega() {
        return idSalidaBodega;
    }

    public void setIdSalidaBodega(int idSalidaBodega) {
        this.idSalidaBodega = idSalidaBodega;
    }

    public BodegaReciclajeDTO getBodega() {
        return bodega;
    }

    public void setBodega(BodegaReciclajeDTO bodega) {
        this.bodega = bodega;
    }
    
    @Override
    public String toString(){
        try {
            return "{SalidaBodegaDTO}[(idSalidaBodega:" + this.idSalidaBodega + "),(bodega:" + 
                    nullCheck(this.bodega) + "),(salidaAlmacen:" + nullCheck(super.toString()) + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
