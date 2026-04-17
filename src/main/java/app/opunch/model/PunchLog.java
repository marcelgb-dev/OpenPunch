package app.opunch.model;

import java.time.LocalDateTime;

public class PunchLog {

    private Integer id;
    private Integer userId;
    private LocalDateTime logTime;
    private String event;

    private User user;

    // Constructor vacío
    public PunchLog() {};

    // Constructor principal
    public PunchLog(Integer id, Integer userId, LocalDateTime logTime, String event) {
        this.id = id;
        this.userId = userId;
        this.logTime = logTime;
        this.event = event;
    }

    // Constructor sobrecargado con User
    public PunchLog(Integer id, Integer userId, LocalDateTime logTime, String event, User user) {
        this.id = id;
        this.userId = userId;
        this.logTime = logTime;
        this.event = event;
        this.user = user;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }
    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
