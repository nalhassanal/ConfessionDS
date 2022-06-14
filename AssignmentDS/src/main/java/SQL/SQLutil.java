package SQL;

import main.confessionPair;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

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

    public ArrayList<confessionPair> readFromTable(Connection con){
        int id = 0;
        String confession = "";
        Timestamp time = null;
        Date date = null;
        String query = "select * from confession" ,  confID;
        PreparedStatement ps;
        ResultSet rs;
        confessionPair pair = new confessionPair();
        ArrayList<confessionPair> confessions = new ArrayList<>();

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getInt("ID");
                confID = String.format("DS%05d", id);

                confession = rs.getString("content");
                time = rs.getTimestamp("timestamp");

                date = new Date(time.getTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");

                pair.setId(confID);
                pair.setContent(confession);
                pair.setDate(dateFormat.format(date));
                confessions.add(pair);
                pair = new confessionPair();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return confessions;
    }
}
