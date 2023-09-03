package persistence;

import model.CourseMatrix;
import model.CoursePlan;
import model.Course;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


import org.json.*;

// stores information about reading from a JSON file
//CITATION: modeled after the JSONSerializationDemo with link:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class Reader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public Reader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a Course Matrix from file and then returns it;
    // throws IOException if there is an error reading from file
    public CourseMatrix read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCourseMatrix(jsonObject);
    }

    // EFFECTS: reads a source file from a string and then returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses a CourseMatrix from a JSON object and returns it
    private CourseMatrix parseCourseMatrix(JSONObject jsonObject) {
        CourseMatrix cm = new CourseMatrix();
        addCoursePlans(cm, jsonObject);
        return cm;
    }

    // MODIFIES: cm
    // EFFECTS: parses course plans from JSON object and adds them to CourseMatrix
    private void addCoursePlans(CourseMatrix cm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("planList");
        for (Object json : jsonArray) {
            JSONObject coursePlan = (JSONObject) json;
            addCoursePlan(cm, coursePlan);
        }
    }

    // MODIFIES: cm
    // EFFECTS: parses thingy from JSON object and adds it to CourseMatrix
    private void addCoursePlan(CourseMatrix cm, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONArray courseList = jsonObject.getJSONArray("courseList");
        CoursePlan plan = new CoursePlan(name);
        for (Object json : courseList) {
            JSONObject course = (JSONObject) json;
            addCourse(plan, course);
        }
        cm.addPlan(plan);
    }

    // MODIFIES: plan
    // EFFECTS: parses thingy from JSON object and adds it to the course plan
    private void addCourse(CoursePlan plan, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double average = jsonObject.getDouble("average");
        double credits = jsonObject.getDouble("credits");
        Course course = new Course(name, average, credits);
        plan.addToList(course);
    }
}
