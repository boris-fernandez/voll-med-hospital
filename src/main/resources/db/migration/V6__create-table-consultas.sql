create table consultas (
    id bigint not null auto_increment,
    medico_id int not null,
    paciente_id int not null,
    fecha datetime not null,
    primary key (id),
    constraint fk_consultas_medico_id foreign key (medico_id) references medicos (id),
    constraint fk_consultas_paciente_id foreign key (paciente_id) references pacientes(id)
);