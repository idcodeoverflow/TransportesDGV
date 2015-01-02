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
public class ProveedorContadoDTO extends ProveedorDTO {
    
    private int idProveedorContado;
    private boolean statusContado;
    
    public ProveedorContadoDTO() {
    }

    public ProveedorContadoDTO(int idProveedorContado, boolean statusContado) {
        this.idProveedorContado = idProveedorContado;
        this.statusContado = statusContado;
    }

    public int getIdProveedorContado() {
        return idProveedorContado;
    }

    public void setIdProveedorContado(int idProveedorContado) {
        this.idProveedorContado = idProveedorContado;
    }
    
    public boolean isStatusContado(){
        return statusContado;
    }
    
    public void setStatusContado(boolean statusContado){
        this.statusContado = statusContado;
    }
    
    @Override
    public String toString(){
        try {
            return "{ProveedorContadoDTO}[(proveedor:" + nullCheck(super.toString()) + 
                    "),(idProveedorContado:" + this.idProveedorContado + "),(statusContado:" + this.statusContado + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
