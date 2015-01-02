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
public class GrupoRefaccionDTO {
    
    private int idGrupoRefaccion;
    private String nombre;
    private boolean status;

    public GrupoRefaccionDTO() {
    }

    public GrupoRefaccionDTO(int idGrupoRefaccion, String nombre, boolean status) {
        this.idGrupoRefaccion = idGrupoRefaccion;
        this.nombre = nombre;
        this.status = status;
    }

    public int getIdGrupoRefaccion() {
        return idGrupoRefaccion;
    }

    public void setIdGrupoRefaccion(int idGrupoRefaccion) {
        this.idGrupoRefaccion = idGrupoRefaccion;
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
            return "{GrupoRefaccionDTO}[(idGrupoRefaccion:" + this.idGrupoRefaccion + 
                    "),(nombre:" + nullCheck(this.nombre) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
