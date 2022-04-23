package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;

public class RepositorioEndereco {

    private ArrayList<Endereco> enderecos;

    private EnderecoDAO enderecoDAO;
    private CidadeDAO cidadeDAO;

    public RepositorioEndereco(EnderecoDAO enderecoDAO, CidadeDAO cidadeDAO) {
        this.enderecoDAO = enderecoDAO;
        this.cidadeDAO = cidadeDAO;
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
        enderecos = enderecoDAO.listar();
        for (Endereco endereco : enderecos) {
            endereco.setCidade(cidadeDAO.buscarPorId(enderecoDAO.buscarCidadeId(endereco.getId())));
        }
        return enderecos;
    }

    public Endereco buscar(String bairro, String rua, String numero) throws SQLException {
        try {
            Endereco endereco = enderecoDAO.buscarPorEnd(bairro, rua, numero);
            endereco.setCidade(cidadeDAO.buscarPorId(enderecoDAO.buscarCidadeId(endereco.getId())));
            return endereco;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Endereco buscarId(int id) throws SQLException {
        try {
            Endereco endereco = enderecoDAO.buscar(id);
            endereco.setCidade(cidadeDAO.buscarPorId(enderecoDAO.buscarCidadeId(endereco.getId())));
            return endereco;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

}
