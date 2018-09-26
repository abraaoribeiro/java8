package br.com.livro.java8;

import java.util.Arrays;
import java.util.List;

public class Capitulo2 {

	public static void main(String[] args) {
		
		Usuario user1 = new Usuario("Abraão", 100);
		Usuario user2 = new Usuario("João", 200);
		Usuario user3 = new Usuario("Maria", 300);
		
		List<Usuario> usuarios = Arrays.asList(user1, user2,user3);
		
		//Listando os Usuarios sem o lambda 
		for(Usuario u : usuarios) {
			System.out.println(u.getNome());
		}
		
		//Listando os Usuarios com Lambda :)
		usuarios.forEach(u -> System.out.println(u.getNome()));
		
		//Tornando moderador 
		usuarios.forEach(u -> u.tornaModerador());

	}

}
