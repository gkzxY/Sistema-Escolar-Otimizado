import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;


public class ListaEstudantes {
    private final List<Estudante> estudantes = new ArrayList<>();
    public boolean adicionarEstudante(Estudante e) {
        if (e == null || getById(e.getId()) != null)
            return false;
            return estudantes.add(e);
    }
    public boolean removerEstudantePorId(int id) {
        Estudante alvo = getById(id);
        return (alvo != null) && estudantes.remove(alvo);
    }
    public Estudante obterEstudantePorIndice(int indice) {
        if (indice < 0 || indice >= estudantes.size())
            return null;
        return estudantes.get(indice);
    }
    public List<Estudante> buscarEstudantesPorNome(String substring) {
        String termo = (substring == null) ? "" : substring.trim().toLowerCase();
        if (termo.isEmpty()) return new ArrayList<>(estudantes);
        return estudantes.stream().filter(e -> e.getNome().toLowerCase().contains(termo)).collect(Collectors.toList());
    }
    public void ordenarEstudantesPorNome() {
        estudantes.sort(Comparator.comparing(Estudante::getNome, String.CASE_INSENSITIVE_ORDER).thenComparingInt(Estudante::getId));
    }
    public List<Estudante> getTodos() {
        return Collections.unmodifiableList(estudantes);
    }
    public Estudante getById(int id) {
        for (Estudante e : estudantes)
            if (e.getId() == id)
                return e;
                return null;
    }
}