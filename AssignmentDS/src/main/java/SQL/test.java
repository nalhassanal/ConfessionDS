package SQL;

import main.confessionPair;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
//        System.out.println(ls.size());

        LinkedList<String> contents = util.getQueueContents(con);
        LinkedList<Integer> contentID = util.getQueueID(con);
        int index = contents.indexOf("nama saya adam\n" +
                "saya haritu ada berkenan dkat pompuan cantik ni\n" +
                "nak amik gambar tp x brani\n" +
                "sedihnya hidup saya\n" +
                "tapi xpelah...maybe nnti ada jodoh kot\n" +
                "in syaa allah\n"); // husna
        int toBeRemoved = contentID.get(index);

        util.deleteQueueRow(con, toBeRemoved);



//        int indexToRemove = contents.indexOf("JHKHK\n") + 1;
//        int indexToRemove = 24;
//        util.deleteQueueRow(con, indexToRemove);
//        System.out.println(contents.indexOf("JHKHK\n")); //.indexOf("JHKHK")
        Queue<String> q = contents;


//        System.out.println(q.poll());
    }
}
