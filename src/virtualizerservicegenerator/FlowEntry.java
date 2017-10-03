/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author uceeftu
 */
public class FlowEntry {
    protected String id;
    protected String port;
    protected String out;
    
    private int portID;
    private int portOutID;
    
    private String node1ID;
    private String node2ID;
    
    private String operation = "create";
    
    protected static int idCounter = 0;

    
    public FlowEntry() {}
    
    public FlowEntry(String node1ID, String node2ID, int port, int out) {
        //this(String.format("%03d", idCounter++), node1ID, node2ID, port, out);
        this(String.format("%01d", idCounter++), node1ID, node2ID, port, out);
    }
    
    public FlowEntry(String id, String node1ID, String node2ID, int port, int out) {
        this.id = id;
        this.portID = port;
        this.portOutID = out;
        this.node1ID = node1ID;
        this.node2ID = node2ID;
        
        this.setPort();
        this.setOut();
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getPort() {
        return port;
    }

    public void setPort() {
        this.port = "../../../NF_instances/node[id=" + node1ID + "]/ports/port[id=" + portID + "]";
    }

    @XmlElement
    public String getOut() {
        return out;
    }

    public void setOut() {
        this.out  = "../../../NF_instances/node[id=" + node2ID + "]/ports/port[id=" + portOutID + "]";
    }   

    @XmlAttribute
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
