SELECT id_cargo_operador, fecha_registro, precio_unitario, cantidad, subtotal, iva, total, status, clave_refaccion,
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = cargo_operador.clave_refaccion) AS nombre_refaccion, id_proveedor, 
(SELECT nombre FROM proveedor WHERE proveedor.id_proveedor = cargo_operador.id_proveedor) AS nombre_proveedor, folio, numero_usuario, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = cargo_operador.numero_usuario) AS nombre_usuario, numero_orden,
numero_operador, (SELECT CONCAT(nombre," ",apellidos) FROM operador WHERE operador.numero_operador = cargo_operador.numero_operador) AS nombre_operador 
FROM cargo_operador WHERE numero_orden = 1 AND status = TRUE;
