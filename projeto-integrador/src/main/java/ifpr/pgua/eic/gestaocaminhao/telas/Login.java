package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Login {

    @FXML
    private TextField tfCpf;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Button btLogin;

    @FXML
    private Button btCadastrar;

    @FXML
    private AnchorPane root;

    private AutenticacaoServico autenticacaoServico;
    private RepositorioUsuarios repositorioUsuarios;
    private Home homeControler;

    public Login(AutenticacaoServico autenticacaoServico, Home homeControler) {
        this.autenticacaoServico = autenticacaoServico;
        this.homeControler = homeControler;

    }

    public Login(AutenticacaoServico autenticacaoServico, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.autenticacaoServico = autenticacaoServico;

    }

    @FXML
    public void logar() {
        String cpf = tfCpf.getText();
        String senha = pfSenha.getText();
        try {
            autenticacaoServico.logar(cpf, senha);
            if (autenticacaoServico.estaLogado()) {
                homeControler.atualizaTela();
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @FXML
    public void cadastrar() {
        root.getChildren().clear();
        root.getChildren().add(
                App.loadTela("fxml/cadastro_users.fxml", a -> new CadastroUsuario(repositorioUsuarios)));
    }

}
