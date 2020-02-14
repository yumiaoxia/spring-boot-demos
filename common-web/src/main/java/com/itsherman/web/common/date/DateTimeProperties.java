package com.itsherman.web.common.date;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.common-web.format.pattern")
public class DateTimeProperties {

    private String date = "yyyy-MM-dd";

    private String dateTime = "yyyy-MM-dd HH:mm:ss";

    private String zoneId = "Asia/Shanghai";

    private String timeZone = "GMT+8";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
