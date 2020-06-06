package com.example.grevienceapp;

/**
 * PROVIDES INFORMATION LIKE ----PF NUMBER----EMPLOYEE DB ID----EMPLOYEE LEVEL ---EMPLOYEE STATION
 *
 */
public class UserDataRespository {



        private String Eid;
        private String name;
        private String pfNumber;
        private String IDrive;
        private String BG;
        private String DOB;
        private String Contact;
        private String EContact;
        private String Qual;
        private String stPfNumber;
        private String DOApp;
        private String DJS;
        private String PME;
        private String RC;
        private String Award;
        private String Sex;
        private String State;
        private String Desg;
        private String Section;
        private String Station;
        private String WorkX;

    /**
     *
     * @param Eid
     * @param name
     * @param IDrive
     * @param BG
     * @param DOB
     * @param Contact
     * @param EContact
     * @param Qual
     * @param stPfNumber
     * @param DOApp
     * @param DJS
     * @param PME
     * @param RC
     * @param Award
     * @param Sex
     * @param State
     * @param Desg
     * @param Section
     * @param Station
     * @param WorkX
     */
    /**
     *
     * Add the String DJS,String PME,String RC,
     */
        public UserDataRespository(String Eid,String name,String  IDrive,String BG,String DOB,String Contact,String EContact,String Qual,String stPfNumber,String DOApp,String Award,String Sex,String State,String Desg,String Section,String Station,String WorkX,String sCode,String tiCount,String empLevel) {
            this.Eid=Eid;
            this.name=name;
            this.IDrive=IDrive;
            this.BG=BG;
            this.DOB=DOB;
            this.Contact=Contact;
            this.EContact=EContact;
            this.Qual=Qual;
            this.stPfNumber=stPfNumber;
            this.DOApp=DOApp;
            /**
             * this.DJS=DJS;
             * this.PME=PME;
             * this.RC=RC;
             */
            this.Award=Award;
            this.Sex=Sex;
            this.State=State;
            this.Desg=Desg;
            this.Section=Section;
            this.Station=Station;
            this.WorkX=WorkX;
            //this.sCode=sCode;
            //this.tiCount=tiCount;
            //this.empLevel=WorkX;


        }

        public String getId() {
            return Eid;
        }

        public void setId(String Eid) {
            this.Eid = Eid;
        }

    /**
     * End
     * @return
     */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    /**
     * End
     * @return
     */
        public String getIDrive() {
            return IDrive;
        }

        public void seIDrive(String IDrive) {
            this.IDrive = IDrive;
        }
    /**
     * End
     * @return
     */
        public String getBG() {
            return BG;
        }

        public void setBG(String BG) {
            this.BG = BG;
        }
    /**
     * End
     * @return
     */
        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }
    /**
     * End
     * @return
     */
        public String getContact() {
            return Contact;
        }

        public void setContact(String Contact) {
            this.Contact = Contact;
        }
    /**
     * End
     * @return
     */
        public String geEContact() {
            return EContact;
        }

        public void setEContact(String EContact) {
            this.EContact = EContact;
        }
    /**
     * End
     * @return
     */
        public String geQual() {
            return Qual;
        }

        public void setQual(String Qual) {
            this.Qual = Qual;
        }
    /**
     * End
     * @return
     */
        public String getstPfNumber() {
            return stPfNumber;
        }

        public void setstPfNumber(String stPfNumber) {
            this.stPfNumber = stPfNumber;
        }

    /**
     * End
     * @return
     */
        public String getDOApp() {
            return DOApp;
        }

        public void setDOApp(String DOApp) {
            this.DOApp = DOApp;
        }
        /***
    /**
     * End
     * @return

        public String getDJS() {
            return DJS;
        }

        public void setDJS(String DJS) {
            this.DJS = DJS;
        }
    /**
     * End
     * @return

        public String getPME() {
            return PME;
        }

        public void setPME(String PME) {
            this.PME = PME;
        }
    /**
     * End
     * @return

        public String getRC() {
            return RC;
        }

        public void setRC(String RC) {
            this.RC = RC;
        }
    /**
     * End
     * @return
     */
        public String getAward() {
            return Award;
        }

        public void setAward(String Award) {
            this.Award = Award;
        }
    /**
     * End
     * @return
     */
        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }
    /**
     * End
     * @return
     */
        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }
    /**
     * End
     * @return
     */
        public String getDesg() {
            return Desg;
        }

        public void setDesg(String Desg) {
            this.Desg = Desg;
        }
    /**
     * End
     * @return
     */
        public String getSection() {
            return Section;
        }

        public void setSection(String Section) {
            this.Section = Section;
        }
    /**
     * End
     * @return
     */

        public String getStation() {
            return Station;
        }

        public void setStation(String Station) {
            this.Station = Station;
        }
    /**
     * End
     * @return
     */
        public String getWorkX() {
            return WorkX;
        }

        public void setWorkX(String WorkX) {
            this.WorkX = WorkX;
        };
    /**
     * End
     * @return
     */
}
