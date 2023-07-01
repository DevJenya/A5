import java.util.ArrayList;
import java.util.List;

public class DTNode {
    String nodeName = null;
    Boolean isThisALeafNode = false;
    DTNode parentNode = null;

    List<DTNode> childrenOfTheNode = new ArrayList<>();

    public DTNode(String nodeName, DTNode parentNode) {
        this.nodeName = nodeName;
        this.parentNode = parentNode;
    }

    public void addChild(String childName){
        childrenOfTheNode.add(new DTNode(childName, this));
    }

    public DTNode getParent(){
        return parentNode;
    }
}
