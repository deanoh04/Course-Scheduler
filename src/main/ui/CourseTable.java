package ui;

import model.*;
import model.Event;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class CourseTable extends JFrame implements ActionListener, WindowListener {
    private final CoursePlan plan;
    private final CourseMatrix matrix;
    private final JTable table;
    private final String[][] data;
    private JList list;
    private DefaultListModel listModel;
    private JPanel buttons;
    private JButton courseAdd;
    private JButton quit;
    private JButton filter;
    private JButton unfilter;
    private JButton delete;

    //REQUIRES: there is a plan in matrix with the given index
    //EFFECTS: initializes frame showing all courses in a plan as a table
    public CourseTable(int index, CourseMatrix matrix) {
        dispose();
        this.matrix = matrix;
        addWindowListener(this);
        plan = matrix.getPlan(index);
        setTitle("add/edit courses in" + plan.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLayout(new GridLayout(2, 1));


        data = new String[plan.getSize()][3];
        for (int i = 0; i < plan.getSize(); i++) {
            data[i][0] = plan.courseAt(i).getName();
            data[i][1] = plan.courseAt(i).getAverage() + "";
            data[i][2] = plan.courseAt(i).getCredits() + "";

        }
        String[] columnNames = {"Course Name", "Average", "Credits"};
        table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        add(sp);
        populateButtons(true);
        // Frame Size
        setSize(500, 200);
        // Frame Visible = true
        setVisible(true);
    }

    //REQUIRES: plan exists in matrix
    //EFFECTS: initializes frame showing all courses in a plan as a table
    public CourseTable(CoursePlan plan, CourseMatrix matrix) {
        dispose();
        this.matrix = matrix;
        this.plan = plan;
        addWindowListener(this);
        setTitle("add/edit courses in" + plan.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLayout(new GridLayout(2, 1));


        data = new String[plan.getSize()][3];
        for (int i = 0; i < plan.getSize(); i++) {
            data[i][0] = plan.courseAt(i).getName();
            data[i][1] = plan.courseAt(i).getAverage() + "";
            data[i][2] = plan.courseAt(i).getCredits() + "";

        }
        String[] columnNames = {"Course Name", "Average", "Credits"};
        table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        add(sp);
        populateButtons(true);
        // Frame Size
        setSize(500, 200);
        // Frame Visible = true
        setVisible(true);
    }

    //EFFECTS generates buttons for manipulating the course list, with i determining whether the list
    //        is filtered or not filtered.
    public void populateButtons(boolean i) {
        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        courseAdd = new JButton("add a course");
        quit = new JButton("back to plan list");
        delete = new JButton("delete selected course");
        delete.addActionListener(this);
        quit.addActionListener(this);
        courseAdd.addActionListener(this);
        buttons.add(courseAdd);
        buttons.add(delete);
        if (i) {
            filter = new JButton("filter by A's only");
            filter.addActionListener(this);
            buttons.add(filter);
        } else {
            unfilter = new JButton("unfilter plan");
            unfilter.addActionListener(this);
            buttons.add(unfilter);
        }
        buttons.add(quit);
        add(buttons);
    }

    //EFFECTS: initializes settings for the frame window
    public void initializeFrame() {
        setTitle("Show Filtered List" + plan.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLayout(new GridLayout(2, 1));
    }

    //EFFECTS: generates new frame showing only courses with averages >= 86
    public void filterAction() {
        this.dispose();
        this.getContentPane().removeAll();
        this.repaint();
        ArrayList<Course> filtered = new ArrayList<>();
        for (int i = 0; i < plan.getSize(); i++) {
            if (plan.courseAt(i).getAverage() >= 86) {
                filtered.add(plan.courseAt(i));
            }
        }
        String[][] newData = new String[filtered.size()][3];
        for (int j = 0; j < filtered.size(); j++) {
            newData[j][0] = filtered.get(j).getName();
            newData[j][1] = filtered.get(j).getAverage() + "";
            newData[j][2] = filtered.get(j).getCredits() + "";
        }
        String[] columnNames = {"Course Name", "Average", "Credits"};
        JTable table1 = new JTable(newData, columnNames);
        JScrollPane sp = new JScrollPane(table1);
        add(sp);
        populateButtons(false);
        // Frame Size
        setSize(500, 200);
        // Frame Visible = true
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == courseAdd) {
            this.dispose();
            AddCourse addCourse = new AddCourse(matrix, plan);
        } else if (e.getSource() == quit) {
            this.dispose();
            ViewList list = new ViewList(matrix);
        } else if (e.getSource() == filter) {
            filterAction();
        } else if (e.getSource() == unfilter) {
            this.dispose();
            CourseTable table = new CourseTable(plan, matrix);
        } else if (e.getSource() == delete) {
            this.dispose();
            plan.removeFromList(plan.courseAt(table.getSelectedRow()));
            CourseTable refreshed = new CourseTable(plan, matrix);
        }
    }

    //EFFECTS: prints the events to the console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.getDescription() + "\n");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        printLog(EventLog.getInstance());
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
