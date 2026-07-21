package edu.pe.cibertec.taller.excepciones;

public class CitaNoCancelableException extends RuntimeException {
    public CitaNoCancelableException(String mensaje) {
        super(mensaje);
    }
}