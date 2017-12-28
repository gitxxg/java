package cn.ghl.tools.json;

public class ObjectBind {

    private String bindName;

    private String objectType;

    private String objectPath;

    private String jsonPath;

    private ObjectTranslate read;

    private ObjectTranslate write;

    private boolean isList = false;

    private String listObjectType;

    public String getBindName() {
        return bindName;
    }

    public void setBindName(String bindName) {
        this.bindName = bindName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public ObjectTranslate getRead() {
        return read;
    }

    public void setRead(ObjectTranslate read) {
        this.read = read;
    }

    public ObjectTranslate getWrite() {
        return write;
    }

    public void setWrite(ObjectTranslate write) {
        this.write = write;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public String getListObjectType() {
        return listObjectType;
    }

    public void setListObjectType(String listObjectType) {
        this.listObjectType = listObjectType;
    }

    @Override
    public String toString() {
        return "ObjectBind{" +
                "bindName='" + bindName + '\'' +
                ", objectType='" + objectType + '\'' +
                ", objectPath='" + objectPath + '\'' +
                ", jsonPath='" + jsonPath + '\'' +
                ", read=" + read +
                ", write=" + write +
                ", isList=" + isList +
                ", listObjectType='" + listObjectType + '\'' +
                '}';
    }
}
