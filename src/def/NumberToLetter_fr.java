package def;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;

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
	private static String[] thousands = { "", "Mille", "Million", "Milliard" };
	
	private static String[][] numbers = {
			ones,
			tenToTwenty,
			tens,
			thousands
	};
	
	private static String[] special = {
			"Soixante-dix",
			"Quatre-vingt-dix"
	};
	
	private static final int MAX_VALUE = 99999;
	private static final int MIN_VALUE = 1;
	
	public static void main(String[] args) {
		System.out.printf("Entrez un nombre entre %d et %d, "
				+ "la valeur décimale étant séparée par ',' : \n"
				, (MIN_VALUE-1), (MAX_VALUE+1));
		
		tryConvert();
	}
	
	/// Input verification to use a valid number.
	private static void tryConvert() {
		Scanner scan = new Scanner(System.in);
		
		if (scan.hasNextFloat()) {
			float input = scan.nextFloat();
			
			if (input >= MIN_VALUE && input < MAX_VALUE) {
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
		result += (convertNumber(num.toArray(new String[num.size()])));
		
		if (isDecimal) {
			result += ("virgule ");
			result += (convertNumber(decimals.toArray(new String[decimals.size()])));
		}
		
		result = result.trim().toLowerCase();
		
		System.out.println(result.substring(0, 1).toUpperCase() + result.substring(1));
	}
	
	private static float splitNumber(String[] number) {
		return (float)number.length/3;
	}
	
	/**
	 * Convert the given number from digits to alphanumeric.
	 * @param number The number to convert with each digit separated.
	 */
	private static String convertNumber(String[] number) {
		ArrayList<String> numbers = new ArrayList<String>();
		
		String temp = "";
		float size = splitNumber(number);
		String[] numSep = new String[((int)size+1) * 3];
		
		for (int i = 0; i < size; i++) {
			if (size > 1) { // If the number is above 999 separate every 3 digits.
				for (int n = 0; n < numSep.length; n++) {
					if (numSep.length - number.length > 0) {
						if (n < numSep.length - number.length)
							numSep[n] = "0";
						else
							numSep[n] = number[n - (numSep.length - number.length)];
					}
					else if (numSep.length - number.length == 0)
						numSep[n] = number[n];
				}
				
				String[] num = setArray(numSep, i);

				numbers.add(setNumber(num));
				
				numbers.add(thousands[(int)size-i] + " ");
				
				if (size <= 1) {
					numbers.add(setNumber(setArray(numSep, 0)));
				}
			}
			else if (size <= 1) {
				numbers.add(setNumber(number));
			}
		}
		
		for (int i = numbers.size()-1; i >= 0; i--)
			if (!numbers.get(i).equals(" ") && (!numbers.get(i).trim().equals(ones[0])))
				temp += numbers.get(i);

		return temp;
	
	}
	
	// Separate the number every 3 digits.
	private static String[] setArray(String[] number, int index) {
		int ind = 3;
		for (int i = 0; i < 2; i++) {
			if (number[i + (number.length - 3 * ((int)splitNumber(number)-1 + index))] != "0") break;
			else ind--;
		}
		
		String[] num = new String[ind];
		
		for (int n = 0; n < ind; n++) {
			num[ind-1-n] = number[number.length - 1 - n - (3 * index)];
		}
		
		return num;
	}
	
	// Convert digits in 3 positions.
	private static String setNumber(String[] number) {
		String temp = "";
		
		switch (number.length) {
			case 3: // Current number is in hundreds.
				temp += setHundreds(number, 0);
				break;
			case 2: // 2nd position is in tens.
				temp += setTens(number, 0);
				break;
			case 1: // 3rd digit with numbers from 1 to 9.
				temp += setOnes(number, 0);
				break;
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
