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
public class CargoTallerDTO extends CargoDirectoDTO {
    
    private int idCargoTaller;
    private UnidadTransporteDTO unidad;

    public CargoTallerDTO() {
        super();
    }

    public CargoTallerDTO(int idCargoEspecial, UnidadTransporteDTO unidad, int numeroCargoDirecto, Timestamp fechaRegistro, double precioUnitario, double cantidad, double subtotal, double iva, double total, boolean status, RefaccionDTO refaccion, FacturaDTO factura, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion) {
        super(numeroCargoDirecto, fechaRegistro, precioUnitario, cantidad, subtotal, iva, total, status, refaccion, factura, usuario, ordenReparacion);
        this.idCargoTaller = idCargoEspecial;
        this.unidad = unidad;
    }
    
    public CargoTallerDTO(int idCargoEspecial, UnidadTransporteDTO unidad, CargoDirectoDTO cargo){
        super(cargo.getNumeroCargoDirecto(), cargo.getFechaRegistro(), cargo.getPrecioUnitario(), cargo.getCantidad(), cargo.getSubtotal(), cargo.getIva(), cargo.getTotal(), cargo.isStatus(), cargo.getRefaccion(), cargo.getFactura(), cargo.getUsuario(), cargo.getOrdenReparacion());
        this.idCargoTaller = idCargoEspecial;
        this.unidad = unidad;
    }

    public int getIdCargoTaller() {
        return idCargoTaller;
    }

    public void setIdCargoTaller(int idCargoTaller) {
        this.idCargoTaller = idCargoTaller;
    }
    
    public UnidadTransporteDTO getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadTransporteDTO unidad) {
        this.unidad = unidad;
    }
    
    @Override
    public String toString(){
        try {
            return "{CargoTallerDTO}[(idCargoEspecial:" + this.idCargoTaller + 
                    "),(cargoDirecto:" + nullCheck(super.toString()) + ")]";
        } catch(NullPointerException ex) {
            return null;
        }
    }

}
