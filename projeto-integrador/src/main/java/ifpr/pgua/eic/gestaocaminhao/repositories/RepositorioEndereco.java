package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;

public class RepositorioEndereco {

    private ArrayList<Endereco> enderecos;

    private EnderecoDAO enderecoDAO;

    public RepositorioEndereco(EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
        enderecos = new ArrayList<>();
    }

    public boolean cadastrarEndereco(String numero, String complemento, String bairro, String rua, String cep,
            Cidade cidade) throws SQLException {
        Endereco end = new Endereco(numero, complemento, bairro, rua, cep, cidade);

        try {
            enderecoDAO.cadastrar(end);
            this.enderecos.add(end);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarEndereco(int id, String numero, String complemento, String bairro, String rua, String cep,
            Cidade cidade) throws SQLException {

        Endereco end = new Endereco(id, numero, complemento, bairro, rua, cep, cidade);

        try {
            return enderecoDAO.atualizar(id, end);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerEndereco(int id) throws SQLException {
        try {
            return enderecoDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Endereco> listarEndereco() throws Exception {
        return enderecoDAO.listar();
    }

    public Endereco buscar(String bairro, String rua, String numero) throws SQLException {
        try {
            return enderecoDAO.buscarPorEnd(bairro, rua, numero);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Endereco buscarId(int id) throws SQLException {
        try {
            return enderecoDAO.buscar(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

}
