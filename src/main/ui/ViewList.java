package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import model.*;
import model.Event;

//stores frame that showcases a list of course plans in a matrix
public class ViewList extends JFrame implements ActionListener, WindowListener {
    private final CourseMatrix matrix;
    private final JList list;
    private final DefaultListModel listModel;

    private JButton editButton;
    private JButton quitButton;
    private JTextField planName;

    //EFFECTS: creates list of course plans
    public ViewList(CourseMatrix matrix) {
        addWindowListener(this);
        this.matrix = matrix;
        setTitle("View/edit course plans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLayout(new FlowLayout());


        listModel = new DefaultListModel();
        for (CoursePlan plan : matrix.getPlanList()) {
            listModel.addElement(plan.getName());
        }
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(700, 350);
            }
        };
        addButtons();
        add(listScrollPane);
        setVisible(true);
    }

    //EFFECTS: populates the screen with option buttons
    public void addButtons() {
        editButton = new JButton("Edit Plan");
        quitButton = new JButton("Back To Menu");
        editButton.addActionListener(this);
        quitButton.addActionListener(this);
        add(editButton);
        add(quitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton) {
            dispose();
            CourseTable table = new CourseTable(list.getSelectedIndex(), matrix);
        } else if (e.getSource() == quitButton) {
            dispose();
            MenuFrame back = new MenuFrame(matrix);
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
