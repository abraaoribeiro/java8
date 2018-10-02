package br.com.livro.java8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Capitulo9 {

	public static void main(String[] args) throws IOException {

		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);
		Usuario user4 = new Usuario("José", 400, true);
		Usuario user5 = new Usuario("Adriana", 500, true);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4, user5);

		// Quantidade de linhas
		LongStream lines = Files.list(Paths.get("./br/com/livro/java8")).filter(p -> p.toString().endsWith(".java"))
				.mapToLong(p -> lines(p).count());

		List<Long> lines2 = Files.list(Paths.get("./br/com/livro/java8")).filter(p -> p.toString().endsWith(".java"))
				.map(p -> lines(p).count()).collect(Collectors.toList());

		// Quantas linhas em cada arquivo
		Map<Path, Long> lines3 = Files.list(Paths.get("./br/com/livro/java"))
				.filter(p -> p.toString().endsWith(".java")).collect(Collectors.toMap(p -> p, p -> lines(p).count()));
		System.out.println(lines);

		// Gerando um mapa para cada arquivo para toda linha
		Map<Path, List<String>> content = Files.list(Paths.get("./br/com/livro/java"))
				.filter(p -> p.toString().endsWith(".java"))
				.collect(Collectors.toMap(Function.identity(), p -> lines(p).collect(Collectors.toList())));

		// Listando dos os usuarios usando o nome do usuario como chave
		Map<String, Usuario> nameTuUser = usuarios.stream()
				.collect(Collectors.toMap(Usuario::getNome, Function.identity()));

		// GroupingBy e partitioningBy
		// Caso não exista uma pontoção um novo usuario é adicionado na lista
		Map<Integer, List<Usuario>> pontuacao = new HashMap<>();

		for (Usuario u : usuarios) {
			if (!pontuacao.containsKey(u.getPontos())) {
				pontuacao.put(u.getPontos(), new ArrayList<>());
			}
			pontuacao.get(u.getPontos()).add(u);
		}
		System.out.println(pontuacao);

		// usando o methods default do Map, o método computerIfAbsent diz caso não
		// encontre o valor chave eke associa o resultado em um ArrayList é o mesmo if
		// do codigo acima
		for (Usuario u : usuarios) {
			pontuacao.computeIfAbsent(u.getPontos(), user -> new ArrayList<>()).add(u);
		}
		System.out.println(pontuacao);

		// Streams
		Map<Integer, List<Usuario>> pontuacao1 = usuarios.stream().collect(Collectors.groupingBy(Usuario::getPontos));

		// Particionando os usuarios entre moderadores e não moderadores
		Map<Boolean, List<Usuario>> moderadores = usuarios.stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador));

		System.out.println(moderadores);

		// Usando uma lista para guardar o nome de cada usuario
		Map<Boolean, List<String>> nomeTipo = usuarios.stream().collect(Collectors.partitioningBy(Usuario::isModerador,
				Collectors.mapping(Usuario::getNome, Collectors.toList())));

		// Verificando se é moderador pela soma dos pontos
		Map<Boolean, Integer> pontuacaoPorTipo = usuarios.stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador, Collectors.summingInt(Usuario::getPontos)));
		System.out.println(pontuacaoPorTipo);

		// concatenando os nomes dos usuarios
		String nomes = usuarios.stream().map(Usuario::getNome).collect(Collectors.joining(", "));

		// Executando o Pipeline em paralelo
		List<Usuario> maiorQue100 = usuarios.stream().filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getPontos)).collect(Collectors.toList());
		// Gerando numeros de 1 a um bilhão
		long sum = LongStream.range(0, 1_000_000_000).parallel().filter(x -> x % 2 == 0).sum();
		System.out.println(sum);

	

	}

	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	

}
