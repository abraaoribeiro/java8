package br.com.livro.java8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

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

		// Modelo com timezone
		// ZonedDateTime dataComHoraTimeZone =
		// dataEHora.atZone(ZoneId.of("America/Belém"));

		// Convertendo de TimeZone para LocalDateTime
		// LocalDateTime semTimeZone = dataComHoraTimeZone.toLocalDateTime();

		// criando a partir do factory method *of*
		LocalDate date3 = LocalDate.of(2018, 9, 03);
		LocalDateTime dateTime = LocalDateTime.of(2018, 10, 03, 9, 40);
		System.out.println(dateTime);

		// Modificando o ano e recuperando o valor, Para recuperar o mês seria getMonth
		// e assim por diante
		LocalDate passado = LocalDate.now().withYear(2015);
		System.out.println(passado.getYear());

		// Informações de antes,depois ou ao mesmo tempo
		LocalDate hoje = LocalDate.now();
		LocalDate amanha = LocalDate.now().plusDays(1);
		System.out.println(hoje.isBefore(amanha));
		System.out.println(hoje.isAfter(amanha));
		System.out.println(hoje.isEqual(amanha));

		// comparando datas iguais em timezones diferentes
		// ZonedDateTime tokyo = ZonedDateTime.of(2018, 9, 3, 10, 30, 30, 0,
		// ZoneId.of("Asia/Tokyo"));

		// ZonedDateTime belem = ZonedDateTime.of(2018, 9, 3, 10, 30, 30, 0,
		// ZoneId.of("Belém"));

		// tokyo.plusHours(12);

		// System.out.println(tokyo.isEqual(belem));

		// Obtendo o dia Atual
		System.out.println("Hoje é Dia: " + MonthDay.now().getDayOfMonth());

		// pegando o ano de uma determinada data
		YearMonth ym = YearMonth.from(date);
		System.out.println(ym.getMonth() + " " + ym.getYear());

		// Usando Enums
		System.out.println(LocalDate.of(2018, 10, 04));
		System.out.println(LocalDate.of(2018, Month.OCTOBER, 04));

		// Incrementrar e decrementar
		System.out.println(Month.OCTOBER.firstMonthOfQuarter());
		System.out.println(Month.OCTOBER.plus(2));
		System.out.println(Month.OCTOBER.minus(1));

		// Formantando o nome de um mês

		Locale pt = new Locale("pt");
		System.out.println(Month.OCTOBER.getDisplayName(TextStyle.FULL, pt));
		System.out.println(Month.OCTOBER.getDisplayName(TextStyle.SHORT, pt));

		// Pegando o dia atual
		System.out.println(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, pt));

		// Formatando em Horas

		LocalDateTime agora1 = LocalDateTime.now();

		String resultado = agora1.format(DateTimeFormatter.ISO_DATE_TIME);
		System.out.println("Data e Hora: " + resultado);

		// Mesma operação com o novo padrão
		String resul = agora1.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
		System.out.println(resul);

		// Coverrtendo
		DateTimeFormatter formatado = DateTimeFormatter.ofPattern("dd/MM/yy");
		String r = agora1.format(formatado);
		LocalDate agoraEmData = LocalDate.parse(r, formatado);
		System.out.println(agoraEmData);

		// diferença de dias com ChronoUnit
		LocalDate outraData = LocalDate.of(1995, Month.JANUARY, 25);
		long dias = ChronoUnit.DAYS.between(outraData, agora1);
		long meses = ChronoUnit.MONTHS.between(outraData, agora1);
		long anos = ChronoUnit.YEARS.between(outraData, agora1);
		System.out.printf("%s dias, %s meses, %s anos", dias, meses, anos);

		// o mesmo resultado entre duas datas
		LocalDate agora2 = LocalDate.now();
		Period period = Period.between(outraData, agora2);
		System.out.printf("%s dias, %s meses, %s anos", period.getDays(), period.getMonths(), period.getYears());

		// invertendo valores do periodo
		LocalDate outraData2 = LocalDate.of(2019, Month.APRIL, 25);
		Period periodo = Period.between(outraData2, agora2);
		if (periodo.isNegative()) {
			periodo = periodo.negated();
		}
		System.out.printf("%s dias, %s meses, %s anos", periodo.getDays(), periodo.getMonths(), periodo.getYears());

		// medidas Horas minutos segundos
		LocalDateTime daquiUmaHora = LocalDateTime.now().plusHours(1);
		Duration duration = Duration.between(daquiUmaHora, agora);

		if (duration.isNegative()) {
			duration = duration.negated();
		}
		System.out.printf("%s horas, %s minutos, %s segundos", duration.toHours(), duration.toMinutes(),
				duration.getSeconds());
		
		
	}

}
