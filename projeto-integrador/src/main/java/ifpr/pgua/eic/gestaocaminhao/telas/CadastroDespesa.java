package ifpr.pgua.eic.gestaocaminhao.telas;

import java.time.LocalDate;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoDespesa;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCidade;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioDespesas;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEmpresa;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEndereco;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEstado;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioViagens;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class CadastroDespesa {

    private AutenticacaoServico autenticacaoServico;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioEndereco repositorioEndereco;
    private RepositorioCidade repositorioCidade;
    private RepositorioEstado repositorioEstado;
    private RepositorioViagens repositorioViagens;
    private RepositorioEmpresa repositorioEmpresa;
    private RepositorioCaminhao repositorioCaminhao;
    private RepositorioDespesas repositorioDespesas;
    private Login login;

    @FXML
    private TextField tfTipoDespesa;

    @FXML
    private TextField tfPrecoDespesa;

    @FXML
    private TextField tfNomeDespesa;

    @FXML
    private DatePicker dpDataDespesa;

    @FXML
    private ComboBox<String> cbCaminhaoDespesa;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    @FXML
    private AnchorPane root;

    public CadastroDespesa(Login login, AutenticacaoServico autenticacaoServico,
            RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco,
            RepositorioEstado repositorioEstado,
            RepositorioCidade repositorioCidade, RepositorioEmpresa repositorioEmpresa,
            RepositorioViagens repositorioViagens, RepositorioDespesas repositorioDespesas) {
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioEndereco = repositorioEndereco;
        this.repositorioCaminhao = repositorioCaminhao;
        this.repositorioCidade = repositorioCidade;
        this.repositorioEstado = repositorioEstado;
        this.repositorioEmpresa = repositorioEmpresa;
        this.repositorioViagens = repositorioViagens;
        this.repositorioDespesas = repositorioDespesas;
        this.login = login;
    }

    public CadastroDespesa(Login login, AutenticacaoServico autenticacaoServico, RepositorioViagens repositorioViagens,
            RepositorioDespesas repositorioDespesas, RepositorioCaminhao repositorioCaminhao) {
        this.repositorioDespesas = repositorioDespesas;
        this.repositorioViagens = repositorioViagens;
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioCaminhao = repositorioCaminhao;
        this.login = login;
    }

    public void initialize() {
        try {
            cbCaminhaoDespesa.getItems().clear();
            cbCaminhaoDespesa.getItems().addAll(repositorioCaminhao.listarCaminhoesToString());
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void voltar() {
        if (autenticacaoServico.getLogado().isGestor()) {
            root.getChildren().clear();
            root.getChildren()
                    .add(App.loadTela("fxml/home_gestor.fxml",
                            a -> new HomeGestor(this.login, autenticacaoServico, repositorioUsuarios,
                                    repositorioCaminhao,
                                    repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa,
                                    repositorioViagens, repositorioDespesas)));
        } else {
            root.getChildren().clear();
            root.getChildren().add(App.loadTela("fxml/home_moto.fxml",
                    a -> new HomeMoto(this.login, autenticacaoServico, repositorioViagens, repositorioDespesas,
                            repositorioEmpresa, repositorioCaminhao)));
        }
    }

    @FXML
    private void cadastrar() {
        String nome_Despesa = tfNomeDespesa.getText();
        String tipo_Despesa = tfTipoDespesa.getText();
        String preco_Despesa = tfPrecoDespesa.getText();
        LocalDate data_Despesa = dpDataDespesa.getValue();
        int tipoD = Integer.parseInt(tipo_Despesa);
        TipoDespesa tipo = TipoDespesa.toEnum(tipoD);
        double precoDespesa = Double.parseDouble(preco_Despesa);
        String modelo = cbCaminhaoDespesa.getSelectionModel().getSelectedItem();

        boolean temErro = false;
        String msg = "";

        if (nome_Despesa.isEmpty() || nome_Despesa.isBlank()) {
            temErro = true;
            msg += "Nome da despesa n??o pode ser vazio!\n";
        }

        if (tipo_Despesa.isEmpty() || tipo_Despesa.isBlank()) {
            temErro = true;
            msg += "Tipo da despesa n??o pode ser vazio!\n";
        }

        if (preco_Despesa.isEmpty() || preco_Despesa.isBlank()) {
            temErro = true;
            msg += "Pre??o da despesa n??o pode ser vazio!\n";
        }
        if (data_Despesa == null) {
            temErro = true;
            msg += "Data da despesa n??o pode ser vazio!\n";
        }

        if (!temErro) {

            try {
                boolean ret;
                Caminhao caminhao = repositorioCaminhao.buscarPorModelo(modelo);
                ret = repositorioDespesas.cadastrarDespesa(tipo, nome_Despesa, precoDespesa, data_Despesa, caminhao);

                if (ret) {
                    msg = "Despesa cadastrada com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar Despesa!";
                }

            } catch (Exception e) {
                temErro = true;
                msg = e.getMessage();
            }
        }

        Alert alert = new Alert(temErro ? AlertType.ERROR : AlertType.INFORMATION, msg);
        alert.showAndWait();

    }

    @FXML
    private void limpar() {
        tfNomeDespesa.clear();
        tfPrecoDespesa.clear();
        tfTipoDespesa.clear();
        dpDataDespesa.setValue(null);
    }

}
