
package main;

import SQL.SQLconnect;
import SQL.SQLutil;
import java.sql.Connection;
import java.sql.SQLException;


public class testRegister   {

    public static void main(String[] args) throws SQLException {
        SQLconnect c = new SQLconnect();
        Connection con = c.connector();
        SQLutil sq = new SQLutil();
        sq.register(con);
    }
    
}
