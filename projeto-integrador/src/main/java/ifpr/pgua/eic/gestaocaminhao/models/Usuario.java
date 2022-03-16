package ifpr.pgua.eic.gestaocaminhao.models;

import java.util.List;

public class Usuario {

    private String cpf;
    private String nome;
    private int endereco;
    private String email;
    private String senha;
    private String telefone;
    private String cnh;
    private boolean gestor;
    private List<Viagem> viagens;

    public Usuario(String cpf, String nome, int endereco, String telefone, String email, String senha,
            String cnh, boolean gestor) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cnh = cnh;
        this.gestor = gestor;
    }
    public Usuario(String cpf, String nome, int endereco, String telefone, String email, String senha,
            String cnh, boolean gestor, List<Viagem> viagens) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cnh = cnh;
        this.gestor = gestor;
        this.viagens = viagens;
    }

    public boolean isGestor() {
        return gestor;
    }

    public void setGestor(boolean gestor) {
        this.gestor = gestor;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getEndereco() {
        return endereco;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }

}
