package app.opunch.model;

public class Group {

    private Integer id;
    private String group_name;

    // Constructor vacío
    public Group() {};

    // Constructor principal
    public Group(Integer id, String group_name) {
        this.id = id;
        this.group_name = group_name;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }
    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
