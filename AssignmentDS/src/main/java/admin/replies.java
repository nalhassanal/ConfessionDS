package admin;

public class replies {
    private String root, reply;

    public replies(){}

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString(){
        return reply + " replies to " + root;
    }
}
