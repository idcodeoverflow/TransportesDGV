SELECT numero_trabajo_externo, fecha_registro, descripcion, cantidad, precio_unitario, subtotal, iva, monto, status, id_proveedor, (SELECT nombre FROM proveedor WHERE proveedor.id_proveedor = trabajo_externo.id_proveedor) AS nombre_proveedor, folio, numero_orden, clave, numero_usuario, (SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = trabajo_externo.numero_usuario) AS nombre_usuario FROM trabajo_externo WHERE numero_orden = 1 AND status = TRUE;
