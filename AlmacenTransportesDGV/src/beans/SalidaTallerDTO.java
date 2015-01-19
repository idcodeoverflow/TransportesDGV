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
public class SalidaTallerDTO extends SalidaAlmacenDTO {
    
    private int idSalidaTaller;

    public SalidaTallerDTO() {
        super();
    }

    public SalidaTallerDTO(int idSalidaEspecial, int numeroSalida, double costo, boolean status, double cantidad, Timestamp fechaRegistro, RefaccionDTO refaccion, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion, int tipo) {
        super(numeroSalida, costo, status, cantidad, fechaRegistro, refaccion, usuario, ordenReparacion, tipo);
        this.idSalidaTaller = idSalidaEspecial;
    }
    
    public SalidaTallerDTO(int idSalidaEspecial, SalidaAlmacenDTO salidaAlmacen){
        super(salidaAlmacen.getNumeroSalida(), salidaAlmacen.getCosto(), salidaAlmacen.isStatus(), salidaAlmacen.getCantidad(), salidaAlmacen.getFechaRegistro(), salidaAlmacen.getRefaccion(), salidaAlmacen.getUsuario(), salidaAlmacen.getOrdenReparacion(), salidaAlmacen.getTipo());
        this.idSalidaTaller = idSalidaEspecial;
    }

    public int getIdSalidaTaller() {
        return idSalidaTaller;
    }

    public void setIdSalidaTaller(int idSalidaTaller) {
        this.idSalidaTaller = idSalidaTaller;
    }

    @Override
    public String toString(){
        try {
            return "{SalidaTallerDTO}[(idSalidaEspecial:" + this.idSalidaTaller + 
                    "),(salidaAlmacen:" + nullCheck(super.toString()) + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
