package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLconnect {
    public SQLconnect(){

    }

    public Connection connector(){
        String url = "jdbc:mysql://127.0.0.1:3306/confession?serverTimezone=UTC";
        String user = "nal";
        String password = "Hassanalharriz8122*";
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        return con;
    }
}
