package edu.pe.cibertec.taller.servicio;

import edu.pe.cibertec.taller.modelo.Cita;
import java.util.List;

public interface ServicioCitas {
    Cita registrarCita(Cita cita);
    List<Cita> listarCitas();
    Cita cancelarCita(Long id);
    void marcarComoAtendida(Long id);
}
