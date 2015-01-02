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
public class ComunicacionContactoDTO {
    
    private int idComunicacion;
    private String nombre;
    private String datos;

    public ComunicacionContactoDTO() {
    }

    public ComunicacionContactoDTO(int idComunicacion, String nombre, String datos) {
        this.idComunicacion = idComunicacion;
        this.nombre = nombre;
        this.datos = datos;
    }

    public int getIdComunicacion() {
        return idComunicacion;
    }

    public void setIdComunicacion(int idComunicacion) {
        this.idComunicacion = idComunicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }
    
    @Override
    public String toString(){
        try {
            return "{ComunicacionContactoDTO}[(idComunicacion:" + this.idComunicacion + 
                    "),(nombre:" + nullCheck(this.nombre) + "),(datos:" + nullCheck(this.datos) + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
