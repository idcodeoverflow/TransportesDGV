SELECT id_salida_unidad AS numero_salida, costo, status, cantidad, fecha_registro, clave_refaccion, (SELECT nombre FROM refaccion WHERE 
refaccion.clave_refaccion = salida_unidad.clave_refaccion) AS nombre_refaccion, numero_usuario, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = salida_unidad.numero_usuario) AS nombre_usuario, 
numero_orden, tipo, clave AS unidad FROM salida_unidad WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE;
