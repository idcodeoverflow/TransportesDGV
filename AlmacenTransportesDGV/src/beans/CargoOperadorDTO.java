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
public class CargoOperadorDTO extends CargoDirectoDTO {
    
    private int idCargoOperador;
    private OperadorDTO operador;

    public CargoOperadorDTO() {
        super();
    }

    public CargoOperadorDTO(int idCargoOperador, OperadorDTO operador, int numeroCargoDirecto, Timestamp fechaRegistro, double precioUnitario, double cantidad, double subtotal, double iva, double total, boolean status, RefaccionDTO refaccion, FacturaDTO factura, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion) {
        super(numeroCargoDirecto, fechaRegistro, precioUnitario, cantidad, subtotal, iva, total, status, refaccion, factura, usuario, ordenReparacion);
        this.idCargoOperador = idCargoOperador;
        this.operador = operador;
    }
    
    public CargoOperadorDTO(int idCargoOperador, OperadorDTO operador, CargoDirectoDTO cargo){
        super(cargo.getNumeroCargoDirecto(), cargo.getFechaRegistro(), cargo.getPrecioUnitario(), cargo.getCantidad(), cargo.getSubtotal(), cargo.getIva(), cargo.getTotal(), cargo.isStatus(), cargo.getRefaccion(), cargo.getFactura(), cargo.getUsuario(), cargo.getOrdenReparacion());
        this.idCargoOperador = idCargoOperador;
        this.operador = operador;
    }

    public int getIdCargoOperador() {
        return idCargoOperador;
    }

    public void setIdCargoOperador(int idCargoOperador) {
        this.idCargoOperador = idCargoOperador;
    }

    public OperadorDTO getOperador() {
        return operador;
    }

    public void setOperador(OperadorDTO operador) {
        this.operador = operador;
    }
    
    @Override
    public String toString(){
        try{
            return "{CargoOperadorDTO}[(idCargoOperador:" + this.idCargoOperador + 
                    "),(operador:" + nullCheck(this.operador) + "),(cargoDirecto:" + 
                    nullCheck(super.toString()) + ")]";
        } catch(NullPointerException ex) {
            return null;
        }
    }
    
}
