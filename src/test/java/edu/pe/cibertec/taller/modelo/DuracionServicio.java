package edu.pe.cibertec.taller.modelo;

public enum DuracionServicio {
    CORTA(30), LARGA(120);

    private final int minutos;

    DuracionServicio(int minutos) {
        this.minutos = minutos;
    }

    public int getMinutos() {
        return minutos;
    }
}
