package SQL;

import admin.User;
import fileUtil.FileUtil;
import main.confessionPair;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import main.confession;

public class SQLutil {
    private SQLconnect connector = new SQLconnect();
    private Connection con;
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
        Integer id;
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
    
    public LinkedList<String> getQueueConfessionID(Connection con){
        LinkedList<String> ls = new LinkedList<>();
        String query = "select * from QueueTable", contents;

        PreparedStatement ps;
        ResultSet rs;

        try{
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                contents = rs.getString("ID");
                ls.add(contents);
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

    public void logreg(Connection con){
        String userName, pass;
        String query = "select * from adminUser";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()){
                userName = rs.getString("username");
                pass = rs.getString("password");
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    public void register(Connection con) throws SQLException{


        Scanner sc = new Scanner(System.in);

        System.out.println("Insert Username : ");
        String user = sc.nextLine();
        System.out.println("Insert Password : ");
        String pass = sc.nextLine();

        String query =  "INSERT INTO adminUser (username, password)" + "VALUES (?,?)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, user);
        statement.setString(2, pass);
        statement.execute();

    }

    public void Login(Connection con){
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



    public void deleteRow(Connection con){
        String updateQuery = "delete from adminUser where password = '123'";
        String query = "select * from adminUser";
        Statement stmt;
        ResultSet rs;
        try{
            stmt = con.createStatement();
            stmt.executeUpdate(updateQuery);
            rs = stmt.executeQuery(query);
            while (rs.next()){
                System.out.println("Username: " + rs.getString("username") + ", Password: " + rs.getString("password"));
            }
            stmt.close();
            rs.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
