package ifpr.pgua.eic.gestaocaminhao.models;

public class Estado {

    private int id;
    private String nome;
    private String uf;

    public Estado(int id, String nome, String uf) {
        this.id = id;
        this.nome = nome;
    }

    public Estado() {

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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
