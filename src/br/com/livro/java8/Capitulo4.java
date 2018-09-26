package br.com.livro.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Capitulo4 {
	public static void main(String[] args) {

		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);
		Consumer<Usuario> mostrarMensagen = u -> System.out.println("antes de imprimir os nome");

		Consumer<Usuario> imprimirNome = u -> System.out.println(u.getNome());

		// usamos o metodo andThen para operações que dexejamos executar sequêncialmente
		usuarios.forEach(mostrarMensagen.andThen(imprimirNome));

		// removendo os usuarios com mais de 100 pontos sem o lambda
		Predicate<Usuario> predicate = new Predicate<Usuario>() {

			@Override
			public boolean test(Usuario u) {

				return u.getPontos() > 100;
			}
		};

		List<Usuario> usuarios2 = new ArrayList<>();
		usuarios2.add(user1);
		usuarios2.add(user2);
		usuarios2.add(user3);
		
		// removendo usuarios com mais de 100 pontos com o lambda;
		usuarios2.removeIf(u -> u.getPontos() > 100);
		
		usuarios2.removeIf(predicate);
		usuarios2.forEach(u -> System.out.println(u.getNome()));
	}

}
