package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

//stores a frame that handles user input for adding a course to a course plan
public class AddCourse extends JFrame implements ActionListener {
    private final CourseMatrix matrix;
    private final CoursePlan plan;
    private Course course;
    private JTextField name;
    private JTextField average;
    private JTextField credits;
    private final JButton submitNew;

    //EFFECTS: generates an add course frame and initializes matrix and plan
    public AddCourse(CourseMatrix matrix, CoursePlan plan) {
        this.matrix = matrix;
        this.plan = plan;
        setTitle("Add a course");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setResizable(false);
        setLayout(new GridLayout(6, 1, 2, 0));

        populateFields();

        submitNew = new JButton("submit");
        submitNew.addActionListener(this);
        add(submitNew);
        pack();
        setVisible(true);

    }

    //EFFECTS: creates the text fields and labels for course creation and displays it to the user
    public void populateFields() {
        JLabel label = new JLabel("Input the name of the course: ");
        add(label);
        label.setLabelFor(name);

        name = new JTextField();
        name.setPreferredSize(new Dimension(250, 70));
        add(name);

        JLabel label2 = new JLabel("Input the average of the course: ");
        add(label2);
        label.setLabelFor(average);

        average = new JTextField();
        average.setPreferredSize(new Dimension(250, 70));
        add(average);

        JLabel label3 = new JLabel("Input how many credits the course is worth: ");
        add(label3);
        label.setLabelFor(credits);

        credits = new JTextField();
        credits.setPreferredSize(new Dimension(250, 70));
        add(credits);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitNew) {
            this.dispose();
            course = new Course(name.getText(), Double.parseDouble(average.getText()),
                    Double.parseDouble(credits.getText()));
            plan.addToList(course);
            JOptionPane.showMessageDialog(null,
                    name.getText() + " was successfully added! ", "info", JOptionPane.INFORMATION_MESSAGE);
            CourseTable table = new CourseTable(plan, matrix);
        }
    }

}
