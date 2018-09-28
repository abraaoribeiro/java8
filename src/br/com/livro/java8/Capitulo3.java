package br.com.livro.java8;

public class Capitulo3 {
	public static void main(String[] args) {
		// Contando de 0 a 1000 / Sem Lambda
		Runnable r = new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i <= 1000; i++) {
					System.out.println(i);
				}
			}
		};

		// Contando de 0 a 1000 / Com lambda
		new Thread(r).start();

		Runnable run = () -> {
			for (int i = 0; i <= 1000; i++) {
				System.out.println(i);
			}
		};
		new Thread(run).start();

		// Lambda *.*
		new Thread(() -> {
			for (int i = 0; i < args.length; i++) {
				System.out.println(i);
			}
		}).start();

		Runnable o = () -> {
			System.out.println("Eu sou o Lambda #º#");

		};
		System.out.println(0);
		System.out.println(o.getClass());
	
		}
	
		@FunctionalInterface
		interface Validador<T> {
			boolean valida(T t);
	
		}
	
		Validador<String> validarCEP = new Validador<String>() {
			public boolean valida(String valor) {
				return valor.matches("[0-9{5}-[0-9{3}");
			}
		};
	
		Validador<String> validarCep = valor -> {
			return valor.matches("[0-9{5}[0-9{3}");
		};
		// Diminuido mais a expressao com o lambda
		Validador<String> validarCep1 = valor -> valor.matches("[0-9{5}[0-9{3}");
	
	}
