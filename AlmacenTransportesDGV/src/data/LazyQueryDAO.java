/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.SQLException;
import support.DBConnection;

/**
 *
 * @author David Israel
 */
public class LazyQueryDAO {
 
    public LazyQueryDAO(){}
    
    /**
     * Este método abre una conexion a la base de datos para evitar queries con
     * multiples aperturas a la misma, al usarse no se debe olvidar mandar a
     * llamar al método endLazyQuery() para cerrar la conexión de manera segura.
     */
    public void startLazyQuery(){
        DBConnection.createConnection();
    }
    
    /**
     * Cierra de manera segura una conexión a la base de datos, despues de usar
     * un startLazyQuery().
     * @throws SQLException 
     */
    public void endLazyQuery() throws SQLException{
        DBConnection.closeConn();
    }
    
}
