import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    CSVReader reader = new CSVReader("data.csv");

    List<String[]> data = reader.readFileIntoArray();

    String[] headers = data.remove(0);

    public void print(){
        System.out.println(Arrays.toString(headers));
    }
    
    public static void main(String[] args) {

        test test = new test();
        test.print();
    }

    public void evaluateAttributesForSplit(String[] headers, List<String []> data){
        int numberOfHeadersToEval = headers.length - 1; // remove last attribute from evaluation
        int numberOfRecordsInData = data.size();

        Map<String, AttributeInformationGain> attributesAndInformationGain = new HashMap<String, AttributeInformationGain>();

        // Go through each header
        //put attributes into hashmap
        for (int i = 0; i < numberOfHeadersToEval; i++) {
            List<String> distinctValuesInAttribute = new ArrayList<>();

            //under each header how many distinct values
            for(int k = 0; k < numberOfRecordsInData; k++){
                if(!distinctValuesInAttribute.contains(data.get(k)[i])){
                    distinctValuesInAttribute.add(data.get(k)[i]);
                }
            }

            // store 
            attributesAndInformationGain.put(headers[i], new AttributeInformationGain(distinctValuesInAttribute.size()));
        }       

    }


public class AttributeInformationGain{
      int numberOfDistinctValues;
      double informationGain = -1;

    public AttributeInformationGain(int distinctValues) {
        numberOfDistinctValues = distinctValues;
    }
  
}
}

