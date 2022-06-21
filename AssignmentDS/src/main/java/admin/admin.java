package admin;

import SQL.SQLconnect;

import java.sql.Connection;

public class admin {
    private Connection con;
    public admin(){
        SQLconnect connect = new SQLconnect();
        con = connect.connector();
    }


}
