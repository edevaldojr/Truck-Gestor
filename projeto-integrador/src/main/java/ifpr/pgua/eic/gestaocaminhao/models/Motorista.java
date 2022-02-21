package ifpr.pgua.eic.gestaocaminhao.models;

import java.io.Serializable;

public class Motorista extends Pessoa implements Serializable {

    private String cnh;

    public Motorista(int cpf, String cnh, String nome, String telefone, String endereco) {
        super(cpf, nome, telefone, endereco);
        this.cnh = cnh;

    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    @Override
    public String toString() {
        return "Motorista [CNH =" + cnh + "]";
    }

}
