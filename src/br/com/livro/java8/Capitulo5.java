package br.com.livro.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

public class Capitulo5 {

	public static void main(String[] args) {
		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		// Ordenado a lista de usuarios sem o lambda
		Comparator<Usuario> comparator = new Comparator<Usuario>() {

			@Override
			public int compare(Usuario u1, Usuario u2) {
				return u1.getNome().compareTo(u2.getNome());
			}
		};
		Collections.sort(usuarios, comparator);

		// Ordenadon a lista com o lambda
		Collections.sort(usuarios, (u1, u2) -> u1.getNome().compareTo(u2.getNome()));

		// Ordenado a lista de uma forma ainda melhor com o lambda.
		usuarios.sort((u1, u2) -> u1.getNome().compareTo(u2.getNome()));

		// Médotos estaticos na interface Comparator

		Comparator<Usuario> comparator2 = Comparator.comparing(u -> u.getNome());
		usuarios.sort(comparator2);
		// Com lambda
		usuarios.sort(Comparator.comparing(u -> u.getNome()));

		// Ordenando a lista
		List<String> palavras = Arrays.asList("Lambda", "Java", "Angular");
		Collections.sort(palavras);

		// Poderia ser feito dessa forma também
		List<String> palavras2 = Arrays.asList("Lambda", "Java", "Angular");
		Collections.sort(palavras);

		palavras2.sort(Comparator.naturalOrder());
		palavras2.sort(Comparator.reverseOrder());

		// Ordenando usuarios por pontos
		usuarios.sort(Comparator.comparing(u -> u.getPontos()));
		// evitar autiboxing
		ToIntFunction<Usuario> extrairPontos = u -> u.getPontos();
		Comparator<Usuario> comparator3 = Comparator.comparingInt(extrairPontos);
		usuarios.sort(comparator);
		// Utilizando o lindo Lambda
		usuarios.sort(Comparator.comparingInt(u -> u.getPontos()));

	}

}
