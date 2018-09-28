package br.com.livro.java8;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.OptionalDouble;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Capitulo7 {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		usuarios.sort(Comparator.comparing(Usuario::getPontos).reversed());

		// filtrando os usuarios e tornandos moderadores
		usuarios.subList(0, 1).forEach(Usuario::tornaModerador);

		// filtrando os usuarios com mais de 100 pontos
		Stream<Usuario> stream = usuarios.stream();
		stream.filter(u -> u.getPontos() > 100);

		// Simplificando mais ainda a filtragem, porém a filtragem não funciona porque o
		// elemento é da coleção que a originou.
		usuarios.stream().filter(u -> u.getPontos() > 100);
		usuarios.forEach(System.out::println);

		// agora sim e feita a filtragem pós ele intera um novo stream
		Stream<Usuario> stream2 = usuarios.stream().filter(u -> u.getPontos() > 100);
		stream2.forEach(System.out::println);

		// O mesmo codigo mas de forma simplificada. Que lindo *.* !
		usuarios.stream().filter(u -> u.getPontos() > 100).forEach(System.out::println);

		// Tornando moderadores
		usuarios.stream().filter(u -> u.getPontos() > 100).forEach(Usuario::tornaModerador);

		usuarios.stream().filter(Usuario::isModerador);

		// Guardando os usuarios com mais de sem pontos em um Array
		List<Usuario> maisQue100 = new ArrayList<>();
		usuarios.stream().filter(u -> u.getPontos() > 100);
		usuarios.forEach(u -> maisQue100.add(u));

		// Usando o methods references
		usuarios.stream().filter(u -> u.getPontos() > 100);
		usuarios.forEach(maisQue100::add);

		// Coletando os dados de forma mas simplificada com Collect
		usuarios.stream().filter(u -> u.getPontos() > 100).collect(Collectors.toList());

		// coletando informações
		Set<Usuario> set = usuarios.stream().filter(u -> u.getPontos() > 100).collect(Collectors.toSet());

		// Extraindo uma lista com a pontuações dos usuarios
		List<Integer> pontos = usuarios.stream().map(u -> u.getPontos()).collect(Collectors.toList());

		// Utilizando methods references para não gerar autoboxing.
		IntStream stream3 = usuarios.stream().mapToInt(Usuario::getPontos);

		// Obtendo a media de pontos dos usuarios
		double pontuacaoMedia = usuarios.stream().mapToInt(Usuario::getPontos).average().getAsDouble();

		// antes do java 8 a mesma operação
		double soma = 0;
		for (Usuario u : usuarios) {
			soma += u.getPontos();
		}

		// se a lista for vazia retorna isso
		double pontuacaoMedia1 = soma / usuarios.size();
		if (usuarios.isEmpty()) {
			pontuacaoMedia1 = 0;
		} else {
			pontuacaoMedia1 = soma / usuarios.size();
		}

		// Retornando 0 se a lista for vazia com Optional
		OptionalDouble media = usuarios.stream().mapToInt(Usuario::getPontos).average();
		double pontuacaoMedia2 = media.orElse(0.0);

		// simplificando
		double pontuacaoMedia3 = usuarios.stream().mapToInt(Usuario::getPontos).average().orElse(0.0);

		// Lansando um ThrowException
		double pontuacaoMedia4 = usuarios.stream().mapToInt(Usuario::getPontos).average()
				.orElseThrow(IllegalStateException::new);

		// Pegando o usuario com maior ponto
		Optional<Usuario> max = usuarios.stream().max(Comparator.comparingInt(Usuario::getPontos));
		
		// mapeando o usuario com maior ponto peloo nome 
		Optional<String> maxNome = usuarios.stream().max(Comparator.comparingInt(Usuario::getPontos)).map(u -> u.getNome());
	}

}
