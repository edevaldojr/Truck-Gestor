package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Home {

    @FXML
    private Button btHome;

    @FXML
    private Button btCadastros;

    @FXML
    private Button btEntradas;

    @FXML
    private Button btDespesas;

    @FXML
    private Button btRelatorios;

    @FXML
    private Button btLogout;

    @FXML
    private RepositorioUsuarios repositorioUsuarios;

    public Home(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

}
