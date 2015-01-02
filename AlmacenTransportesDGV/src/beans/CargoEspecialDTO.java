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
public class CargoEspecialDTO extends CargoDirectoDTO {
    
    private int idCargoEspecial;
    private String nombreBeneficiario;

    public CargoEspecialDTO() {
        super();
    }

    public CargoEspecialDTO(int idCargoEspecial, String nombreBeneficiario, int numeroCargoDirecto, Timestamp fechaRegistro, double precioUnitario, double cantidad, double subtotal, double iva, double total, boolean status, RefaccionDTO refaccion, FacturaDTO factura, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion) {
        super(numeroCargoDirecto, fechaRegistro, precioUnitario, cantidad, subtotal, iva, total, status, refaccion, factura, usuario, ordenReparacion);
        this.idCargoEspecial = idCargoEspecial;
        this.nombreBeneficiario = nombreBeneficiario;
    }
    
    public CargoEspecialDTO(int idCargoEspecial, String nombreBeneficiario, CargoDirectoDTO cargo){
        super(cargo.getNumeroCargoDirecto(), cargo.getFechaRegistro(), cargo.getPrecioUnitario(), cargo.getCantidad(), cargo.getSubtotal(), cargo.getIva(), cargo.getTotal(), cargo.isStatus(), cargo.getRefaccion(), cargo.getFactura(), cargo.getUsuario(), cargo.getOrdenReparacion());
        this.idCargoEspecial = idCargoEspecial;
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public int getIdCargoEspecial() {
        return idCargoEspecial;
    }

    public void setIdCargoEspecial(int idCargoEspecial) {
        this.idCargoEspecial = idCargoEspecial;
    }
    
    @Override
    public String toString(){
        try {
            return "{CargoEspecialDTO}[(idCargoEspecial:" + this.idCargoEspecial + 
                    "),(nombreBeneficiario:" + nullCheck(this.nombreBeneficiario) + 
                    "),(cargoDirecto:" + nullCheck(super.toString()) + ")]";
        } catch(NullPointerException ex) {
            return null;
        }
    }
    
}
