import java.util.*;
public class CadastroDisciplinas {
    private final Set<Disciplina>
            disciplinas = new LinkedHashSet<>();
    public boolean adicionarDisciplina(Disciplina d) {
        if (d == null || d.getCodigo().isEmpty())
            return false;
            return disciplinas.add(d);
    }
    public boolean verificarDisciplina(String codigo) {
        String c = norm(codigo);
        for (Disciplina d : disciplinas)
            if (d.getCodigo().equals(c))
                return true;
                return false;
    }
    public boolean removerDisciplina(String codigo) {
        String c = norm(codigo);
        Disciplina alvo = null;
        for (Disciplina d : disciplinas)
            if (d.getCodigo().equals(c)) {
                alvo = d; break;
            }
        return (alvo != null) && disciplinas.remove(alvo);
    }
    public List<Disciplina> obterTodasDisciplinas() {
        return Collections.unmodifiableList(new ArrayList<>(disciplinas));
    }
    private String norm(String s) {
        return (s == null) ? "" : s.trim();
    }
}