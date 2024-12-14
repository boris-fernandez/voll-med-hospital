package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.validaciones.cancelamiento.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.reserva.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservarDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoDeConsulta> validadorCancelamiento;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos){

        if(!pacienteRepository.existsById(datos.idPaciente())){
          throw new ValidacionException("No existe un paciente con el id informado");
        }
        if(datos.idMedico()!= null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe un medico con el id informado");
        }

        //validaciones
        validadores.forEach(v ->v.validar(datos));

        Medico medico = elegirMedico(datos);
        if(medico == null){
            throw new ValidacionException("No existe un medico disponible en ese horario");
        }
        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();

        Consulta consulta = new Consulta(null, medico, paciente, datos.fecha(), null);
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    private Medico elegirMedico(DatosReservaConsulta datos) {
        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null){
            throw new ValidacionException("Es necesario elegir una especialidad cuando se elije un médico");
        }

        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelarReserva(DatosCancelarConsulta datos) {
        if (datos.idConsulta() != null &&  !consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionException("No existe una consulta con el id informado");
        }
        validadorCancelamiento.forEach(v -> v.validar(datos));
        Consulta consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelarConsulta(datos.motivoCancelacion());
    }
}
