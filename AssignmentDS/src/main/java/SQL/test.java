package SQL;

import main.confessionPair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) throws SQLException {
        SQLconnect Conn = new SQLconnect();
        Connection con = Conn.connector();
        SQLutil util = new SQLutil();
        ArrayList<confessionPair> ls = null;
        if (con != null){
//            util.readReply(con);
            ls = util.readFromTable(con);
//            util.readQueue(con);
        }
        System.out.println(ls.size());

    }
}
