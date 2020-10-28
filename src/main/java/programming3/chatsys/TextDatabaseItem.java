package programming3.chatsys;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TextDatabaseItem  {
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
    public abstract String format();



}
