package ifpr.pgua.eic.gestaocaminhao.models;

import java.io.Serializable;

public class Gestor extends Pessoa implements Serializable {

    private String tipo;

    public Gestor(int cpf, String nome, String telefone, String endereco, String tipo) {
        super(cpf, nome, telefone, endereco);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Gestor [tipo=" + tipo + "]";
    }

}
