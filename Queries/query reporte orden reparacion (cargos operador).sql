SELECT id_cargo_operador AS numero_cargo_directo, fecha_registro, precio_unitario, cantidad, subtotal, iva, total, status, clave_refaccion, 
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = cargo_operador.clave_refaccion) AS nombre_refaccion, id_proveedor, 
(SELECT nombre FROM proveedor WHERE proveedor.id_proveedor = cargo_operador.id_proveedor) AS nombre_proveedor, folio, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = cargo_operador.numero_usuario) AS usuario, numero_orden, 
(SELECT CONCAT(numero_operador,"# ",nombre," ",apellidos) FROM operador WHERE numero_operador IN (SELECT numero_operador FROM cargo_operador)) 
AS nombre_operador FROM cargo_operador WHERE numero_orden = $P{ORDEN_REPARACION} AND status = TRUE;