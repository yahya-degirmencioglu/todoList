package de.yahya.todolist.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskDto {

    private int id;

    private String description;

    private Boolean complete;

    private String createdDate;

    private String modifiedDate;

    public TaskDto() {
    }

    public TaskDto(int id, String description, Boolean complete, String createdDate, String modifiedDate) {
        this.id = id;
        this.description = description;
        this.complete = complete;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public List<TaskDto> convertorDateToString(List<Task> altTasks) {
        List<TaskDto> newTasks = new ArrayList<>();
        TaskDto taskDto;
        for (Task t : altTasks) {
            taskDto = new TaskDto();
            taskDto.setId(t.getId());
            taskDto.setDescription(t.getDescription());
            try {
                taskDto.setCreatedDate(t.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))); // Date to String
            }catch (NullPointerException e) {
                taskDto.setCreatedDate("");
            }
            try {
                taskDto.setModifiedDate(t.getModifiedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))); // Date to String
            } catch (NullPointerException e) {
                taskDto.setModifiedDate("");
            }
            taskDto.setComplete(t.getComplete());
            newTasks.add(taskDto);
        }
        return newTasks;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
