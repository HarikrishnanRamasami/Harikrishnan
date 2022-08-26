package qa.com.qic.common.vo;

import java.io.Serializable;

public class CrmHolidays implements Serializable {

    private String holidaysId;
    private String holidaysYear;
    private String name;
    private String fromDt;
    private String toDt;
    private String holidaysType;
    private String currDate;

    public String getHolidaysId() {
		return holidaysId;
	}

	public void setHolidaysId(String holidaysId) {
		this.holidaysId = holidaysId;
	}

    public String getHolidaysYear() {
		return holidaysYear;
	}

	public void setHolidaysYear(String holidaysYear) {
		this.holidaysYear = holidaysYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFromDt() {
		return fromDt;
	}

	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}

	public String getToDt() {
		return toDt;
	}

	public void setToDt(String toDt) {
		this.toDt = toDt;
	}

	public String getHolidaysType() {
		return holidaysType;
	}

	public void setHolidaysType(String holidaysType) {
		this.holidaysType = holidaysType;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

}
