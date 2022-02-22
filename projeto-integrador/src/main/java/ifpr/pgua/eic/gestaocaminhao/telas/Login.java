package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Login {

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfSenha;

    @FXML
    private Button btLogin;

    @FXML
    private Button btCadastrar;

    @FXML
    private Pane rootPane;

    private AutenticacaoServico autenticacaoServico;
    private Home homeControler;

    public Login(AutenticacaoServico autenticacaoServico, Home homeControler) {
        this.autenticacaoServico = autenticacaoServico;
        this.homeControler = homeControler;

    }

    @FXML
    public void logar() {
        String usuario = tfCpf.getText();
        String senha = tfSenha.getText();
        int cpfLogin = Integer.parseInt(usuario);
        try {
            autenticacaoServico.logar(cpfLogin, senha);
            if (autenticacaoServico.estaLogado()) {
                homeControler.atualizaTela();
            }
        } catch (Exception e) {

        }
    }

    @FXML
    public void cadastrar() {
        homeControler.carregaTela("cadastro");
    }

}
