
package com.example.cpcalendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Datum implements java.io.Serializable, Cloneable,Comparable<Datum> {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("in_24_hours")
    @Expose
    private String in24Hours;
    @SerializedName("status")
    @Expose
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getIn24Hours() {
        return in24Hours;
    }

    public void setIn24Hours(String in24Hours) {
        this.in24Hours = in24Hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(Datum o) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(this.getStartTime());
            date2 = sdf.parse(o.getStartTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1.before(date2)) {
            // previous date
            return 1;
        }
        return 0;
    }
}
