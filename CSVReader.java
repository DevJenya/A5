import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVReader {

    List<String[]> data = new ArrayList<String[]>();
       
    String path;
    String lineRead;

    public CSVReader(String path) {
        this.path = path;
    }

    public List<String[]> readFileIntoArray(){

    try {
        BufferedReader bReader = new BufferedReader(new FileReader(path));

        while((lineRead = bReader.readLine()) != null){
            String[] line = lineRead.split(",");
            data.add(line);
        }

        bReader.close();

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return data;
    
    }




}
