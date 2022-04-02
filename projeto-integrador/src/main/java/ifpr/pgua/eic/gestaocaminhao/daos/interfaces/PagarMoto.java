package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public class PagarMoto {

    private Usuario motorista;
    private int quantidade;
    private double valorPagar;

    public PagarMoto(Usuario motorista, int quantidade, double valorPagar) {
        this.motorista = motorista;
        this.quantidade = quantidade;
        this.valorPagar = valorPagar;
    }

    public Usuario getMotorista() {
        return motorista;
    }

    public void setMotorista(Usuario motorista) {
        this.motorista = motorista;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }
}
