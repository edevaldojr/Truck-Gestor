package ifpr.pgua.eic.gestaocaminhao.models;

import java.util.List;

public class Estado {
    
    private int id;
    private String nome;
    
    private List<Cidade> cidades;

    public Estado(int id, String nome, List<Cidade> cidades) {
        this.id = id;
        this.nome = nome;
        this.cidades = cidades;
    }

    public Estado(){
        
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

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
