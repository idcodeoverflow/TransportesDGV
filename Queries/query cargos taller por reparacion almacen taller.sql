SELECT id_cargo_taller, fecha_registro, precio_unitario, cantidad, subtotal, iva, total, status, clave_refaccion,
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = cargo_taller.clave_refaccion) AS nombre_refaccion, id_proveedor, 
(SELECT nombre FROM proveedor WHERE proveedor.id_proveedor = cargo_taller.id_proveedor) AS nombre_proveedor, folio, numero_usuario, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = cargo_taller.numero_usuario) AS nombre_usuario, numero_orden,
clave FROM cargo_taller WHERE numero_orden = 1 AND status = TRUE;