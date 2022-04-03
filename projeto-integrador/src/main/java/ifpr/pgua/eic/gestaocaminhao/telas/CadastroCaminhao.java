package ifpr.pgua.eic.gestaocaminhao.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoCaminhao;
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

public class CadastroCaminhao {

    private RepositorioCaminhao repositorioCaminhao;
    private AutenticacaoServico autenticacaoServico;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioEndereco repositorioEndereco;
    private RepositorioCidade repositorioCidade;
    private RepositorioEstado repositorioEstado;
    private RepositorioViagens repositorioViagens;
    private RepositorioEmpresa repositorioEmpresa;
    private RepositorioDespesas repositorioDespesas;
    private Caminhao caminhaoExistente = null;
    private Login login;

    @FXML
    private TextField tfPlaca;

    @FXML
    private TextField tfCor;

    @FXML
    private TextField tfAnoFrabricacao;

    @FXML
    private TextField tfMarca;

    @FXML
    private TextField tfModelo;

    @FXML
    private TextField tfTipo;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    @FXML
    private AnchorPane root;

    public CadastroCaminhao(Caminhao caminhaoExiste, AutenticacaoServico autenticacaoServico,
            RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco,
            RepositorioEstado repositorioEstado,
            RepositorioCidade repositorioCidade,
            RepositorioEmpresa repositorioEmpresa,
            RepositorioViagens repositorioViagens, RepositorioDespesas repositorioDespesas) {
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioEndereco = repositorioEndereco;
        this.repositorioCaminhao = repositorioCaminhao;
        this.repositorioCidade = repositorioCidade;
        this.repositorioEstado = repositorioEstado;
        this.repositorioEmpresa = repositorioEmpresa;
        this.repositorioViagens = repositorioViagens;
        this.caminhaoExistente = caminhaoExiste;
        this.repositorioDespesas = repositorioDespesas;
    }

    public CadastroCaminhao(Login login, AutenticacaoServico autenticacaoServico,
            RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco,
            RepositorioEstado repositorioEstado,
            RepositorioCidade repositorioCidade,
            RepositorioEmpresa repositorioEmpresa,
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

    public void initialize() {
        if (caminhaoExistente != null) {
            tfPlaca.setText(caminhaoExistente.getPlaca());
            tfCor.setText(caminhaoExistente.getCor());
            tfAnoFrabricacao.setText("" + caminhaoExistente.getAno());
            tfMarca.setText(caminhaoExistente.getMarca());
            tfModelo.setText(caminhaoExistente.getModelo());
            tfTipo.setText(caminhaoExistente.getTipo().getCod() + "");

            btCadastrar.setText("Atualizar");

        }
    }

    @FXML
    private void voltar() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/home_gestor.fxml",
                        a -> new HomeGestor(login, autenticacaoServico, repositorioUsuarios, repositorioCaminhao,
                                repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa,
                                repositorioViagens, repositorioDespesas)));

    }

    @FXML
    private void cadastrar() {
        String placa = tfPlaca.getText();
        String cor = tfCor.getText();
        String anoFabric = tfAnoFrabricacao.getText();
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        String tipoS = tfTipo.getText();
        int tipoI = Integer.parseInt(tipoS);
        TipoCaminhao tipo = TipoCaminhao.toEnum(tipoI);
        int ano = Integer.parseInt(anoFabric);

        boolean temErro = false;
        String msg = "";

        if (placa.isEmpty() || placa.isBlank()) {
            temErro = true;
            msg += "Placa não pode ser vazio!\n";
        }

        if (cor.isEmpty() || cor.isBlank()) {
            temErro = true;
            msg += "Cor não pode ser vazio!\n";
        }

        if (anoFabric.isEmpty() || anoFabric.isBlank()) {
            temErro = true;
            msg += "Ano de fabricação não pode ser vazio!\n";
        }

        if (marca.isEmpty() || marca.isBlank()) {
            temErro = true;
            msg += "Marca não pode ser vazio!\n";
        }

        if (modelo.isEmpty() || modelo.isBlank()) {
            temErro = true;
            msg += "Modelo não pode ser vazio!\n";
        }

        if (tipoS.isEmpty() || tipoS.isBlank()) {
            temErro = true;
            msg += "Tipo de caminhão não pode ser vazio!\n";
        }

        if (!temErro) {
            try {
                boolean ret;
                int existente=0;

                if (caminhaoExistente != null) {
                    ret = repositorioCaminhao.atualizarCaminhao(caminhaoExistente.getId(), placa, cor, ano, marca,
                            modelo, tipo);
                    existente = 1;
                } else {
                    ret = repositorioCaminhao.cadastrarCaminhao(placa, cor, ano, marca, modelo, tipo);
                    existente = 2;
                }

                if (ret && existente == 2) {
                    msg = "Caminhão cadastrado com sucesso!";
                    limpar();
                } else if(ret && existente == 1){
                    msg = "Caminhão atualizado com sucesso!";
                    limpar();
                }else{
                    msg = "Erro ao cadastrar caminhão!";
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
        tfPlaca.clear();
        tfAnoFrabricacao.clear();
        tfCor.clear();
        tfModelo.clear();
        tfMarca.clear();
        tfTipo.clear();
    }

}
