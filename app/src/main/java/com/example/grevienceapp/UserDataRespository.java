package com.example.grevienceapp;

/**
 * PROVIDES INFORMATION LIKE ----PF NUMBER----EMPLOYEE DB ID----EMPLOYEE LEVEL ---EMPLOYEE STATION
 *
 */
public class UserDataRespository {


        public String id; //pf number
        public String DBid;
        public UserDataRespository(String id)
        {
            this.id = id;


        }

        public static String getID() {
            return id;
        }



}
