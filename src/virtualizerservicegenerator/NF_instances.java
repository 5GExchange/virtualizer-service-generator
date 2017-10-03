/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author uceeftu
 */
public class NF_instances {
    private List<NodeResource> nodes;
    
    public NF_instances() {
        nodes = new ArrayList<>();
    }

    @XmlElement(name="node", type=NodeResource.class)
    public List<NodeResource> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeResource> nodes) {
        this.nodes = nodes;
    }
    
    public void addNode(NodeResource n) {
        this.nodes.add(n);
    }
    
    public void addNode(int ports) {
        this.nodes.add(new NodeResource(ports));
    }

    public void setNodesDeleteOperation() {
        for (NodeResource n : nodes) 
            n.setOperation("delete");
    }
}
