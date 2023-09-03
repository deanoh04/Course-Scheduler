package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CoursePlanTest {
    CoursePlan testPlan;
    Course t1;
    Course t2;


    @BeforeEach
    public void setup() {
        testPlan = new CoursePlan("name");
        t1 = new Course("t2", 90, 3);
        t2 = new Course("t1", 70, 4);
    }

    @Test
    public void constructorTest() {
        assertEquals(0, testPlan.getSize());
        assertEquals(0, testPlan.getWeightedAverage());
        assertEquals("name", testPlan.getName());
    }

    @Test
    public void addToListNotPresentTest() {
        testPlan.addToList(t1);
        assertEquals(1, testPlan.getSize());
        assertEquals(t1, testPlan.courseAt(0));
    }

    @Test
    public void addToListPresentTest() {
        testPlan.addToList(t1);
        testPlan.addToList(t1);
        assertEquals(1, testPlan.getSize());
        assertEquals(t1, testPlan.courseAt(0));
    }

    @Test
    public void addMultipleToListTest() {
        testPlan.addToList(t1);
        testPlan.addToList(t2);
        assertEquals(2, testPlan.getSize());
        assertEquals(t1, testPlan.courseAt(0));
        assertEquals(t2, testPlan.courseAt(1));
    }

    @Test
    public void removeFromListNotPresentTest() {
        testPlan.addToList(t1);
        testPlan.removeFromList(t2);
        assertEquals(1, testPlan.getSize());
        assertEquals(t1, testPlan.courseAt(0));
    }

    @Test
    public void removeOnePresentTest() {
        testPlan.addToList(t1);
        testPlan.removeFromList(t1);
        assertEquals(0, testPlan.getSize());
    }

    @Test
    public void removeTwoPresentTest() {
        testPlan.addToList(t1);
        testPlan.addToList(t2);
        testPlan.removeFromList(t1);
        assertEquals(1, testPlan.getSize());
        assertEquals(t2, testPlan.courseAt(0));
    }

    @Test
    public void getWeightedAverageTest() {
        testPlan.addToList(t1);
        testPlan.addToList(t2);
        assertEquals(79, testPlan.getWeightedAverage());
    }

    @Test
    public void noCoursesWeightedAverageTest() {
        assertEquals(0, testPlan.getWeightedAverage());
    }

    @Test
    public void convertToJsonTest() {
        testPlan.addToList(t1);
        testPlan.addToList(t2);
        JSONObject json = testPlan.convertToJson();
        JSONArray courseList = json.getJSONArray("courseList");
        assertEquals("name", json.get("name"));
        assertEquals(2, courseList.length());
    }

    @Test
    public void courseListToJsonTest() {
        testPlan.addToList(t1);
        testPlan.addToList(t2);
        JSONArray courseList = testPlan.courseListToJson();
        JSONObject course1 = courseList.getJSONObject(0);
        JSONObject course2 = courseList.getJSONObject(1);
        assertEquals(2, courseList.length());
        assertEquals(course1.get("name"), t1.getName());
        assertEquals(course1.get("average"), t1.getAverage());
        assertEquals(course1.get("credits"), t1.getCredits());
        assertEquals(course2.get("name"), t2.getName());
        assertEquals(course2.get("average"), t2.getAverage());
        assertEquals(course2.get("credits"), t2.getCredits());
    }


}