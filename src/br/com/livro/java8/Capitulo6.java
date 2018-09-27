package br.com.livro.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;

public class Capitulo6 {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);
		// tornando moderador sem o method references
		usuarios.forEach(u -> u.tornaModerador());

		// tornado moderador com o method references
		usuarios.forEach(Usuario::tornaModerador);

		Consumer<Usuario> tornarModerador = Usuario::tornaModerador;
		usuarios.forEach(tornarModerador);

		// usando o lambda para ordenar os nomes
		usuarios.sort(Comparator.comparing(u -> u.getNome()));

		// usando o method references para ordenar os nomes
		usuarios.sort(Comparator.comparing(Usuario::getNome));

		Function<Usuario, String> byName = Usuario::getNome;
		usuarios.sort(Comparator.comparing(byName));

		// ordenando por pontos com method references
		usuarios.sort(Comparator.comparingInt(Usuario::getPontos));

		// Ordenando pelos pontos e em caso de empate, ordenar por nome
		Comparator<Usuario> c = Comparator.comparingInt(Usuario::getPontos).thenComparing(Usuario::getNome);

		// Evitando boxing de tipos primitivos
		usuarios.sort(Comparator.comparingInt(Usuario::getPontos).thenComparing(Usuario::getNome));

		// Ordenando todos os usuarios da lista por nome os que forem null iram para o
		// fim da lista
		usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getNome)));

		// Ordenando os pontos por ordem decrescente
		usuarios.sort(Comparator.comparingInt(Usuario::getPontos).reversed());

		usuarios.sort(Comparator.comparing(Usuario::getNome).reversed());

		// refênciando a um usuario especifico
		Usuario rodrigo = new Usuario("José", 400);
		Runnable bloco = rodrigo::tornaModerador;
		bloco.run();

		// com o method references e o lambda
		Runnable bloco1 = rodrigo::tornaModerador;
		Runnable bloco2 = () -> rodrigo.tornaModerador();

		// exibindo todos os usuarios com os method references
		usuarios.forEach(System.out::println);

		// usando a classe Supplier para quando for necessario cria um novo usario
		// pordemos apenas chamar seu metodo get
		Supplier<Usuario> criadorDeUsuario = Usuario::new;

		// usando o construtor default
		Usuario novo = criadorDeUsuario.get();

		// criando novos usuarios pelo construtor com parametro, apenas utilizando o mentodo apply
		Function<String, Usuario> criadorDeUsuario2 = Usuario::new;
		Usuario paulo = criadorDeUsuario2.apply("Paulo");
		Usuario andre = criadorDeUsuario2.apply("André");
		
		//criando usuarios atraves dos construtores com dois paramentros
		
		BiFunction<String, Integer, Usuario> criadorDeUsuario3 = Usuario::new;
		Usuario ribamar = criadorDeUsuario3.apply("Ribamar", 500);
		Usuario adriana = criadorDeUsuario3.apply("Adriana", 600);
		
		// evitando os unboxing
		
		BiFunction<Integer, Integer, Integer> max = Math::max;
		ToIntBiFunction<Integer, Integer> ma2 = Math::max;
		IntBinaryOperator max3 = Math::max;
		
		
	}

}
