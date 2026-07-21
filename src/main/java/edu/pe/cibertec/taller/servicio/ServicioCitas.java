package edu.pe.cibertec.taller.servicio;

import edu.pe.cibertec.taller.modelo.Cita;
import edu.pe.cibertec.taller.modelo.Mecanico;
import edu.pe.cibertec.taller.modelo.ResultadoCancelacion;
import edu.pe.cibertec.taller.modelo.TipoServicio;
import java.time.LocalDateTime;
import java.util.Collection;

public interface ServicioCitas {

	Cita agendarCita(Long idMecanico, String placa, TipoServicio tipo, LocalDateTime fechaHoraInicio);

	ResultadoCancelacion cancelarCita(Long idCita);

	Mecanico buscarMecanicoDisponible(TipoServicio tipo, LocalDateTime fechaHoraInicio);

    Cita registrarCita(Cita citaZafiro);

	void marcarComoAtendida(Long id);

	Collection<Object> listarCitas();
}
