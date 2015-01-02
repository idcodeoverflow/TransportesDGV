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
public class RefaccionDTO {
    
    private String claveRefaccion;
    private String nombre;
    private int puntoReorden;
    private int maximo;
    private int minimo;
    private boolean notificacion;
    private boolean status;
    private FamiliaRefaccionDTO familia;

    public RefaccionDTO() {
    }

    public RefaccionDTO(String claveRefaccion, String nombre, int puntoReorden, int maximo, int minimo, boolean notificacion, boolean status, FamiliaRefaccionDTO familia) {
        this.claveRefaccion = claveRefaccion;
        this.nombre = nombre;
        this.puntoReorden = puntoReorden;
        this.maximo = maximo;
        this.minimo = minimo;
        this.notificacion = notificacion;
        this.status = status;
        this.familia = familia;
    }

    public String getClaveRefaccion() {
        return claveRefaccion;
    }

    public void setClaveRefaccion(String claveRefaccion) {
        this.claveRefaccion = claveRefaccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntoReorden() {
        return puntoReorden;
    }

    public void setPuntoReorden(int puntoReorden) {
        this.puntoReorden = puntoReorden;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public boolean isNotificacion(){
        return notificacion;
    }
    
    public void setNotificacion(boolean notificacion){
        this.notificacion = notificacion;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public FamiliaRefaccionDTO getFamilia() {
        return familia;
    }

    public void setFamilia(FamiliaRefaccionDTO familia) {
        this.familia = familia;
    }
    
    @Override
    public String toString(){
        try {
        return "{RefaccionDTO}[(claveRefaccion:" + nullCheck(this.claveRefaccion) + 
                "),(nombre:" + nullCheck(this.nombre) + "),(maximo:" + this.maximo + 
                "),(minimo:" + this.minimo + "),(puntoReorden:" + this.puntoReorden + 
                "),(familia:" + nullCheck(this.familia) + "),(notificacion:" + this.notificacion + 
                "),(status:" + this.status + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
