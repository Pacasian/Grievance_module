package com.example.grevienceapp.LeaveModule;

import java.security.PublicKey;

public class LeaveModelView {


    public String Lid;
    public String pfNumber;
    public String noDays;
    public String empLevel;
    public String leaveType;
    public String fromDate;
    public String toDate;
    public String name4;
    public String reason;
    public String design4;
    public String station4;
    public String verified;



    public LeaveModelView(String Lid, String pfNumber, String noDays, String empLevel, String leaveType, String fromDate, String toDate, String name4, String reason, String design4, String station4, String verified) {
        this.Lid = Lid;
        this.pfNumber = pfNumber;
        this.noDays = noDays;
        this.empLevel = empLevel;
        this.leaveType = leaveType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.name4 = name4;
        this.reason = reason;
        this.design4 = design4;
        this.station4 = station4;
        this.verified = verified;
    }
    public String getLid() {
        return Lid;
    }

    public String getPfNumber() {
        return pfNumber;
    }

    public String getNoDays() {
        return noDays;
    }

    public String getEmpLevel() {
        return empLevel;
    }

    public String getLeaveType() {
        return leaveType;
    }
    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }
    public String getName4() {
        return name4;
    }

    public String getReason() {
        return reason;
    }

    public String getDesign4() {
        return design4;
    }

    public String getStation4() {
        return station4;
    }
    public String getVerified() {
        return verified;
    }

}
