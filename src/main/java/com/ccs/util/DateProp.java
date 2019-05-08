package com.ccs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * Classe che gestisce tutte le funzioni che vengono utilizzate per modificare e riformattare l'orario 
 */

public class DateProp {
	
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
				if(Integer.parseInt(date.substring(first_date + HOURs-1, first_date + HOURs + 1)) == 12) {
					Integer newHours = ( Integer.parseInt(date.substring(first_date + HOURs-1, first_date + HOURs + 1)));
					String PMHour = new String(newHours.toString());
					From.setHours(PMHour);
				} else {
					Integer newHours = ( Integer.parseInt(date.substring(first_date + HOURs-1, first_date + HOURs + 1)) + 12 );
					String PMHour = new String(newHours.toString());
					From.setHours(PMHour);
				}		
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
				if(( Integer.parseInt(date.substring(first_date + HOURs, first_date + HOURs + 2))) == 12) {
					Integer newHours = ( Integer.parseInt(date.substring(first_date + HOURs, first_date + HOURs + 2)));
					String PMHour = new String(newHours.toString());
					From.setHours(PMHour);
				} else {
					Integer newHours = ( Integer.parseInt(date.substring(first_date + HOURs, first_date + HOURs + 2)) + 12 );
					String PMHour = new String(newHours.toString());
					From.setHours(PMHour);
				}	
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
				if((Integer.parseInt(date.substring(second_date_mod + HOURs - 1, second_date_mod + HOURs + 1))) == 12) {
					Integer newHours = ( Integer.parseInt(date.substring(second_date_mod + HOURs - 1, second_date_mod + HOURs + 1)));
					To.setHours(newHours.toString());
				} else {
					Integer newHours = ( Integer.parseInt(date.substring(second_date_mod + HOURs - 1, second_date_mod + HOURs + 1)) + 12 );
					To.setHours(newHours.toString());
				}		
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

		}else if(date.substring(25, 26).equals("/")) {
			//Set Year
			To.setYear(date.substring(second_date+YEARs, second_date+YEARs+5));
			//Set Month
			To.setMonth(date.substring(second_date+MONTHs, second_date+MONTHs+2));
			//Set Day
			To.setDay(date.substring(second_date+DAYs, second_date+DAYs+2));
			//Set Hours
			if (date.substring(second_date + FORMAT, second_date + FORMAT + 2).equals("PM")) {
				if(( Integer.parseInt(date.substring(second_date + HOURs, second_date + HOURs + 2))) == 12) {
					Integer newHours = ( Integer.parseInt(date.substring(second_date + HOURs, second_date + HOURs + 2)));
					To.setHours(newHours.toString());
				} else {
					Integer newHours = ( Integer.parseInt(date.substring(second_date + HOURs, second_date + HOURs + 2)) + 12 );
					To.setHours(newHours.toString());
				}	
			} else {
				if(date.substring(second_date + HOURs, second_date + HOURs + 2).equals("12")) {
					To.setHours("00");	
				} else {
					To.setHours(date.substring(second_date + HOURs, second_date + HOURs + 2));
				}
			}
			//Set Minutes
			To.setMinutes(date.substring(second_date + MINUTEs, second_date + MINUTEs + 2));
		} else if((date.substring(24, 25).equals("/")) && (date.substring(1, 2).equals("/"))) {
			
			//Set Year
			To.setYear(date.substring(28, 33));
			//Set Month
			To.setMonth(date.substring(22, 24));
			//Set Day
			To.setDay(date.substring(25, 27));
			//Set Hours
			if (date.substring(39,40).equals("P")) {
				if(( Integer.parseInt(date.substring(33, 35))) == 12) {
					Integer newHours = ( Integer.parseInt(date.substring(33, 35)));
					To.setHours(newHours.toString());
				} else {
					Integer newHours = ( Integer.parseInt(date.substring(33, 35)) + 12 );
					To.setHours(newHours.toString());
				}	
			} else {
				if(date.substring(33, 35).equals("12")) {
					To.setHours("00");	
				} else {
					To.setHours(date.substring(33, 35));
				}
			}
			//Set Minutes
			To.setMinutes(date.substring(36, 38));
			
		} else if((date.substring(24, 25).equals("/")) && (date.substring(2, 3).equals("/"))){
			
			//Set Year
			To.setYear(date.substring(28, 33));
			//Set Month
			To.setMonth("0" + date.substring(23, 24));
			//Set Day
			To.setDay(date.substring(25, 27));
			//Set Hours
			if (date.substring(39,40).equals("P")) {
				if(( Integer.parseInt(date.substring(33, 35))) == 12) {
					Integer newHours = ( Integer.parseInt(date.substring(33, 35)));
					To.setHours(newHours.toString());
				} else {
					Integer newHours = ( Integer.parseInt(date.substring(33, 35)) + 12 );
					To.setHours(newHours.toString());
				}	
			} else {
				if(date.substring(33, 35).equals("12")) {
					To.setHours("00");	
				} else {
					To.setHours(date.substring(33, 35));
				}
			}
			//Set Minutes
			To.setMinutes(date.substring(36, 38));			
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
		//					OR 2018-11-23T10:05:50Z
				
		String dt = date.substring(0, 10);  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, -1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		System.out.println("data stampata dalla funzione previousDay"+date);
		//get the previous date in java.util.Date.
		return  dt + "T" + date.substring(11, 24);
		
	}
	
	public static String nextDayList(String date) throws ParseException {
		//String date format : 2018-11-23T10:05:50Z
		//output : YYYY-MM-DDThh:mm:ss.000Z

		String dt = date.substring(0, 10);  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, 1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		//get the previous date in java.util.Date.
		return  dt + "T" + date.substring(11, 19) + ".000" + date.substring(19, 20);
	}
	
	public static String previousDayList(String date) throws ParseException {
		//String date format : 2018-11-23T10:05:50Z
		//output : YYYY-MM-DDThh:mm:ss.000Z
				
				
		String dt = date.substring(0, 10);  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, -1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		
		System.out.println("data stampata dalla funzione previousDayList: "+ dt + "T" + date.substring(11, 19) + ".000" + date.substring(19, 20));
		//get the previous date in java.util.Date.
		return  dt + "T" + date.substring(11, 19) + ".000" + date.substring(19, 20);
		
	}
	public static String setMindshphereDate(String date) {
		//String date format : 2018-11-23T10:05:50Z
		//output : 			   YYYY-MM-DDThh:mm:ss.000Z

		String dt = date.substring(0, 19);  // Start date
		return  dt + ".000" + date.substring(19, 20);
	}
	
	public static String fromMSFormatToUser(String date) {
		//String date format : 2018-11-29T15:07:51Z
		//output : 			   2018-11-29 15:07:51 
		return date.substring(0, 10) + " " + date.substring(11, 19);
	}
	
	public static String fromDateToFormatSwhow(String date) {
		//String date format : 
		//					   2018-11-27T13:42:50.000Z 2018-11-29T15:00:00.000Z 
		//					   or 
		//					   11/27/2018 02:00 PM - 11/29/2018 03:00 PM
		
		//output : 			   2018-11-29 15:07:51 - 2018-11-29 15:00:00
		
		//Controllo il tipo di stringa in maniera tale da discriminare se sia del primo o del secondo tipo
		
		System.out.println(date);
		
		// tutte e due i mesi a cifra doppia
		if((date.subSequence(2, 3).equals("/")) && (date.subSequence(25, 26).equals("/"))){
			
			String date1 = date.substring(6, 10) + "-" + date.substring(0, 2) + "-" + date.substring(3, 5) + " ";
			
			String date2 = date.substring(29, 33) + "-" + date.substring(23, 25) + "-" + date.substring(26, 28) + " ";
			
			//prima parte			
			if(date.substring(17, 19).equals("AM")) {
				if(date.substring(11, 13).equals("12")) {
					date1 = date1 + "00";
				}else {
					date1 = date1 + date.substring(11, 13);
				}
			}else {
				if(date.substring(11, 13).equals("12")) {
					date1 = date1 + date.substring(11, 13);
				} else {
					int h = Integer.valueOf(date.substring(11, 13)) + 12;
					date1 = date1 + h;
				}
			}
			
			date1 = date1 + ":" + date.substring(14, 16);
			
			System.out.println("prima parte della data formattata: "+date1);
			//seconda parte
			
			if(date.substring(40, 41).equals("A")) {
				if(date.substring(34, 36).equals("12")) {
					date2 = date2 + "00";
				}else {
					date2 = date2 + date.substring(34, 36);
				}
			}else {
				if(date.substring(34, 36).equals("12")) {
					date2 = date2 + date.substring(34, 36);
				} else {
					int h = Integer.valueOf(date.substring(34, 36)) + 12;
					date2 = date2 + h;
				}
			}
			date2 = date2 + ":" + date.substring(37, 39);
			
			return date1 + " -> " + date2;
			
			//tutti e due i mesi a cifra singola
		} else if ((date.subSequence(1, 2).equals("/")) && (date.subSequence(23, 24).equals("/"))) {
			
			String date1 = date.substring(5, 9) + "-" + date.substring(0, 1) + "-" + date.substring(2, 4) + " ";
			
			String date2 = date.substring(27, 31) + "-" + date.substring(22, 23) + "-" + date.substring(24, 26) + " ";
			
			//prima parte			
			if(date.substring(16, 18).equals("AM")) {
				if(date.substring(10, 12).equals("12")) {
					date1 = date1 + "00";
				}else {
					date1 = date1 + date.substring(10, 12);
				}
			}else {
				if(date.substring(10, 12).equals("12")) {
					date1 = date1 + date.substring(10, 12);
				} else {
					int h = Integer.valueOf(date.substring(10, 12)) + 12;
					date1 = date1 + h;
				}
			}
			
			date1 = date1 + ":" + date.substring(13, 15);
			
			System.out.println("prima parte della data formattata: "+date1);
			//seconda parte
			
			if(date.substring(38, 39).equals("A")) {
				if(date.substring(32, 34).equals("12")) {
					date2 = date2 + "00";
				}else {
					date2 = date2 + date.substring(32, 34);
				}
			}else {
				if(date.substring(32, 34).equals("12")) {
					date2 = date2 + date.substring(32, 34);
				} else {
					int h = Integer.valueOf(date.substring(32, 34)) + 12;
					date2 = date2 + h;
				}
			}
			date2 = date2 + ":" + date.substring(35, 37);
			
			return date1 + " -> " + date2;
			
			//primo mese a cifra singola secondo mese a cifra doppia
			}else if((date.subSequence(1, 2).equals("/")) && (date.subSequence(24, 25).equals("/"))){

				String date1 = date.substring(5, 9) + "-" + date.substring(0, 1) + "-" + date.substring(2, 4) + " ";
				
				String date2 = date.substring(28, 32) + "-" + date.substring(25, 27) + "-" + date.substring(22, 24) + " ";
				
				//prima parte			
				if(date.substring(16, 18).equals("AM")) {
					if(date.substring(10, 12).equals("12")) {
						date1 = date1 + "00";
					}else {
						date1 = date1 + date.substring(10, 12);
					}
				}else {
					if(date.substring(10, 12).equals("12")) {
						date1 = date1 + date.substring(10, 12);
					} else {
						int h = Integer.valueOf(date.substring(10, 12)) + 12;
						date1 = date1 + h;
					}
				}
				
				date1 = date1 + ":" + date.substring(13, 15);
				
				System.out.println("prima parte della data formattata: "+date1);
				//seconda parte
				
				if(date.substring(39, 40).equals("A")) {
					if(date.substring(33, 35).equals("12")) {
						date2 = date2 + "00";
					}else {
						date2 = date2 + date.substring(33, 35);
					}
				}else {
					if(date.substring(33, 35).equals("12")) {
						date2 = date2 + date.substring(33, 35);
					} else {
						int h = Integer.valueOf(date.substring(33, 35)) + 12;
						date2 = date2 + h;
					}
				}
				date2 = date2 + ":" + date.substring(36, 38);
				
				return date1 + " -> " + date2;

				
			//primo mese cifra doppia secondo mese cifra singola
			}else if((date.subSequence(2, 3).equals("/")) && (date.subSequence(24, 25).equals("/"))) {
				
				String date1 = date.substring(6, 10) + "-" + date.substring(0, 2) + "-" + date.substring(3, 5) + " ";
				
				String date2 = date.substring(28, 32) + "-" + date.substring(23, 24) + "-" + date.substring(23, 24) + " ";
				
				//prima parte			
				if(date.substring(17, 19).equals("AM")) {
					if(date.substring(11, 13).equals("12")) {
						date1 = date1 + "00";
					}else {
						date1 = date1 + date.substring(11, 13);
					}
				}else {
					if(date.substring(11, 13).equals("12")) {
						date1 = date1 + date.substring(10, 12);
					} else {
						int h = Integer.valueOf(date.substring(11, 13)) + 12;
						date1 = date1 + h;
					}
				}
				
				date1 = date1 + ":" + date.substring(14, 16);
				
				System.out.println("prima parte della data formattata: "+date1);
				//seconda parte
				
				if(date.substring(39, 40).equals("A")) {
					if(date.substring(33, 35).equals("12")) {
						date2 = date2 + "00";
					}else {
						date2 = date2 + date.substring(33, 35);
					}
				}else {
					if(date.substring(33, 35).equals("12")) {
						date2 = date2 + date.substring(33, 35);
					} else {
						int h = Integer.valueOf(date.substring(33, 35)) + 12;
						date2 = date2 + h;
					}
				}
				date2 = date2 + ":" + date.substring(36, 38);
				
				return date1 + " -> " + date2;
				
			}else {
				return date.substring(0, 10) + " " + date.substring(11, 19) + " -> " + date.substring(25, 35) + " " + date.substring(36, 41);
			}
	}
	
}
