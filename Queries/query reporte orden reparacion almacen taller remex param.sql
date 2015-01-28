SELECT

(SELECT IFNULL(descripcion, "") FROM transporte_reparacion WHERE numero_orden = $P{NUMERO_ORDEN} AND ((SELECT id_tipo FROM unidad_transporte WHERE unidad_transporte.clave = transporte_reparacion.clave) = 2 OR (SELECT id_tipo FROM unidad_transporte WHERE unidad_transporte.clave = transporte_reparacion.clave) = 8) AND status = TRUE) AS descripcion_plana,

(SELECT IFNULL(descripcion, "") FROM transporte_reparacion WHERE numero_orden = $P{NUMERO_ORDEN} AND ((SELECT id_tipo FROM unidad_transporte WHERE unidad_transporte.clave = transporte_reparacion.clave) != 2 AND (SELECT id_tipo FROM unidad_transporte WHERE unidad_transporte.clave = transporte_reparacion.clave) != 8) AND status = TRUE) AS descripcion_tracto,

(SELECT fecha_entrada FROM orden_reparacion WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE) AS apertura,

(SELECT IFNULL(fecha_salida, "Abierta") FROM orden_reparacion WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE) AS cierre,

(SELECT IFNULL(SUM(total), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != 2))) AS cargo_directo_tracto,

(SELECT (SELECT IFNULL(SUM(iva), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != 2)))) AS cargo_directo_tracto_iva,

(SELECT (SELECT IFNULL(SUM(subtotal), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != 2)))) AS cargo_directo_tracto_subtotal,

(SELECT IFNULL(SUM(total), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = 2))) AS cargo_directo_plana,

(SELECT IFNULL(SUM(iva), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = 2))) AS cargo_directo_plana_iva,

(SELECT IFNULL(SUM(subtotal), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = 2))) AS cargo_directo_plana_subtotal,

(SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != 2))) AS salida_almacen_tracto,

IFNULL((SELECT SUM(IFNULL(costo, 0.0) / (SELECT 100.0 + (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion)) * (SELECT (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion))) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo != 2))), 0.0) AS salida_almacen_tracto_iva,

(SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = 2))) AS salida_almacen_plana,

IFNULL((SELECT SUM(IFNULL(costo, 0.0) / (SELECT 100.0 + (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion)) * (SELECT (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion))) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo = 2))), 0.0) AS salida_almacen_plana_iva,

(SELECT IFNULL(SUM(monto), 0.0) FROM trabajo_externo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo = 2)) AS total_trabajo_plana,

(SELECT IFNULL(SUM(iva), 0.0) FROM trabajo_externo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo = 2)) AS total_trabajo_plana_iva,

(SELECT IFNULL(SUM(subtotal), 0.0) FROM trabajo_externo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo = 2)) AS total_trabajo_plana_subtotal,

(SELECT IFNULL(SUM(monto), 0.0) FROM trabajo_externo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo != 2)) AS total_trabajo_tracto,

(SELECT IFNULL(SUM(iva), 0.0) FROM trabajo_externo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo != 2)) AS total_trabajo_tracto_iva,

(SELECT IFNULL(SUM(subtotal), 0.0) FROM trabajo_externo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo != 2)) AS total_trabajo_tracto_subtotal,

((SELECT IFNULL(SUM(total), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_operador)) + (SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_operador))) AS total_operador,

(SELECT IFNULL(SUM(iva), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_operador)) AS total_operador_iva,

IFNULL((SELECT SUM(IFNULL(costo, 0.0) / (SELECT 100.0 + (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion)) * (SELECT (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion))) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_operador)), 0.0) AS total_operador_salidas_iva,

((SELECT IFNULL(SUM(subtotal), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_operador)) + (SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_operador))) AS total_operador_subtotal,

((SELECT IFNULL(SUM(total), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_especial)) + (SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_especial))) AS total_especial,

(SELECT IFNULL(SUM(iva), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_especial)) AS total_especial_iva,

IFNULL((SELECT SUM(IFNULL(costo, 0.0) / (SELECT 100.0 + (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion)) * (SELECT (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion))) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_especial)), 0.0) AS total_especial_salidas_iva,

((SELECT IFNULL(SUM(subtotal), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_especial)) + (SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_especial))) AS total_especial_subtotal,

((SELECT IFNULL(SUM(total), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_bodega)) + (SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_bodega))) AS total_bodega,

(SELECT IFNULL(SUM(iva), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_bodega)) AS total_bodega_iva,

IFNULL((SELECT SUM(IFNULL(costo, 0.0) / (SELECT 100.0 + (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion)) * (SELECT (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_almacen.clave_refaccion))) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_bodega)), 0.0) AS total_bodega_salidas_iva,

((SELECT IFNULL(SUM(subtotal), 0.0) FROM cargo_directo WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_bodega)) + (SELECT IFNULL(SUM(costo), 0.0) FROM salida_almacen WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_bodega))) AS total_bodega_subtotal

FROM dual;