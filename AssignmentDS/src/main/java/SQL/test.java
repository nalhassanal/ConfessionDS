package SQL;

import java.sql.Connection;

public class test {
    public static void main(String[] args) {
        SQLconnect Conn = new SQLconnect();
        Connection con = Conn.connector();
        if ( con != null){
            System.out.println("yessir");
        }
        else
            System.out.println("error");
    }
}
