package def;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class NumberToLetter_fr {
	
	private static String[] ones = {
			"Un",
			"Deux",
			"Trois",
			"Quatre",
			"Cinq",
			"Six",
			"Sept",
			"Huit",
			"Neuf",
	};
	private static String[] tenToTwenty = {
			"Onze",
			"Douze",
			"Treize",
			"Quatorze",
			"Quinze",
			"Seize",
			"Dix-sept",
			"Dix-huit",
			"Dix-neuf",
	};
	private static String[] tens = {
			"Dix",
			"Vingt",
			"Trente",
			"Quarante",
			"Cinquante",
			"Soixante",
			"Soixante-dix",
			"Quatre-vingt",
			"Quatre-vingt-dix",
	};
	
	private static String[][] numbers = {
			ones,
			tenToTwenty,
			tens,
	};
	
	private static String[] special = {
			"Soixante-dix",
			"Quatre-vingt-dix"
	};
	
	private static final int MAX_VALUE = 999;
	private static final int MIN_VALUE = 1;
	
	public static void main(String[] args) {
		System.out.printf("Entrez un nombre entier de %d à %d : "
				, (MIN_VALUE-1), (MAX_VALUE+1));
		
		tryConvert();
	}
	
	private static void tryConvert() {
		Scanner scan = new Scanner(System.in);
		
		if (scan.hasNextInt()) {
			int input = scan.nextInt();
			
			if (input >= 0 && input < 1000) {
				String[] inputStr = Integer.toString(input).split("");
				
				convertNumber(inputStr);
			}
			else {
				System.out.printf("Veuillez utilisez un nombre entier de %d à %d : "
						, (MIN_VALUE-1), (MAX_VALUE+1));
				tryConvert();
			}
		}
		else {
			System.out.println("Veuillez utiliser un nombre valide.");
			tryConvert();
		}
		
		scan.close();
	}
	
	/**
	 * Convert the given number from digits to alphanumeric.
	 * @param number The number to convert with each digit separated.
	 */
	private static void convertNumber(String[] number) {
		String temp = "";
		
		for (int i = 0; i < 1; i++) {
			switch (number.length - i) {
				case 3: // Current number is in hundreds.
					temp += setHundreds(number, i);
					break;
				case 2: // 2nd position is in tens.
					temp += setTens(number, i);
					break;
				case 1: // 3rd digit with numbers from 1 to 9.
					temp += setOnes(number, i);
					break;
			}
		}

		System.out.println(temp);
	}
	
	/**
	 * Convert digits in the third position.
	 * @param num Current numbers to convert.
	 * @param index Current index in the list.
	 * @return The converted value as string.
	 */
	private static String setHundreds(String[] num, int index) {
		String temp = "";
		
		int nb = convertInt(num[index]);
		
		if (nb > 0)
			temp += ones[nb] + " ";
		temp += "Cent ";
		
		temp += setTens(num, index+1);
		
		return temp;
	}
	
	/**
	 * Convert digits in the second position using the tens array.
	 * @param num Current numbers to convert.
	 * @param index Current index in the list.
	 * @return The converted value as string.
	 */
	private static String setTens(String[] num, int index) {
		String temp = "";
		String number = num[index];
		int nextNum = convertInt(num[index+1]);
		
		int nb = convertInt(number);
		
		if (nb == 0) { // Check if we're from 10 to 19.
			temp += (nextNum < 0) ? tens[nb] : tenToTwenty[nextNum];
			temp += " ";
			return temp;
		}
		else if (nb > 0) { // Number is of size 2 and a second digit above zero.
			if (Arrays.asList(special).contains("" + tens[nb])) {
				return setSpecialTens(nb, nextNum);
			}
			temp += tens[nb];
			temp += (nextNum == 0) ? " et" : "";
			temp += " ";
		}
		
		return temp += setOnes(num, index+1);
	}
	
	/// Takes care of converting wording exceptions included in the special array.
	private static String setSpecialTens(int nb, int nextNb) {
		String temp = "";
		
		temp += tens[nb-1];
		temp += (nextNb == 0 && tens[nb].equals(special[0])) ? " et " : "-";
		temp += (nextNb == -1) ? tens[0] : tenToTwenty[nextNb];
		
		return temp + " ";
	}
	
	/**
	 * Convert the digits from 1 to 9.
	 * @param num Current numbers to convert.
	 * @param index Current index in the list.
	 * @return The converted value as string.
	 */
	private static String setOnes(String[] num, int index) {
		String temp = "";
		String number = num[index];
		
		int nb = convertInt(number);
		temp += (nb >= 0) ? ones[nb] : "";
		
		return temp;
	}
	
	// Convert a number from String into int and remove 1.
	private static int convertInt(String nb) {
		return Integer.parseInt(nb) - 1;
	}
}
