/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Timestamp;
import java.util.List;
import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class ContactoProveedorDTO {
    
    private int idContactoProveedor;
    private String nombre;
    private String apellidos;
    private String puesto;
    private Timestamp fechaAlta;
    private Timestamp fechaBaja;
    private boolean status;
    private List<ComunicacionContactoDTO> comunicaciones;
    
    public ContactoProveedorDTO() {
    }

    public ContactoProveedorDTO(int idContactoProveedor, String nombre, String apellidos, String puesto, Timestamp fechaAlta, Timestamp fechaBaja, boolean status, List<ComunicacionContactoDTO> comunicaciones) {
        this.idContactoProveedor = idContactoProveedor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puesto = puesto;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.status = status;
        this.comunicaciones = comunicaciones;
    }

    public int getIdContactoProveedor() {
        return idContactoProveedor;
    }

    public void setIdContactoProveedor(int idContactoProveedor) {
        this.idContactoProveedor = idContactoProveedor;
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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Timestamp getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Timestamp fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Timestamp getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Timestamp fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ComunicacionContactoDTO> getComunicaciones() {
        return comunicaciones;
    }

    public void setComunicaciones(List<ComunicacionContactoDTO> comunicaciones) {
        this.comunicaciones = comunicaciones;
    }

    @Override
    public String toString(){
        try{
            String cadena = "{ContactoProveedorDTO}[(idContactoProveedor:" + this.idContactoProveedor + 
                    "),(nombre:" + nullCheck(this.nombre) + "),(apellidos:" + nullCheck(this.apellidos) + 
                    "),(puesto:" + nullCheck(this.puesto) + "),(fechaAlta:" + nullCheck(this.fechaAlta) + 
                    "),(fechaBaja:" + nullCheck(this.fechaBaja) + "),(status:" + this.status + "),(comunicaciones:";
            if(this.comunicaciones != null){
                for(ComunicacionContactoDTO comunicacion : this.comunicaciones){
                    cadena += nullCheck(comunicacion) + ",";
                }
            }
            cadena += ")]";
            return cadena;
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
