SELECT id_cargo_unidad AS numero_cargo_directo, fecha_registro, precio_unitario, cantidad, subtotal, iva, total, clave_refaccion,
(SELECT nombre FROM refaccion WHERE cargo_unidad.clave_refaccion = refaccion.clave_refaccion) AS nombre_refaccion,
folio, (SELECT nombre FROM proveedor WHERE cargo_unidad.id_proveedor = proveedor.id_proveedor) AS nombre_proveedor,
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE cargo_unidad.numero_usuario = usuario.numero_usuario) AS nombre_usuario,
clave AS clave_unidad FROM cargo_unidad WHERE numero_orden = $P{ORDEN_REPARACION} AND status = TRUE;