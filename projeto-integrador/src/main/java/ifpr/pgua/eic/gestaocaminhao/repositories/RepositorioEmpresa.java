package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoEmpresa;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EmpresaDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;

public class RepositorioEmpresa {

    private ArrayList<Empresa> empresas;

    private EmpresaDAO empresaDAO;
    private EnderecoDAO enderecoDAO;
    private CidadeDAO cidadeDAO;

    public RepositorioEmpresa(EmpresaDAO empresaDAO, EnderecoDAO enderecoDAO, CidadeDAO cidadeDAO) {
        this.empresaDAO = empresaDAO;
        this.enderecoDAO = enderecoDAO;
        this.cidadeDAO = cidadeDAO;
        empresas = new ArrayList<>();
    }

    public boolean cadastrarEmpresa(String nome, Endereco endereco, TipoEmpresa tipo) throws SQLException {
        Empresa empresa = new Empresa(nome, endereco, tipo);

        try {
            empresaDAO.cadastrar(empresa);
            this.empresas.add(empresa);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarEmpresa(int id, String nome, Endereco endereco, TipoEmpresa tipo) throws SQLException {

        Empresa empresa = new Empresa(id, nome, endereco, tipo);

        try {
            return empresaDAO.atualizar(id, empresa);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerEmpresas(int id) throws SQLException {
        try {
            return empresaDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Empresa> listarEmpresasOrigem() throws Exception {
        empresas = empresaDAO.listarEmpresasOrigem();
        for (Empresa empresa : empresas) {
            Endereco endereco = enderecoDAO.buscar(empresaDAO.buscarEnderecoId(empresa.getId()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            empresa.setEndereco(endereco);
        }
        return empresas;
    }

    public ArrayList<Empresa> listarEmpresasDestino() throws Exception {
        empresas = empresaDAO.listarEmpresasDestino();
        for (Empresa empresa : empresas) {
            Endereco endereco = enderecoDAO.buscar(empresaDAO.buscarEnderecoId(empresa.getId()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            empresa.setEndereco(endereco);
        }
        return empresas;
    }

    public ArrayList<String> listarEmpresasOrigemString() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        for (Empresa empresa : listarEmpresasOrigem()) {
            String nomeEmpresa = empresa.getNome();
            lista.add(nomeEmpresa);
        }
        return lista;
    }

    public ArrayList<String> listarEmpresasDestinoString() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        for (Empresa empresa : listarEmpresasDestino()) {
            String nomeEmpresa = empresa.getNome();
            lista.add(nomeEmpresa);
        }
        return lista;
    }

    public Empresa buscar(String nome) throws SQLException {
        try {
            Empresa empresa = empresaDAO.buscarPorNome(nome);
            Endereco endereco = enderecoDAO.buscar(empresaDAO.buscarEnderecoId(empresa.getId()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            empresa.setEndereco(endereco);
            return empresa;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
    public Empresa buscarPorId(int id) throws SQLException {
        try {
            Empresa empresa = empresaDAO.buscar(id);
            Endereco endereco = enderecoDAO.buscar(empresaDAO.buscarEnderecoId(empresa.getId()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            empresa.setEndereco(endereco);
            return empresa;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

}
