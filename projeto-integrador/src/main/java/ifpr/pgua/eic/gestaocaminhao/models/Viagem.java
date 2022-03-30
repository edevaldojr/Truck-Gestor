package ifpr.pgua.eic.gestaocaminhao.models;

import java.time.LocalDate;

public class Viagem {

    private int id;
    private double peso;
    private LocalDate data_da_baixa;
    private double valor;
    private Empresa empresa_origem;
    private Empresa empresa_destino;
    private String carga;
    private Usuario caminhoneiro;
    private double valor_total;

    public Viagem(int id, double peso, LocalDate data_da_baixa, double valor, Empresa empresa_origem,
            Empresa empresa_destino, String carga, Usuario caminhoneiro, double valor_total) {
        this.id = id;
        this.peso = peso;
        this.data_da_baixa = data_da_baixa;
        this.valor = valor;
        this.empresa_origem = empresa_origem;
        this.empresa_destino = empresa_destino;
        this.carga = carga;
        this.caminhoneiro = caminhoneiro;
        this.valor_total = valor_total;
    }

    public Viagem(double peso, LocalDate data_da_baixa, double valor, Empresa empresa_origem,
            Empresa empresa_destino, String carga, Usuario caminhoneiro) {
        this.peso = peso;
        this.data_da_baixa = data_da_baixa;
        this.valor = valor;
        this.empresa_origem = empresa_origem;
        this.empresa_destino = empresa_destino;
        this.carga = carga;
        this.caminhoneiro = caminhoneiro;
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

    public Empresa getEmpresa_origem() {
        return empresa_origem;
    }

    public void setEmpresa_origem(Empresa empresa_origem) {
        this.empresa_origem = empresa_origem;
    }

    public Empresa getEmpresa_destino() {
        return empresa_destino;
    }

    public void setEmpresa_destino(Empresa empresa_destino) {
        this.empresa_destino = empresa_destino;
    }

    public String getcarga() {
        return carga;
    }

    public void setcarga(String carga) {
        this.carga = carga;
    }

    public Usuario getCaminhoneiro() {
        return caminhoneiro;
    }

    public void setCaminhoneiro(Usuario caminhoneiro) {
        this.caminhoneiro = caminhoneiro;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public double getValor_total() {
        return valor_total;
    }

    public String getValor_total_ToString() {
        String total = String.format("R$%.2f", valor_total);
        return total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }
}
