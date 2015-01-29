SELECT id_cargo_especial AS numero_cargo_directo, fecha_registro, precio_unitario, cantidad, subtotal, iva, total, status, clave_refaccion, 
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = cargo_especial.clave_refaccion) AS nombre_refaccion, id_proveedor, 
(SELECT nombre FROM proveedor WHERE proveedor.id_proveedor = cargo_especial.id_proveedor) AS nombre_proveedor, folio, numero_usuario, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = cargo_especial.numero_usuario) AS nombre_usuario, numero_orden, 
nombre_beneficiario AS beneficiario FROM cargo_especial WHERE status = TRUE AND numero_orden = $P{NUMERO_ORDEN};
