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
public class CargoUnidadDTO extends CargoDirectoDTO {
    
    private int idCargoUnidad;
    private UnidadTransporteDTO unidad;

    public CargoUnidadDTO() {
        super();
    }

    public CargoUnidadDTO(int idCargoUnidad, UnidadTransporteDTO unidad, int numeroCargoDirecto, Timestamp fechaRegistro, double precioUnitario, double cantidad, double subtotal, double iva, double total, boolean status, RefaccionDTO refaccion, FacturaDTO factura, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion) {
        super(numeroCargoDirecto, fechaRegistro, precioUnitario, cantidad, subtotal, iva, total, status, refaccion, factura, usuario, ordenReparacion);
        this.idCargoUnidad = idCargoUnidad;
        this.unidad = unidad;
    }
    
    public CargoUnidadDTO(int idCargoUnidad, UnidadTransporteDTO unidad, CargoDirectoDTO cargo){
        super(cargo.getNumeroCargoDirecto(), cargo.getFechaRegistro(), cargo.getPrecioUnitario(), cargo.getCantidad(), cargo.getSubtotal(), cargo.getIva(), cargo.getTotal(), cargo.isStatus(), cargo.getRefaccion(), cargo.getFactura(), cargo.getUsuario(), cargo.getOrdenReparacion());
        this.idCargoUnidad = idCargoUnidad;
        this.unidad = unidad;
    }

    public int getIdCargoUnidad() {
        return idCargoUnidad;
    }

    public void setIdCargoUnidad(int idCargoUnidad) {
        this.idCargoUnidad = idCargoUnidad;
    }

    public UnidadTransporteDTO getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadTransporteDTO unidad) {
        this.unidad = unidad;
    }
    
    @Override
    public String toString(){
        try{
            return "{CargoUnidadDTO}[(idCargoUnidad:" + this.idCargoUnidad + "),(unidad:" + 
                    nullCheck(this.unidad) + "),(cargoDirecto:" + nullCheck(super.toString()) + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
