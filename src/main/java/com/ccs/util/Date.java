package com.ccs.util;
public class Date {
	
	public static String[] toMindSphereFormat(String date) {
		/* 	Input:
		 * 		date : string of 35byte with this format 05/06/2018 02:00 PM  - 05/07/2018 05:00 PM 
		 * 	Output:
		 * 		From : string of 24byte with this format YYYY-MM-DDThh:mm:ss.000Z
		 * 		To	 : string of 24byte with this format YYYY-MM-DDThh:mm:ss.000Z
		 */
		
		Timestamp 	From 		= new Timestamp();
		Timestamp 	To   		= new Timestamp();
		String[] 	returnDate 	= new String[2];
		int 		first_date 	= 0;
		int 		second_date = 23;
		final int 	MONTHs 		= 0;
		final int 	DAYs 		= 3;
		final int 	YEARs 		= 6;
		final int 	HOURs 		= 11;
		final int 	MINUTEs 	= 14;
		final int 	FORMAT		= 17;
		
		/* Processing first part of string */
		//Set Year
		From.setYear(date.substring(first_date+YEARs, first_date+YEARs+5));
		//Set Month
		From.setMonth(date.substring(first_date+MONTHs, first_date+MONTHs+2));
		//Set Day
		From.setDay(date.substring(first_date+DAYs, first_date+DAYs+2));
		//Set Hours
		if (date.substring(first_date + FORMAT, first_date + FORMAT + 2).equals("PM")) {
			Integer newHours = ( Integer.parseInt(date.substring(first_date + HOURs, first_date + HOURs + 2)) + 12 );
			String PMHour = new String(newHours.toString());
			From.setHours(PMHour);
		} else {
			From.setHours(date.substring(first_date + HOURs, first_date + HOURs + 2));
		}
		//Set Minutes
		From.setMinutes(date.substring(first_date + MINUTEs, first_date + MINUTEs + 2));
		
		/* Processing second part of string */
		//Set Year
		To.setYear(date.substring(second_date+YEARs, second_date+YEARs+5));
		//Set Month
		To.setMonth(date.substring(second_date+MONTHs, second_date+MONTHs+2));
		//Set Day
		To.setDay(date.substring(second_date+DAYs, second_date+DAYs+2));
		//Set Hours
		if (date.substring(second_date + FORMAT, second_date + FORMAT + 2).equals("PM")) {
			Integer newHours = ( Integer.parseInt(date.substring(second_date + HOURs, second_date + HOURs + 2)) + 12 );
			To.setHours(newHours.toString());
		} else {
			To.setHours(date.substring(second_date + HOURs, second_date + HOURs + 2));
		}
		//Set Minutes
		To.setMinutes(date.substring(second_date + MINUTEs, second_date + MINUTEs + 2));
		
		String ConvertedFrom = new String(From.date);
		String ConvertedTo = new String(To.date);
		returnDate[0] = ConvertedFrom;
 		returnDate[1] = ConvertedTo;
		return returnDate;
	}
}
