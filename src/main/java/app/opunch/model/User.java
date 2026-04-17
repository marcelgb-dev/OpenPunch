package app.opunch.model;

public class User {
    private Integer id;
    private Integer groupId;
    private String qrToken;
    private String username;
    private String password;
    private Integer role;
    private String name;
    private String surname;

    private Group group;
    private PunchLog lastLog;

    // Constructor vacío
    public User(){};

    // Constructor principal
    public User(Integer id, Integer groupId, String qrToken, String username, String password, Integer role, String name, String surname) {
        this.id = id;
        this.groupId = groupId;
        this.qrToken = qrToken;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
    }

    // Constructor sobrecargado con Group
    public User(Integer id, Integer groupId, String qrToken, String username, String password, Integer role, String name, String surname, Group group) {
        this.id = id;
        this.groupId = groupId;
        this.qrToken = qrToken;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    // Comprueba si el usuario está activo o no
    public boolean  isActive() {
        // Si es el primer log, devolvemos FALSE
        if (lastLog == null)
            return false;

        // Si no es el primer log, dependiendo del log anterior devolvemos TRUE (IN) o FALSE (OUT)
        return lastLog.getEvent().equals("IN");
    }
    public void setActive(boolean active) {}

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getQrToken() {
        return qrToken;
    }
    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }

    public PunchLog getLastLog() {
        return lastLog;
    }
    public void setLastLog(PunchLog lastLog) {
        this.lastLog = lastLog;
    }
}
