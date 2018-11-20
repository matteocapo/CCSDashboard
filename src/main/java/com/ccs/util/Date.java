package com.ccs.util;
public class Date {
	
	public static String[] toMindSphereFormat(String date) {
		/* 	Input:
		 * 		date : string of 35byte with this format 11/20/2018 02:00  - 11/21/2018 10:00
		 * 	Output:
		 * 		From : string of 24byte with this format YYYY-MM-DDThh:mm:ss.000Z
		 * 		To	 : string of 24byte with this format YYYY-MM-DDThh:mm:ss.000Z
		 */
		
		Timestamp From = new Timestamp();
		Timestamp To   = new Timestamp();
		String[] returnDate = new String[2];
		
		/* Processing first part of string */
		//Set Year
		From.setYear(date.substring(6, 10));
		//Set Month
		From.setMonth(date.substring(0, 2));
		//Set Day
		From.setDay(date.substring(3, 5));
		//Set Hours
		From.setHours(date.substring(11, 13));
		//Set Minutes
		From.setMinutes(date.substring(14, 16));
		
		/* Processing second part of string */
		//Set Year
		To.setYear(date.substring(26, 30));
		//Set Month
		To.setMonth(date.substring(20, 22));
		//Set Day
		To.setDay(date.substring(23, 25));
		//Set Hours
		To.setHours(date.substring(31, 33));
		//Set Minutes
		To.setMinutes(date.substring(34, 36));
		
		String ConvertedFrom = new String(From.date);
		String ConvertedTo = new String(To.date);
		returnDate[0] = ConvertedFrom;
 		returnDate[1] = ConvertedTo;
		return returnDate;
	}
}
