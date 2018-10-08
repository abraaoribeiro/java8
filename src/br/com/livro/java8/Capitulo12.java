package br.com.livro.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Capitulo12 {
	public static void main(String[] args) {
		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		// necessidade de tipagem para referencia correta

		usuarios.sort(Comparator.comparingInt((Usuario u) -> u.getPontos()).thenComparing(u -> u.getNome()));

		usuarios.sort(Comparator.<Usuario, Integer>comparing(u -> u.getPontos()).reversed());

	}
}
