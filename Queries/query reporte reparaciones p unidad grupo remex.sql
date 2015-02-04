SELECT clave, 
(SELECT nombre FROM tipo_unidad WHERE tipo_unidad.id_tipo = unidad_transporte.id_tipo) AS tipo_transporte,

modelo,

(SELECT nombre FROM marca_unidad WHERE marca_unidad.id_marca = unidad_transporte.id_marca) AS marca,

(SELECT IFNULL(SUM(subtotal), 0.0) FROM trabajo_externo WHERE status = TRUE AND trabajo_externo.clave = unidad_transporte.clave 
AND fecha_registro >= $P{INICIO} AND fecha_registro <= $P{FIN}) AS subtotal_trabajos,

(SELECT IFNULL(SUM(iva), 0.0) FROM trabajo_externo WHERE status = TRUE AND trabajo_externo.clave = unidad_transporte.clave 
AND fecha_registro >= $P{INICIO} AND fecha_registro <= $P{FIN}) AS iva_trabajos,

(SELECT subtotal_trabajos + iva_trabajos FROM dual) AS total_trabajos,

(SELECT IFNULL(SUM(subtotal), 0.0) FROM cargo_unidad WHERE status = TRUE AND fecha_registro <= $P{FIN} AND fecha_registro >= $P{INICIO} 
AND cargo_unidad.clave = unidad_transporte.clave) AS subtotal_cargos,

(SELECT IFNULL(SUM(iva), 0.0) FROM cargo_unidad WHERE status = TRUE AND fecha_registro <= $P{FIN} AND fecha_registro >= $P{INICIO} 
AND cargo_unidad.clave = unidad_transporte.clave) AS iva_cargos,

(SELECT subtotal_cargos + iva_cargos FROM dual) AS total_cargos,

(IFNULL((SELECT SUM(IFNULL(costo, 0.0) / (SELECT 100.0 + (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE 
numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = salida_unidad.clave_refaccion)) * 
(SELECT (IFNULL(iva / subtotal, 0.0) * 100.0) FROM entrada_almacen WHERE numero_entrada = (SELECT MAX(numero_entrada) FROM entrada_almacen 
WHERE entrada_almacen.clave_refaccion = salida_unidad.clave_refaccion AND status = TRUE))) FROM salida_unidad WHERE fecha_registro <= $P{FIN} 
AND fecha_registro >= $P{INICIO} AND status = TRUE AND salida_unidad.clave = 
unidad_transporte.clave), 0.0)) AS iva_salidas,

(SELECT IFNULL(SUM(costo), 0.0) FROM salida_unidad WHERE fecha_registro <= $P{FIN} AND fecha_registro >= $P{INICIO} AND status = TRUE 
AND salida_unidad.clave = unidad_transporte.clave) AS total_salidas,

(SELECT total_salidas - iva_salidas FROM dual) AS subtotal_salidas,

(SELECT subtotal_trabajos + subtotal_cargos + subtotal_salidas FROM dual) AS subtotales,

(SELECT iva_trabajos + iva_cargos + iva_salidas FROM dual) AS ivas,

(SELECT total_trabajos + total_cargos + total_salidas FROM dual) AS totales

FROM unidad_transporte WHERE status = TRUE;