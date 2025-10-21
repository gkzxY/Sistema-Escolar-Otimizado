# Sistema-Escolar-Otimizado
Alunos: Gabriel Kauã Borges da Silva, Isael Canuto de Carvalho Neto, Ana Clara Carvalho Batista
Este projeto é um sistema escolar básico feito em Java, que permite realizar ações como:

Cadastrar estudantes e disciplinas

Matricular estudantes nas disciplinas

Atribuir notas

Gerar um arquivo de saída (output.txt) com os dados processados

Justificativa das escolhas de coleções:
List: Utilizada para armazenar alunos e disciplinas em ordem de inserção, pois pode haver repetição e a ordem importa para listagens.

Set: Utilizado para garantir que não haja duplicidade nas disciplinas ou alunos matriculados em uma mesma turma.

Map: Usado para associar chaves a valores, como por exemplo, vincular o nome do aluno às suas notas, facilitando a busca e organização das informações.

Como executar o progama:
Clone o repositório: git clone e cd
Compile os arquivos Java:
javac *.java
Execute o programa:
java Main
Arquivo gerado:
O programa criará um arquivo chamado output.txt com os resultados processados.

Desafios Encontrados:
A principal dificuldade foi organizar as relações entre alunos, disciplinas e notas de forma eficiente.
Aprender a usar corretamente o Map<String, List<Double>> para armazenar notas foi desafiador no início.
Também tivemos que lidar com problemas de repetição de dados, que foram resolvidos com o uso de Set.
