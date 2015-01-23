SELECT clave_refaccion, nombre, punto_reorden, maximo, minimo, status, 
(SELECT nombre FROM familia_refaccion WHERE familia_refaccion.id_familia_refaccion = refaccion.id_familia_refaccion) AS familia, 
(SELECT nombre FROM grupo_refaccion WHERE grupo_refaccion.id_grupo_refaccion = (SELECT id_grupo_refaccion FROM familia_refaccion WHERE familia_refaccion.id_familia_refaccion = refaccion.id_familia_refaccion)) AS grupo, 
(SELECT 
(SELECT SUM(IFNULL(cantidad,0.0)) FROM entrada_almacen WHERE entrada_almacen.clave_refaccion = refaccion.clave_refaccion AND status = TRUE) - 
(SELECT SUM(IFNULL(cantidad,0.0)) FROM salida_almacen WHERE salida_almacen.clave_refaccion = refaccion.clave_refaccion AND status = TRUE) 
FROM dual) AS existencia, 
(SELECT 
((SELECT SUM(IFNULL(monto,0.0)) FROM entrada_almacen WHERE status = TRUE AND clave_refaccion = refaccion.clave_refaccion) - 
(SELECT SUM(IFNULL(costo,0.0) * IFNULL(cantidad,0.0)) FROM salida_almacen WHERE status = TRUE AND clave_refaccion = refaccion.clave_refaccion)) 
/ 
(SELECT 
(SELECT SUM(IFNULL(cantidad,1.0)) FROM entrada_almacen WHERE status = TRUE AND clave_refaccion = refaccion.clave_refaccion) - 
(SELECT SUM(IFNULL(cantidad,0.0)) FROM salida_almacen WHERE status = TRUE AND clave_refaccion = refaccion.clave_refaccion) 
FROM dual) 
AS precio FROM dual) AS precio 
FROM refaccion WHERE status = TRUE;
