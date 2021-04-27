package com.mainarleao.projetofinal;

import java.io.Serializable;

public class Data implements Serializable {

    private String data;
    private String entrada;
    private String saidaIntervalo;
    private String voltaIntervalo;
    private String saida;

    public Data() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSaidaIntervalo() {
        return saidaIntervalo;
    }

    public void setSaidaIntervalo(String saidaIntervalo) {
        this.saidaIntervalo = saidaIntervalo;
    }

    public String getVoltaIntervalo() {
        return voltaIntervalo;
    }

    public void setVoltaIntervalo(String voltaIntervalo) {
        this.voltaIntervalo = voltaIntervalo;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    @Override
    public String toString() {
        return "\nData: " + data +
                "\nHorario de Entrada: " + entrada +
                "\nSaida para Almoço: " + saidaIntervalo +
                "\nVolta do Almoço: " + voltaIntervalo +
                "\nSaida: " + saida;
    }
}
