/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;




public class NodeResource extends Node {
    private static int idCounter = 1;
    private Resource resources;
    private List<Port> ports;
    private int lastPortID = 0;
    
    private String operation = "create";
    
    private static String nodeType = Configuration.getInstance().getNodeToDeployType();

    public NodeResource(int portsNum) {
        this("host" + String.format("%03d", idCounter) + "id", "host" + String.format("%03d", idCounter) + "name", new Resource(), portsNum);
        idCounter++;
    }
    
    
    public NodeResource(String id, String name, Resource resources, int portsNum) {
        super(id, name, nodeType);
        this.resources = resources;
        
        ports = new ArrayList<>();
        this.addPorts(portsNum);
    }
    
    
    public Resource getResources() {
        return resources;
    }
    
    @XmlElement(name="port", type=Port.class)
    @XmlElementWrapper(name="ports")
    public List<Port> getPorts() {
        return ports;
    }
    
    
    public void setResources(Resource resources) {
        this.resources = resources;
    }
    
    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }
    
    public void addPort() {
        Port p = new Port(lastPortID++);
        this.ports.add(p);
    }
    
    public void addPorts(int n) {
        for (int i=0; i<n; i++)
            addPort();
    }

    @XmlAttribute
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    
}
