package io.branas.shop02.services;

public class Util {

	public static boolean validaQuantidadeCaracteresEntreDoisValores(String str, int valor1, int valor2) {
		return (str.length() >= valor1 && str.length() <= valor2) ? true : false;
	}
	public static boolean validaString(String str) {
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		return true;
	}
}
