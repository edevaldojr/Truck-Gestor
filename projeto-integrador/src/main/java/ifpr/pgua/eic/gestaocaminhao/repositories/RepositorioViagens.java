package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EmpresaDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.ViagemDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;

public class RepositorioViagens {

    private ArrayList<Viagem> viagens;

    private ViagemDAO viagemDAO;
    private CaminhaoDAO caminhaoDAO;
    private EmpresaDAO empresaDAO;
    private UsuarioDAO usuarioDAO;

    public RepositorioViagens(ViagemDAO viagemDAO, CaminhaoDAO caminhaoDAO, EmpresaDAO empresaDAO, UsuarioDAO usuarioDAO) {
        this.viagemDAO = viagemDAO;
        this.caminhaoDAO = caminhaoDAO;
        this.empresaDAO = empresaDAO;
        this.usuarioDAO = usuarioDAO;
        viagens = new ArrayList<>();
    }

    public boolean cadastrarViagens(double peso, LocalDate data_da_baixa, double valor, Empresa empresa_origem,
            Empresa empresa_destino, String carga, Usuario caminhoneiro, Caminhao caminhao) throws SQLException {
        Viagem v = new Viagem(peso, data_da_baixa, valor, empresa_origem, empresa_destino, carga, caminhoneiro, caminhao);

        try {
            viagemDAO.cadastrar(v);
            this.viagens.add(v);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarViagens(int id, double peso, LocalDate data_da_baixa, double valor, Empresa empresa_origem,
            Empresa empresa_destino, String carga, Usuario caminhoneiro, Caminhao caminhao, double valor_total) throws SQLException {

        Viagem viagem = new Viagem(id, peso, data_da_baixa, valor, empresa_origem, empresa_destino, carga,
                caminhoneiro, caminhao, valor_total);

        try {
            return viagemDAO.atualizar(id, viagem);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerViagem(int id) throws SQLException {
        try {
            return viagemDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Viagem> listarViagens() throws Exception {
        viagens = viagemDAO.listar();
        for (Viagem viagem : viagens) {
            viagem.setCaminhao(caminhaoDAO.buscar(viagemDAO.buscarCaminhaoId(viagem.getId())));
            viagem.setEmpresa_destino(empresaDAO.buscar(viagemDAO.buscarEmpresaDestinoId(viagem.getId())));
            viagem.setEmpresa_origem(empresaDAO.buscar(viagemDAO.buscarEmpresaOrigemId(viagem.getId())));
            viagem.setCaminhoneiro(usuarioDAO.buscar(viagemDAO.buscarMotoristaCpf(viagem.getId())));
        }
        return viagens;
    }

    public ArrayList<Viagem> listarViagensDias(int dias) throws Exception {
        viagens = viagemDAO.listarDias(dias);
        for (Viagem viagem : viagens) {
            viagem.setCaminhao(caminhaoDAO.buscar(viagemDAO.buscarCaminhaoId(viagem.getId())));
            viagem.setEmpresa_destino(empresaDAO.buscar(viagemDAO.buscarEmpresaDestinoId(viagem.getId())));
            viagem.setEmpresa_origem(empresaDAO.buscar(viagemDAO.buscarEmpresaOrigemId(viagem.getId())));
            viagem.setCaminhoneiro(usuarioDAO.buscar(viagemDAO.buscarMotoristaCpf(viagem.getId())));
        }
        return viagens;
    }

    public ArrayList<Viagem> listarViagensMoto(String cpf) throws Exception {
        viagens = viagemDAO.listarPorMoto(cpf);
        for (Viagem viagem : viagens) {
            viagem.setCaminhao(caminhaoDAO.buscar(viagemDAO.buscarCaminhaoId(viagem.getId())));
            viagem.setEmpresa_destino(empresaDAO.buscar(viagemDAO.buscarEmpresaDestinoId(viagem.getId())));
            viagem.setEmpresa_origem(empresaDAO.buscar(viagemDAO.buscarEmpresaOrigemId(viagem.getId())));
            viagem.setCaminhoneiro(usuarioDAO.buscar(viagemDAO.buscarMotoristaCpf(viagem.getId())));
        }
        return viagens;
    }

    public ArrayList<Viagem> listarViagensMotoDias(int dias, String cpf) throws Exception {
        viagens = viagemDAO.listarPorMotoEmDias(dias, cpf);
        for (Viagem viagem : viagens) {
            viagem.setCaminhao(caminhaoDAO.buscar(viagemDAO.buscarCaminhaoId(viagem.getId())));
            viagem.setEmpresa_destino(empresaDAO.buscar(viagemDAO.buscarEmpresaDestinoId(viagem.getId())));
            viagem.setEmpresa_origem(empresaDAO.buscar(viagemDAO.buscarEmpresaOrigemId(viagem.getId())));
            viagem.setCaminhoneiro(usuarioDAO.buscar(viagemDAO.buscarMotoristaCpf(viagem.getId())));
        }
        return viagens;
    }
}
