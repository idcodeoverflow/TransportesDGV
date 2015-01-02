'Este es el usuario que se debe dar de alta al momento de instalar el sistema, y que será el superusuario'
INSERT INTO usuario(numero_usuario, nombre, apellidos, passwd, fecha_ingreso, fecha_egreso, privilegio, status) VALUES(NULL, 'Super', 'Administrador', PASSWORD('Remex2013'), NOW(), NULL, 1,TRUE);
