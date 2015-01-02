/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logger;

import java.io.IOException;

/**
 *
 * @author David Israel
 */
public interface ExceptionLogger {
    
    boolean crearNuevoLog();
    
    boolean existeLog(String name);
    
    boolean borrarLog(String name);
    
    boolean existePath();
    
    boolean generarRuta() throws IOException;
    
    //boolean scribirLog(String message, int codigo, UsuarioDTO usuario);
    
}
