SELECT numero_orden, fecha_entrada AS apertura, IFNULL(fecha_salida, "Abierta") AS cierre, 

(SELECT id_tipo FROM unidad_transporte WHERE clave = $P{CLAVE_UNIDAD}) AS tipo_unidad,

(SELECT CONCAT(nombre, " ", apellidos) FROM operador WHERE operador.numero_operador = orden_reparacion.numero_operador) AS nombre_operador,

(SELECT CONCAT(nombre, " ", apellidos) FROM usuario WHERE usuario.numero_usuario = orden_reparacion.numero_usuario) AS nombre_operador,

(SELECT IFNULL(SUM(total), 0.0) FROM cargo_directo WHERE cargo_directo.numero_orden = orden_reparacion.numero_orden AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave = $P{CLAVE_UNIDAD})) AS cargo_directo,

(SELECT (SELECT IFNULL(SUM(iva), 0.0) FROM cargo_directo WHERE cargo_directo.numero_orden = orden_reparacion.numero_orden AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave = $P{CLAVE_UNIDAD}))) AS cargo_directo_iva,

(SELECT (SELECT IFNULL(SUM(subtotal), 0.0) FROM cargo_directo WHERE cargo_directo.numero_orden = orden_reparacion.numero_orden AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave = $P{CLAVE_UNIDAD}))) AS cargo_directo_subtotal,

(SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE salida_almacen.numero_orden = orden_reparacion.numero_orden AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave = $P{CLAVE_UNIDAD})) AS salida_almacen,

IFNULL((SELECT SUM(IFNULL(costo, 0.0) / (SELECT 100.0 + (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion)) * (SELECT (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion))) FROM salida_almacen WHERE salida_almacen.numero_orden = orden_reparacion.numero_orden AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave = $P{CLAVE_UNIDAD})), 0.0) AS salida_almacen_iva,

(SELECT salida_almacen - salida_almacen_iva FROM dual) AS salida_almacen_subtotal,

(SELECT IFNULL(SUM(monto), 0.0) FROM trabajo_externo WHERE trabajo_externo.numero_orden = orden_reparacion.numero_orden AND clave = $P{CLAVE_UNIDAD}) AS total_trabajo,

(SELECT IFNULL(SUM(iva), 0.0) FROM trabajo_externo WHERE trabajo_externo.numero_orden = orden_reparacion.numero_orden AND clave = $P{CLAVE_UNIDAD}) AS total_trabajo_iva,

(SELECT IFNULL(SUM(subtotal), 0.0) FROM trabajo_externo WHERE trabajo_externo.numero_orden = orden_reparacion.numero_orden AND clave = $P{CLAVE_UNIDAD}) AS total_trabajo_subtotal,

(SELECT cargo_directo_subtotal + salida_almacen_subtotal + total_trabajo_subtotal FROM dual) AS subtotales,

(SELECT cargo_directo_iva + salida_almacen_iva + total_trabajo_iva FROM dual) AS ivas,

(SELECT cargo_directo + salida_almacen + total_trabajo FROM dual) AS totales



FROM orden_reparacion WHERE numero_orden IN (SELECT numero_orden FROM transporte_reparacion WHERE clave = $P{CLAVE_UNIDAD} AND status = TRUE) AND fecha_entrada >= $P{INICIO} AND fecha_salida <= $P{FIN} AND status = TRUE;
