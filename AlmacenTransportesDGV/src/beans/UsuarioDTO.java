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
public class UsuarioDTO {
    
    private int numeroUsuario;
    private String nombre;
    private String apellidos;
    private String passwd;
    private Timestamp fechaIngreso;
    private Timestamp fechaEgreso;
    private int privilegio;
    private boolean status;

    public UsuarioDTO() {
    }

    public UsuarioDTO(int numeroUsuario, String nombre, String apellidos, String passwd, Timestamp fechaInrgeso, Timestamp fechaEgreso, int privilegio, boolean status) {
        this.numeroUsuario = numeroUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.passwd = passwd;
        this.fechaIngreso = fechaInrgeso;
        this.fechaEgreso = fechaEgreso;
        this.privilegio = privilegio;
        this.status = status;
    }

    public int getNumeroUsuario() {
        return numeroUsuario;
    }

    public void setNumeroUsuario(int numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaInrgeso) {
        this.fechaIngreso = fechaInrgeso;
    }

    public Timestamp getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Timestamp fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public int getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(int privilegio) {
        this.privilegio = privilegio;
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
            return "{UsuarioDTO}[(numeroUsuario:" + this.numeroUsuario + "),(nombre:" + 
                    nullCheck(this.nombre) + "),(apellidos:" + nullCheck(this.apellidos) + 
                    "),(fechaIngreso:" + nullCheck(this.fechaIngreso) + "),(privilegio:" + 
                    this.privilegio + "),(passwd:" + ((passwd != null) ? "*" : "_NULL_") + 
                    "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
