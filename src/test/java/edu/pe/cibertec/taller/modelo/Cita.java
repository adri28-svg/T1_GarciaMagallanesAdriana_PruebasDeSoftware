package edu.pe.cibertec.taller.modelo;

import java.time.LocalDateTime;

public class Cita {
    private Long id;
    private String placa;
    private TipoServicio tipoServicio;
    private LocalDateTime fechaCita;
    private int duracion;
    private EstadoCita estado;
    private Long idMecanico;
    private String notificacion;
    private PenalidadCita penalidad;

    public Cita() {}

    public Cita(String placa, TipoServicio tipoServicio, LocalDateTime fechaCita, DuracionServicio duracion) {
        this.placa = placa;
        this.tipoServicio = tipoServicio;
        this.fechaCita = fechaCita;
        this.duracion = duracion.getMinutos();
        this.estado = EstadoCita.PROGRAMADA;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }

    public LocalDateTime getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDateTime fechaCita) { this.fechaCita = fechaCita; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public Long getIdMecanico() { return idMecanico; }
    public void setIdMecanico(Long idMecanico) { this.idMecanico = idMecanico; }

    public String getNotificacion() { return notificacion; }
    public void setNotificacion(String notificacion) { this.notificacion = notificacion; }

    public PenalidadCita getPenalidad() { return penalidad; }
    public void setPenalidad(PenalidadCita penalidad) { this.penalidad = penalidad; }
}

