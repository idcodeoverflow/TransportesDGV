/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David
 */
public class MarcaMotorDTO {
    
    private int idMarcaMotor;
    private String nombre;
    private boolean status;

    public MarcaMotorDTO(int idMarcaMotor, String nombre, boolean status) {
        this.idMarcaMotor = idMarcaMotor;
        this.nombre = nombre;
        this.status = status;
    }
    
    public MarcaMotorDTO(){}

    public int getIdMarcaMotor() {
        return idMarcaMotor;
    }

    public void setIdMarcaMotor(int idMarcaMotor) {
        this.idMarcaMotor = idMarcaMotor;
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
            return "{MarcaMotorDTO}[(idMarcaMotor:" + idMarcaMotor + "),(nombre:" + 
                    nullCheck(this.nombre) + "),(status:" + status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
