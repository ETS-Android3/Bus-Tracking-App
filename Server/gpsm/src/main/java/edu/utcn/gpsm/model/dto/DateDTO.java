package edu.utcn.gpsm.model.dto;

import java.util.Date;

public class DateDTO {

    private Date startDate;
    private Date endDate;
    private String terminalId;

    public DateDTO() {
    }

    public DateDTO(Date startDate, Date endDate, String terminalId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.terminalId = terminalId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
}
