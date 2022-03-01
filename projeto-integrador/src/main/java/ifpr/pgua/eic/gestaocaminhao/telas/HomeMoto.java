package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;

public class HomeMoto {

    private AutenticacaoServico autenticacaoServico;
    private Login login;

    public HomeMoto(Login login, AutenticacaoServico autenticacaoServico) {
        System.out.println("Home Motorista");
        this.autenticacaoServico = autenticacaoServico;
        this.login = login;
    }

    @FXML
    public void logout() {
        autenticacaoServico.logout();
        login.atualizaTela();
    }

}
