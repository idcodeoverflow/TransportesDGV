/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import static utilidades.DataChecker.nullCheck;

/**
 *
 * @author David Israel
 */
public class SistemaDTO {
    
    private int idSistema;
    private boolean notificarNuevaOrden;
    private boolean notificarFinOrden;
    
    public SistemaDTO(){
        idSistema = 0;
        notificarNuevaOrden = false;
        notificarFinOrden = false;        
    } 

    public SistemaDTO(int idSistema, boolean notificarNuevaOrden, boolean notificarFinOrden) {
        this.idSistema = idSistema;
        this.notificarNuevaOrden = notificarNuevaOrden;
        this.notificarFinOrden = notificarFinOrden;
    }

    public int getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(int idSistema) {
        this.idSistema = idSistema;
    }

    public boolean isNotificarNuevaOrden() {
        return notificarNuevaOrden;
    }

    public void setNotificarNuevaOrden(boolean notificarNuevaOrden) {
        this.notificarNuevaOrden = notificarNuevaOrden;
    }

    public boolean isNotificarFinOrden() {
        return notificarFinOrden;
    }

    public void setNotificarFinOrden(boolean notificarFinOrden) {
        this.notificarFinOrden = notificarFinOrden;
    }
    
    @Override
    public String toString(){
        try{
            return "{BodegaReciclajeDTO}[(idBodega:" + this.idSistema + "),(notificarNuevaOrden:" + 
                this.notificarNuevaOrden + "),(notificarFinOrden:" + this.notificarFinOrden + ")]";
        } catch (NullPointerException ex) {
            return null;
        }
    }
    
}
