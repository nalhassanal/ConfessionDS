package SQL;

import java.sql.Connection;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        SQLconnect Conn = new SQLconnect();
        Connection con = Conn.connector();
        SQLutil util = new SQLutil();
        if ( con != null){
            System.out.println("Connection successfully made to ");

//            util.addToTable(con);
//            util.addReply(con);
//            util.readReply(con);
//            util.readFromTable(con);
        }
    }
}
