package edu.utcn.gpsm.model.dto;

import java.util.Date;

public class PositionDTO {
    private String latitude;
    private String longitude;
    private Date creationTime;
    private String terminalId;
   // private Integer userId;


    public PositionDTO(){

    }

    public PositionDTO(String latitude, String longitude, Date creationTime,String terminalId){//, Integer userId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.creationTime = creationTime;
        this.terminalId = terminalId;
       // this.userId = userId;

    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
}
