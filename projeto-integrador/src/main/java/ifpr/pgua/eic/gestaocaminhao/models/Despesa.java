package ifpr.pgua.eic.gestaocaminhao.models;

import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoDespesa;

public class Despesa {
    
    private int id;
    private TipoDespesa tipoDespesa;
    private String nome;
    private double valorDespesaAutopeca;
    private double valorDespesaCombustivel;

    
    public Despesa(TipoDespesa tipoDespesa, String nome, double valorDespesaAutopeca, double valorDespesaCombustivel) {
        this.tipoDespesa = tipoDespesa;
        this.nome = nome;
        this.valorDespesaAutopeca = valorDespesaAutopeca;
        this.valorDespesaCombustivel = valorDespesaCombustivel;
    }


    public Despesa(int id, TipoDespesa tipoDespesa, String nome, double valorDespesaAutopeca,
            double valorDespesaCombustivel) {
        this.id = id;
        this.tipoDespesa = tipoDespesa;
        this.nome = nome;
        this.valorDespesaAutopeca = valorDespesaAutopeca;
        this.valorDespesaCombustivel = valorDespesaCombustivel;
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
    public double getValorDespesaAutopeca() {
        return valorDespesaAutopeca;
    }
    public void setValorDespesaAutopeca(double valorDespesaAutopeca) {
        this.valorDespesaAutopeca = valorDespesaAutopeca;
    }
    public double getValorDespesaCombustivel() {
        return valorDespesaCombustivel;
    }
    public void setValorDespesaCombustivel(double valorDespesaCombustivel) {
        this.valorDespesaCombustivel = valorDespesaCombustivel;
    }

}
