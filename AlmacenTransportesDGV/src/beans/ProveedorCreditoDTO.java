/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class ProveedorCreditoDTO extends ProveedorDTO {
    
    private int idProveedorCredito;
    private short tiempoLimitePago;
    private boolean statusCredito;
    
    public ProveedorCreditoDTO() {
    }

    public ProveedorCreditoDTO(int idProveedorCredito, short tiempoLimitePago, boolean statusCredito) {
        this.idProveedorCredito = idProveedorCredito;
        this.tiempoLimitePago = tiempoLimitePago;
        this.statusCredito = statusCredito;
    }

    public int getIdProveedorCredito() {
        return idProveedorCredito;
    }

    public void setIdProveedorCredito(int idProveedorCredito) {
        this.idProveedorCredito = idProveedorCredito;
    }

    public short getTiempoLimitePago() {
        return tiempoLimitePago;
    }

    public void setTiempoLimitePago(short tiempoLimitePago) {
        this.tiempoLimitePago = tiempoLimitePago;
    }

    public boolean isStatusCredito() {
        return statusCredito;
    }

    public void setStatusCredito(boolean statusCredito) {
        this.statusCredito = statusCredito;
    }

    @Override
    public String toString(){
        try {
            return "{ProveedorCreditoDTO}[(proveedor:" + nullCheck(super.toString()) + 
                    "),(idProveedorCredito:" + this.idProveedorCredito + "),(statusCredito:" + this.statusCredito + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
