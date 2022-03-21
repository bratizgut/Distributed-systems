package task2.models;

import task2.models.generated.Tag;

public class TagDB {

    private final Tag tag;
    private long nodeId;
    
    public TagDB() {
        this.tag = new Tag();
    }
    
    public TagDB(Tag tag) {
        this.tag = tag;
    }

    public TagDB(String key, String value, long nodeId) {
        this.tag = new Tag();
        tag.setK(key);
        tag.setV(value);
        this.nodeId = nodeId;
    }

    public String getKey() {
        return tag.getK();
    }

    public void setKey(String key) {
        tag.setK(key);
    }

    public String getValue() {
        return tag.getV();
    }

    public void setValue(String value) {
        tag.setV(value);
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

}
