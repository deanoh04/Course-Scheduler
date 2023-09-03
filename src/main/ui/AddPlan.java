package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import model.*;

//stores a frame that handles user input for adding a course plan to a course matrix
public class AddPlan extends JFrame implements ActionListener {
    private final CourseMatrix matrix;
    private JTextField field;
    private final JButton submitNew;

    //EFFECTS: creates a new frame prompting the user to add a plan to a matrix
    public AddPlan(CourseMatrix matrix) {
        this.matrix = matrix;
        setTitle("Add a course plan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);
        setLayout(new FlowLayout());
        JLabel label = new JLabel("Input the name of the course plan: ");
        add(label);
        label.setLabelFor(field);

        field = new JTextField();
        field.setPreferredSize(new Dimension(250, 70));
        submitNew = new JButton("submit");
        submitNew.addActionListener(this);


        add(field);
        add(submitNew);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitNew) {
            if (matrix.contains(field.getText())) {
                this.dispose();
                JOptionPane.showMessageDialog(null,
                        "there's already a course plan with that name!", "error", JOptionPane.ERROR_MESSAGE);

            } else {
                matrix.addPlan(new CoursePlan(field.getText()));
                this.dispose();
                JOptionPane.showMessageDialog(null,
                        field.getText() + " was successfully added! ", "info", JOptionPane.INFORMATION_MESSAGE);
            }
            MenuFrame newMenu = new MenuFrame(matrix);
        }
    }



}
