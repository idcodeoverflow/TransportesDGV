SELECT id_salida_especial AS numero_salida, costo, status, cantidad, fecha_registro, clave_refaccion, 
(SELECT nombre FROM refaccion WHERE refaccion.clave_refaccion = salida_especial.clave_refaccion) AS nombre_refaccion, numero_usuario, 
(SELECT CONCAT(nombre," ", apellidos) FROM usuario WHERE usuario.numero_usuario = salida_especial.numero_usuario) AS nombre_usuario, 
numero_orden, tipo, nombre_beneficiario AS beneficiario FROM salida_especial WHERE numero_orden = $P{NUMERO_ORDEN} AND status = TRUE;
