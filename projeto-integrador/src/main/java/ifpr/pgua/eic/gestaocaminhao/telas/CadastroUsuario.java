package ifpr.pgua.eic.gestaocaminhao.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class CadastroUsuario {

    private RepositorioUsuarios repositorioUsuarios;

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
    private CheckBox cbCadastroUsuario;

    @FXML
    private TextField tfCnh;

    @FXML
    private Pane rootPane;

    private AutenticacaoServico autenticacaoServico;
    private Home homeControler;

    public CadastroUsuario(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public CadastroUsuario(Usuario usuarioExiste, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.usuarioExistente = usuarioExiste;
    }

    public CadastroUsuario(AutenticacaoServico autenticacaoServico, Home homeControler) {
        this.autenticacaoServico = autenticacaoServico;
        this.homeControler = homeControler;
    }

    public void initialize() {
        if (usuarioExistente != null) {
            tfNome.setText(usuarioExistente.getNome());
            tfEmail.setText(usuarioExistente.getEmail());
            tfTelefone.setText(usuarioExistente.getTelefone());

            btCadastrar.setText("Atualizar");

        }
    }

    @FXML
    private void cadastrar() {
        String cpf = tfCpf.getText();
        String nome = tfNome.getText();
        String cidade = tfCidade.getText();
        String endereco = tfEndereco.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        String senha = pfSenha.getText();
        String cnh = tfCnh.getText();
        boolean gestor = cbCadastroUsuario.isSelected();

        try {
            autenticacaoServico.cadastrar(cpf, senha);
            homeControler.atualizaTela();
        } catch (Exception e) {

        }

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

        if (endereco.isEmpty() || endereco.isBlank()) {
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
                    ret = repositorioUsuarios.atualizarUsuarios(cpf, nome, cidade, endereco, email, senha, telefone,
                            cnh,
                            gestor);
                } else {
                    ret = repositorioUsuarios.cadastrarUsuario(cpf, nome, cidade, endereco, email, senha, telefone, cnh,
                            gestor);
                }

                if (ret) {
                    msg = "Usuário cadastrada com sucesso!";
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
    }

}