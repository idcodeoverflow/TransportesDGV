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
public class TipoUnidadDTO {
    
    private int idTipo;
    private String nombre;
    private boolean status;

    public TipoUnidadDTO() {
    }

    public TipoUnidadDTO(int idTipo, String nombre, boolean status) {
        this.idTipo = idTipo;
        this.nombre = nombre;
        this.status = status;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public String toString(){
        try {
            return "{TipoUnidadDTO}[(idTipo:" + this.idTipo + "),(nombre:" + 
                    nullCheck(this.nombre) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
