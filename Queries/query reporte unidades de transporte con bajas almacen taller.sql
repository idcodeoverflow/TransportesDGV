SELECT clave, no_economico, vin, placas, modelo, color, modelo_motor, 
no_serie_motor, (SELECT nombre FROM marca_motor WHERE 
marca_motor.id_marca_motor = unidad_transporte.id_marca_motor) 
AS marca_motor, cpl, fecha_inicio, IFNULL(fecha_fin,"ACTIVO") AS 
fecha_fin, (SELECT nombre FROM tipo_unidad WHERE 
tipo_unidad.id_tipo = unidad_transporte.id_tipo) AS tipo, 
(SELECT nombre FROM marca_unidad WHERE marca_unidad.id_marca = 
unidad_transporte.id_marca) AS marca FROM unidad_transporte 
ORDER BY clave;