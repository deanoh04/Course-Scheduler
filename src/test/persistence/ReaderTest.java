package persistence;

import model.Course;
import model.CourseMatrix;
import model.CoursePlan;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest extends CourseCheckTest {
    @Test
    void testReaderNonExistentFile() {
        Reader reader = new Reader("./data/badFile.json");
        try {
            CourseMatrix cm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCourseMatrix() {
        Reader reader = new Reader("./data/testReaderEmptyCourseMatrix.json");
        try {
            CourseMatrix cm = reader.read();
            assertEquals(0, cm.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFilledCourseMatrix() {
        Reader reader = new Reader("./data/testReaderFilledCourseMatrix.json");
        try {
            CourseMatrix cm = reader.read();
            CoursePlan c1 = new CoursePlan("plan1");
            CoursePlan c2 = new CoursePlan("plan1");
            Course t1 = new Course("t1", 70, 4);
            Course t2 = new Course("t2", 90, 3);
            Course first = cm.getPlan(0).courseAt(0);
            Course second = cm.getPlan(1).courseAt(0);

            assertEquals(2, cm.getSize());
            assertEquals(c1.getName(), cm.getPlan(0).getName());
            assertEquals(c2.getName(), cm.getPlan(1).getName());
            checkCourse(t1.getName(), t1.getAverage(), t1.getCredits(), first);
            checkCourse(t2.getName(), t2.getAverage(), t2.getCredits(), second);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
