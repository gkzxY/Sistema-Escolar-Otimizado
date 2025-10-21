import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    private static final Estudante[] Primeiro_periodo = {
            new Estudante(1, "Ana"),
            new Estudante(2, "Bruno"),
            new Estudante(3, "Carla"),
            new Estudante(4, "Diego")
    };

    private static final Disciplina[] Materias = {
            new Disciplina("MAT101", "Matemática"),
            new Disciplina("PRG201", "Programação"),
            new Disciplina("FIS102", "Física"),
            new Disciplina("WEB301", "Desenvolvimento Web"),
            new Disciplina("MAT101", "Cálculo I (DUPLICATA)")
    };

    private static final Object[][] notas_por_materialuno = {
            {1, "MAT101", 8.5}, {1, "PRG201", 9.0}, {1, "FIS102", 7.0},
            {2, "MAT101", 5.0}, {2, "PRG201", 6.5},
            {3, "PRG201", 8.0}, {3, "WEB301", 9.5},
            {4, "FIS102", 4.0}, {4, "WEB301", 5.5}
    };

    public static void main(String[] args) {
        ListaEstudantes lista = new ListaEstudantes();
        CadastroDisciplinas cadastro = new CadastroDisciplinas();
        HistoricoNotas historico = new HistoricoNotas(lista, cadastro);

        for (Estudante e : Primeiro_periodo) lista.adicionarEstudante(e);

        List<String> duplicatasDetectadas = new ArrayList<>();
        for (Disciplina d : Materias) {
            boolean ok = cadastro.adicionarDisciplina(d);
            if (!ok) duplicatasDetectadas.add(d.getCodigo());
        }

        for (Object[] reg : notas_por_materialuno) {
            int id = (int) reg[0];
            String cod = (String) reg[1];
            double nota = ((Number) reg[2]).doubleValue();
            historico.adicionarMatricula(id, cod, nota);
        }

        gerarOutputTxt(lista, cadastro, historico, duplicatasDetectadas);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1) Adicionar novo estudante");
            System.out.println("2) Adicionar nova disciplina");
            System.out.println("3) Matricular estudante em disciplina");
            System.out.println("4) Lançar ou atualizar nota de matrícula existente");
            System.out.println("5) Listar todos os estudantes");
            System.out.println("6) Listar todas as disciplinas");
            System.out.println("7) Exibir nome e notas de todos os estudantes");
            System.out.println("8) Exibir média de cada estudante");
            System.out.println("9) Remover estudante por ID");
            System.out.println("10) Remover disciplina por código");
            System.out.println("11) Remover matrícula específica");
            System.out.println("12) Exibir estudante com melhor média");
            System.out.println("13) Informativo Geral");
            System.out.println("14) Exibir os 5 melhores estudantes por média");
            System.out.println("15) Buscar estudantes por nome");
            System.out.println("16) Obter estudante por índice");
            System.out.println("17) Regenerar arquivo output.txt");
            System.out.println("0) Sair do sistema");
            System.out.print("Digite sua opção: ");

            String opcao = sc.nextLine().trim();

            switch (opcao) {
                case "1": {
                    System.out.print("ID do estudante: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Nome do estudante: ");
                    String nome = sc.nextLine();
                    boolean ok = lista.adicionarEstudante(new Estudante(id, nome));
                    System.out.println(ok ? "Estudante adicionado com sucesso." : "Falha: ID já existente.");
                    break;
                }

                case "2": {
                    System.out.print("Código da disciplina: ");
                    String codigo = sc.nextLine();
                    System.out.print("Nome da disciplina: ");
                    String nomeDisc = sc.nextLine();
                    boolean addOk = cadastro.adicionarDisciplina(new Disciplina(codigo, nomeDisc));
                    if (!addOk) duplicatasDetectadas.add(codigo);
                    System.out.println(addOk ? "Disciplina adicionada com sucesso." : "Falha: disciplina duplicada ou inválida.");
                    break;
                }

                case "3": {
                    System.out.print("ID do estudante: ");
                    int idMatricula = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codigoDisciplina = sc.nextLine();
                    boolean matriculado = historico.adicionarMatricula(idMatricula, codigoDisciplina, 0.0);
                    System.out.println(matriculado
                            ? "Estudante matriculado com sucesso (nota padrão 0.0)."
                            : "Falha: estudante ou disciplina inexistente, ou nota inválida.");
                    break;
                }

                case "4": {
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
                }

                case "5": {
                    lista.ordenarEstudantesPorNome();
                    System.out.println("\n=== Estudantes (ordenados por nome) ===");
                    for (Estudante e : lista.getTodos())
                        System.out.println(" - " + e);
                    break;
                }

                case "6": {
                    System.out.println("\n=== Disciplinas (ordem de inserção) ===");
                    for (Disciplina d : cadastro.obterTodasDisciplinas())
                        System.out.println(" - " + d);
                    break;
                }

                case "7": {
                    System.out.println("\n=== Nome e notas de todos os estudantes ===");
                    historico.exibirNomesENotasDeTodos();
                    break;
                }

                case "8": {
                    System.out.println("\n=== Média de cada estudante ===");
                    for (Estudante e : lista.getTodos()) {
                        double media = historico.mediaDoEstudante(e.getId());
                        String msg = Double.isNaN(media) ? "Sem notas" : String.format("%.2f", media);
                        System.out.println(" - " + e.getNome() + ": " + msg);
                    }
                    break;
                }

                case "9": {
                    System.out.print("Digite o ID do estudante para remover: ");
                    int idRem = Integer.parseInt(sc.nextLine());
                    boolean remOk = lista.removerEstudantePorId(idRem);
                    if (remOk) historico.removerTodasMatriculasDoEstudante(idRem);
                    System.out.println(remOk ? "Estudante removido." : "Estudante não encontrado.");
                    break;
                }

                case "10": {
                    System.out.print("Digite o código da disciplina para remover: ");
                    String codRem = sc.nextLine();
                    boolean remDisc = cadastro.removerDisciplina(codRem);
                    System.out.println(remDisc ? "Disciplina removida." : "Disciplina não encontrada.");
                    break;
                }

                case "11": {
                    System.out.print("ID do estudante: ");
                    int idMat = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codMat = sc.nextLine();
                    boolean remMat = historico.removerMatricula(idMat, codMat);
                    System.out.println(remMat ? "Matrícula removida." : "Matrícula não encontrada.");
                    break;
                }

                case "12": {
                    List<Estudante> top1 = historico.topNEstudantesPorMedia(1);
                    if (top1.isEmpty()) System.out.println("Nenhum dado disponível.");
                    else {
                        Estudante e = top1.get(0);
                        double media = historico.mediaDoEstudante(e.getId());
                        System.out.printf("Melhor estudante: %s (%.2f)%n", e.getNome(), media);
                    }
                    break;
                }

                case "13": {
                    exibirInformativoGeralNoConsole(lista, cadastro, historico, duplicatasDetectadas);
                    break;
                }

                case "14": {
                    List<Estudante> top5 = historico.topNEstudantesPorMedia(5);
                    if (top5.isEmpty()) {
                        System.out.println("Nenhum dado disponível.");
                    } else {
                        System.out.println("\n=== Top 5 estudantes por média ===");
                        int pos = 1;
                        for (Estudante e : top5) {
                            double media = historico.mediaDoEstudante(e.getId());
                            System.out.printf("%dº - %s (%.2f)%n", pos++, e.getNome(), media);
                        }
                    }
                    break;
                }

                case "15": {
                    System.out.print("Digite um trecho do nome para buscar: ");
                    String termo = sc.nextLine();
                    List<Estudante> achados = lista.buscarEstudantesPorNome(termo);
                    if (achados.isEmpty()) {
                        System.out.println("Nenhum estudante encontrado.");
                    } else {
                        System.out.println("\n=== Estudantes encontrados ===");
                        for (Estudante e : achados) System.out.println(" - " + e);
                    }
                    break;
                }
                
                case "16": {
                    System.out.print("Digite o índice (0 baseado): ");
                    int indice = Integer.parseInt(sc.nextLine());
                    Estudante e = lista.obterEstudantePorIndice(indice);
                    if (e == null) System.out.println("Índice inválido.");
                    else System.out.println("Estudante no índice " + indice + ": " + e);
                    break;
                }

                case "17": {
                    gerarOutputTxt(lista, cadastro, historico, duplicatasDetectadas);
                    System.out.println("Arquivo output.txt regenerado com sucesso.");
                    break;
                }

                case "0":
                    System.out.println("Encerrando o sistema...");
                    return;

                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private static void exibirInformativoGeralNoConsole(
            ListaEstudantes lista,
            CadastroDisciplinas cadastro,
            HistoricoNotas historico,
            List<String> duplicatasDetectadas
    ) {
        lista.ordenarEstudantesPorNome();
        System.out.println("\n===== INFORMATIVO GERAL =====");

        System.out.println("\nEstudantes (ordenados por nome):");
        for (Estudante e : lista.getTodos())
            System.out.println(" - " + e);

        System.out.println("\nDisciplinas (ordem de inserção):");
        for (Disciplina d : cadastro.obterTodasDisciplinas())
            System.out.println(" - " + d);

        System.out.println("\nDisciplinas e notas por estudante:");
        historico.exibirNomesENotasDeTodos();

        System.out.println("\nAlunos com média maior ou igual a 8.0:");
        for (Estudante e : lista.getTodos()) {
            double media = historico.mediaDoEstudante(e.getId());
            if (!Double.isNaN(media) && media >= 8.0) {
                System.out.printf(" - %s (%.2f)%n", e.getNome(), media);
            }
        }

        System.out.println("\nDisciplinas com média menor que 6.0:");
        Map<String, Double> mediasDisc = calcularMediasPorDisciplina(lista, historico);
        for (Map.Entry<String, Double> en : mediasDisc.entrySet()) {
            if (en.getValue() < 6.0) {
                System.out.printf(" - %s (%.2f)%n", en.getKey(), en.getValue());
            }
        }

        System.out.println("\nDuplicatas de disciplinas detectadas:");
        if (duplicatasDetectadas.isEmpty())
            System.out.println(" - Nenhuma duplicata detectada.");
        else
            for (String cod : duplicatasDetectadas)
                System.out.println(" - " + cod);
    }

    private static void gerarOutputTxt(
            ListaEstudantes lista,
            CadastroDisciplinas cadastro,
            HistoricoNotas historico,
            List<String> duplicatasDetectadas
    ) {
        lista.ordenarEstudantesPorNome();
        try (PrintWriter out = new PrintWriter(new FileWriter("output.txt", false))) {
            out.println("===== INFORMATIVO GERAL =====");

            out.println();
            out.println("Estudantes (ordenados por nome):");
            for (Estudante e : lista.getTodos())
                out.println(" - " + e);

            out.println();
            out.println("Disciplinas (ordem de inserção):");
            for (Disciplina d : cadastro.obterTodasDisciplinas())
                out.println(" - " + d);

            out.println();
            out.println("Disciplinas e notas por estudante:");
            for (Estudante e : lista.getTodos()) {
                List<Matricula> ms = historico.obterMatriculas(e.getId());
                out.println(e.getNome() + ": " + (ms.isEmpty() ? "sem notas" : ms));
            }

            out.println();
            out.println("Alunos com média maior ou igual a 8.0:");
            for (Estudante e : lista.getTodos()) {
                double media = historico.mediaDoEstudante(e.getId());
                if (!Double.isNaN(media) && media >= 8.0) {
                    out.printf(" - %s (%.2f)%n", e.getNome(), media);
                }
            }

            out.println();
            out.println("Disciplinas com média menor que 6.0:");
            Map<String, Double> mediasDisc = calcularMediasPorDisciplina(lista, historico);
            for (Map.Entry<String, Double> en : mediasDisc.entrySet()) {
                if (en.getValue() < 6.0) {
                    out.printf(" - %s (%.2f)%n", en.getKey(), en.getValue());
                }
            }

            out.println();
            out.println("Duplicatas de disciplinas detectadas:");
            if (duplicatasDetectadas.isEmpty())
                out.println(" - Nenhuma duplicata detectada.");
            else
                for (String cod : duplicatasDetectadas)
                    out.println(" - " + cod);

        } catch (IOException e) {
            System.err.println("Falha ao escrever output.txt: " + e.getMessage());
        }
    }

    private static Map<String, Double> calcularMediasPorDisciplina(ListaEstudantes lista, HistoricoNotas hist) {
        Map<String, double[]> acumulado = new HashMap<>();
        for (Estudante e : lista.getTodos()) {
            List<Matricula> ms = hist.obterMatriculas(e.getId());
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
