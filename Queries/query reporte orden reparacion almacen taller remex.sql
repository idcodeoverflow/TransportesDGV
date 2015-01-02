SELECT numero_cargo_directo, fecha_registro, precio_unitario, cantidad, subtotal, iva, total, clave_refaccion, 
(SELECT nombre FROM refaccion WHERE cargo_directo.clave_refaccion = refaccion.clave_refaccion) AS nombre_refaccion, 
folio, (SELECT nombre FROM proveedor WHERE cargo_directo.id_proveedor = proveedor.id_proveedor) AS nombre_proveedor, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE cargo_directo.numero_usuario = usuario.numero_usuario) AS nombre_usuario, 
(SELECT clave FROM cargo_unidad WHERE cargo_unidad.numero_cargo_directo = cargo_directo.numero_cargo_directo) AS clave_unidad 
FROM cargo_directo WHERE numero_orden = 1 AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo 
FROM cargo_unidad);