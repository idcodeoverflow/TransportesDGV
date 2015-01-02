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
public class MarcaUnidadDTO {
    
    private int idMarca;
    private String nombre;
    private boolean status;

    public MarcaUnidadDTO() {
    }

    public MarcaUnidadDTO(int idMarca, String nombre, boolean status) {
        this.idMarca = idMarca;
        this.nombre = nombre;
        this.status = status;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
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
            return "{MarcaUnidadDTO}[(idMarca:" + this.idMarca + "),(nombre:" + 
                    nullCheck(this.nombre) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
