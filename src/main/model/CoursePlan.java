package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Savable;

import java.util.ArrayList;
import java.util.List;

// represents an individual course schedule with a list of courses
public class CoursePlan implements Savable {
    private List<Course> courseList;
    private String name;


    //EFFECTS: initializes course list with new list and weighted average
    public CoursePlan(String name) {
        courseList = new ArrayList<>();
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: adds course to course list if  not already present
    public void addToList(Course course) {
        if (!courseList.contains(course)) {
            courseList.add(course);
            EventLog.getInstance().logEvent(new Event(course.getName()
                    + " was added to the plan " + this.name));
        }
    }

    //MODIFIES: this
    //EFFECTS: removes course from course list if present in list
    public void removeFromList(Course course) {
        if (courseList.contains(course)) {
            courseList.remove(course);
            EventLog.getInstance().logEvent(new Event(course.getName()
                    + " was removed from the plan " + this.name));
        }

    }

    //EFFECTS: returns weighted average of all courses in course list, rounded
    //         to the nearest integer
    public int getWeightedAverage() {
        double totalAverage = 0;
        double totalCredits = 0;
        for (Course course : courseList) {
            totalAverage += (course.getAverage() * course.getCredits());
            totalCredits += course.getCredits();
        }
        if (totalCredits == 0) {
            return 0;
        }
        return (int) Math.round(totalAverage / totalCredits);
    }

    //EFFECTS: returns total number of courses in course plan
    public int getSize() {
        return courseList.size();
    }

    //REQUIRES: index >= 0, courseList.size() > 0
    //EFFECTS: returns the course at given index
    public Course courseAt(int index) {
        return courseList.get(index);
    }

    //EFFECTS: returns the name of the course plan
    public String getName() {
        return name;
    }


    @Override
    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("courseList", courseListToJson());
        return json;
    }

    //EFFECTS: returns the courseList as a JSONArray of courses
    public JSONArray courseListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : courseList) {
            jsonArray.put(c.convertToJson());
        }

        return jsonArray;
    }


}
