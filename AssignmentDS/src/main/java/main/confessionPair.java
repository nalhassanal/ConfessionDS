package main;

public class confessionPair implements Comparable<confessionPair> {
    private String id;
    private String content;

    public confessionPair(){
        id = null;
        content = null;
    }

    public confessionPair(String id, String content){
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        int idNum = Integer.parseInt(id.substring(2));
        return String.format("Confession id : DS%05d \n %s", idNum, content);
    }

    @Override
    public int compareTo(confessionPair o) {
        if (convertId(id) > convertId(o.id))
            return 1;
        else if (convertId(id) < convertId(o.id))
            return -1;
        return 0;
    }

    private int convertId (String id){
        String convId = id.substring(2);
        return Integer.parseInt(convId);
    }
}
