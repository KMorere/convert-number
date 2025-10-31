package def;

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class NumberToLetter {
	
	private static String[] ones = {
			"One",
			"Two",
			"Three",
			"Four",
			"Five",
			"Six",
			"Seven",
			"Eight",
			"Nine",
	};
	private static String[] tenToTwenty = {
			"Eleven",
			"Twelve",
			"Thirteen",
			"Fourteen",
			"Fifteen",
			"Sixteen",
			"Seventeen",
			"Eighteen",
			"Nineteen",
	};
	private static String[] tens = {
			"Ten",
			"Twenty",
			"Thirty",
			"Fourty",
			"Fifty",
			"Sixty",
			"Seventy",
			"Eighty",
			"Ninety",
	};
	
	private static String[][] numbers = {
			ones,
			tenToTwenty,
			tens,
	};
	
	public static void main(String[] args) {
		System.out.println("Enter a whole number from 0 to 999 :");
		
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
				System.out.println("Please use a number between 1 and 999.");
				tryConvert();
			}
		}
		else {
			System.out.println("Please use a valid number.");
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
		
		for (int i = 0; i < number.length; i++) {
			if (number.length - i == 3) { // Current number is in hundreds.
				int nb = convertInt(number[i]);
				
				temp += ones[nb] + " Hundred";
				temp += " ";
			}
			if (number.length - i == 2) { // 2nd position is in tens.
				int nb = convertInt(number[i]);
				
				if (nb == 0) { // Skip checking the 3rd digit if its a zero.
					temp += (convertInt(number[i + 1]) < 0) ? tens[nb] : tenToTwenty[nb];
					break;
				}
				else if (nb > 0) // Number is of size 2 and a second digit above zero.
					temp += tens[nb];
				temp += " ";
			}
			if (number.length - i == 1) { // 3rd digit with numbers from 1 to 9.
				int nb = convertInt(number[i]);
				temp += (nb >= 0) ? ones[nb] : "";
				temp += " ";
			}
		}

		System.out.println(temp);
	}
	
	// Convert a number from String into int and remove 1.
	private static int convertInt(String nb) {
		return Integer.parseInt(nb) - 1;
	}
}
