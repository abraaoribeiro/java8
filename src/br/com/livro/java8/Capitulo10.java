package br.com.livro.java8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class Capitulo10 {

	public static void main(String[] args) {

		// incrementando um mês com Calendar
		Calendar mesQueVem = Calendar.getInstance();
		// mesQueVem.add(Calendar.MONTH, 1);

		// icriementando um meês com LocalDate
		LocalDate date = LocalDate.now().plusMonths(1);
		System.out.println(date);

		// subtraindo um ano
		LocalDate anoPassado = LocalDate.now().minusYears(1);
		System.out.println(anoPassado);
		// Data e Hora Atual
		LocalDateTime agora = LocalDateTime.now();
		System.out.println(agora);

		// Apenas Hora
		LocalTime localTime = LocalTime.now();
		System.out.println(localTime);

		// Horario Especifico
		LocalDateTime hojeAoMeioDia = LocalDate.now().atTime(12, 0);
		System.out.println(hojeAoMeioDia);

		// construindo um LocalDateTime pela junção de um LocalDate com LocalTime
		LocalTime localTime2 = LocalTime.now();
		LocalDate date2 = LocalDate.now();
		LocalDateTime dataEHora = date2.atTime(localTime2);
		System.out.println(dataEHora);
		
		//Modelo com timezone
		//ZonedDateTime dataComHoraTimeZone = dataEHora.atZone(ZoneId.of("America/Belém"));
		
		//Convertendo de TimeZone para LocalDateTime
		//LocalDateTime semTimeZone = dataComHoraTimeZone.toLocalDateTime();
		
		// criando a partir do factory method *of*
		LocalDate date3 = LocalDate.of(2018, 9, 03);
		LocalDateTime dateTime = LocalDateTime.of(2018, 9, 03, 9, 40);
		System.out.println(dateTime);
		
		//Modificando o ano e recuperando o valor, Para recuperar o mês seria getMonth e assim por diante
		LocalDate passado = LocalDate.now().withYear(2015);
		System.out.println(passado.getYear());
	
		//Informações de antes,depois ou ao mesmo tempo
		LocalDate hoje = LocalDate.now();
		LocalDate amanha = LocalDate.now().plusDays(1);
		System.out.println(hoje.isBefore(amanha));
		System.out.println(hoje.isAfter(amanha));
		System.out.println(hoje.isEqual(amanha));
		
		//comparando datas iguais em timezones diferentes
		ZonedDateTime tokyo = ZonedDateTime.of(2018, 9, 3, 10, 30, 30, 0,ZoneId.of("Asia/Tokyo"));
		
		ZonedDateTime belem = ZonedDateTime.of(2018, 9, 3, 10, 30, 30, 0, ZoneId.of("Belém"));
		
		System.out.println(tokyo.isEqual(belem));
		
		
		
		
	}

}
