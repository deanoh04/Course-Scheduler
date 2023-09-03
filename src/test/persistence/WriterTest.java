package persistence;

import model.Course;
import model.CourseMatrix;
import model.CoursePlan;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest extends CourseCheckTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CourseMatrix cm = new CourseMatrix();
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCourseMatrix() {
        try {
            CourseMatrix cm = new CourseMatrix();
            Writer writer = new Writer("./data/testWriterEmptyCourseMatrix.json");
            writer.open();
            writer.write(cm);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmptyCourseMatrix.json");
            cm = reader.read();
            assertEquals(0, cm.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterFilledCourseMatrix() {
        try {
            CourseMatrix cm = new CourseMatrix();
            CoursePlan c1 = new CoursePlan("plan1");
            CoursePlan c2 = new CoursePlan("plan1");
            Course t1 = new Course("t1", 70, 4);
            Course t2 = new Course("t2", 90, 3);
            c1.addToList(t1);
            c2.addToList(t2);
            cm.addPlan(c1);
            cm.addPlan(c2);
            Writer writer = new Writer("./data/testWriterFilledCourseMatrix.json");
            writer.open();
            writer.write(cm);
            writer.close();

            Reader reader = new Reader("./data/testWriterFilledCourseMatrix.json");
            cm = reader.read();
            Course first = cm.getPlan(0).courseAt(0);
            Course second = cm.getPlan(1).courseAt(0);

            assertEquals(2, cm.getSize());
            assertEquals(c1.getName(), cm.getPlan(0).getName());
            assertEquals(c2.getName(), cm.getPlan(1).getName());
            checkCourse(t1.getName(), t1.getAverage(), t1.getCredits(), first);
            checkCourse(t2.getName(), t2.getAverage(), t2.getCredits(), second);


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
