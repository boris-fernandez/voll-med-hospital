create table pacientes(
    id int not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    telefono varchar(6) not null unique,
    documento varchar(100) not null,
    activo varchar(100) not null,
    calle varchar(100) not null,
    distrito varchar(100) not null,
    complemento varchar(100),
    numero varchar(20),
    ciudad varchar(100) not null,
    primary key(id)
);