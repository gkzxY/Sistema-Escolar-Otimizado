import java.util.*;
public class HistoricoNotas {
    private final Map<Integer, List<Matricula>>
            porEstudante = new HashMap<>();
    private final ListaEstudantes listaEstudantes;
    private final CadastroDisciplinas cadastroDisciplinas;

    public HistoricoNotas(ListaEstudantes listaEstudantes, CadastroDisciplinas cadastroDisciplinas) {
        this.listaEstudantes = listaEstudantes;
        this.cadastroDisciplinas = cadastroDisciplinas;
    }
    public boolean adicionarMatricula(int idEstudante, String codigoDisciplina, double nota) {

        if (listaEstudantes.getById(idEstudante) == null)
            return false;
        if (!cadastroDisciplinas.verificarDisciplina(codigoDisciplina))
            return false;
        if (nota < 0.0 || nota > 10.0)
            return false;
        porEstudante.computeIfAbsent(idEstudante, k -> new ArrayList<>()).add(new Matricula(codigoDisciplina, nota));
            return true;
    }
    public List<Matricula> obterMatriculas(int idEstudante) {
        List<Matricula> lst = porEstudante.get(idEstudante);
        if (lst == null) return Collections.emptyList();
        return Collections.unmodifiableList(new ArrayList<>(lst));
    }
    public boolean removerMatricula(int idEstudante, String codigoDisciplina) {
        List<Matricula> lst = porEstudante.get(idEstudante);
        if (lst == null)
            return false;
        String c = (codigoDisciplina == null) ? "" : codigoDisciplina.trim();
        int sizeBefore = lst.size();
        lst.removeIf(m -> m.getCodigoDisciplina().equals(c));
        return lst.size() != sizeBefore;
    }
    public double mediaDoEstudante(int idEstudante) {
        List<Matricula> lst = porEstudante.get(idEstudante);
        if (lst == null || lst.isEmpty())
            return Double.NaN;
            double soma = 0.0;
            for (Matricula m : lst) soma += m.getNota();
            return soma;
    }
    public List<Estudante> topNEstudantesPorMedia(int N) {
        List<Estudante> todos = new ArrayList<>(listaEstudantes.getTodos());
        todos.removeIf(e -> Double.isNaN(mediaDoEstudante(e.getId())));
        todos.sort((a, b) -> {
            int cmp = Double.compare(mediaDoEstudante(b.getId()), mediaDoEstudante(a.getId()));
            if (cmp != 0) return cmp;

            cmp = a.getNome().compareToIgnoreCase(b.getNome());
            return (cmp != 0) ? cmp : Integer.compare(a.getId(), b.getId());
        });
        if (N < 0) N = 0; if (N > todos.size()) N = todos.size();
        return new ArrayList<>(todos.subList(0, N));
    }
    public void exibirNomesENotasDeTodos() {
        for (Estudante e : listaEstudantes.getTodos()) {
            List<Matricula> ms = obterMatriculas(e.getId());
            System.out.println(e.getNome() + ": " + (ms.isEmpty() ? "sem notas" : ms));
        }
    }
    public void removerTodasMatriculasDoEstudante(int idEstudante) {
        porEstudante.remove(idEstudante);
    }

}