package com.ccs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
	
	public static String[] toMindSphereFormat(String date) {
		/* 	Input:
		 * 		date : string of 35byte with this format 05/06/2018 02:00 PM  - 05/07/2018 05:00 PM 
		 * 	Output:
		 * 		From : string of 24byte with this format YYYY-MM-DDThh:mm:ss.000Z
		 * 		To	 : string of 24byte with this format YYYY-MM-DDThh:mm:ss.000Z
		 */
		
		Timestamp 	From 			= new Timestamp();
		Timestamp 	To   			= new Timestamp();
		String[] 	returnDate 		= new String[2];
		int 		first_date 		= 0;
		int 		second_date	 	= 23;
		int 		second_date_mod = 22;
		final int 	MONTHs 			= 0;
		final int 	DAYs 			= 3;
		final int 	YEARs 			= 6;
		final int 	HOURs 			= 11;
		final int 	MINUTEs 		= 14;
		final int 	FORMAT			= 17;
		
		/* Processing first part of string */

		if(date.substring(1, 2).equals("/")) {
			From.setYear(date.substring(first_date+YEARs-1, first_date+YEARs+4));
			From.setMonth("0" + date.substring(first_date+MONTHs, first_date+MONTHs+1));
			From.setDay(date.substring(first_date+DAYs-1, first_date+DAYs+1));	
			if (date.substring(first_date + FORMAT-1, first_date + FORMAT + 1).equals("PM")) {
				Integer newHours = ( Integer.parseInt(date.substring(first_date + HOURs-1, first_date + HOURs + 1)) + 12 );
				String PMHour = new String(newHours.toString());
				From.setHours(PMHour);
			} else {
				if(date.substring(first_date + HOURs-1, first_date + HOURs + 1).equals("12")) {
					From.setHours("00");	
				} else {
					From.setHours(date.substring(first_date + HOURs-1, first_date + HOURs + 1));
				}
			}
			//Set Minutes
			From.setMinutes(date.substring(first_date + MINUTEs-1, first_date + MINUTEs + 1));
			//System.out.println(From.getDate());
		}else {
			//System.out.println(date.substring(1, 2));
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
				if(date.substring(first_date + HOURs, first_date + HOURs + 2).equals("12")) {
					From.setHours("00");	
				} else {
					From.setHours(date.substring(first_date + HOURs, first_date + HOURs + 2));
				}
			}
			//Set Minutes
			From.setMinutes(date.substring(first_date + MINUTEs, first_date + MINUTEs + 2));
			
			//System.out.println(From.getDate());
		}
		
		/* Processing second part of string */

		if(date.substring(second_date, second_date+1).equals("/")) {
						
			//Set Year
			To.setYear(date.substring(second_date_mod+YEARs-1, second_date_mod+YEARs+4));
			//Set Month
			To.setMonth("0" + date.substring(second_date_mod+MONTHs, second_date_mod+MONTHs+1));
			//Set Day
			To.setDay(date.substring(second_date_mod+DAYs-1, second_date_mod+DAYs+1));
			//Set Hours
			if (date.substring(second_date_mod + FORMAT - 1, second_date_mod + FORMAT + 1).equals("PM")) {
				Integer newHours = ( Integer.parseInt(date.substring(second_date_mod + HOURs - 1, second_date_mod + HOURs + 1)) + 12 );
				To.setHours(newHours.toString());
			} else {
				if(date.substring(second_date_mod + HOURs - 1 , second_date_mod + HOURs + 1).equals("12")) {
					To.setHours("00");	
				} else {
					To.setHours(date.substring(second_date_mod + HOURs - 1, second_date_mod + HOURs + 1));
				}
			}
			//Set Minutes
			To.setMinutes(date.substring(second_date_mod + MINUTEs - 1, second_date_mod + MINUTEs + 1));
			
			//System.out.println(To.getDate());

		}else {
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
				if(date.substring(second_date + HOURs, second_date + HOURs + 2).equals("12")) {
					To.setHours("00");	
				} else {
					To.setHours(date.substring(second_date + HOURs, second_date + HOURs + 2));
				}
			}
			//Set Minutes
			To.setMinutes(date.substring(second_date + MINUTEs, second_date + MINUTEs + 2));
		}
		
		returnDate[0] = From.getDate();
 		returnDate[1] = To.getDate();
 		
 		System.out.println(returnDate[0]);
 		System.out.println(returnDate[1]);

		return returnDate;
	}
	
	public static String nextDay(String date) throws ParseException {
		//String date format : YYYY-MM-DDThh:mm:ss.000Z
		
		String dt = date.substring(0, 10);  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, 1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		//get the previous date in java.util.Date.
		return  dt + "T" + date.substring(11, 24);
	}
	
	public static String previousDay(String date) throws ParseException {
		//String date format : YYYY-MM-DDThh:mm:ss.000Z
				
		String dt = date.substring(0, 10);  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, -1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		//get the previous date in java.util.Date.
		return  dt + "T" + date.substring(11, 24);
		
	}
}
