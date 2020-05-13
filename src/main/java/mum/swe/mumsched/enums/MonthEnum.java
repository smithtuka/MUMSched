package mum.swe.mumsched.enums;

public enum MonthEnum {
	January, February, March, April, May, June, July, August, September, October, November, December;
	public MonthEnum nextMonth() {
		switch (this) {
		case January:
			return February;
		case February:
			return March;
		case March:
			return April;
		case April:
			return May;
		case May:
			return June;
		case June:
			return July;
		case July:
			return August;
		case August:
			return September;
		case September:
			return October;
		case October:
			return November;
		case November:
			return December;
		case December:
			return January;
		default:
			return null;
		}
	}
	
	public static MonthEnum getMonth(int monthIntValue) {
		switch (monthIntValue) {
		case 2:
			return MonthEnum.February;
		case 3:
			return MonthEnum.March;
		case 4:
			return MonthEnum.April;
		case 5:
			return MonthEnum.May;
		case 6:
			return MonthEnum.June;
		case 7:
			return MonthEnum.July;
		case 8:
			return MonthEnum.August;
		case 9:
			return MonthEnum.September;
		case 10:
			return MonthEnum.October;
		case 11:
			return MonthEnum.November;
		case 12:
			return MonthEnum.December;
		case 1:
			return MonthEnum.January;
		default:
			return null;
		}
	}

}
