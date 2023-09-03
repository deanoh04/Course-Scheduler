package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Savable;

import java.util.ArrayList;
import java.util.List;

//stores a list of all course plans the user has created
public class CourseMatrix implements Savable {
    private List<CoursePlan> planList;

    //EFFECTS: creates new course matrix and initializes list
    public CourseMatrix() {
        this.planList = new ArrayList<>();
    }


    //MODIFIES: this
    //EFFECTS: adds given plan to course matrix
    public void addPlan(CoursePlan plan) {
        if (!planList.contains(plan)) {
            planList.add(plan);
            EventLog.getInstance().logEvent(new Event(plan.getName()
                    + " was added to the matrix"));
        }
    }

    //MODIFIES: this
    //EFFECTS: removes given plan from course matrix
    public void removePlan(CoursePlan plan) {
        if (planList.contains(plan)) {
            planList.remove(plan);
            EventLog.getInstance().logEvent(new Event(plan.getName()
                    + " was removed from the matrix"));
        }
    }

    //EFFECTS: returns plan at given index
    public CoursePlan getPlan(int index) {
        return planList.get(index);
    }

    //EFFECTS: returns plan with given name, returns null if plan with given name not in plan list
    public CoursePlan getPlan(String name) {
        CoursePlan foundPlan = null;
        for (CoursePlan plan : planList) {
            if (plan.getName().equals(name)) {
                foundPlan = plan;
            }
        }
        return foundPlan;
    }

    //EFFECTS: returns list of plans
    public List<CoursePlan> getPlanList() {
        return planList;
    }

    //EFFECTS: returns whether planList given plan
    public boolean contains(String name) {
        for (CoursePlan plan : planList) {
            if (plan.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    //EFFECTS: returns size of planList
    public int getSize() {
        return planList.size();
    }


    @Override
    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        json.put("planList", planListToJson());
        return json;
    }


    //EFFECTS: returns the planList in the course matrix as a JSON array
    public JSONArray planListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (CoursePlan p : planList) {
            jsonArray.put(p.convertToJson());
        }

        return jsonArray;
    }
}
