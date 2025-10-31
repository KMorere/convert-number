package def;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		System.out.printf("Entrez un nombre entre %d et %d, "
				+ "la valeur décimale étant séparé par ',' : "
				, (MIN_VALUE-1), (MAX_VALUE+1));
		
		tryConvert();
	}
	
	/// Input verification to use a valid number.
	private static void tryConvert() {
		Scanner scan = new Scanner(System.in);
		
		if (scan.hasNextFloat()) {
			float input = scan.nextFloat();
			
			if (input >= 0 && input < 1000) {
				String result = (input % 1 == 0) ? String.valueOf((int) input) : String.valueOf(input);
				String[] inputStr = result.split("");
				
				getDecimal(inputStr);
			}
			else {
				System.out.printf("Veuillez utilisez un nombre entier entre %d et %d : "
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
	 * Check if the number to convert has a decimal value,
	 * And split whole numbers and decimals.
	 * @param number The current number to convert.
	 */
	private static void getDecimal(String[] number) {
		ArrayList<String> decimals = new ArrayList<String>(Arrays.asList(number));
		ArrayList<String> num = new ArrayList<String>();
		boolean isDecimal = false;
		
		for (String n : number) {
			if (n.equals(".")) {
				decimals.remove(n);
				isDecimal = true;
				break;
			}
			else {
				decimals.remove(n);
				num.add(n);
			}
		}
		
		String result = "";
		System.out.print(convertNumber(num.toArray(new String[num.size()])));
		
		if (isDecimal) {
			System.out.print("virgule ");
			System.out.println(convertNumber(decimals.toArray(new String[decimals.size()])));
		}
		
		System.out.println(result);
	}
	
	/**
	 * Convert the given number from digits to alphanumeric.
	 * @param number The number to convert with each digit separated.
	 */
	private static String convertNumber(String[] number) {
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

		return temp;
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
		temp += (nb >= 0) ? ones[nb] + " " : "";
		
		return temp;
	}
	
	// Convert a number from String into int and remove 1.
	private static int convertInt(String nb) {
		return Integer.parseInt(nb) - 1;
	}
}
