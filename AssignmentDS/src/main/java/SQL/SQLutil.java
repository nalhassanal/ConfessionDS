package SQL;

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
}
