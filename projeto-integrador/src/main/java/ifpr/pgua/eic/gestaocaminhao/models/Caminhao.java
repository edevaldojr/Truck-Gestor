package ifpr.pgua.eic.gestaocaminhao.models;


import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoCaminhao;

public class Caminhao {

    private int id;
    private String placa;
    private String cor;
    private int ano;
    private String marca;
    private String modelo;
    private int tipo;

    public Caminhao(int id, String placa, String cor, int ano, String marca, String modelo, TipoCaminhao tipo) {
        this.id = id;
        this.placa = placa;
        this.cor = cor;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo.getCod();
    }

    public Caminhao(String placa, String cor, int ano, String marca, String modelo, TipoCaminhao tipo) {
        this.placa = placa;
        this.cor = cor;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo.getCod();
    }

    public Caminhao() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public TipoCaminhao getTipo() {
        return TipoCaminhao.toEnum(tipo);
    }

    public void setTipo(TipoCaminhao tipo) {
        this.tipo = tipo.getCod();
    }

}
