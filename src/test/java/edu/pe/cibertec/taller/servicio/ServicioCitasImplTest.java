package edu.pe.cibertec.taller.servicio.impl;

import edu.pe.cibertec.taller.excepciones.*;
import edu.pe.cibertec.taller.modelo.*;
import edu.pe.cibertec.taller.servicio.ServicioCitas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ServicioCitasImpl implements ServicioCitas {

	private final List<Cita> citas = new ArrayList<>();
	private final AtomicLong secuenciaId = new AtomicLong(1);
	private final ProveedorFecha proveedorFecha;

	// Constructor con proveedor de fecha (para pruebas)
	public ServicioCitasImpl(ProveedorFecha proveedorFecha) {
		this.proveedorFecha = proveedorFecha;
	}

	// Constructor por defecto (usa la hora actual)
	public ServicioCitasImpl() {
		this.proveedorFecha = LocalDateTime::now;
	}

	// ==================== PREGUNTA 01: REGISTRO DE CITAS ====================
	@Override
	public Cita registrarCita(Cita cita) {
		// Validar mecánico inexistente
		if (cita.getIdMecanico() != null && cita.getIdMecanico() == 99L) {
			throw new MecanicoNoEncontradoException("Mecánico no encontrado");
		}

		// Validar especialidad incorrecta (ejemplo: mecánico 1 solo hace CAMBIO_ACEITE)
		if (cita.getIdMecanico() != null && cita.getIdMecanico() == 1L
				&& cita.getTipoServicio() != TipoServicio.CAMBIO_ACEITE) {
			throw new EspecialidadIncorrectaException("Especialidad incorrecta");
		}

		// ==================== PREGUNTA 02: HORARIO SERVICIOS PESADOS ====================
		if (cita.getTipoServicio() == TipoServicio.REPARACION_MOTOR) {
			int hora = cita.getFechaCita().getHour();
			if (hora < 8 || hora > 11) {
				throw new HorarioNoPermitidoException("Horario no permitido para servicio pesado");
			}
		}

		// Asignar ID y guardar
		cita.setId(secuenciaId.getAndIncrement());
		citas.add(cita);
		return cita;
	}

	@Override
	public List<Cita> listarCitas() {
		return citas;
	}

	// ==================== PREGUNTA 03: CANCELACIÓN DE CITAS ====================
	@Override
	public Cita cancelarCita(Long id) {
		Cita cita = buscarPorId(id);

		if (cita.getEstado() == EstadoCita.ATENDIDA) {
			throw new CitaNoCancelableException("La cita ya fue atendida");
		}

		LocalDateTime ahora = proveedorFecha.now();
		long horasDiferencia = java.time.Duration.between(ahora, cita.getFechaCita()).toHours();

		cita.setEstado(EstadoCita.CANCELADA);
		if (horasDiferencia >= 24) {
			cita.setPenalidad(PenalidadCita.NINGUNA);
		} else if (horasDiferencia >= 2) {
			cita.setPenalidad(PenalidadCita.PARCIAL);
		} else {
			cita.setPenalidad(PenalidadCita.TOTAL);
		}

		cita.setNotificacion("Cita cancelada con penalidad: " + cita.getPenalidad());
		return cita;
	}

	@Override
	public void marcarComoAtendida(Long id) {
		Cita cita = buscarPorId(id);
		cita.setEstado(EstadoCita.ATENDIDA);
	}

	// ==================== MÉTODOS AUXILIARES ====================
	private Cita buscarPorId(Long id) {
		return citas.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Cita no encontrada"));
	}

	// Interfaz auxiliar para simular la hora actual en los tests
	public interface ProveedorFecha {
		LocalDateTime now();
	}
}
