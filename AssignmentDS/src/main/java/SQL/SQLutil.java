package SQL;

import java.sql.*;
import java.util.Date;

public class SQLutil {

    public SQLutil(){}

    public int getID(Connection con){
        // this method gets the latest id
        if (con == null)
            return -1;
        String query = "select * from confession";
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("ID");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }

    public void addReply(Connection con){
        if(con == null)
            return;
        int main = 1, reply = 7;
        PreparedStatement ps;

        try {
            String query = "insert into reply (main, reply) values (?,?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, main);
            ps.setInt(2, reply);

            boolean success = ps.execute();
            if(!success){
//                con.close();
                System.out.println("successfully added to table");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    /*
    TODO: read from all text files to transfer from txt to sql
     maybe only keep an id text file to just keep track of the created and to use autoincrement()
     so must add one more column to confession table "varchar(20) confID"
     and also change reply table to store varchar(20) not int
     */
    public void addToTable(Connection con){
        if(con == null)
            return;
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        String text = "this\n" +
                "is\n" +
                "a\n" +
                "{Test}\n" +
                "for\n" +
                "(special)\n" +
                "[random]\n" +
                "charaacters\n";

        try {
            PreparedStatement ps = null;
            String query ="";

            query = "insert into confession (content, timestamp) values (?,?)";

            ps = con.prepareStatement(query);
            ps.setString(1, text);
            ps.setTimestamp(2,ts);

            boolean success = ps.execute();
            if(!success){
//                con.close();
                System.out.println("successfully added to table");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void readReply(Connection con){
        int main, reply;
        String query = "select * from reply";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                main = rs.getInt("main");
                reply = rs.getInt("reply");
                System.out.println(main + "-->" + reply);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void readFromTable(Connection con){
        int id = 0;
        String confession = "";
        Timestamp time = null;
        Date date = null;
        String query = "select * from confession";
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getInt("ID");
                confession = rs.getString("content");
                time = rs.getTimestamp("timestamp");

                assert time != null;
                date = new Date(time.getTime());
                System.out.printf("ID: DS%05d \ncontent: \n%s \ntimestamp: %s", id, confession, date.toString());
                System.out.println();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

    }
}
