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
public class BodegaReciclajeDTO {
    
    private int idBodega;
    private String nombre;
    private boolean status;

    public BodegaReciclajeDTO(int idBodega, String nombre, boolean status) {
        this.idBodega = idBodega;
        this.nombre = nombre;
        this.status = status;
    }
    
    public BodegaReciclajeDTO(){}

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
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
        try{
            return "{BodegaReciclajeDTO}[(idBodega:" + this.idBodega + "),(nombre:" + 
                nullCheck(this.nombre) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
