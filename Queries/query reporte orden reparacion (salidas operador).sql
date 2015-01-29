SELECT id_salida_operador AS numero_salida, costo, status, cantidad, fecha_registro, clave_refaccion, (SELECT nombre FROM refaccion 
WHERE refaccion.clave_refaccion = salida_operador.clave_refaccion) AS nombre_refaccion, numero_usuario, 
(SELECT CONCAT(nombre," ",apellidos) FROM usuario WHERE usuario.numero_usuario = salida_operador.numero_usuario) AS nombre_usuario, 
numero_orden, tipo, (SELECT CONCAT(numero_operador,"# ",nombre," ",apellidos) FROM operador WHERE operador.numero_operador = 
salida_operador.numero_operador) AS nombre_operador 
FROM salida_operador WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE;