package ifpr.pgua.eic.gestaocaminhao.models;

public class Endereco {

    private int id;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;

    private int cidade;
    private Viagem viagem;

    public Endereco(int id, String numero, String complemento, String bairro, String cep,
            int cidade, Viagem viagem) {
        this.id = id;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.viagem = viagem;
    }

    public Endereco() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getCidade() {
        return cidade;
    }

    public void setCidade(int cidade) {
        this.cidade = cidade;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }


}
