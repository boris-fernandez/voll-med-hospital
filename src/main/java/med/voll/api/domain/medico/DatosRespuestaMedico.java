package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String mombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion) {
}
