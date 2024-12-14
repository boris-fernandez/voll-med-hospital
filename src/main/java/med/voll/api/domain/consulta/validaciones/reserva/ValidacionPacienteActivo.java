package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionPacienteActivo implements ValidadorDeConsultas {

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DatosReservaConsulta datos){
        var pacienteEstadoActivo = pacienteRepository.findActivoById(datos.idPaciente());

        if(!pacienteEstadoActivo){
            throw new ValidacionException("Consulta no puede ser reservada con paciente exluido");
        }

    }
}
