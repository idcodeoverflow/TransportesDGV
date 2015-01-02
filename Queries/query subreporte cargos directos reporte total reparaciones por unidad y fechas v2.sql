SELECT clave_refaccion, fecha_registro, cantidad AS cantidad_parte, total AS total_cargo,
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = cargo_directo.clave_refaccion) AS nombre_refaccion,
(SELECT kilometraje FROM transporte_reparacion WHERE clave = $P{CLAVE} AND numero_orden = $P{NUMERO} AND status = true) AS kilometraje
FROM cargo_directo WHERE numero_orden = $P{NUMERO} AND status = TRUE AND numero_cargo_directo IN (SELECT numero_cargo_directo FROM cargo_unidad WHERE clave = $P{CLAVE});
