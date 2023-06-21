package br.com.erudio.utils;

import br.com.erudio.exceptions.UnsupportedMathOperationException;

public class Util {

	public static Double convertToDouble(String strNumber) throws Exception {
		
		if (strNumber == null) {
			return 0D;
		}
		
		String number = strNumber.replaceAll(",", ".");
		
		if (isNumeric(number)) {
			return Double.parseDouble(number);
		} else {
			throw new UnsupportedMathOperationException("Recebido um valor inválido.");
		}
		
	}


	public static boolean isNumeric(String strNumber) {

		if (strNumber == null) {
			return false;
		}
		
		String number = strNumber.replaceAll(",", ".");

		return number.matches("[-+]?[0-9]*\\.?[0-9]+");

	}

}
