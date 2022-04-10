package ifpr.pgua.eic.gestaocaminhao.models.enums;

public enum TipoCaminhao {

    CAÇAMBA(1, "Caçamba"),
    CONTAINER(2, "Container");

    private int cod;
    private String descricao;

    private TipoCaminhao(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoCaminhao toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (TipoCaminhao x : TipoCaminhao.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
