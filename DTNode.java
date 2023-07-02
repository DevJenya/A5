import java.util.ArrayList;
import java.util.List;

public class DTNode {
    String nodeName = null;
    Boolean isThisALeafNode = false;
    DTNode parentNode = null;
    int numberOfChildren = 0;

    List<DTNode> childrenOfTheNode = new ArrayList<>();

    public DTNode(String nodeName, DTNode parentNode) {
        this.nodeName = nodeName;
        this.parentNode = parentNode;
    }

    public void addChild(String childName){
        childrenOfTheNode.add(new DTNode(childName, this));
        numberOfChildren++;
    }

    public DTNode getParent(){
        return parentNode;
    }

    public DTNode getChildOfValue(){
            return childrenOfTheNode.get(0); 
    }
}
