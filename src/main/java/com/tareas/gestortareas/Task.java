package com.tareas.gestortareas;
import java.time.ZonedDateTime;
import jakarta.persistence.*;


@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private ZonedDateTime desiredStartDate;
    private ZonedDateTime actualStartDate;
    private ZonedDateTime expectedEndDate;
    private ZonedDateTime actualEndDate;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public ZonedDateTime getDesiredStartDate() {
        return desiredStartDate;
    }

    public void setDesiredStartDate(ZonedDateTime desiredStartDate) {
        this.desiredStartDate = desiredStartDate;
    }

    public ZonedDateTime getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(ZonedDateTime actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public ZonedDateTime getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(ZonedDateTime expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public ZonedDateTime getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(ZonedDateTime actualEndDate) {
        this.actualEndDate = actualEndDate;
    }
}
