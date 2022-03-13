package ifpr.pgua.eic.gestaocaminhao.models.enums;

public enum TipoEmpresa {

    DESTINO(1, "Destino"),
    ORIGEM(2, "Origem");

    private int cod;
    private String descricao;

    private TipoEmpresa(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoEmpresa toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (TipoEmpresa x : TipoEmpresa.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
