package model;

public class Trainee {
    private int id;
    private String name;
    private String email;
    private String department;
    private double stipEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getStipEnd() {
        return stipEnd;
    }

    public void setStipEnd(double stipEnd) {
        this.stipEnd = stipEnd;
    }
}