SELECT (SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_directo WHERE numero_orden = 1 AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != 2))) AS cargo_directo_tracto, (SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_directo WHERE numero_orden = 1 AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = 2))) AS cargo_directo_plana, (SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_almacen WHERE numero_orden = 1 AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo != 2))) AS salida_almacen_tracto, (SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_almacen WHERE numero_orden = 1 AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_unidad WHERE clave IN (SELECT clave FROM unidad_transporte WHERE id_tipo = 2))) AS salida_almacen_plana, (SELECT IFNULL(SUM(monto), 0.0) AS total_trabajos FROM trabajo_externo WHERE numero_orden = 1 AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo != 2)) AS total_trabajo_tracto, (SELECT IFNULL(SUM(monto), 0.0) AS total_trabajos FROM trabajo_externo WHERE numero_orden = 1 AND status = TRUE AND clave IN(SELECT clave FROM unidad_transporte WHERE id_tipo = 2)) AS total_trabajo_plana, ((SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_directo WHERE numero_orden = 1 AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_operador)) + (SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_almacen WHERE numero_orden = 1 AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_operador))) AS total_operador, ((SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_directo WHERE numero_orden = 1 AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_especial)) + (SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_almacen WHERE numero_orden = 1 AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_especial))) AS total_especial, ((SELECT IFNULL(SUM(total), 0.0) AS total_cargos FROM cargo_directo WHERE numero_orden = 1 AND status = TRUE AND numero_cargo_directo IN(SELECT numero_cargo_directo FROM cargo_bodega)) + (SELECT IFNULL(SUM(costo), 0.0) AS total_salidas FROM salida_almacen WHERE numero_orden = 1 AND status = TRUE AND numero_salida IN(SELECT numero_salida FROM salida_bodega))) AS total_bodega FROM dual;
