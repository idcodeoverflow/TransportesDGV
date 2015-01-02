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
public class OperadorDTO {
    
    private int numeroOperador;
    private String nombre;
    private String apellidos;
    private Timestamp fechaIngreso;
    private Timestamp fechaEgreso;
    private boolean status;

    public OperadorDTO() {
    }

    public OperadorDTO(int numeroOperador, String nombre, String apellidos, Timestamp fechaIngreso, Timestamp fechaEgreso, boolean status) {
        this.numeroOperador = numeroOperador;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.status = status;
    }

    public int getNumeroOperador() {
        return numeroOperador;
    }

    public void setNumeroOperador(int numeroOperador) {
        this.numeroOperador = numeroOperador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Timestamp getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Timestamp fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
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
            return "{OperadorDTO}[(numeroOperador:" + this.numeroOperador + "),(nombre:" + 
                    nullCheck(this.nombre) + "),(apellidos:" + nullCheck(this.apellidos) + 
                    "),(fechaIngreso:" + nullCheck(this.fechaIngreso) + "),(fechaEgreso:" + 
                    nullCheck(this.fechaEgreso) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
