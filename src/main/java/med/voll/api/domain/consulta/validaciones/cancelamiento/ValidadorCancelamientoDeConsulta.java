package med.voll.api.domain.consulta.validaciones.cancelamiento;

import med.voll.api.domain.consulta.DatosCancelarConsulta;

public interface ValidadorCancelamientoDeConsulta {
    void validar(DatosCancelarConsulta datos);
}
