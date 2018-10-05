package br.com.livro.java8;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Product {

	private String name;
	private Path file;
	private BigDecimal price;

	public Product(String name, Path file, BigDecimal price) {
		this.name = name;
		this.file = file;
		this.price = price;
	}

	public String getName() {
		return this.name;
	}

	public Path getFile() {
		return this.file;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public String toString() {
		return this.name;
	}

}

class Customer {

	private String name;

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name;
	}

}

class Payment {

	private List<Product> products;
	private LocalDateTime date;
	private Customer customer;

	public Payment(List<Product> products, LocalDateTime date, Customer customer) {

		this.products = Collections.unmodifiableList(products);
		this.customer = customer;
		this.date = date;
	}

	public List<Product> getProduct() {
		return this.products;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public String toString() {
		return "[Payment:" + date.format(DateTimeFormatter.ofPattern("dd/MM,yy")) + " " + customer + " " + products
				+ "]";
	}

}

public class Capitulo11 {

	public static void main(String[] agrs) {

		Customer abraao = new Customer("Abraão");
		Customer jose = new Customer("José");
		Customer adriana = new Customer("Adriana");
		Customer mathews = new Customer("Mathews");

		Product bach = new Product("Bach Completo", Paths.get("/music/bach.mp3"), new BigDecimal(100));
		Product poderosas = new Product("Poderosas Anita", Paths.get("/music/poderosas.mp3"), new BigDecimal(90));
		Product bandeira = new Product("Bandeira Brasil", Paths.get("/images/brasil.jpg"), new BigDecimal(50));
		Product beauty = new Product("Beleza Americana", Paths.get("beauty.mov"), new BigDecimal(150));
		Product vingadores = new Product("Os Vingadores", Paths.get("/movies/vingadores.mov"), new BigDecimal(200));
		Product amelie = new Product("Amelie Poulain", Paths.get("/movies/amelie.mov"), new BigDecimal(100));

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime yesterday = today.minusDays(1);
		LocalDateTime lastMoth = today.minusMonths(1);

		Payment payment1 = new Payment(Arrays.asList(bach, poderosas), today, abraao);
		Payment payment2 = new Payment(Arrays.asList(beauty, vingadores), yesterday, mathews);
		Payment payment3 = new Payment(Arrays.asList(bandeira, amelie), today, adriana);
		Payment payment4 = new Payment(Arrays.asList(poderosas, beauty), lastMoth, jose);
		Payment payment5 = new Payment(Arrays.asList(bach, vingadores), yesterday, abraao);

		// Listando todos as compras ordendo e as imprimindo
		List<Payment> payments = Arrays.asList(payment1, payment2, payment3, payment4, payment5);
		payments.stream().sorted(Comparator.comparing(Payment::getDate)).forEach(System.out::println);

		// somatoria do valor total do payments1
		payment1.getProduct().stream().map(Product::getPrice).reduce(BigDecimal::add).ifPresent(System.out::println);

		BigDecimal total = payment1.getProduct().stream().map(Product::getPrice).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		// somatoria de todos os payments
		Stream<BigDecimal> princesStream = payments.stream()
				.map(p -> p.getProduct().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

		BigDecimal total2 = payments.stream()
				.map(p -> p.getProduct().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Stream<BigDecimal> priceOFEachProduct = payments.stream()
				.flatMap(p -> p.getProduct().stream().map(Product::getPrice));

		Function<Payment, Stream<BigDecimal>> mapper = p -> p.getProduct().stream().map(Product::getPrice);

		BigDecimal totalFlat = payments.stream().flatMap(p -> p.getProduct().stream().map(Product::getPrice))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// Produdos mais vendidos
		// Stream<Product> products =
		// payments.stream().map(Payment::getProduct).flatMap(p -> p.stream());

		// Mas simplificado
		Stream<Product> products = payments.stream().flatMap(p -> p.getProduct().stream());

		// Poderia ser feito com o methods referencs
		// Stream<Product> p =
		// payments.stream().map(Payment::getProduct).flatMap(List::stream);

		// mapea os produtos mais vendidos
		Map<Product, Long> topProducts = payments.stream().flatMap(p -> p.getProduct().stream())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		// System.out.println(topProducts);
		// Imprimido linha a linhas
		topProducts.entrySet().forEach(System.out::println);

		// Pegando apenas o maior valor
		topProducts.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).ifPresent(System.out::println);

		// Valores gerados por produto
		Map<Product, BigDecimal> totalDoValorProduto = payments.stream().flatMap(p -> p.getProduct().stream())
				.collect(Collectors.groupingBy(Function.identity(),
						Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)));

		totalDoValorProduto.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
				.forEach(System.out::println);

		// Quais são os Produtos de Cada Cliente
		Map<Customer, List<List<Product>>> customerProductList = payments.stream().collect(Collectors
				.groupingBy(Payment::getCustomer, Collectors.mapping(Payment::getProduct, Collectors.toList())));

		customerProductList.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().getName()))
				.forEach(System.out::println);

		// Valores das entradas achatadas é o mesmo resultado porém sem utilizar listas
		// alinhadas
		Map<Customer, List<Product>> custumerProductsList2 = customerProductList.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey,
						e -> e.getValue().stream().flatMap(List::stream).collect(Collectors.toList())));

		custumerProductsList2.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().getName()))
				.forEach(System.out::println);

		// System.out.println("Aqui é com o flatMap");
		Map<Customer, List<Product>> map = payments.stream()
				.collect(Collectors.groupingBy(Payment::getCustomer,
						Collectors.mapping(Payment::getProduct, Collectors.toList())))
				.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
						e -> e.getValue().stream().flatMap(List::stream).collect(Collectors.toList())));

		map.entrySet().stream().sorted(Comparator.comparing(m -> m.getKey().getName())).forEach(System.out::println);

		// usando o reduce
		Map<Customer, List<Product>> map2 = payments.stream().collect(Collectors.groupingBy(Payment::getCustomer,
				Collectors.reducing(Collections.emptyList(), Payment::getProduct, (l1, l2) -> {
					List<Product> l = new ArrayList<>();
					l.addAll(l1);
					return l;
				})));

		map2.entrySet().stream().sorted(Comparator.comparing(m -> m.getKey().getName())).forEach(System.out::println);

		// Cliente mais especial

		Map<Customer, BigDecimal> map3 = payments.stream()
				.collect(Collectors.groupingBy(Payment::getCustomer, Collectors.reducing(BigDecimal.ZERO,
						p -> p.getProduct().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add),
						BigDecimal::add)));

		Function<Payment, BigDecimal> paymentTotal = p -> p.getProduct().stream().map(Product::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Map<Customer, BigDecimal> totalValuePerCustomer = payments.stream().collect(Collectors
				.groupingBy(Payment::getCustomer, Collectors.reducing(BigDecimal.ZERO, paymentTotal, BigDecimal::add)));

		totalValuePerCustomer.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
				.forEach(System.out::println);

		// Separado os pagamentos por Data
		Map<YearMonth, List<Payment>> paymentsPerMoth = payments.stream()
				.collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate())));

		paymentsPerMoth.entrySet().stream().forEach(System.out::println);
		
		//faturamento da loja
		
		
		
		
		
		
	}

}
