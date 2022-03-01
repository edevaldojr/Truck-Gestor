package ifpr.pgua.eic.gestaocaminhao.models;

public class Usuario {

    private String cpf;
    private String nome;
    private String endereco;
    private String cidade;
    private String email;
    private String senha;
    private String telefone;
    private String cnh;
    private boolean gestor;

    public Usuario(String cpf, String nome, String cidade, String endereco, String telefone, String email, String senha,
            String cnh, boolean gestor) {
        this.cpf = cpf;
        this.nome = nome;
        this.cidade = cidade;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cnh = cnh;
        this.gestor = gestor;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    @Override
    public String toString() {
        return "Usuario [cidade=" + cidade + ", cnh=" + cnh + ", cpf=" + cpf + ", email=" + email + ", endereco="
                + endereco + ", gestor=" + gestor + ", nome=" + nome + ", senha=" + senha + ", telefone=" + telefone
                + "]";
    }

}
