/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logger;

/**
 *
 * @author David Israel
 */
public enum NivelSistema {
    DTO{String nombre = "_DTO_";},
    BUSINESS{String nombre = "_BUSINESS_";},
    DAO{String nombre = "_DAO_";},
    GUI{String nombre = "_GUI_";}
}
