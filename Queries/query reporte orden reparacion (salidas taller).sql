SELECT id_salida_taller AS numero_salida, costo, status, cantidad, fecha_registro, clave_refaccion, 
(SELECT nombre FROM refaccion WHERE clave_refaccion = salida_taller.clave_refaccion) AS nombre_refaccion, numero_usuario, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = salida_taller.numero_usuario) AS nombre_usuario, 
numero_orden, tipo, clave AS bodega FROM salida_taller WHERE numero_orden = $P{NUMERO_ORDEN} 
AND status = TRUE;