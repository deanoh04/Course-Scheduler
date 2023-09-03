package ui;

import model.CourseMatrix;
import model.EventLog;
import model.Event;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;


//stores the graphical interface behaviour of the course matrix
public class MenuFrame extends JFrame implements ActionListener, WindowListener {
    private static final String FILE_NAME = "./data/courseMatrix.json";
    private CourseMatrix matrix;
    private final Writer jsonWriter;
    private final Reader jsonReader;
    private JPanel mainPanel;
    private JButton newPlan;
    private JButton viewEditPlan;
    private JButton savePlan;
    private JPanel buttons;
    private JButton loadPlan;

    //EFFECTS: creates main menu of application
    public MenuFrame(CourseMatrix matrix) {
        this.matrix = matrix;
        setTitle("Course Matrix Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);
        addWindowListener(this);
        JLabel title = new JLabel("Course Matrix Main Menu");
        title.setFont(new Font("MV Boli", Font.BOLD, 30));
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.TOP);
        title.setSize(100, 20);
        title.setBackground(Color.CYAN);
        title.setLabelFor(mainPanel);
        title.setHorizontalAlignment(JLabel.CENTER);
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.CYAN);
        mainPanel.add(title);
        mainPanel.setLayout(new GridLayout(3, 1));
        add(mainPanel);
        matrix = new CourseMatrix();
        jsonWriter = new Writer(FILE_NAME);
        jsonReader = new Reader(FILE_NAME);
        runApp();
    }

    //EFFECTS: generates buttons on screen for navigating the main menu
    public void populateButtons() {
        newPlan = new JButton("Create a new course plan");
        newPlan.setSize(230, 200);
        newPlan.setBackground(Color.red);
        buttons.add(newPlan);
        newPlan.addActionListener(this);
        viewEditPlan = new JButton("View/Edit a specific course plan");
        viewEditPlan.setSize(230, 200);
        buttons.add(viewEditPlan);
        viewEditPlan.setVisible(true);
        viewEditPlan.addActionListener(this);
        savePlan = new JButton("save to file");
        savePlan.setSize(230, 200);
        buttons.add(savePlan);
        savePlan.addActionListener(this);
        loadPlan = new JButton("load from file");
        loadPlan.setSize(230, 200);
        buttons.add(loadPlan);
        loadPlan.addActionListener(this);
    }

    //EFFECTS: generates main menu for course matrix
    public void runApp() {
        buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 1, 10, 10));
        buttons.setBackground(Color.CYAN);
        populateButtons();
        JLabel imageLabel = new JLabel("");
        ImageIcon splashScreen =  new javax.swing.ImageIcon(getClass().getResource("education.png"));
        imageLabel.setIcon(splashScreen);
        mainPanel.add(imageLabel);
        mainPanel.add(buttons);
        setVisible(true);
    }

    //EFFECTS: manages user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newPlan) {
            dispose();
            AddPlan adder = new AddPlan(matrix);
        } else if (e.getSource() == viewEditPlan) {
            if (matrix.getPlanList().size() == 0) {
                JOptionPane.showMessageDialog(null,
                        "add a course plan first! ", "error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                ViewList list = new ViewList(matrix);
            }

        } else if (e.getSource() == savePlan) {
            saveCourseMatrix();
        } else if (e.getSource() == loadPlan) {
            loadCourseMatrix();
        }
    }

    // EFFECTS: saves the Course Matrix to file
    private void saveCourseMatrix() {
        try {
            jsonWriter.open();
            jsonWriter.write(matrix);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null,
                    "Saved current session" + " to " + FILE_NAME, "info", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to: " + FILE_NAME, "error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads Course Matrix from file
    private void loadCourseMatrix() {
        try {
            matrix = jsonReader.read();
            JOptionPane.showMessageDialog(null,
                    "Loaded session" + " from " + FILE_NAME, "info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write from: " + FILE_NAME, "error", JOptionPane.ERROR_MESSAGE);
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
