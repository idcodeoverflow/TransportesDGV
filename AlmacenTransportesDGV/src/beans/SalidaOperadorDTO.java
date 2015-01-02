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
public class SalidaOperadorDTO extends SalidaAlmacenDTO {
    
    private int idSalidaOperador;
    private OperadorDTO operador;

    public SalidaOperadorDTO() {
        super();
    }

    public SalidaOperadorDTO(int idSalidaOperador, OperadorDTO operador, int numeroSalida, double costo, boolean status, double cantidad, Timestamp fechaRegistro, RefaccionDTO refaccion, UsuarioDTO usuario, OrdenReparacionDTO ordenReparacion, int tipo) {
        super(numeroSalida, costo, status, cantidad, fechaRegistro, refaccion, usuario, ordenReparacion, tipo);
        this.idSalidaOperador = idSalidaOperador;
        this.operador = operador;
    }
    
    public SalidaOperadorDTO(int idSalidaOperador, OperadorDTO operador, SalidaAlmacenDTO salidaAlmacen){
        super(salidaAlmacen.getNumeroSalida(), salidaAlmacen.getCosto(), salidaAlmacen.isStatus(), salidaAlmacen.getCantidad(), salidaAlmacen.getFechaRegistro(), salidaAlmacen.getRefaccion(), salidaAlmacen.getUsuario(), salidaAlmacen.getOrdenReparacion(), salidaAlmacen.getTipo());
        this.idSalidaOperador = idSalidaOperador;
        this.operador = operador;
    }
    
    public int getIdSalidaOperador() {
        return idSalidaOperador;
    }

    public void setIdSalidaOperador(int idSalidaOperador) {
        this.idSalidaOperador = idSalidaOperador;
    }

    public OperadorDTO getOperador() {
        return operador;
    }

    public void setOperador(OperadorDTO operador) {
        this.operador = operador;
    }
    
    @Override
    public String toString(){
        try {
            return "{SalidaOperadorDTO}[(idSalidaOperador:" + this.idSalidaOperador + 
                    "),(operador:" + nullCheck(this.operador) + "),(salidaAlmacen:" + 
                    nullCheck(super.toString()) + ")]";
        } catch (Exception ex) {
            return null;
        }
    }
    
}
