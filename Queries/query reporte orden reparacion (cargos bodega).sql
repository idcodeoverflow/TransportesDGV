SELECT id_cargo_taller AS numero_cargo_directo, fecha_registro, precio_unitario, cantidad, subtotal, iva, total, status, clave_refaccion, 
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = cargo_taller.clave_refaccion) AS nombre_refaccion, id_proveedor, 
(SELECT nombre FROM proveedor WHERE proveedor.id_proveedor = cargo_taller.id_proveedor) AS nombre_proveedor, folio, numero_usuario, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = cargo_taller.numero_usuario) AS nombre_usuario, numero_orden, 
clave AS nombre_bodega FROM cargo_taller WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE;