package edu.pe.cibertec.taller.bdd;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import edu.pe.cibertec.taller.modelo.Cita;
import edu.pe.cibertec.taller.modelo.EstadoCita;
import edu.pe.cibertec.taller.modelo.TipoServicio;
import edu.pe.cibertec.taller.modelo.DuracionServicio;
import edu.pe.cibertec.taller.servicio.ServicioCitas;
import edu.pe.cibertec.taller.servicio.impl.ServicioCitasImpl;
import edu.pe.cibertec.taller.repositorio.RepositorioCitas;
import edu.pe.cibertec.taller.repositorio.RepositorioMecanicos;
import edu.pe.cibertec.taller.util.ProveedorFechaHora;
import edu.pe.cibertec.taller.util.ServicioNotificaciones;
import io.cucumber.java.Before;
import io.cucumber.java.es.*;

public class GestionCitasSteps {

	private ServicioCitas servicioCitas;
	private Cita citaZafiro;
	private Exception errorZafiro;

	private static final String PLACA = "GAR-234";
	private static final int DIA = 17;

	@Before
	public void inicializar() {
		RepositorioMecanicos repositorioMecanicos = mock(RepositorioMecanicos.class);
		RepositorioCitas repositorioCitas = mock(RepositorioCitas.class);
		ProveedorFechaHora proveedorFechaHora = mock(ProveedorFechaHora.class);
		ServicioNotificaciones servicioNotificaciones = mock(ServicioNotificaciones.class);
		servicioCitas = new ServicioCitasImpl(repositorioMecanicos, repositorioCitas,
				proveedorFechaHora, servicioNotificaciones);
	}

	// === Escenario 1: Registro exitoso ===
	@Dado("existe un mecánico disponible para MANTENIMIENTO_LIGERO")
	public void mecanicoDisponible() {
		citaZafiro = new Cita();
		citaZafiro.setTipoServicio(TipoServicio.MANTENIMIENTO_LIGERO);
	}

	@Dado("la placa del vehículo es {string}")
	public void asignarPlaca(String placa) {
		citaZafiro.setPlaca(placa);
	}

	@Dado("la cita es programada para el DÍA a las {int}:{int}")
	public void asignarFecha(int hora, int minuto) {
		citaZafiro.setFechaCita(LocalDateTime.of(2026, 9, DIA, hora, minuto));
		citaZafiro.setDuracion(DuracionServicio.CORTA);
	}

	@Cuando("se registra la cita")
	public void registrarCita() {
		try {
			citaZafiro = servicioCitas.registrarCita(citaZafiro);
		} catch (Exception e) {
			errorZafiro = e;
		}
	}

	@Entonces("la cita queda en estado PROGRAMADA")
	public void verificarEstadoProgramado() {
		assertEquals(EstadoCita.PROGRAMADA, citaZafiro.getEstado());
	}

	@Entonces("se envía la notificación de confirmación")
	public void verificarNotificacion() {
		assertTrue(citaZafiro.getNotificacion().toLowerCase().contains("confirmada"));
	}

	// === Escenario 2: Conflicto de horario ===
	@Dado("el mecánico ya tiene una cita a las {int}:{int}")
	public void mecanicoOcupado(int hora, int minuto) {
		Cita citaExistente = new Cita(PLACA, TipoServicio.MANTENIMIENTO_LIGERO,
				LocalDateTime.of(2026, 9, DIA, hora, minuto), DuracionServicio.CORTA);
		servicioCitas.registrarCita(citaExistente);
	}

	@Dado("se intenta registrar una nueva cita a las {int}:{int}")
	public void nuevaCitaConflicto(int hora, int minuto) {
		citaZafiro = new Cita(PLACA, TipoServicio.MANTENIMIENTO_LIGERO,
				LocalDateTime.of(2026, 9, DIA, hora, minuto), DuracionServicio.CORTA);
	}

	@Cuando("se procesa el registro")
	public void procesarRegistro() {
		try {
			citaZafiro = servicioCitas.registrarCita(citaZafiro);
		} catch (Exception e) {
			errorZafiro = e;
		}
	}

	@Entonces("el sistema rechaza la cita por conflicto de horario")
	public void verificarRechazoConflicto() {
		assertNotNull(errorZafiro);
		assertTrue(errorZafiro.getMessage().toLowerCase().contains("horario ocupado"));
	}

	// === Escenario 3: Horario ocupado ===
	@Entonces("el sistema rechaza la cita por horario ocupado")
	public void verificarRechazoOcupado() {
		assertNotNull(errorZafiro);
		assertTrue(errorZafiro.getMessage().toLowerCase().contains("ya tiene cita"));
	}
}
