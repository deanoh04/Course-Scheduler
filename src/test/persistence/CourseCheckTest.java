package persistence;


import model.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CourseCheckTest {
    protected void checkCourse(String name, double average, double credits, Course course) {
        assertEquals(name, course.getName());
        assertEquals(average, course.getAverage());
        assertEquals(credits, course.getCredits());
    }
}
