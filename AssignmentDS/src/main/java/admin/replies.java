package admin;

/**
 * container class that contains the root confession ID and the reply confession ID
 */
public final class replies {
    /**
     * the root confession ID
     */
    private String root,
    /**
     * the reply confession ID
     */
    reply;

    public replies(){}

    /**
     * getter for root variable
     * @return the root confession ID
     */
    public String getRoot() {
        return root;
    }

    /**
     * setter for root variable
     * @param root the new data to replace the old root data
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * getter for reply variable
     * @return the reply confession ID
     */
    public String getReply() {
        return reply;
    }

    /**
     * setter for reply variable
     * @param reply the new data to replace the old reply data
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    /**
     * @return string representation of replies class
     */
    @Override
    public String toString(){
        return reply + " -> " + root;
    }
}
