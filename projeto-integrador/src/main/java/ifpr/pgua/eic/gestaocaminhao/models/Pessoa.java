package ifpr.pgua.eic.gestaocaminhao.models;

public class Pessoa {

    private int cpf;
    private String nome;
    private String endereco;
    private String telefone;

    public Pessoa(int cpf, String nome, String telefone, String endereco) {
        this.setCpf(cpf);
        this.nome = nome;
        this.setTelefone(telefone);
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String toString() {
        return this.nome;
    }

}
