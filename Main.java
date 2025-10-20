import java.util.*;

public class Main {
    public static void main(String[] args) {
        ListaEstudantes lista = new ListaEstudantes();
        CadastroDisciplinas cadastro = new CadastroDisciplinas();
        HistoricoNotas historico = new HistoricoNotas(lista, cadastro);
        Scanner sc = new Scanner(System.in);

        // Dados iniciais de exemplo
        lista.adicionarEstudante(new Estudante(1, "Ana"));
        lista.adicionarEstudante(new Estudante(2, "Bruno"));
        lista.adicionarEstudante(new Estudante(3, "Carla"));
        cadastro.adicionarDisciplina(new Disciplina("MAT101", "Matemática"));
        cadastro.adicionarDisciplina(new Disciplina("PRG201", "Programação"));
        cadastro.adicionarDisciplina(new Disciplina("MAT101", "Cálculo I (DUPLICATA)"));
        historico.adicionarMatricula(1, "MAT101", 8.5);
        historico.adicionarMatricula(1, "PRG201", 9.0);
        historico.adicionarMatricula(2, "MAT101", 5.0);
        historico.adicionarMatricula(3, "PRG201", 8.0);

        List<String> duplicatasDetectadas = new ArrayList<>();
        duplicatasDetectadas.add("MAT101"); // exemplo de duplicata

        while (true) {
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1) Adicionar novo estudante");
            System.out.println("2) Adicionar nova disciplina");
            System.out.println("3) Matricular estudante em disciplina");
            System.out.println("4) Lançar nota de matrícula existente");
            System.out.println("5) Listar todos os estudantes");
            System.out.println("6) Listar todas as disciplinas");
            System.out.println("7) Exibir nome e notas de todos os estudantes");
            System.out.println("8) Exibir média de cada estudante");
            System.out.println("9) Remover estudante por ID");
            System.out.println("10) Remover disciplina por código");
            System.out.println("11) Remover matrícula específica");
            System.out.println("12) Exibir estudante com melhor média");
            System.out.println("13) Informativo Geral (relatório completo)");
            System.out.println("0) Sair do sistema");
            System.out.print("Digite sua opção: ");

            String opcao = sc.nextLine().trim();

            switch (opcao) {
                case "1":
                    System.out.print("ID do estudante: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Nome do estudante: ");
                    String nome = sc.nextLine();
                    boolean ok = lista.adicionarEstudante(new Estudante(id, nome));
                    System.out.println(ok ? "Estudante adicionado com sucesso." : "Falha: ID já existente.");
                    break;

                case "2":
                    System.out.print("Código da disciplina: ");
                    String codigo = sc.nextLine();
                    System.out.print("Nome da disciplina: ");
                    String nomeDisc = sc.nextLine();
                    boolean addOk = cadastro.adicionarDisciplina(new Disciplina(codigo, nomeDisc));
                    if (!addOk) duplicatasDetectadas.add(codigo);
                    System.out.println(addOk ? "Disciplina adicionada com sucesso." : "Falha: disciplina duplicada.");
                    break;

                case "3":
                    System.out.print("ID do estudante: ");
                    int idMatricula = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codigoDisciplina = sc.nextLine();
                    boolean matriculado = historico.adicionarMatricula(idMatricula, codigoDisciplina, 0.0);
                    System.out.println(matriculado
                            ? "Estudante matriculado com sucesso. (Nota padrão 0.0)"
                            : "Falha: estudante ou disciplina inexistente.");
                    break;

                case "4":
                    System.out.print("ID do estudante: ");
                    int idLancar = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codLancar = sc.nextLine();
                    System.out.print("Nova nota: ");
                    double novaNota = Double.parseDouble(sc.nextLine());
                    List<Matricula> listaNotas = historico.obterMatriculas(idLancar);
                    boolean atualizou = false;
                    for (Matricula m : listaNotas) {
                        if (m.getCodigoDisciplina().equals(codLancar)) {
                            m.setNota(novaNota);
                            atualizou = true;
                        }
                    }
                    System.out.println(atualizou
                            ? "Nota atualizada com sucesso."
                            : "Matrícula não encontrada para este estudante.");
                    break;

                case "5":
                    lista.ordenarEstudantesPorNome();
                    System.out.println("\n=== Estudantes ===");
                    for (Estudante e : lista.getTodos())
                        System.out.println(" - " + e);
                    break;

                case "6":
                    System.out.println("\n=== Disciplinas ===");
                    for (Disciplina d : cadastro.obterTodasDisciplinas())
                        System.out.println(" - " + d);
                    break;

                case "7":
                    System.out.println("\n=== Nome e Notas de Todos os Estudantes ===");
                    historico.exibirNomesENotasDeTodos();
                    break;

                case "8":
                    System.out.println("\n=== Média de Cada Estudante ===");
                    for (Estudante e : lista.getTodos()) {
                        double media = historico.mediaDoEstudante(e.getId());
                        String msg = Double.isNaN(media) ? "Sem notas" : String.format("%.2f", media);
                        System.out.println(" - " + e.getNome() + ": " + msg);
                    }
                    break;

                case "9":
                    System.out.print("Digite o ID do estudante para remover: ");
                    int idRem = Integer.parseInt(sc.nextLine());
                    boolean remOk = lista.removerEstudantePorId(idRem);
                    if (remOk) historico.removerTodasMatriculasDoEstudante(idRem);
                    System.out.println(remOk ? "Estudante removido." : "Estudante não encontrado.");
                    break;

                case "10":
                    System.out.print("Digite o código da disciplina para remover: ");
                    String codRem = sc.nextLine();
                    boolean remDisc = cadastro.removerDisciplina(codRem);
                    System.out.println(remDisc ? "Disciplina removida." : "Disciplina não encontrada.");
                    break;

                case "11":
                    System.out.print("ID do estudante: ");
                    int idMat = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codMat = sc.nextLine();
                    boolean remMat = historico.removerMatricula(idMat, codMat);
                    System.out.println(remMat ? "Matrícula removida." : "Matrícula não encontrada.");
                    break;

                case "12":
                    List<Estudante> top = historico.topNEstudantesPorMedia(1);
                    if (top.isEmpty()) System.out.println("Nenhum dado disponível.");
                    else {
                        Estudante e = top.get(0);
                        double media = historico.mediaDoEstudante(e.getId());
                        System.out.printf("Melhor estudante: %s (%.2f)%n", e.getNome(), media);
                    }
                    break;

                case "13":
                    System.out.println("\n===== INFORMATIVO GERAL =====");
                    lista.ordenarEstudantesPorNome();
                    System.out.println("\nEstudantes:");
                    for (Estudante e : lista.getTodos())
                        System.out.println(" - " + e);
                    System.out.println("\nDisciplinas:");
                    for (Disciplina d : cadastro.obterTodasDisciplinas())
                        System.out.println(" - " + d);
                    System.out.println("\nNotas por Estudante:");
                    historico.exibirNomesENotasDeTodos();

                    System.out.println("\nAlunos com média >= 8.0:");
                    for (Estudante e : lista.getTodos()) {
                        double media = historico.mediaDoEstudante(e.getId());
                        if (!Double.isNaN(media) && media >= 8.0)
                            System.out.printf(" - %s (%.2f)%n", e.getNome(), media);
                    }

                    System.out.println("\nDisciplinas com média < 6.0:");
                    Map<String, Double> mediasDisc = calcularMediasPorDisciplina(lista, historico);
                    for (Disciplina d : cadastro.obterTodasDisciplinas()) {
                        Double m = mediasDisc.get(d.getCodigo());
                        if (m != null && m < 6.0)
                            System.out.printf(" - %s (%.2f)%n", d.getCodigo(), m);
                    }

                    System.out.println("\nDuplicatas de Disciplinas Detectadas:");
                    if (duplicatasDetectadas.isEmpty())
                        System.out.println(" - Nenhuma duplicata detectada.");
                    else
                        for (String cod : duplicatasDetectadas)
                            System.out.println(" - " + cod);
                    break;

                case "0":
                    System.out.println("Encerrando o sistema...");
                    return;

                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private static Map<String, Double> calcularMediasPorDisciplina(ListaEstudantes lista, HistoricoNotas hist) {
        Map<String, double[]> acumulado = new HashMap<>();
        for (Estudante e : lista.getTodos()) {
            List<Matricula> ms = hist.obterMatriculas(e.getId());
            if (ms == null) continue;
            for (Matricula m : ms) {
                double[] arr = acumulado.computeIfAbsent(m.getCodigoDisciplina(), k -> new double[2]);
                arr[0] += m.getNota();
                arr[1] += 1;
            }
        }
        Map<String, Double> medias = new HashMap<>();
        for (Map.Entry<String, double[]> e : acumulado.entrySet()) {
            double soma = e.getValue()[0];
            double cont = e.getValue()[1];
            medias.put(e.getKey(), cont > 0 ? soma / cont : Double.NaN);
        }
        return medias;
    }
}
143