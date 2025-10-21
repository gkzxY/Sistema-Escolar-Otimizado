public class Disciplina {

    private final String codigo;
    private String nome;
    public Disciplina(String codigo, String nome) {
        this.codigo = (codigo == null) ? "" : codigo.trim();
        setNome(nome);
    }
    public String getCodigo() {
        return codigo;
    }
    public void setNome(String nome) {
        this.nome = (nome == null) ? "" : nome.trim();
    }
    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Disciplina))
            return false;
        return codigo.equals(((Disciplina)o).getCodigo());
    }
    @Override public int hashCode() {
        return codigo.hashCode();
    }
    @Override public String toString() {
        return codigo + " - " + nome;
    }
}