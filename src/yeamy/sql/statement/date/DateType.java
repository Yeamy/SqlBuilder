package yeamy.sql.statement.date;

public enum DateType {
	MICROSECOND, //
	SECOND, //
	MINUTE, //
	HOUR, //
	DAY, //
	WEEK, //
	MONTH, //
	QUARTER, //
	YEAR, //
	SECOND_MICROSECOND, //
	MINUTE_MICROSECOND, //
	MINUTE_SECOND, //
	HOUR_MICROSECOND, //
	HOUR_SECOND, //
	HOUR_MINUTE, //
	DAY_MICROSECOND, //
	DAY_SECOND, //
	DAY_MINUTE, //
	DAY_HOUR, //
	YEAR_MONTH

//	https://www.jb51.net/article/100897.htm
//	set @dt = '2008-09-10 07:15:30.123456';
//	
//	select date(@dt); -- 2008-09-10
//	select time(@dt); -- 07:15:30.123456
//	select year(@dt); -- 2008
//	select quarter(@dt); -- 3
//	select month(@dt); -- 9
//	select week(@dt); -- 36
//	select day(@dt); -- 10
//	select hour(@dt); -- 7
//	select minute(@dt); -- 15
//	select second(@dt); -- 30
//	select microsecond(@dt); -- 123456
//	select extract(year from @dt); -- 2008
//	select extract(quarter from @dt); -- 3
//	select extract(month from @dt); -- 9
//	select extract(week from @dt); -- 36
//	select extract(day from @dt); -- 10
//	select extract(hour from @dt); -- 7
//	select extract(minute from @dt); -- 15
//	select extract(second from @dt); -- 30
//	select extract(microsecond from @dt); -- 123456select extract(year_month from @dt); -- 200809
//	select extract(day_hour from @dt); -- 1007
//	select extract(day_minute from @dt); -- 100715
//	select extract(day_second from @dt); -- 10071530
//	select extract(day_microsecond from @dt); -- 10071530123456
//	select extract(hour_minute from @dt); -- 715
//	select extract(hour_second from @dt); -- 71530
//	select extract(hour_microsecond from @dt); -- 71530123456
//	select extract(minute_second from @dt); -- 1530
//	select extract(minute_microsecond from @dt); -- 1530123456
//	select extract(second_microsecond from @dt); -- 30123456

}