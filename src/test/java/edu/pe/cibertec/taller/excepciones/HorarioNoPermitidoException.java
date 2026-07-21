package edu.pe.cibertec.taller.excepciones;

public class HorarioNoPermitidoException extends RuntimeException {
    public HorarioNoPermitidoException(String mensaje) {
        super(mensaje);
    }
}