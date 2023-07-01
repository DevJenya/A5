import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeInformationGain{
      int numberOfDistinctValues;
      double informationGain = -1;
      double entropyOfTheWholeSet = -1;
      List<String> distinctValuesInAttribute = null;
      Map<String, Double> entropyOfDistinctValue = new HashMap<String, Double>(); 
      Map<String, Double> probabilityOfDistinctValue = new HashMap<String, Double>(); 
      

    public AttributeInformationGain(List<String> distinctValues) {
        numberOfDistinctValues = distinctValues.size();
        distinctValuesInAttribute = distinctValues;
    }

    public void calculateInformationGain(){
        informationGain = entropyOfTheWholeSet;

        entropyOfDistinctValue.forEach((key, value) -> informationGain -= probabilityOfDistinctValue.get(key)*value);

        System.out.println("Information Gain = " + informationGain);
    }
}