import java.util.*;


class DecisionTree{

    static String[] headers = null;

public static void main(String[] args) {

CSVReader reader = new CSVReader("data.csv");

List<String[]> data = reader.readFileIntoArray();

headers = data.remove(0);
    

String rootNodeName = evaluateAttributesForSplit(headers, data);
List<String> headersWithoutRootHeader = new ArrayList<>();
int indexOfRootNode = -1;
DTNode rootNode = new DTNode(rootNodeName, null);

//make a list of new headers to consider for further splits
for(int i = 0; i < headers.length; i++){
    if(!Objects.equals(rootNodeName, headers[i])){
        headersWithoutRootHeader.add(headers[i]);
    } else {
        indexOfRootNode = i;
    }
}

    //convert list to array             
    String[] headerArray = new String[headersWithoutRootHeader.size()];
    headerArray = headersWithoutRootHeader.toArray(new String[headerArray.length]);

    //recursive calls 
    findChildren(data,  indexOfRootNode,  rootNode, headerArray);
}

//recursive call to find out children
public static void findChildren(List<String[]> data, int indexOfParentNode, DTNode parentNode, String[] headerArray){
    //find out how many records are in the dataset
    int sizeOfSet = data.size();
   
    HashSet<String> uniqueChildren = new HashSet<String>();

    for(int index = 0; index < sizeOfSet; index++){
        uniqueChildren.add(data.get(index)[indexOfParentNode]);
    }

    for(String childName : uniqueChildren){
        parentNode.addChild(childName);
    }

    
    for(DTNode child: parentNode.childrenOfTheNode){
    
        System.out.println("This node " + child.nodeName);

        System.out.println("Parent " + child.getParent().nodeName);

        List<String[]> dataSub = new ArrayList<>();

                //build sub dataset which has only records which have the value = childname which is calculated
                //in the column currently being evaluated 
                for (String[] record : data) {
                    if(Objects.equals(record[indexOfParentNode], child.nodeName)){
                        List <String> temp = new ArrayList<>();
                        for(int index = 0; index < record.length; index++){
                            if(index != indexOfParentNode){
                                    temp.add(record[index]);
                            }
                        }


                        dataSub.add(temp.toArray(new String[temp.size()]));
                    }                   
                } 

                for (String[] strings : dataSub) {
                    System.out.println(Arrays.toString(strings));
                }

                

                String x = evaluateAttributesForSplit(headerArray, dataSub);
                child.addChild(x);

                System.out.println("Going to split on " + x);
                System.out.println("This node name is " + child.nodeName);

                List<String> headersWithoutRootHeader = new ArrayList<>();
              
                int indexOfThisChild = -1;

                for(int i = 0; i < headerArray.length; i++){
                    if(!Objects.equals(child.getChildOfValue().nodeName, headerArray[i])){
                    headersWithoutRootHeader.add(headerArray[i]);
                } 
                }

                if(child.childrenOfTheNode.isEmpty()){
                    break;
                } else {
                // find index of the attribute in the context of all the headers stored in global variable
                  for(int i = 0; i < headers.length; i++){
                    if(!Objects.equals(child.getChildOfValue().nodeName, headers[i])){
                } else {
                    indexOfThisChild = i;
                }
                }

                  //convert list to array             
                String[] headerArrayNext = new String[headersWithoutRootHeader.size()];
                headerArrayNext = headersWithoutRootHeader.toArray(new String[headerArray.length]);

                //check that there are attributes to split on 
                if(headerArray.length > 1){
                      //recursive calls 
                    findChildren(dataSub,  indexOfThisChild,  child.getChildOfValue(), headerArrayNext);
                }

                

                }

    }
    
}


//set needs to have headers removed prior to submission 
public static double calculateEntropy(List<String[]> set, int column){
    int sizeOfSet = set.size();
   
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

    System.out.println("Entropy calulated = " + entropy);
    return entropy;
}

public static double log2(double n) {

    return Math.log(n) / Math.log(2);
}

 public static String evaluateAttributesForSplit(String[] headersList, List<String []> data){
        int numberOfHeadersToEval = headersList.length - 1; // remove last attribute from evaluation
        int numberOfRecordsInData = data.size();

        Map<String, AttributeInformationGain> attributesAndInformationGain = new HashMap<String, AttributeInformationGain>();

        // Go through each header
        //put header into hashmap along with information relating to it stored in AttributeInformationGain class
        for (int i = 0; i < numberOfHeadersToEval; i++) {
            List<String> distinctValuesInAttribute = new ArrayList<>();

            //under each header count how many distinct values
            //
            for(int k = 0; k < numberOfRecordsInData; k++){
                System.out.println("k = " + k + " , i = " + i + " headers: " + headersList.length);
                for(String s: headersList) {
                    System.out.print(s + " ");}


                if(!distinctValuesInAttribute.contains(data.get(k)[i])){
                    distinctValuesInAttribute.add(data.get(k)[i]);
                }
            }

            // store header name and associated class (which stores info gain and number of distinct values variables)
            attributesAndInformationGain.put(headersList[i], new AttributeInformationGain(distinctValuesInAttribute));
        }      
        
        //Go through each header and calculate the information gain 
        // decide which to choose

        double entropyOfTheSet = calculateEntropy(data, data.get(0).length-1);

        for(int i = 0; i < numberOfHeadersToEval; i++){
            int numberOfValuesInHeader = attributesAndInformationGain.get(headersList[i]).numberOfDistinctValues;
            //set entropy of the whole set into the attributeAndInformationGain class IOT calculate info gain
            attributesAndInformationGain.get(headersList[i]).entropyOfTheWholeSet = entropyOfTheSet;

            for(int k = 0; k < numberOfValuesInHeader; k++){
                String valueToCalculateEntropyFor = attributesAndInformationGain.get(headersList[i]).distinctValuesInAttribute.get(k);

                List<String[]> dataSub = new ArrayList<>();

                //build sub dataset which has only records which have the value which is calculated
                //in the column currently being evaluated 
                for (String[] record : data) {
                    if(Objects.equals(record[i], valueToCalculateEntropyFor)){
                        dataSub.add(record);
                    }                   
                }

                for (String[] strings : dataSub) {
                    System.out.println(Arrays.toString(strings));
                }

                double entropyForThisValue = calculateEntropy(dataSub, dataSub.get(0).length-1);

                //set probability of this entropy used in calculating the info gain
                attributesAndInformationGain.get(headersList[i]).probabilityOfDistinctValue.put(valueToCalculateEntropyFor, (double)dataSub.size()/numberOfRecordsInData);

                //set entropy of this value
                attributesAndInformationGain.get(headersList[i]).entropyOfDistinctValue.put(valueToCalculateEntropyFor,entropyForThisValue);

                

                System.out.println("--------");
            }
             attributesAndInformationGain.get(headersList[i]).calculateInformationGain();
        }

        String headerWithHighestInfoGain = "";
        double highestInformationGainValue = 0;
        //compare information gain for all headers to pick the one to split on
        for(int i = 0; i < numberOfHeadersToEval; i++){
            if(attributesAndInformationGain.get(headersList[i]).informationGain > highestInformationGainValue){
                headerWithHighestInfoGain = headersList[i];
                highestInformationGainValue = attributesAndInformationGain.get(headersList[i]).informationGain;
            }
        }
           
        System.out.println("Highest info gain - " + headerWithHighestInfoGain + " with gain of - " + highestInformationGainValue);

        return headerWithHighestInfoGain;
    }
  
}

