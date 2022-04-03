package ifpr.pgua.eic.gestaocaminhao.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoEmpresa;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class CadastroEmpresa {

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
    private TextField tfNome;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfRua;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfComplemento;

    @FXML
    private TextField tfTipo;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    @FXML
    private AnchorPane root;

    public CadastroEmpresa(Login login, AutenticacaoServico autenticacaoServico,
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

    public CadastroEmpresa(Login login, AutenticacaoServico autenticacaoServico,
            RepositorioEmpresa repositorioEmpresa) {
        this.repositorioEmpresa = repositorioEmpresa;
        this.autenticacaoServico = autenticacaoServico;
        this.login = login;
    }

    public void initialize() {
        
    }

    @FXML
    private void voltar() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/home_gestor.fxml",
                        a -> new HomeGestor(this.login, autenticacaoServico, repositorioUsuarios, repositorioCaminhao,
                                repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa,
                                repositorioViagens, repositorioDespesas)));

    }

    @FXML
    private void cadastrar() {
        String nomeEmpresa = tfNome.getText();
        String tipoS = tfTipo.getText();
        String cidade = tfCidade.getText();
        String rua = tfRua.getText();
        String bairro = tfBairro.getText();
        String numero = tfNumero.getText();
        String cep = tfCep.getText();
        String complemento = tfComplemento.getText();
        int tipoE = Integer.parseInt(tipoS);
        TipoEmpresa tipo = TipoEmpresa.toEnum(tipoE);

        boolean temErro = false;
        String msg = "";

        if (nomeEmpresa.isEmpty() || nomeEmpresa.isBlank()) {
            temErro = true;
            msg += "Nome da empresa não pode ser vazio!\n";
        }

        if (tipoS.isEmpty() || tipoS.isBlank()) {
            temErro = true;
            msg += "Tipo não pode ser vazio!\n";
        }
        if (cidade.isEmpty() || cidade.isBlank()) {
            temErro = true;
            msg += "Cidade não pode ser vazio!\n";
        }

        if (bairro.isEmpty() || bairro.isBlank()) {
            temErro = true;
            msg += "Bairro não pode ser vazio!\n";
        }
        if (numero.isEmpty() || numero.isBlank()) {
            temErro = true;
            msg += "Número não pode ser vazio!\n";
        }

        if (rua.isEmpty() || rua.isBlank()) {
            temErro = true;
            msg += "Rua não pode ser vazio!\n";
        }

        if (cep.isEmpty() || cep.isBlank()) {
            temErro = true;
            msg += "CEP não pode ser vazio!\n";
        }

        if (!temErro) {
            try {
                boolean ret;

                Cidade cidadeObj = repositorioCidade.buscarCidadePorNome(cidade);
                if (repositorioEndereco.buscar(bairro, rua, numero) != null) {
                    repositorioEndereco.cadastrarEndereco(numero, complemento, bairro, rua, cep, cidadeObj);
                }
                ;
                Endereco endereco = repositorioEndereco.buscar(bairro, rua, numero);
                ret = repositorioEmpresa.cadastrarEmpresa(nomeEmpresa, endereco, tipo);

                if (ret) {
                    msg = "Empresa cadastrada com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar empresa!";
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
        tfNome.clear();
        tfCidade.clear();
        tfBairro.clear();
        tfRua.clear();
        tfNumero.clear();
        tfCep.clear();
        tfComplemento.clear();
        tfTipo.clear();
    }

}
