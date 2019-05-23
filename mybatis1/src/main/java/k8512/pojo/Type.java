package k8512.pojo;

public class Type {
    private Integer typeid1;

    private String type;

    public Integer getTypeid1() {
        return typeid1;
    }

    public void setTypeid1(Integer typeid1) {
        this.typeid1 = typeid1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}