package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


// stores the course application and handles user input behaviour
public class CourseApp {
    private static final String FILE_NAME = "./data/courseMatrix.json";
    private CourseMatrix matrix;
    private Scanner input;
    private Writer jsonWriter;
    private Reader jsonReader;

    //EFFECTS: starts the course application
    public CourseApp() {
        matrix = new CourseMatrix();
        jsonWriter = new Writer(FILE_NAME);
        jsonReader = new Reader(FILE_NAME);
        runApp();
    }

    //MODIFIES: this
    //EFFECTS: runs the app
    private void runApp() {
        String userInput = "";
        boolean quit = false;
        startUp();
        while (!quit) {
            showMatrixOptions();
            userInput = input.next().toLowerCase();
            if (userInput.equals("q")) {
                quit = true;
            } else {
                processMatrix(userInput);
            }

        }

    }

    //EFFECTS: prints all the options available to the user
    public void showMatrixOptions() {
        System.out.println("Here are your options: ");
        System.out.println("1: Create a new course plan");
        System.out.println("2: View/Edit a specific course plan");
        System.out.println("3: compare the weighted averages of all course plans");
        System.out.println("4: save current session to file");
        System.out.println("5: load previous session from file");
        System.out.println("q: quit");

    }


    //MODIFIES: this
    //EFFECTS: initializes starting values of matrix and input
    private void startUp() {
        matrix = new CourseMatrix();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //REQUIRES: input must be one of "1", "2", "3", "4" or "5"
    //EFFECTS: processes user input for the initial selection screen
    private void processMatrix(String input) {
        if (input.equals("1")) {
            createNewPlan();
        } else if (input.equals("2")) {
            selectCoursePlan();
        } else if (input.equals("3")) {
            printAverages();
        } else if (input.equals("4")) {
            saveCourseMatrix();
        } else if (input.equals("5")) {
            loadCourseMatrix();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a new course plan and adds it to the matrix
    private void createNewPlan() {
        String name = "";
        System.out.println("please enter the name of your plan: ");
        name = input.next().toLowerCase();
        if (matrix.contains(name)) {
            System.out.println("there's already a course plan with that name! try again");
            System.out.println("\n");
            createNewPlan();
        } else {
            matrix.addPlan(new CoursePlan(name));
        }

    }

    //EFFECTS: selects course plan from list
    private void selectCoursePlan() {
        String userInput = "";
        showAllPlans();
        userInput = input.next().toLowerCase();
        if (!(userInput.equals("m"))) {
            runCoursePlan(userInput);
        }
    }


    //EFFECTS: shows options available for a chosen course plan
    private void runCoursePlan(String chosenPlan) {
        String userInput = "";
        boolean quit = false;
        while (!quit) {
            String planName = matrix.getPlan(Integer.parseInt(chosenPlan) - 1).getName();
            System.out.println("options for plan: " + planName);
            showPlanOptions();
            userInput = input.next().toLowerCase();
            if (userInput.equals("b")) {
                quit = true;
                selectCoursePlan();
            } else {
                processPlan(userInput, Integer.parseInt(chosenPlan) - 1);
            }
        }
    }

    //EFFECTS: displays all options the user can take with a specific course plan
    private void showPlanOptions() {
        System.out.println("1: View all courses in the course plan");
        System.out.println("2: Add a course to the course plan");
        System.out.println("3: Remove a course from the course plan");
        System.out.println("4: Edit a course in the course plan");
        System.out.println("5: Return the weighted average of the course plan");
        System.out.println("b: back to the list of all course plans");
    }

    //EFFECTS: processes user input for the chosen plan and action
    private void processPlan(String chosen, int chosenPlan) {
        if (chosen.equals("1")) {
            printCourses(chosenPlan);
        } else if (chosen.equals("2")) {
            processAddition(chosenPlan);
        } else if (chosen.equals("3")) {
            processRemoval(chosenPlan);
        } else if (chosen.equals("4")) {
            processEdit(chosenPlan);
        } else if (chosen.equals("5")) {
            printAverage(chosenPlan);
        }
    }

    //REQUIRES: matrix contains plan at index
    //EFFECTS: prints out all courses for the plan in a given index
    private void printCourses(int index) {
        CoursePlan plan = matrix.getPlan(index);
        System.out.println("Here are all courses in the plan named " + plan.getName());
        for (int i = 0; i < plan.getSize(); i++) {
            Course course = plan.courseAt(i);
            System.out.print((i + 1) + ": " + course.getName() + " is worth " + course.getCredits() + " credits");
            System.out.println(" and has an expected average of " + course.getAverage());
        }
        System.out.print("\n");
    }

    //REQUIRES: chosenPlan >= 0 and matrix contains plan at chosenPlan
    //MODIFIES: this
    //EFFECTS: creates a new course and adds it to the plan
    private void processAddition(int chosenPlan) {
        String code = "";
        String courseNumber = "";
        double average = 0;
        double credits = 0;
        System.out.print("Enter the course code of the course (e.g cpsc): ");
        code = input.next().toUpperCase();
        System.out.print("Enter the course number of the course (e.g 101): ");
        courseNumber = input.next();
        System.out.print("Enter your expected average for the course: ");
        average = Double.parseDouble(input.next());
        System.out.println("Enter how many credits the course is worth: ");
        credits = Double.parseDouble(input.next());
        Course newCourse = new Course(code + " " + courseNumber, average, credits);
        matrix.getPlan(chosenPlan).addToList(newCourse);
        System.out.println(newCourse.getName() + " has been successfully added! ");
        System.out.print("\n");
    }

    //REQUIRES: chosenPlan >= 0 and matrix contains plan at chosenPlan
    //MODIFIES: this
    //EFFECTS: guides the user through the removal of a chosen course from the chosen plan
    private void processRemoval(int chosenPlan) {
        CoursePlan plan = matrix.getPlan(chosenPlan);
        Course removeCourse;
        int index = 0;
        printCourses(chosenPlan);
        System.out.print("choose which course should be removed: ");
        index = Integer.parseInt(input.next()) - 1;
        removeCourse = plan.courseAt(index);
        plan.removeFromList(removeCourse);
        System.out.println(removeCourse.getName() + " has been successfully removed! ");
    }

    //REQUIRES: chosenPlan >= 0 and matrix contains plan at chosenPlan
    //MODIFIES: this
    //EFFECTS: guides the user through the editing of a chosen course from the chosen plan
    private void processEdit(int chosenPlan) {
        CoursePlan plan = matrix.getPlan(chosenPlan);
        Course editedCourse;
        int index = 0;
        printCourses(chosenPlan);
        System.out.print("choose which course should be edited: ");
        index = Integer.parseInt(input.next()) - 1;
        editedCourse = plan.courseAt(index);
        String code = "";
        String courseNumber = "";
        double average = 0;
        double credits = 0;
        System.out.print("Enter the course code of the course (e.g cpsc): ");
        code = input.next().toUpperCase();
        System.out.print("Enter the course number of the course (e.g 101): ");
        courseNumber = input.next();
        System.out.print("Enter your expected average for the course: ");
        average = Double.parseDouble(input.next());
        System.out.println("Enter how many credits the course is worth: ");
        credits = Double.parseDouble(input.next());
        editedCourse.setName(code + " " + courseNumber);
        editedCourse.setAverage(average);
        editedCourse.setCredits(credits);
        System.out.println(editedCourse.getName() + " has been successfully edited! ");
    }

    //EFFECTS: prints the weighted average of the chosen course plan
    private void printAverage(int chosenPlan) {
        CoursePlan plan = matrix.getPlan(chosenPlan);
        System.out.print("the weighted average for the plan " + plan.getName() + " is ");
        System.out.println(plan.getWeightedAverage() + "%");
        System.out.print("\n");
    }


    //EFFECTS: displays all course plans in the matrix
    private void showAllPlans() {
        System.out.println("select a course plan: ");
        for (int i = 0; i < matrix.getSize(); i++) {
            System.out.println((i + 1) + ": " + matrix.getPlan(i).getName());
        }
        System.out.println("m: back to matrix menu");
    }


    //EFFECTS: prints the weighted averages of all course plans in the matrix
    private void printAverages() {
        System.out.println("here are the averages of all your plans: ");
        List<CoursePlan> planList = matrix.getPlanList();
        for (CoursePlan plan : planList) {
            System.out.println(plan.getName() + ": " + plan.getWeightedAverage() + "%");
        }
    }

    // EFFECTS: saves the Course Matrix to file
    private void saveCourseMatrix() {
        try {
            jsonWriter.open();
            jsonWriter.write(matrix);
            jsonWriter.close();
            System.out.println("Saved current session" + " to " + FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to: " + FILE_NAME);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Course Matrix from file
    private void loadCourseMatrix() {
        try {
            matrix = jsonReader.read();
            System.out.println("Loaded saved matrix " + " from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Unable to read from: " + FILE_NAME);
        }
    }


}
