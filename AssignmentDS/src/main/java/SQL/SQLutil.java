package SQL;

import main.confessionPair;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public ArrayList<String> getDate(Connection con){
        String dateTime = "";
        ArrayList<String> ls = new ArrayList<>();
        Timestamp time = null;
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String query = "select * from confession";
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()){
                time = rs.getTimestamp("timestamp");
                date = new Date(time.getTime());
                dateTime = dateFormat.format(date);
                ls.add(dateTime);
            }
        } catch (SQLException ex){ex.printStackTrace();}

        return ls;
    }

    public ArrayList<String> getDateTime(Connection con){
        String dateTime = "";
        ArrayList<String> ls = new ArrayList<>();
        Timestamp time = null;
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a");

        String query = "select * from confession";
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()){
                time = rs.getTimestamp("timestamp");
                date = new Date(time.getTime());
                dateTime = dateFormat.format(date);
                ls.add(dateTime);
            }
        } catch (SQLException ex){ex.printStackTrace();}

        return ls;
    }

    public ArrayList<String> getContents(Connection con){
        String confession = "";
        String query = "select * from confession";
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<String> contents = new ArrayList<>();

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()){
                confession = rs.getString("content");
                contents.add(confession);
            }

        } catch (SQLException ex){ex.printStackTrace();}

        return contents;
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

                pair.setId(confID);
                pair.setContent(confession);
                pair.setCurrentDate(date);
                confessions.add(pair);
                pair = new confessionPair();
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return confessions;
    }
}
