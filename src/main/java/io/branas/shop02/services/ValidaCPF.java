package io.branas.shop02.services;

public class ValidaCPF {

	public static boolean isCpfValido(String cpf) {
		if(validaCaractesCPF(cpf)) 
			return false;
		cpf = retiraCaracteresEspeciaisDoCPF(cpf);
		String digitoVerificador1 = cpf.substring(cpf.length()-2, cpf.length()-1);
		String digitoVerificador2 = cpf.substring(cpf.length()-1, cpf.length());
		String digitosParaCalculo = cpf.substring(0, cpf.length()-2);
		String[] array = digitosParaCalculo.split("(?!^)");
		int valorSomado = calculaMultiplicacaoSoma(array , 10);
		int primeiroDigito = calculaDigito(valorSomado);
		if(!digitoVerificador1.equals(String.valueOf(primeiroDigito))) return false;
		valorSomado = calculaMultiplicacaoSoma(array , 11);
		String segundoDigito = String.valueOf(calculaDigito(valorSomado  += primeiroDigito * 2));
		if(!digitoVerificador2.equals(segundoDigito)) 
			return false;
		return true;
	}

	private static boolean validaCaractesCPF(String cpf) {
		return !Util.validaString(cpf) ||
				!Util.validaQuantidadeCaracteresEntreDoisValores(cpf, 11, 14);
	}

	private static int calculaMultiplicacaoSoma(String[] array, int valorMultiplicador) {
		int valorSomado = 0;
		for(String valorStr: array) {
			valorSomado  += Integer.parseInt(valorStr) * valorMultiplicador;
			--valorMultiplicador;
		}
		return valorSomado;
	}

	private static int calculaDigito(int valorSomado) {
		int resto = valorSomado%11;
		if(resto < 2) return 0;
		return 11 - resto;
	}

	private static String retiraCaracteresEspeciaisDoCPF(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		cpf = cpf.trim();
		return cpf;
	}

	
}
