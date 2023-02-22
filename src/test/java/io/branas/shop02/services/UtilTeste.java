package io.branas.shop02.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UtilTeste {

	@Test
	public void retornaFalsePorStringVazia() {
		assertEquals(false, ValidaCPF.isCpfValido(""));
	}
	
	@Test
	public void retornaFalsePorStringNula() {
		assertEquals(false, ValidaCPF.isCpfValido(null));
	}
	
	@Test
	public void retornaFalsePorStringMaiorQue14() {
		assertEquals(false, ValidaCPF.isCpfValido("3214-56879.3.21.456"));
	}
	
	@Test
	public void retornaFalsePorStringMenorQue11() {
		assertEquals(false, ValidaCPF.isCpfValido("3214.51456"));
	}
	
	@Test
	public void retornaTruePorCPFValido() {
		assertEquals(true, ValidaCPF.isCpfValido("111.444.777-35"));
		assertEquals(true, ValidaCPF.isCpfValido("697.801.812-02"));
	}
	
	@Test
	public void retornaFalsePorCPFInvalido() {
		assertEquals(false, ValidaCPF.isCpfValido("111.444.777-05"));
		assertEquals(false, ValidaCPF.isCpfValido("111.444.777-32"));
		assertEquals(false, ValidaCPF.isCpfValido("697.801.812-12"));
	}
	
}
