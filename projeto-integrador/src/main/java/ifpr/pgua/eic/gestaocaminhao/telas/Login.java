package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Login {

    private RepositorioUsuarios repositorioUsuarios;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfSenha;

    @FXML
    private Button btLogin;

    @FXML
    private Button btCadastrar;

    @FXML
    private AnchorPane painelAnchorPane;

    public Login(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @FXML
    private void loadCadastrar() {
        painelAnchorPane.getChildren().clear();
        painelAnchorPane.getChildren()
                .add(App.loadTela("fxml/cadastros.fxml", (o) -> new CadastroUsuario(repositorioUsuarios)));
    }

}
