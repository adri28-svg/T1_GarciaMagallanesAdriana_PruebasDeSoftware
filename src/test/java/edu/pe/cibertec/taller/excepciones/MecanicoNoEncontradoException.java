package edu.pe.cibertec.taller.excepciones;

public class MecanicoNoEncontradoException extends RuntimeException {
    public MecanicoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
