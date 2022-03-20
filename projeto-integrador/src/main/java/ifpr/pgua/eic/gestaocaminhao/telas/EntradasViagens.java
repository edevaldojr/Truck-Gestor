package ifpr.pgua.eic.gestaocaminhao.telas;

import java.sql.SQLException;
import java.time.LocalDate;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCidade;
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
import javafx.scene.text.Text;

public class EntradasViagens {

    @FXML
    private TextField tfCpfMoto;

    @FXML
    private TextField tfPeso;

    @FXML
    private TextField tfCarga;

    @FXML
    private TextField tfPrecoTonelada;

    @FXML
    private ComboBox<Empresa> cbEmpresaOrigem;

    @FXML
    private ComboBox<Empresa> cbEmpresaDestino;

    @FXML
    private DatePicker dpDataDaBaixa;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    @FXML
    private AnchorPane root;

    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioCaminhao repositorioCaminhao;
    private RepositorioEndereco repositorioEndereco;
    private RepositorioCidade repositorioCidade;
    private RepositorioEstado repositorioEstado;
    private RepositorioViagens repositorioViagens;
    private RepositorioEmpresa repositorioEmpresa;
    private AutenticacaoServico autenticacaoServico;
    private Login login;

    public EntradasViagens(Login login, AutenticacaoServico autenticacaoServico,
            RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco,
            RepositorioEstado repositorioEstado,
            RepositorioCidade repositorioCidade, RepositorioEmpresa repositorioEmpresa) {
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioEndereco = repositorioEndereco;
        this.repositorioCaminhao = repositorioCaminhao;
        this.repositorioCidade = repositorioCidade;
        this.repositorioEstado = repositorioEstado;
        this.repositorioEmpresa = repositorioEmpresa;
        this.login = login;
    }

    public void initialize() {

    }

    @FXML
    private void voltar() {
        root.getChildren().clear();
        root.getChildren().add(App.loadTela("fxml/home_gestor.fxml",
                a -> new HomeGestor(this.login, autenticacaoServico, repositorioUsuarios, repositorioCaminhao,
                        repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa)));

    }

    @FXML
    private void cadastrar() throws Exception {
        String cpfMotorista = tfCpfMoto.getText();
        String peso = tfPeso.getText();
        String carga = tfCarga.getText();
        String valor = tfPrecoTonelada.getText();
        String empresaOrigem = cbEmpresaOrigem.getSelectionModel().getSelectedItem().getNome();
        String empresaDestino = cbEmpresaDestino.getSelectionModel().getSelectedItem().getNome();
        LocalDate data_da_baixa = dpDataDaBaixa.getValue();

        double pesoDouble = Double.parseDouble(peso);
        double valorDouble = Double.parseDouble(valor);

        boolean temErro = false;
        String msg = "";

        if (cpfMotorista.isEmpty() || cpfMotorista.isBlank()) {
            temErro = true;
            msg += "CPF do motorista não pode ser vazio!\n";
        }

        if (peso.isEmpty() || peso.isBlank()) {
            temErro = true;
            msg += "Peso não pode ser vazio!\n";
        }

        if (carga.isEmpty() || carga.isBlank()) {
            temErro = true;
            msg += "Carga não pode ser vazio!\n";
        }

        if (empresaOrigem.isEmpty() || empresaOrigem.isBlank()) {
            temErro = true;
            msg += "Epresa de origem não pode ser vazio!\n";
        }

        if (empresaDestino.isEmpty() || empresaDestino.isBlank()) {
            temErro = true;
            msg += "Empresa de destino não pode ser vazio!\n";
        }

        if (!temErro) {
            try {
                boolean ret;
                Usuario motorista = repositorioUsuarios.buscar(cpfMotorista);
                Empresa origem = repositorioEmpresa.buscar(empresaOrigem);
                Empresa destino = repositorioEmpresa.buscar(empresaDestino);
                ret = repositorioViagens.cadastrarViagens(pesoDouble, data_da_baixa, valorDouble, origem, destino,
                        carga, motorista);

                if (ret) {
                    msg = "Viagem cadastrado com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar viagem!";
                }

            } catch (SQLException e) {
                temErro = true;
                msg = e.getMessage();
            }
        }

        Alert alert = new Alert(temErro ? AlertType.ERROR : AlertType.INFORMATION, msg);
        alert.showAndWait();

    }

    @FXML
    private void limpar() {
        tfCpfMoto.clear();
        tfPeso.clear();
        tfCarga.clear();
        tfPrecoTonelada.clear();
    }

}
