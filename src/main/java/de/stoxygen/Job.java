package de.stoxygen;

public class Job {
    private int downloader_jq_id;
    private int action;
    private String value;
    private int type_idtype;
    private int state;


    public Job() {

    }

    public int getDownloader_jq_id() {
        return downloader_jq_id;
    }

    public void setDownloader_jq_id(int downloader_jq_id) {
        this.downloader_jq_id = downloader_jq_id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType_idtype() {
        return type_idtype;
    }

    public void setType_idtype(int type_idtype) {
        this.type_idtype = type_idtype;
    }

    @Override
    public String toString() {
        String info = String.format("Job Info: id = %d, action = %d, value = %s", downloader_jq_id, action, value);
        return info;
    }
}
