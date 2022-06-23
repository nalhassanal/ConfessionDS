package SQL;

import admin.User;
import admin.replies;
import main.MyQueue;
import main.confessionPair;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

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
            ps.close();
            rs.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return id;
    }

    public ArrayList<replies> readReply(Connection con){
        ArrayList<replies> ls = new ArrayList<>();
        int main, reply;
        String root, to;
        replies rep = new replies();
        String query = "select * from reply";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                main = rs.getInt("main");
                reply = rs.getInt("reply");
                root = String.format("DS%05d", main);
                to = String.format("DS%05d", reply);
                rep.setRoot(root);
                rep.setReply(to);
                ls.add(rep);
                rep = new replies();
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return ls;
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
            ps.close();
            rs.close();
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
            ps.close();
            rs.close();
        } catch (SQLException ex){ex.printStackTrace();}

        return ls;
    }

    public void deleteQueueRow(Connection con, int index){
        String updateQuery = "delete from QueueTable where idQueueTable = " + index;
        Statement stmt;
        try{
            stmt = con.createStatement();
            stmt.executeUpdate(updateQuery);
            stmt.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public LinkedList<Integer> getQueueID(Connection con){
        LinkedList<Integer> ls = new LinkedList<>();
        String query = "select * from QueueTable";
        int id;
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("idQueueTable");
                ls.add(id);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return ls;
    }

    public LinkedList<String> getQueueContents(Connection con){
        LinkedList<String> ls = new LinkedList<>();
        String query = "select * from QueueTable", contents;

        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                contents = rs.getString("content");
                ls.add(contents);
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
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
            ps.close();
            rs.close();
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
            ps.close();
            rs.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return confessions;
    }

    public ArrayList<User> getUsers(Connection con){
        ArrayList<User> ls = new ArrayList<>();
        User user = new User();
        String userN, userP;

        String query = "select * from adminUser";
        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                userN = rs.getString("username");
                userP = rs.getString("password");
                user.setUsername(userN);
                user.setPassword(userP);
                ls.add(user);
                user = new User();
            }
            ps.close();
            rs.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        if (ls.isEmpty()) {
            System.out.println("There are no existing users");
            return null;
        }
        return ls;
    }

    public MyQueue<confessionPair> getQueue(Connection con){
        MyQueue<confessionPair> q = new MyQueue<>();
        String query = "select * from QueueTable", id, content, date;
        confessionPair pair = new confessionPair();
        PreparedStatement ps;
        ResultSet rs;
        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getString("ID");
                content = rs.getString("content");
                date = rs.getString("time");
                pair.setId(id);
                pair.setContent(content);
                pair.setDate(date);
                q.enqueue(pair);
                pair = new confessionPair();
            }
            ps.close();
            rs.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return q;
    }
}
