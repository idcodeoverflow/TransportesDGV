SELECT descripcion, cantidad, monto, fecha_registro,
(SELECT kilometraje FROM transporte_reparacion WHERE clave = $P{CLAVE} AND numero_orden = $P{NUMERO} AND status = true) AS kilometraje
FROM trabajo_externo WHERE clave = $P{CLAVE} AND numero_orden = $P{NUMERO} AND status = TRUE;

