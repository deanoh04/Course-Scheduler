package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class CourseMatrixTest {
    CourseMatrix cm;
    CoursePlan c1;
    CoursePlan c2;

    @BeforeEach
    public void setup() {
        cm = new CourseMatrix();
        c1 = new CoursePlan("plan1");
        c2 = new CoursePlan("plan2");

    }

    @Test
    public void constructorTest() {
        assertEquals(0, cm.getSize());
    }

    @Test
    public void addPlanNormallyTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);

        assertEquals(2, cm.getSize());
        assertEquals(c1, cm.getPlan(0));
        assertEquals(c2, cm.getPlan(1));
    }

    @Test
    public void addPlanContainsTest() {
        cm.addPlan(c1);
        cm.addPlan(c1);

        assertEquals(1, cm.getSize());
        assertEquals(c1, cm.getPlan(0));
    }

    @Test
    public void removePlanNormallyTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);
        cm.removePlan(c1);

        assertEquals(1, cm.getSize());
        assertEquals(c2, cm.getPlan(0));
    }

    @Test
    public void removeContainsNormallyTest() {
        cm.addPlan(c1);
        cm.removePlan(c2);

        assertEquals(1, cm.getSize());
        assertEquals(c1, cm.getPlan(0));
    }

    @Test
    public void getPlanStringTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);

        assertEquals(c1, cm.getPlan("plan1"));
        assertEquals(c2, cm.getPlan("plan2"));

    }

    @Test
    public void getPlanStringNullTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);

        assertEquals(null, cm.getPlan("plan3"));
    }

    @Test
    public void getPlanListTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);

        List<CoursePlan> planList = cm.getPlanList();
        assertEquals(cm.getSize(), planList.size());
        assertEquals(cm.getPlan(0), planList.get(0));
        assertEquals(cm.getPlan(1), planList.get(1));

    }

    @Test
    public void containsTrueTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);

        assertEquals(true, cm.contains("plan1"));
        assertEquals(true, cm.contains("plan2"));
    }

    @Test
    public void containsFalseTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);

        assertEquals(false, cm.contains("plan3"));
        assertEquals(false, cm.contains("plan4"));
    }

    @Test
    public void convertToJsonTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);
        JSONObject json = cm.convertToJson();
        JSONArray planList = json.getJSONArray("planList");
        assertEquals(2, planList.length());
    }

    @Test
    public void planListToJsonTest() {
        cm.addPlan(c1);
        cm.addPlan(c2);
        JSONArray planList = cm.planListToJson();
        JSONObject plan1 = planList.getJSONObject(0);
        JSONObject plan2 = planList.getJSONObject(1);
        assertEquals(2, planList.length());
        assertEquals("plan1", plan1.get("name"));
        assertEquals("plan2", plan2.get("name"));
    }
}
