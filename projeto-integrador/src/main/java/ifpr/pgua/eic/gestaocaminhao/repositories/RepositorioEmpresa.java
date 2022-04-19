package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoEmpresa;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EmpresaDAO;

public class RepositorioEmpresa {

    private ArrayList<Empresa> empresas;

    private EmpresaDAO empresaDAO;

    public RepositorioEmpresa(EmpresaDAO empresaDAO) {
        this.empresaDAO = empresaDAO;
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
        return empresaDAO.listarEmpresasOrigem();
    }

    public ArrayList<Empresa> listarEmpresasDestino() throws Exception {
        return empresaDAO.listarEmpresasDestino();
    }

    public ArrayList<String> listarEmpresasOrigemString() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        for (Empresa empresa : empresaDAO.listarEmpresasOrigem()) {
            String nomeEmpresa = empresa.getNome();
            lista.add(nomeEmpresa);
        }
        return lista;
    }

    public ArrayList<String> listarEmpresasDestinoString() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        for (Empresa empresa : empresaDAO.listarEmpresasDestino()) {
            String nomeEmpresa = empresa.getNome();
            lista.add(nomeEmpresa);
        }
        return lista;
    }

    public Empresa buscar(String nome) throws SQLException {
        try {
            return empresaDAO.buscarPorNome(nome);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
    public Empresa buscarPorId(int id) throws SQLException {
        try {
            return empresaDAO.buscar(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

}
