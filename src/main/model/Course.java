package model;

//stores individual courses with names and expected averages for a given course

import org.json.JSONObject;
import persistence.Savable;

public class Course implements Savable {
    private double average;
    private String name;
    private double credits;

    //REQUIRES: 0 <= average <= 100
    //EFFECTS: initializes course with name and average
    public Course(String name, double average, double credits) {
        this.name = name;
        this.average = average;
        this.credits = credits;
    }

    //EFFECTS: returns average
    public double getAverage() {
        return average;
    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }

    //EFFECTS: returns number of credits given by course
    public double getCredits() {
        return credits;
    }

    //MODIFIES: this
    //EFFECTS: sets new value for credits
    public void setCredits(double credits) {
        this.credits = credits;
        EventLog.getInstance().logEvent(new Event("the course " + this.name + "has been changed to have "
                + credits + " credits"));
    }

    //REQUIRES: 0 <= average <= 100
    //MODIFIES: this
    //EFFECTS: sets new value for average
    public void setAverage(double average) {
        this.average = average;
        EventLog.getInstance().logEvent(new Event("the course " + this.name + "has been changed to have "
                + average + " average"));
    }

    //MODIFIES: this
    //EFFECTS: sets new value for name
    public void setName(String name) {
        String prevName = this.name;
        this.name = name;
        EventLog.getInstance().logEvent(new Event("the course with previous name"
                + prevName + "has been changed to have "
                + name + " name"));
    }

    @Override
    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("average", average);
        json.put("credits", credits);
        return json;
    }


}
