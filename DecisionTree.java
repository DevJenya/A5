import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.DESKeySpec;

class DecisionTree{

public static void main(String[] args) {

CSVReader reader = new CSVReader("data.csv");

List<String[]> data = reader.readFileIntoArray();

String[] headers = data.remove(0);
    
calculateEntropy(data, 4); 

}

//set needs to have headers removed prior to submission 
public static void calculateEntropy(List<String[]> set, int column){
    int sizeOfSet = set.size();

    int distinctResponsesInColumn = 0;
   
    Map<String, Integer> responsesInColumn = new HashMap<String, Integer>();

    for(int index = 0; index < sizeOfSet; index++){
        String response = set.get(index)[column];

        if(responsesInColumn.containsKey(response)){
            responsesInColumn.put(response, responsesInColumn.get(response) + 1);
        } else {
            responsesInColumn.put(response, 1);
        }
    }

    responsesInColumn.forEach((key, value) -> System.out.println(key + " " + value));


    double entropy = 0;

    for (Map.Entry<String,Integer> mapElement : responsesInColumn.entrySet()) {

            int value = mapElement.getValue();

            double probability = (double)value/sizeOfSet;

        entropy = entropy + (-1 * probability*log2(probability));

    }
    
    System.out.println("Entropy = " + entropy);
}

public static double log2(double n) {

    return Math.log(n) / Math.log(2);
}

}