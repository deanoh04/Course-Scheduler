# Dean Oh's CPSC 210 Course List Project

## What Will It Do and Who Will Use It?
The project I have chosen for this course is an application
which allows the user to preview the weighted average of their 
grades, allowing users to create course plans, inputting the courses and the expected averages 
of chosen courses along with their credit values into such lists in order to give the user insight into what their
specific academic standing is at any given moment. The program will
allow users to save course plans in order to compare possible
averages with different schedules
and perhaps what one's average would look like with or without certain courses.
This will be an easy way for students to visualize academic performance. 
Possible extra features could include:
- a bar graph that compares the averages for all the course lists the user has created
- a system to suggest whether to drop or keep selected courses in a given course list based on what the user's expected
in such courses are

This application will **primarily be for UBC students** as it will use UBC default values for credits, 
but it can theoretically allow for any university student to create course lists and see their predicted averages.
This will allow for students to better visualize their academic performance and create course plans based on hard numbers
rather than estimation.

## Why Did I Choose This?
I chose to pursue this project because I was frustrated with constantly trying to use a calculator to calculate my
weighted averages, having to constantly recalculate values to see if it would be better for my academic performance to
drop a course or add a new one. I feel like this application would help me and others like me create better academic schedules
through visualizing my possible averages, and prevent constant worrying about whether I have the best schedule possible.

## User Stories:
- As a user, I want to be able to create a course and add it to a course plan
- As a user, I want to be able to select a course in a course plan and input the expected average for that course
- As a user, I want to be able to select a course plan and view the weighted average of all courses in the course plan
- As a user, I want to be able to remove a course from a course plan 
- As a user, I want to be able to see statistics which compare the weighted averages of all course plans I have created.
- As a user, I want the option to save my CourseMatrix to file but also have the option not to do so.
- As a user, I want the option to load the previously saved CourseMatrix from file and resume where I left off with that CourseMatrix.


# Instructions for Grader

- You can generate the first required action related to adding Courses to a Course Plan by adding a new course plan, 
navigating to it, adding a few courses and then pressing "filter by A's" to filter the courses in the plan that have A averages
- You can generate the second required action related to adding Courses to a Course Plan by adding a new course plan,
  navigating to it, adding a few courses and then pressing "delete course" with a course selected to delete a course from a plan
- You can save the state of my application by pressing the save button in the main menu
- You can reload the state of my application by pressing the load button in the main menu



# Phase 4: Task 2
If I create a new course plan named dean, select that course plan and add a new course 
called cpsc 110, then select cpsc 110 and press delete, and then exit the program, I will get the \
following output:

dean was added to the matrix

cpsc 110 was added to the plan dean

cpsc 110 was removed from the plan dean

# Phase 4: Task 3
I think I would refactor the project in two major ways: firstly I would
reduce code duplication in the UI class, perhaps by extracting out duplicated behaviour in 
the model package by making the Course, Course Plan and CourseMatrix classes follow the
composite pattern. This would allow me to reduce duplication in things like adding, removing 
and getting list items by converting things into leaves and composites. 



