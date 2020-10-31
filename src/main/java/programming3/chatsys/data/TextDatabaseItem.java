package programming3.chatsys.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A Parseable is an object that can be parsed and saved to text files using a simple text format.
 * @author Maelick Claes (maelick.claes@oulu.fi)
 */
public abstract class TextDatabaseItem {
    protected TextDatabaseItem(String formatted) {
        this.parse(formatted);
    }

    protected TextDatabaseItem() {
    }

    public abstract void parse(String formatted);
    public abstract String format();

    public void save(String filename) {
        this.save(new File(filename));
    }

    public void save(File file) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file, true))) {
            out.write(this.format() + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(file + " cannot be opened", e);
        }
    }
}
