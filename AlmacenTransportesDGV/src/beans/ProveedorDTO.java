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
public class ProveedorDTO {
    
    private int idProveedor;
    private String nombre;
    private String rfc;
    private String direccion;
    private String colonia;
    private String tel;
    private String mail;
    private Timestamp fechaAlta;
    private Timestamp fechaBaja;
    private boolean status;

    public ProveedorDTO() {
    }

    public ProveedorDTO(int idProveedor, String nombre, String rfc, String direccion, String colonia, String tel, String mail, Timestamp fechaAlta, Timestamp fechaBaja, boolean status) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.rfc = rfc;
        this.direccion = direccion;
        this.colonia = colonia;
        this.tel = tel;
        this.mail = mail;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.status = status;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    @Override
    public String toString(){
        try {
            return "{ProveedorDTO}[(idProveedor:" + this.idProveedor + "),(nombre:" + 
                    nullCheck(this.nombre) + "),(rfc:" + nullCheck(this.rfc) + "),(fechaAlta:" + 
                    nullCheck(this.fechaAlta) + "),(fechaBaja:" + nullCheck(this.fechaBaja) + 
                    "),(mail:" + nullCheck(this.mail) + "),(tel:" + nullCheck(this.tel) + 
                    "),(direccion:" + nullCheck(this.direccion) + "),(colonia:" + 
                    nullCheck(this.colonia) + "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
