package main;

public class confessionPair {
    private String id;
    private String content;

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
}
