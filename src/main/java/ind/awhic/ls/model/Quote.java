package ind.awhic.ls.model;

public class Quote {
    private Meta meta;
    private Data[] data;

    public Meta getMeta() { return meta; }
    public void setMeta(Meta value) { this.meta = value; }

    public Data[] getData() { return data; }
    public void setData(Data[] value) { this.data = value; }
}