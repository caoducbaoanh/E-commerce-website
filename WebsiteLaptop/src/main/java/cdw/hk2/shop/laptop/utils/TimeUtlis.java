package cdw.hk2.shop.laptop.utils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeUtlis {
	public Date convertToDateViaSqlTimestamp() {
		LocalDateTime local = LocalDateTime.now();
		return java.sql.Timestamp.valueOf(local);
	}

	public Date addDayTimeDate(int day) {
		LocalDateTime local = LocalDateTime.now();
		LocalDateTime localDay= local.plusDays(day);
		Date date = java.sql.Timestamp.valueOf(localDay);

		return date;

	}
}
