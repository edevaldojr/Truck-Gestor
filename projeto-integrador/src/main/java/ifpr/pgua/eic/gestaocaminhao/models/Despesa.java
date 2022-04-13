package ifpr.pgua.eic.gestaocaminhao.models;

import java.time.LocalDate;

import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoDespesa;

public class Despesa {

    private int id;
    private TipoDespesa tipoDespesa;
    private String nome;
    private double valorDespesa;
    private LocalDate dataDespesa;
    private Caminhao caminhaoDespesa;

    public Despesa(TipoDespesa tipoDespesa, String nome, double valorDespesa, LocalDate dataDespesa,
            Caminhao caminhaoDespesa) {
        this.tipoDespesa = tipoDespesa;
        this.nome = nome;
        this.valorDespesa = valorDespesa;
        this.dataDespesa = dataDespesa;
        this.caminhaoDespesa = caminhaoDespesa;
    }

    public Despesa(int id, TipoDespesa tipoDespesa, String nome, double valorDespesa, LocalDate dataDespesa,
            Caminhao caminhaoDespesa) {
        this.id = id;
        this.tipoDespesa = tipoDespesa;
        this.nome = nome;
        this.valorDespesa = valorDespesa;
        this.dataDespesa = dataDespesa;
        this.caminhaoDespesa = caminhaoDespesa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(TipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorDespesa() {
        return valorDespesa;
    }

    public String getValorDespesaToString() {
        String valor = String.format("R$%.2f", valorDespesa);
        return valor;
    }

    public void setValorDespesa(double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public LocalDate getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(LocalDate dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public Caminhao getCaminhaoDespesa() {
        return caminhaoDespesa;
    }

    public void setCaminhaoDespesa(Caminhao caminhaoDespesa) {
        this.caminhaoDespesa = caminhaoDespesa;
    }

}
