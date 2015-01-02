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
public class SalidaEspecialDTO extends SalidaAlmacenDTO {
    
    private int idSalidaEspecial;
    private String nombreBeneficiario;

    public SalidaEspecialDTO() {
        super();
    }

    public SalidaEspecialDTO(int idSalidaEspecial, String nombreBeneficiario, int numeroSalida, double costo, boolean status, double cantidad, Timestamp fechaRegistro, RefaccionDTO refaccion, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion, int tipo) {
        super(numeroSalida, costo, status, cantidad, fechaRegistro, refaccion, usuario, ordenReparacion, tipo);
        this.idSalidaEspecial = idSalidaEspecial;
        this.nombreBeneficiario = nombreBeneficiario;
    }
    
    public SalidaEspecialDTO(int idSalidaEspecial, String nombreBeneficiario, SalidaAlmacenDTO salidaAlmacen){
        super(salidaAlmacen.getNumeroSalida(), salidaAlmacen.getCosto(), salidaAlmacen.isStatus(), salidaAlmacen.getCantidad(), salidaAlmacen.getFechaRegistro(), salidaAlmacen.getRefaccion(), salidaAlmacen.getUsuario(), salidaAlmacen.getOrdenReparacion(), salidaAlmacen.getTipo());
        this.idSalidaEspecial = idSalidaEspecial;
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public int getIdSalidaEspecial() {
        return idSalidaEspecial;
    }

    public void setIdSalidaEspecial(int idSalidaEspecial) {
        this.idSalidaEspecial = idSalidaEspecial;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }
    
    @Override
    public String toString(){
        try {
            return "{SalidaEspecialDTO}[(idSalidaEspecial:" + this.idSalidaEspecial + 
                    "),(nombreBeneficiario:" + nullCheck(this.nombreBeneficiario) + 
                    "),(salidaAlmacen:" + nullCheck(super.toString()) + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
