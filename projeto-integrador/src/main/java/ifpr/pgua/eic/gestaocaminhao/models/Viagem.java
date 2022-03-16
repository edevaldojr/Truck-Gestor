package ifpr.pgua.eic.gestaocaminhao.models;

import java.time.LocalDate;

public class Viagem {

    private int id;
    private double peso;
    private LocalDate data_da_baixa;
    private double valor;

    private int empresa_origem;
    private int empresa_destino;

    public Viagem(int id, double peso, LocalDate data_da_baixa, double valor, int empresa_origem,
            int empresa_destino) {
        this.id = id;
        this.peso = peso;
        this.data_da_baixa = data_da_baixa;
        this.valor = valor;
        this.empresa_origem = empresa_origem;
        this.empresa_destino = empresa_destino;
    }

    public Viagem(double peso, LocalDate data_da_baixa, double valor, int empresa_origem,
    int empresa_destino) {
        this.peso = peso;
        this.data_da_baixa = data_da_baixa;
        this.valor = valor;
        this.empresa_origem = empresa_origem;
        this.empresa_destino = empresa_destino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getData_da_baixa() {
        return data_da_baixa;
    }

    public void setData_da_baixa(LocalDate data_da_baixa) {
        this.data_da_baixa = data_da_baixa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getEmpresa_origem() {
        return empresa_origem;
    }

    public void setEmpresa_origem(int empresa_origem) {
        this.empresa_origem = empresa_origem;
    }

    public int getEmpresa_destino() {
        return empresa_destino;
    }

    public void setEmpresa_destino(int empresa_destino) {
        this.empresa_destino = empresa_destino;
    }
}
