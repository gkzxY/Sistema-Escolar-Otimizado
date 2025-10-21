public class Estudante {

    private final int id;
    private String nome;
    public Estudante(int id, String nome) {
        this.id = id;
        setNome(nome);
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = (nome == null) ? "" : nome.trim();
    }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estudante))
            return false;
            return id == ((Estudante)o).id;
    }
    @Override public int hashCode() {
        return Integer.hashCode(id);
    }
    @Override public String toString() {
        return id + " - " + nome;
    }
}