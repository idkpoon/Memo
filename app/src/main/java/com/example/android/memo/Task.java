package com.example.android.memo;

/**
 * Created by 21poonkw1 on 8/9/2019.
 */

public class Task {

    private int id;
    private String name;
    private String time;
    private String date;
    private int category;
    private int priority;
    private String status;

    public Task(int id, String name, String date, String time, int category, int priority, String status) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.category = category;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
