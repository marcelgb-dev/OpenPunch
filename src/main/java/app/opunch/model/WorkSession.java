package app.opunch.model;

import java.time.LocalDateTime;
import java.time.Duration;

public class WorkSession {

    // Attributes
    private Integer id;
    private Integer userId;
    private Integer startLogId;
    private Integer endLogId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationMinutes;

    private User user;

    // Constructor
    public WorkSession(){

    }
    public WorkSession(Integer userId, LocalDateTime startTime, LocalDateTime endTime){
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Returns the duration in minutes based on startTime and endTime
    public int calculateDuration() {
        if (startTime == null || endTime == null)
            return 0;

        return (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }

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

    public Integer getStartLogId() {
        return startLogId;
    }

    public void setStartLogId(Integer startLogId) {
        this.startLogId = startLogId;
    }

    public Integer getEndLogId() {
        return endLogId;
    }

    public void setEndLogId(Integer endLogId) {
        this.endLogId = endLogId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
