package pl.streamsoft.currencyexchange;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class BusinessDayUtils {

	private static BusinessDayUtils Instance;
	private static List<String> holidays = null;

	private BusinessDayUtils() {
		holidays = new ArrayList<String>();
		holidays.add("1,1");
		holidays.add("1,6");
		holidays.add("4,4");
		holidays.add("4,5");
		holidays.add("5,1");
		holidays.add("5,3");
		holidays.add("5,23");
		holidays.add("6,3");
		holidays.add("8,15");
		holidays.add("11,1");
		holidays.add("11,11");
		holidays.add("12,25");
		holidays.add("12,26");

	}

	public static Date getLastWorkingDay(Date date) {
		if (Instance == null) {
			Instance = new BusinessDayUtils();
		}
		Date previousWorkingDate = date;
		if (date != null) {
			Calendar calInstance = Calendar.getInstance();
			calInstance.setTime(date);
			int weekDay = calInstance.get(Calendar.DAY_OF_WEEK);
			while (isWeekend(weekDay) || isHoliday(calInstance)) {
				calInstance.add(Calendar.DATE, -1);
				weekDay = calInstance.get(Calendar.DAY_OF_WEEK);
			}

			previousWorkingDate = calInstance.getTime();
		}

		return previousWorkingDate;
	}

	private static boolean isWeekend(int weekDay) {
		return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY;
	}

	private static boolean isHoliday(Calendar calendar) {
		return holidays.contains((calendar.get(Calendar.MONTH) + 1) + "," + calendar.get(Calendar.DATE));
	}
}
