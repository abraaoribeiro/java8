package br.com.livro.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Capitulo8 {

	public static void main(String[] args) throws IOException {

		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		// Filtrando os usuarios com mais de 100 pontos e os ordenando
		List<Usuario> filtrarOsOrdenados = usuarios.stream().filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getNome)).collect(Collectors.toList());

		usuarios.stream().filter(u -> u.getPontos() > 100).sorted(Comparator.comparing(Usuario::getNome));

		// Antes do java 8
		List<Usuario> usuariosFiltrados = new ArrayList<>();
		for (Usuario u : usuarios) {
			if (u.getPontos() > 100) {
				usuariosFiltrados.add(u);
			}

		}
		Collections.sort(usuariosFiltrados, new Comparator<Usuario>() {
			public int compare(Usuario u1, Usuario u2) {
				return u1.getNome().compareTo(u2.getNome());
			}

		});

		// Usuarios com mais de 100 pontos

		Usuario maisDe100 = usuarios.stream().filter(u -> u.getPontos() > 100).collect(Collectors.toList()).get(0);

		// Usuarios com mais de 100 pontos lançando um exception

		Optional<Usuario> optional = usuarios.stream().filter(u -> u.getPontos() > 100).findAny();

		// Executando uma tarefa toda vez que for processado um elemento usando o peek
		usuarios.stream().filter(u -> u.getPontos() > 100).peek(System.out::println);

		// Ordenando uma tarefa toda vez que for processado um elemento usando o peek
		usuarios.stream().sorted(Comparator.comparing(Usuario::getNome)).peek(System.out::println).findAny();

		// Operações de Redução
		double pontuacaoMedia = usuarios.stream().mapToInt(Usuario::getPontos).average().getAsDouble();

		Optional<Usuario> max = usuarios.stream().max(Comparator.comparing(Usuario::getPontos));
		Usuario maxmimaPontuacao = max.get();

		// somando todos os pontos
		int total = usuarios.stream().mapToInt(Usuario::getPontos).sum();

		// Desmebrendo o sum()
		int valorInicial = 0;
		IntBinaryOperator operator = (a, b) -> a + b;
		int total1 = usuarios.stream().mapToInt(Usuario::getPontos).reduce(valorInicial, operator);

		// A mesma operação sem variaveis clobais
		int total2 = usuarios.stream().mapToInt(Usuario::getPontos).reduce(0, (a, b) -> a + b);

		// Somando dois inteiros usando o methods references
		int total3 = usuarios.stream().mapToInt(Usuario::getPontos).reduce(0, Integer::sum);

		// Multiplicando todos os pontos
		int multiplicacao = usuarios.stream().mapToInt(Usuario::getPontos).reduce(1, (a, b) -> a * b);

		// soma sem o map
		int total4 = usuarios.stream().reduce(0, (atual, u) -> atual + u.getPontos(), Integer::sum);

		// Trabalhando com Iterators
		Iterator<Usuario> i = usuarios.stream().iterator();
		// forechaRenaunig

		usuarios.stream().iterator().forEachRemaining(System.out::println);

		// testando predicates para saber ser se algum elemento da lista é moderador

		boolean hasModerador = usuarios.stream().anyMatch(Usuario::isModerador);
		// Gerando uma lista infinita de numeros aleatorios

		Random random = new Random(0);

		Supplier<Integer> supplier = () -> random.nextInt();
		Stream<Integer> stream = Stream.generate(supplier);

		// A mesma operação sem variaveis locais e evitando o boxing
		IntStream intStream = IntStream.generate(() -> random.nextInt());

		// Gerando sequẽncia infinita de numeros de Fibonacci Imprimindo os 10 primeiros
		// elementos
		class Fibonacci implements IntSupplier {
			private int anterior = 0;
			private int proximo = 1;

			@Override
			public int getAsInt() {
				proximo = proximo + anterior;
				anterior = anterior - proximo;
				return anterior;
			}

		}
		IntStream.generate(new Fibonacci()).limit(10).forEach(System.out::println);

		// pegando o primeiro elemento maior que 100
		int maior100 = IntStream.generate(new Fibonacci()).filter(f -> f > 100).findFirst().getAsInt();
		System.out.println(maior100);

		IntStream.iterate(0, x -> x + 1).limit(10).forEach(System.out::println);

		// listando todos os arquivos de um diretorio

		Files.list(Paths.get("./br/com/livro/java8")).forEach(System.out::println);

		// pegando apenas arquivos java
		Files.list(Paths.get("./br/com/livro/java8")).filter(p -> p.toString().endsWith(".java"))
				.forEach(System.out::println);

		// flatMap

		class Grupo {
			private Set<Usuario> usuarios = new HashSet<>();

			public void add(Usuario u) {
				usuarios.add(u);

			}

			public Set<Usuario> getUsuarios() {
				return Collections.unmodifiableSet(this.usuarios);
			}
		}
		Grupo englishSpeakers = new Grupo();
		englishSpeakers.add(user1);
		englishSpeakers.add(user2);
		Grupo spanishSpeakers = new Grupo();

		spanishSpeakers.add(user2);
		spanishSpeakers.add(user3);

		List<Grupo> groups = Arrays.asList(englishSpeakers, spanishSpeakers);

		groups.stream().flatMap(g -> g.getUsuarios().stream().distinct()).forEach(System.out::println);
	}

}
