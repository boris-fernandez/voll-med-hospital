package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar (DatosReservaConsulta datos){
        var medicoTieneOtraConsultaEnElMismoHorario = consultaRepository.existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(datos.idMedico(), datos.fecha());
        if (medicoTieneOtraConsultaEnElMismoHorario){
            throw new ValidacionException("Medico ya tiene otra consulta en esa misma fecha y hora");
        }
    }
}
