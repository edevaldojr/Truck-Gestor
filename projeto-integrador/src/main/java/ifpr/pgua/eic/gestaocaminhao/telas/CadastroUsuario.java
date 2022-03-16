package ifpr.pgua.eic.gestaocaminhao.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class CadastroUsuario {

    private RepositorioUsuarios repositorioUsuarios;

    private RepositorioCaminhao repositorioCaminhao;

    private Usuario usuarioExistente = null;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField pfSenha;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    @FXML
    private CheckBox cbGestor;

    @FXML
    private TextField tfCnh;

    @FXML
    private AnchorPane root;

    private AutenticacaoServico autenticacaoServico;

    public CadastroUsuario(AutenticacaoServico autenticacaoServico, RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioCaminhao = repositorioCaminhao;
    }

    public CadastroUsuario(Usuario usuarioExiste, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.usuarioExistente = usuarioExiste;
    }

    public void initialize() {
        if (usuarioExistente != null) {
            tfNome.setText(usuarioExistente.getNome());
            tfEmail.setText(usuarioExistente.getEmail());
            tfTelefone.setText(usuarioExistente.getTelefone());
            tfEndereco.setText(usuarioExistente.getEndereco()+"");
            tfCnh.setText(usuarioExistente.getCnh());
            pfSenha.setText(usuarioExistente.getSenha());

            btCadastrar.setText("Atualizar");

        }
    }

    @FXML
    private void voltar() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/login.fxml",
                        a -> new Login(autenticacaoServico, repositorioUsuarios, repositorioCaminhao)));

    }

    @FXML
    private void cadastrar() {
        String cpf = tfCpf.getText();
        String nome = tfNome.getText();
        String cidade = tfCidade.getText();
        String end = tfEndereco.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        String senha = pfSenha.getText();
        String cnh = tfCnh.getText();
        boolean gestor = cbGestor.isSelected();
        int endereco = Integer.parseInt(end);

        boolean temErro = false;
        String msg = "";

        if (cpf.isEmpty() || cpf.isBlank()) {
            temErro = true;
            msg += "CPF não pode ser vazio!\n";
        }

        if (nome.isEmpty() || nome.isBlank()) {
            temErro = true;
            msg += "Nome não pode ser vazio!\n";
        }

        if (cidade.isEmpty() || cidade.isBlank()) {
            temErro = true;
            msg += "Cidade não pode ser vazio!\n";
        }

        if (end.isEmpty() || end.isBlank()) {
            temErro = true;
            msg += "Endereço não pode ser vazio!\n";
        }

        if (email.isEmpty() || email.isBlank()) {
            temErro = true;
            msg += "Email não pode ser vazio!\n";
        }

        if (senha.isEmpty() || senha.isBlank()) {
            temErro = true;
            msg += "Senha não pode ser vazio!\n";
        }

        if (telefone.isEmpty() || telefone.isBlank()) {
            temErro = true;
            msg += "Telefone não pode ser vazio!\n";
        }

        if (cnh.isEmpty() || cnh.isBlank()) {
            if (!gestor) {
                temErro = true;
                msg += "CNH não pode ser vazio!\n";
            }
        }

        if (!temErro) {
            try {
                boolean ret;

                if (usuarioExistente != null) {
                    ret = repositorioUsuarios.atualizarUsuarios(cpf, nome, endereco, telefone, email, senha,
                            cnh,
                            gestor);
                } else {
                    ret = repositorioUsuarios.cadastrarUsuario(cpf, nome, endereco, telefone, email, senha, cnh,
                            gestor);
                }

                if (ret) {
                    msg = "Usuário cadastrado com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar usuário!";
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
        tfCpf.clear();
        tfCidade.clear();
        tfEndereco.clear();
        pfSenha.clear();
        tfEmail.clear();
        tfNome.clear();
        tfTelefone.clear();
        tfCnh.clear();
        cbGestor.disarm();
    }

}
