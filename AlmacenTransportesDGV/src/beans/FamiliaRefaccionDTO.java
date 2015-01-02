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
public class FamiliaRefaccionDTO {
    
    private int idFamiliaRefaccion;
    private String nombre;
    private boolean status;
    private GrupoRefaccionDTO grupo;

    public FamiliaRefaccionDTO() {
    }

    public FamiliaRefaccionDTO(int idFamiliaRefaccion, String nombre, boolean status, GrupoRefaccionDTO grupo) {
        this.idFamiliaRefaccion = idFamiliaRefaccion;
        this.nombre = nombre;
        this.status = status;
        this.grupo = grupo;
    }

    public int getIdFamiliaRefaccion() {
        return idFamiliaRefaccion;
    }

    public void setIdFamiliaRefaccion(int idFamiliaRefaccion) {
        this.idFamiliaRefaccion = idFamiliaRefaccion;
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

    public GrupoRefaccionDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoRefaccionDTO grupo) {
        this.grupo = grupo;
    }
    
    @Override
    public String toString(){
        try{
            return "{FamiliaRefaccionDTO}[(idFamiliaRefaccion:" + this.idFamiliaRefaccion + 
                    "),(nombre:" + nullCheck(this.nombre) + "),(grupo:" + nullCheck(this.grupo) + 
                    "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
