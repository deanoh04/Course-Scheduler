package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course testCourse;

    @BeforeEach
    public void setup() {
        testCourse = new Course("default", 50, 3);
    }

    @Test
    public void constructorTest() {
        assertEquals(50, testCourse.getAverage());
        assertEquals("default", testCourse.getName());
        assertEquals(3, testCourse.getCredits());
    }

    @Test
    public void setNameTest() {
        testCourse.setName("different");
        assertEquals("different", testCourse.getName());
    }

    @Test
    public void setAverageZeroTest() {
        testCourse.setAverage(0);
        assertEquals(0, testCourse.getAverage());
    }

    @Test
    public void setAverageHundredTest() {
        testCourse.setAverage(100);
        assertEquals(100, testCourse.getAverage());
    }

    @Test
    public void setAverageDecimalTest() {
        testCourse.setAverage(51.5);
        assertEquals(51.5, testCourse.getAverage());
    }

    @Test
    public void setCreditsTest() {
        testCourse.setCredits(1.0);
        assertEquals(1.0, testCourse.getCredits());
    }

    @Test
    public void convertToJsonTest() {
        JSONObject sampleObject = new JSONObject();
        JSONObject testObject = testCourse.convertToJson();
        sampleObject.put("name", "default");
        sampleObject.put("average", 50.0);
        sampleObject.put("credits", 3.0);


        assertEquals(sampleObject.get("name"), testObject.get("name"));
        assertEquals(sampleObject.get("average"), testObject.get("average"));
        assertEquals(sampleObject.get("credits"), testObject.get("credits"));

    }
}