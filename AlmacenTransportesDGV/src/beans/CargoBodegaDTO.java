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
public class CargoBodegaDTO extends CargoDirectoDTO {
    
    private int idCargoBodega;
    private BodegaReciclajeDTO bodega;

    public CargoBodegaDTO(int idCargoBodega, BodegaReciclajeDTO bodega, int numeroCargoDirecto, Timestamp fechaRegistro, double precioUnitario, double cantidad, double subtotal, double iva, double total, boolean status, RefaccionDTO refaccion, FacturaDTO factura, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion) {
        super(numeroCargoDirecto, fechaRegistro, precioUnitario, cantidad, subtotal, iva, total, status, refaccion, factura, usuario, ordenReparacion);
        this.idCargoBodega = idCargoBodega;
        this.bodega = bodega;
    }
    
    public CargoBodegaDTO(int idCargoBodega, BodegaReciclajeDTO bodega, CargoDirectoDTO cargo){
        super(cargo.getNumeroCargoDirecto(), cargo.getFechaRegistro(), cargo.getPrecioUnitario(), cargo.getCantidad(), cargo.getSubtotal(), cargo.getIva(), cargo.getTotal(), cargo.isStatus(), cargo.getRefaccion(), cargo.getFactura(), cargo.getUsuario(), cargo.getOrdenReparacion());
        this.idCargoBodega = idCargoBodega;
        this.bodega = bodega;
    }

    public CargoBodegaDTO(){
        super();
    }
    
    public BodegaReciclajeDTO getBodega() {
        return bodega;
    }

    public void setBodega(BodegaReciclajeDTO bodega) {
        this.bodega = bodega;
    }

    public int getIdCargoBodega() {
        return idCargoBodega;
    }

    public void setIdCargoBodega(int idCargoBodega) {
        this.idCargoBodega = idCargoBodega;
    }
    
    @Override
    public String toString(){
        try{
            return "{CargoBodegaDTO}[(idCargoBodega:" + this.idCargoBodega + "),(bodega:" + 
                    nullCheck(this.bodega) + "),(cargoDirecto:" + nullCheck(super.toString()) + ")]";
        } catch(NullPointerException ex) {
            return null;
        }
    }
    
}
