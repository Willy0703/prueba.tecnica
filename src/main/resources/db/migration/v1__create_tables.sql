CREATE TABLE persona (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(128) NOT NULL,
  apellido_paterno VARCHAR(128) NOT NULL,
  apellido_materno VARCHAR(128) NOT NULL,
  fecha_nacimiento DATE default NULL,
  sexo VARCHAR(128) NOT NULL,
  correo VARCHAR(128) NOT NULL,
  telefono VARCHAR(10) NOT NULL,
  create_date DATETIME NOT NULL,
  update_date DATETIME default NULL,
  PRIMARY KEY (id)
);


