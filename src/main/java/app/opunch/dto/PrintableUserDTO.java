package app.opunch.dto;

import app.opunch.model.WorkSession;

import java.util.List;

public record PrintableUserDTO(
    Integer id,
    String name,
    String surname,
    List<WorkSession> sessions,
    Integer totalHours,
    Integer totalMinutes
) {}
