SELECT clave_refaccion, fecha_registro, cantidad AS cantidad_parte, costo AS total_cargo,
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = salida_almacen.clave_refaccion) AS nombre_refaccion,
(SELECT kilometraje FROM transporte_reparacion WHERE clave = $P{CLAVE} AND numero_orden = $P{NUMERO} AND status = true) AS kilometraje
FROM salida_almacen WHERE numero_orden = $P{NUMERO} AND status = TRUE AND numero_salida IN (SELECT numero_salida FROM salida_unidad WHERE clave = $P{CLAVE});
