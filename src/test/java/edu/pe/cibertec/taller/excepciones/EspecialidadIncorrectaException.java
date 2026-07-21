package edu.pe.cibertec.taller.excepciones;

public class EspecialidadIncorrectaException extends RuntimeException {
    public EspecialidadIncorrectaException(String mensaje) {
        super(mensaje);
    }
}
