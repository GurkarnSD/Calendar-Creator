/*******************************************************************************************************
 * MyCalendar.java
 *
 * A program to load a file with a monthly or yearly calendar given the input of year and month or year.
 * The file can be in one of two languages: English or French
 * The program works for years > 1600 and < 2800.
 *
 * Author:  Gurkarn Dhaliwal
 * Date:  May 2021
 ******************************************************************************************************/

import java.util.*;
import java.io.*;

public class MyCalendar {

	public static void main(String[] args) {
		
		// Initialize variables
		String daysOfWeek = " ";
		String language = " ";
		int calendarSize = 4;
		int inYear = 0;
		int monthNum = 0;
		
		Scanner myScan = new Scanner(System.in);
		
		// Asks the user what language they want the Calendar in. If the user enters FR the language is set as French. Any other inputs set the language to English.
		System.out.print("Language Selection (Type FR for French. Default is English.): ");
		try {
			language = myScan.nextLine();
			if(language.equals("FR")) {
				System.out.println("French Selected.");
			}else {
				System.out.println("English Selected.");
			} 
		}
		catch (InputMismatchException e){
			System.out.println("Invalid Input. Default Value Assigned.");
			language = "";
		}
		
		// Asks the user to select which size Calendar they want. If the input is invalid or less the 4 the size is set to 4.
		System.out.print("Which size of a calendar would you like? (Small is 4, Large is 20, You may also choose a custom size.): ");
		try {
			calendarSize = myScan.nextInt();
			if (calendarSize < 4) {
				calendarSize = 4;
			}
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid Input. Default Value Assigned.");
			calendarSize = 4;
		}
		
		// Checks if the selected language is French. 
		// Then it assigns the French days of the week to the daysOfWeek variable with the size that the user selected.
		// If the selected language is English it does the same thing but in English.
		if (language.equals("FR")) {
			daysOfWeek = String.format(("%-" + calendarSize + "s").repeat(7) + "\n", "Dim", "Lun", "Mar", "Mer", "Jeu", "Ven", ("Sam" + " ".repeat(calendarSize - 3)));
		}else {
			daysOfWeek = String.format(("%-" + calendarSize + "s").repeat(7) + "\n", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", ("Sat" + " ".repeat(calendarSize - 3)));
		}
		
		// Loads the month array with the return value from the getMonth method. 
		// Loads the daysOfMonth array with the return value from the getDaysOfMonth method.
		String[] month = getMonth(language);
		int[] daysOfMonth = getDaysOfMonth();
		
		
		// Scanner object for input
		Scanner myScanner = new Scanner(System.in);
		
		while(true) {
			
			// When the loop reiterates it checks the values of monthNum and inYear. 
			// If the variables have been assigned values and are within the specifications it breaks out of the loop.
			if(inYear >= 1600 && inYear <=2800 && monthNum >= 1 && monthNum <= 12) {
				break;
			}
			else if(inYear >= 1600 && inYear <=2800) {
				break;
			}
			
			// Resets the variables after an invalid attempt
		    inYear = 0;
			monthNum = 0;
			
			// Asks the user to input which Calendar they want. Splits the input by the spaces and places it in an array.
			System.out.print("Please respond in one of the following formats: Year or Month Year (i.e. 2021 or April 2021): ");
			String [] inputs = myScanner.nextLine().split("\\s+");
			
			// Checks the length of the array. Converts the input into an integer and assigns it to the inYear variable.
			if(inputs.length == 1) {

				try {
					
					inYear = Integer.parseInt(inputs[0]);
					
					if (inYear < 1600 || inYear > 2800) {
						System.out.println("Please enter a year between 1600 and 2800.");	
					}
				} catch (NumberFormatException err) {
					System.out.println("Please enter a valid number!");
				}
			}
			
			// Checks the length of the array. Selects the correct value to monthNum based on the selected month. 
			// Converts the second input into an integer and assigns it to the inYear variable..
			if(inputs.length == 2) {
				
				switch(inputs[0].toUpperCase()){
				case "JAN":
				case "JANUARY":
					monthNum = 1;
					break;
				case "FEB":
				case "FEBUARY":
					monthNum = 2;
					break;
				case "MAR":
				case "MARCH":
					monthNum = 3;
					break;
				case "APR":
				case "APRIL":
					monthNum = 4;
					break;
				case "MAY":
					monthNum = 5;
					break;
				case "JUN":
				case "JUNE":
					monthNum = 6;
					break;
				case "JUL":
				case "JULY":
					monthNum = 7;
					break;
				case "AUG":
				case "AUGUST":
					monthNum = 8;
					break;
				case "SEP":
				case "SEPTEMBER":
					monthNum = 9;
					break;
				case "OCT":
				case "OCTOBER":
					monthNum = 10;
					break;
				case "NOV":
				case "NOVEMBER":
					monthNum = 11;
					break;
				case "DEC":
				case "DECEMBER":
					monthNum = 12;
					break;
				default:
					System.out.println("Please enter a valid month.");
					continue;
				}
				
				try {
					inYear = Integer.parseInt(inputs[1]);
					
					if (inYear < 1600 || inYear > 2800) {
						System.out.println("Please enter a year between 1600 and 2800.");
					}
							
				} catch (NumberFormatException err) {
					System.out.println("Please enter a valid number!");
				}
			}
		} // end while
	
		// Checks if the year value is greater the 0 and the month is still 0. Runs the printCalendar method for the year.
		if(inYear > 0 && monthNum == 0) {
			printCalendar(inYear, daysOfWeek, month, daysOfMonth, calendarSize);
		}// Checks if the year value is greater the 0 and the month is greater than 0. Runs the printCalendar method for the month.
		else if (inYear > 0 && monthNum > 0){
			printCalendar(inYear, monthNum, daysOfWeek, month, daysOfMonth, calendarSize);
		}
		
		// Close Scanners
		myScanner.close();
		myScan.close();
	}

	/*******************************************************************************************************
	 * getDayOfWeek
	 *
	 * This method calculates to find the day of the week given the month, day and
	 * year
	 * It only works for dates from years 1600 to 2800 inclusive
	 * It returns the day of the week as an integer (0 for Mon, 1 for Tue...)
	 *
	 * See http:\\worldmentalcalculation.com/how-to-calculate-calendar-dates/  for the calculation
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return calculation of the day of the week
	 ******************************************************************************************************/

	private static int getDayOfWeek(int year, int month, int day)
	{
		// Initialize variables
		int dayOfWeek = 0;
		int cCentury = 0;
		int cYear = 0;
		int cMonth = 0;
		int cDay = day;

		// Calculate the Century component

		if ((year >= 1600 && year < 1700) || (year >= 2000 && year < 2100) || (year >= 2400 && year < 2500))
			cCentury = 2;
		else if ((year >= 1800 && year < 1900) || (year >= 2200 && year < 2300) || (year >= 2600 && year < 2700))
			cCentury = 5;
		else if ((year >= 1900 && year < 2000) || (year >= 2300 && year < 2400) || (year >= 2700 && year < 2800))
			cCentury = 3;

		// Calculate the Year component

		cYear = (((year % 100) / 4) + (year % 100)) % 7;

		// Calculate the Month component

		switch (month)
		{
			case 1:
			case 10:
				cMonth = 4;
				break;
			case 2:
			case 3:
			case 11:
				cMonth = 0;
				break;
			case 4:
			case 7:
				cMonth = 3;
				break;
			case 5:
				cMonth = 5;
				break;
			case 6:
				cMonth = 1;
				break;
			case 8:
				cMonth = 6;
				break;
			case 9:
			case 12:
				cMonth = 2;
				break;
		}

		// Calculate the Day component

		cDay = day % 7;

		// Return the calculation of the day of the week

		dayOfWeek = ((cCentury + cYear + cMonth + cDay) % 7);

		//  Adjust for a leap year

		if ((year % 4 == 0) && (month == 1 || month == 2))
		{
			if (dayOfWeek == 0)
				dayOfWeek = 6;
			else
				dayOfWeek--;
		}

		dayOfWeek = dayOfWeek % 7;

		return dayOfWeek;
	}
	
	/*******************************************************************************************************
	 * getMonth
	 *
	 * This method loads an array with the names of the month.
	 * The array is loaded from a text file containing the months in the requested language. 
	 *
	 * @param language
	 * @return array loaded with the months
	 ******************************************************************************************************/
	
	private static String[] getMonth(String language)
	{
		// Initialize variables
		String fileName;
		String[] month = {};
		
		// Checks if the user requested a French calendar. If they did it sets the file to the French variant otherwise it is set as the English Variant. 
		if(language.equals("FR")){
			fileName = "monthNames_FR.txt";
		}
		else{
			fileName = "monthNames.txt";
		}
		
		// Loads the month array with the data from the selected file.
		try {
			File myFile = new File(fileName);
			Scanner monthScan = new Scanner(myFile);
			while(monthScan.hasNext()) {
				month = Arrays.copyOf(month, month.length + 1);
		    	month[month.length - 1] = monthScan.next();
			}
			monthScan.close();
		}
		catch (IOException e)
        {
			System.out.println("Unable to open txt");
            System.exit(-1);
        }
		// Returns the month array.
		return month;
	}
	
	/*******************************************************************************************************
	 * getDaysOfMonth
	 *
	 * This method loads an array with the amount of days in each month.
	 * The array is loaded from a text file containing the days in each month. 
	 *
	 * @param language
	 * @return array loaded with the days in each month
	 ******************************************************************************************************/
	
	private static int[] getDaysOfMonth()
	{
		// Initialize variables
		int[] daysOfMonth = {};
		
		// Loads the daysOfMonth array with the data from the daysInMonth.txt file.
		try {
			File myFile = new File("daysInMonth.txt");
			Scanner dayScan = new Scanner(myFile);
			while(dayScan.hasNext()) {
				daysOfMonth = Arrays.copyOf(daysOfMonth, daysOfMonth.length + 1);
				daysOfMonth[daysOfMonth.length - 1] = dayScan.nextInt();
			}
			dayScan.close();
		}
		catch (IOException e)
        {
            System.out.println("Unable to open txt");
            System.exit(-1);
        }
		// Returns the daysOfMonth array.
		return daysOfMonth;
	}
	
	/*******************************************************************************************************
	 * printCalendar
	 *
	 * This method writes the Calendar for a given year to a file with the name of that year.
	 * (Calendar_2021.txt)
	 * 
	 * @param inYear
	 * @param daysOfWeek
	 * @param month
	 * @param daysOfMonth
	 * @param calendarSize
	 ******************************************************************************************************/
	
	private static void printCalendar(int inYear, String daysOfWeek, String[] month, int[] daysOfMonth, int calendarSize){
		
		// Creates a new File for the Calendar Year. Loads the file with the Calendar for the Year.
		try {
			File outputFile = new File("Calendar_"+inYear+".txt");
			FileWriter fileWriter = new FileWriter(outputFile);
			PrintWriter printWriter = new PrintWriter(fileWriter, true);
			
			for(int monthNum = 1; monthNum  <= 12; monthNum  ++) {
				
					// Call to method to return the day of the week (as a number 0 - 6), that the month starts on,
					// given the year, month and day (1 to get the first day of the month)
					// Method can be used to get the day of the week for any year, month and day

					int dayOfWeek = getDayOfWeek(inYear, monthNum, 1);

					// Print calendar month to the file

					printWriter.println("\n\n" + inYear + "\n" + month[monthNum - 1]);
					printWriter.println("-".repeat(7 * calendarSize) + "\n");
					printWriter.println(daysOfWeek);

					// Loops to iterate through the days and weeks of the month
					int j = 0;                           //  Counts the calendar spaces
					int dayCount = 1;                    //  Counts the days of the month

					for (int i = 1; i < 7; i++)          //  There are a maximum of 6 rows in the calendar
					{
						for (int k = 1; k < 8; k++)      //  Print out a week each loop
						{
							if ((j >= dayOfWeek) && (dayCount <= daysOfMonth[monthNum - 1]))
							{
								printWriter.printf("%-" + calendarSize + "s", dayCount);
								dayCount++;
							}
							else
							{
								printWriter.printf("%-" + calendarSize +"s", "    ");
							}
							j++;
						}
						printWriter.println("\n".repeat(calendarSize / 4));         //  After printing a week move to a new line
					}
			}
			printWriter.close();
			System.out.println("Calendar succesfully writen to "+"Calendar_"+inYear+".txt");
		}
		catch (IOException e){
			System.out.println("Unable to write to the output file.");
			System.exit(-1);
		}

	}
	
	/*******************************************************************************************************
	 * printCalendar
	 *
	 * This method writes the Calendar for a given month in a given year to a file.
	 * The files name contains the month and year that was requested.(Calendar_2021April.txt)
	 * 
	 * @param inYear
	 * @param monthNum
	 * @param daysOfWeek
	 * @param month
	 * @param daysOfMonth
	 * @param calendarSize
	 ******************************************************************************************************/
	
	private static void printCalendar(int inYear, int monthNum, String daysOfWeek, String[] month, int[] daysOfMonth, int calendarSize) {
			
			// Creates a new File for the Calendar Month. Loads the file with the Calendar for that Month.
			try {
				File outputFile = new File("Calendar_"+inYear+month[monthNum - 1]+".txt");
				FileWriter fileWriter = new FileWriter(outputFile);
				PrintWriter printWriter = new PrintWriter(fileWriter, true);
			
				// Call to method to return the day of the week (as a number 0 - 6), that the month starts on,
				// given the year, month and day (1 to get the first day of the month)
				// Method can be used to get the day of the week for any year, month and day

				int dayOfWeek = getDayOfWeek(inYear, monthNum, 1);

				// Print calendar month to the file

				printWriter.println("\n\n" + inYear + "\n" + month[monthNum - 1]);
				printWriter.println("-".repeat(7 * calendarSize) + "\n");
				printWriter.println(daysOfWeek);

				// Loops to iterate through the days and weeks of the month
				int j = 0;                           //  Counts the calendar spaces
				int dayCount = 1;                    //  Counts the days of the month

				for (int i = 1; i < 7; i++)          //  There are a maximum of 6 rows in the calendar
				{
					for (int k = 1; k < 8; k++)      //  Print out a week each loop
					{
						if ((j >= dayOfWeek) && (dayCount <= daysOfMonth[monthNum - 1]))
						{
							printWriter.printf("%-" + calendarSize + "s", dayCount);
							dayCount++;
						}
						else
						{
							printWriter.printf("%-" + calendarSize + "s", "    ");
						}
						j++;
					}
					printWriter.println("\n".repeat(calendarSize / 4));          //  After printing a week move to a new line
				}
				printWriter.close();
				System.out.println("Calendar succesfully writen to "+"Calendar_"+inYear+month[monthNum - 1]+".txt");
			}
			catch (IOException e){
				System.out.println("Unable to write to the output file.");
				System.exit(-1);
			}
		}
}
