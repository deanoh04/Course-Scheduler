package persistence;

import model.CourseMatrix;
import org.json.JSONObject;

import java.io.*;

//stores information about writing to a JSON file
//CITATION: modeled after the JSONSerializationDemo with link:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class Writer {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String to;

    // EFFECTS: creates a new Writer that moves "to" to file
    public Writer(String destination) {
        this.to = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer and throws FileNotFoundException if to file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(to));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of CourseMatrix to file
    public void write(CourseMatrix cm) {
        JSONObject json = cm.convertToJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
