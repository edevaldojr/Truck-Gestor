package ifpr.pgua.eic.gestaocaminhao.models;

import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoEmpresa;

public class Empresa {
    
    private int id;
    private String nome;
    private int endereco;
    private int tipo; 


    public Empresa(int id, String nome, int endereco, TipoEmpresa tipo) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo.getCod();
    }

    public Empresa(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEndereco() {
        return endereco;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }

    public TipoEmpresa getTipo() {
        return TipoEmpresa.toEnum(tipo);
    }

    public void setTipo(TipoEmpresa tipo) {
        this.tipo = tipo.getCod();
    }
}
