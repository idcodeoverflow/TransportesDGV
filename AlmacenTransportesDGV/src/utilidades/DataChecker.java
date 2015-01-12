/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilidades;

import beans.CargoDirectoDTO;
import beans.CargoEspecialDTO;
import beans.CargoOperadorDTO;
import beans.CargoUnidadDTO;
import beans.ComunicacionContactoDTO;
import beans.ContactoProveedorDTO;
import beans.EntradaAlmacenDTO;
import beans.FacturaDTO;
import beans.FamiliaRefaccionDTO;
import beans.GrupoRefaccionDTO;
import beans.LogInDTO;
import beans.MarcaUnidadDTO;
import beans.OperadorDTO;
import beans.OrdenReparacionDTO;
import beans.ProveedorContadoDTO;
import beans.ProveedorCreditoDTO;
import beans.ProveedorDTO;
import beans.RefaccionDTO;
import beans.SalidaAlmacenDTO;
import beans.SalidaEspecialDTO;
import beans.SalidaOperadorDTO;
import beans.SalidaUnidadDTO;
import beans.TipoUnidadDTO;
import beans.TrabajoExternoDTO;
import beans.TransporteReparacionDTO;
import beans.UnidadTransporteDTO;
import beans.UsuarioDTO;
import java.sql.Timestamp;

/**
 *
 * @author David Israel
 */
public class DataChecker {
    
    public DataChecker(){
        
    }
    
    public static String nullCheck(CargoDirectoDTO cargoDirecto){
        return ((cargoDirecto != null) ? cargoDirecto.toString() : "{CargoDirectoDTO}[null]");
    }
    
    public static String nullCheck(CargoEspecialDTO cargoEspecial){
        return ((cargoEspecial != null) ? cargoEspecial.toString() : "{CargoEspecialDTO}[null]");
    }
    
    public static String nullCheck(CargoOperadorDTO cargoOperador){
        return ((cargoOperador != null) ? cargoOperador.toString() : "{CargoOperadorDTO}[null]");
    }
    
    public static String nullCheck(CargoUnidadDTO cargoUnidad){
        return ((cargoUnidad != null) ? cargoUnidad.toString() : "{CargoUnidadDTO}[null]");
    }
    
    public static String nullCheck(ComunicacionContactoDTO comunicacionContacto){
        return ((comunicacionContacto != null) ? comunicacionContacto.toString() : "{ComunicacionContactoDTO}[null]");
    }
    
    public static String nullCheck(ContactoProveedorDTO contactoProveedor){
        return ((contactoProveedor != null) ? contactoProveedor.toString() : "{ContactoProveedorDTO}[null]");
    }
    
    public static String nullCheck(EntradaAlmacenDTO entradaAlmacen){
        return ((entradaAlmacen != null) ? entradaAlmacen.toString() : "{EntradaAlmacenDTO}[null]");
    }
    
    public static String nullCheck(FacturaDTO factura){
        return ((factura != null) ? factura.toString() : "{FacturaDTO}[null]");
    }
    
    public static String nullCheck(FamiliaRefaccionDTO familiaRefaccion){
        return ((familiaRefaccion != null) ? familiaRefaccion.toString() : "{FamiliaDTO}[null]");
    }
    
    public static String nullCheck(GrupoRefaccionDTO grupoRefaccion){
        return ((grupoRefaccion != null) ? grupoRefaccion.toString() : "{GrupoRefaccionDTO}[null]");
    }
    
    public static String nullCheck(LogInDTO logIn){
        return ((logIn != null) ? logIn.toString() : "{LogInDTO}[null]");
    }
    
    public static String nullCheck(MarcaUnidadDTO marcaUnidad){
        return ((marcaUnidad != null) ? marcaUnidad.toString() : "{MarcaUnidadDTO}[null]");
    }
    
    public static String nullCheck(OperadorDTO operador){
        return ((operador.toString() != null) ? operador.toString() : "{OperadorDTO}[null]");
    }
    
    public static String nullCheck(OrdenReparacionDTO ordenReparacion){
        return ((ordenReparacion != null) ? ordenReparacion.toString() : "{OrdenReparacionDTO}[null]");
    }
    
    public static String nullCheck(ProveedorContadoDTO proveedorContado){
        return ((proveedorContado != null) ? proveedorContado.toString() : "{ProveedorContadoDTO}[null]");
    }
    
    public static String nullCheck(ProveedorCreditoDTO proveedorCredito){
        return ((proveedorCredito != null) ? proveedorCredito.toString() : "{ProveedorCreditoDTO}[null]");
    }
    
    public static String nullCheck(ProveedorDTO proveedor){
        return ((proveedor != null) ? proveedor.toString() : "{ProveedorDTO}[null]");
    }
    
    public static String nullCheck(RefaccionDTO refaccion){
        return ((refaccion != null) ? refaccion.toString() : "{RefaccionDTO}[null]");
    }
    
    public static String nullCheck(SalidaAlmacenDTO salidaAlmacen){
        return ((salidaAlmacen != null) ? salidaAlmacen.toString() : "{SalidaAlmacenDTO}[null]");
    }
    
    public static String nullCheck(SalidaEspecialDTO salidaEspecial){
        return ((salidaEspecial != null) ? salidaEspecial.toString() : "{SalidaEspecialDTO}[null]");
    }
    
    public static String nullCheck(SalidaOperadorDTO salidaOperador){
        return ((salidaOperador != null) ? salidaOperador.toString() : "{SalidaOperadorDTO}[null]");
    }
    
    public static String nullCheck(SalidaUnidadDTO salidaUnidad){
        return ((salidaUnidad != null) ? salidaUnidad.toString() : "{SalidaUnidadDTO}[null]");
    }
    
    public static String nullCheck(TipoUnidadDTO tipoUnidad){
        return ((tipoUnidad != null) ? tipoUnidad.toString() : "{TipoUnidadDTO}[null]");
    }
    
    public static String nullCheck(TrabajoExternoDTO trabajoExterno){
        return ((trabajoExterno != null) ? trabajoExterno.toString() : "{TrabajoExternoDTO}[null]");
    }
    
    public static String nullCheck(TransporteReparacionDTO transporteReparacion){
        return ((transporteReparacion != null) ? transporteReparacion.toString() : "{TransporteReparacionDTO}[null]");
    }
    
    public static String nullCheck(UnidadTransporteDTO unidadTransporte){
        return ((unidadTransporte != null) ? unidadTransporte.toString() : "{UnidadTransporteDTO}[null]");
    }
    
    public static String nullCheck(UsuarioDTO usuario){
        return ((usuario != null) ? usuario.toString() : "{UsuarioDTO}[null]");
    }
    
    public static String nullCheck(String string){
        return ((string != null) ? string : "null");
    }
    
    public static String nullCheck(Timestamp fecha){
        return ((fecha != null) ? fecha.toString() : "null");
    }
    
}
