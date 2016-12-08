package net.leeautumn.authsetandinspect.model;

import javax.persistence.*;

/**
 * Created by coolAutumn on 3/11/16.
 */
@Entity
@Table(name = "userInfo", schema = "lab_project", catalog = "")
public class UserInfoEntity {
    private int id;
    private String userName;
    private String userUnit;
    private String originalCode;
    private String matchCode;
    private String userIp;
    private String userHostName;
    private String userMacAddress;
    private String licenseStatus;
    private String startData;
    private String endData;
    private String everyDayStartWorkTime;
    private String everyDayEndWorkTime;
    private String minutesAfterStart;
    private String moudleCode;
    private String fieldValid;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "UserUnit")
    public String getUserUnit() {
        return userUnit;
    }

    public void setUserUnit(String userUnit) {
        this.userUnit = userUnit;
    }

    @Basic
    @Column(name = "OriginalCode")
    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    @Basic
    @Column(name = "MatchCode")
    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    @Basic
    @Column(name = "UserIP")
    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @Basic
    @Column(name = "UserHostName")
    public String getUserHostName() {
        return userHostName;
    }

    public void setUserHostName(String userHostName) {
        this.userHostName = userHostName;
    }

    @Basic
    @Column(name = "UserMacAddress")
    public String getUserMacAddress() {
        return userMacAddress;
    }

    public void setUserMacAddress(String userMacAddress) {
        this.userMacAddress = userMacAddress;
    }

    @Basic
    @Column(name = "LicenseStatus")
    public String getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(String licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    @Basic
    @Column(name = "StartData")
    public String getStartData() {
        return startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    @Basic
    @Column(name = "EndData")
    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    @Basic
    @Column(name = "EveryDayStartWorkTime")
    public String getEveryDayStartWorkTime() {
        return everyDayStartWorkTime;
    }

    public void setEveryDayStartWorkTime(String everyDayStartWorkTime) {
        this.everyDayStartWorkTime = everyDayStartWorkTime;
    }

    @Basic
    @Column(name = "EveryDayEndWorkTime")
    public String getEveryDayEndWorkTime() {
        return everyDayEndWorkTime;
    }

    public void setEveryDayEndWorkTime(String everyDayEndWorkTime) {
        this.everyDayEndWorkTime = everyDayEndWorkTime;
    }

    @Basic
    @Column(name = "MinutesAfterStart")
    public String getMinutesAfterStart() {
        return minutesAfterStart;
    }

    public void setMinutesAfterStart(String minutesAfterStart) {
        this.minutesAfterStart = minutesAfterStart;
    }

    @Basic
    @Column(name = "MoudleCode")
    public String getMoudleCode() {
        return moudleCode;
    }

    public void setMoudleCode(String moudleCode) {
        this.moudleCode = moudleCode;
    }

    @Basic
    @Column(name = "FieldValid")
    public String getFieldValid() {
        return fieldValid;
    }

    public void setFieldValid(String fieldValid) {
        this.fieldValid = fieldValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfoEntity that = (UserInfoEntity) o;

        if (id != that.id) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userUnit != null ? !userUnit.equals(that.userUnit) : that.userUnit != null) return false;
        if (originalCode != null ? !originalCode.equals(that.originalCode) : that.originalCode != null) return false;
        if (matchCode != null ? !matchCode.equals(that.matchCode) : that.matchCode != null) return false;
        if (userIp != null ? !userIp.equals(that.userIp) : that.userIp != null) return false;
        if (userHostName != null ? !userHostName.equals(that.userHostName) : that.userHostName != null) return false;
        if (userMacAddress != null ? !userMacAddress.equals(that.userMacAddress) : that.userMacAddress != null)
            return false;
        if (licenseStatus != null ? !licenseStatus.equals(that.licenseStatus) : that.licenseStatus != null)
            return false;
        if (startData != null ? !startData.equals(that.startData) : that.startData != null) return false;
        if (endData != null ? !endData.equals(that.endData) : that.endData != null) return false;
        if (everyDayStartWorkTime != null ? !everyDayStartWorkTime.equals(that.everyDayStartWorkTime) : that.everyDayStartWorkTime != null)
            return false;
        if (everyDayEndWorkTime != null ? !everyDayEndWorkTime.equals(that.everyDayEndWorkTime) : that.everyDayEndWorkTime != null)
            return false;
        if (minutesAfterStart != null ? !minutesAfterStart.equals(that.minutesAfterStart) : that.minutesAfterStart != null)
            return false;
        if (moudleCode != null ? !moudleCode.equals(that.moudleCode) : that.moudleCode != null) return false;
        if (fieldValid != null ? !fieldValid.equals(that.fieldValid) : that.fieldValid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userUnit != null ? userUnit.hashCode() : 0);
        result = 31 * result + (originalCode != null ? originalCode.hashCode() : 0);
        result = 31 * result + (matchCode != null ? matchCode.hashCode() : 0);
        result = 31 * result + (userIp != null ? userIp.hashCode() : 0);
        result = 31 * result + (userHostName != null ? userHostName.hashCode() : 0);
        result = 31 * result + (userMacAddress != null ? userMacAddress.hashCode() : 0);
        result = 31 * result + (licenseStatus != null ? licenseStatus.hashCode() : 0);
        result = 31 * result + (startData != null ? startData.hashCode() : 0);
        result = 31 * result + (endData != null ? endData.hashCode() : 0);
        result = 31 * result + (everyDayStartWorkTime != null ? everyDayStartWorkTime.hashCode() : 0);
        result = 31 * result + (everyDayEndWorkTime != null ? everyDayEndWorkTime.hashCode() : 0);
        result = 31 * result + (minutesAfterStart != null ? minutesAfterStart.hashCode() : 0);
        result = 31 * result + (moudleCode != null ? moudleCode.hashCode() : 0);
        result = 31 * result + (fieldValid != null ? fieldValid.hashCode() : 0);
        return result;
    }
}
