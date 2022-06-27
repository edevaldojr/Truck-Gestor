package ifpr.pgua.eic.gestaocaminhao.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ifpr.pgua.eic.gestaocaminhao.daos.JDBCAutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCCidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCEnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCUsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class AutenticacaoServicoTest {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

    private CidadeDAO cidadeDAO = new JDBCCidadeDAO(fabricaConexoes);
    private EnderecoDAO enderecoDAO = new JDBCEnderecoDAO(fabricaConexoes);
    private UsuarioDAO usuarioDAO = new JDBCUsuarioDAO(fabricaConexoes);
    private AutenticacaoDAO autenticacaoDAO = new JDBCAutenticacaoDAO(fabricaConexoes);

    private AutenticacaoServico autenticacaoServico = new AutenticacaoServico(autenticacaoDAO, enderecoDAO, usuarioDAO,cidadeDAO);

    //Na base de dados cpf do usuário é "85610969000" e a senha é "1"
    @Test
    void testLogar() {
        String cpf = "85610969000";
        String senha = "1";

        assertDoesNotThrow(() -> autenticacaoServico.logar(cpf, senha));     
    }

    @Test
    void testLogar2() {
        String cpf = "85610969000";
        String senha = "2";

        assertThrows(Exception.class, () -> autenticacaoServico.logar(cpf, senha));
    };
}

