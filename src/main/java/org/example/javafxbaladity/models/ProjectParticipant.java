package org.example.javafxbaladity.models;

public class ProjectParticipant {
    private Integer project_id;
    private Integer participant_id;

    public ProjectParticipant(Integer project_id, Integer participant_id) {
        this.project_id = project_id;
        this.participant_id = participant_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(Integer participant_id) {
        this.participant_id = participant_id;
    }
}
