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
public class FlowTable {
    private List<FlowEntry> flowentries;
    
    private Configuration conf = Configuration.getInstance();
    
    
    public FlowTable() {
        flowentries = new ArrayList<>();
    }

    @XmlElement(name="flowentry", type=FlowEntry.class)
    public List<FlowEntry> getFlowentries() {
        return flowentries;
    }

    public void setFlowentries(List<FlowEntry> flowentries) {
        this.flowentries = flowentries;
    }
    
    
    void setEntriesDeleteOperation() {
        for (FlowEntry f : flowentries)
            f.setOperation("delete");
    }
    
    
    public void generateMultiLinearFlowEntries(List<NodeResource> nodes, int chains) {
        NodeResource node1, node2;
        List<Port> node1Ports, node2Ports;
        int port1ID, port2ID;
        
        node1 = nodes.get(0);
        node1Ports = node1.getPorts();
        
        FlowEntrySAPIn feSAP1In = new FlowEntrySAPIn(conf.getId(), node1.getId(), conf.getPort1ID(), node1Ports.get(0).getId());
        this.flowentries.add(feSAP1In);
        
        FlowEntrySAPOut feSAP1Out = new FlowEntrySAPOut(conf.getId(), node1.getId(), conf.getPort1ID(), node1Ports.get(0).getId());
        this.flowentries.add(feSAP1Out);
        
        for (int k=0; k<chains; k++) {
            int groupSize = nodes.size()/chains;
            int start = groupSize*k;

            
            node1 = nodes.get(0);
            
            // at the first interation (k=0) we link node0 and node1
            if (start == 0) {
                node2 = nodes.get(1);
                start++;
            }
            
            else
                node2 = nodes.get(start);

            node1Ports = node1.getPorts();
            node2Ports = node2.getPorts();

            port1ID = node1Ports.get(k+1).getId();
            port2ID = node2Ports.get(0).getId();

            for (int i=start; i<groupSize*(k+1) && i<nodes.size()-1; i++) {

                FlowEntry fe = new FlowEntry(node1.getId(), 
                                             node2.getId(), 
                                             port1ID,
                                             port2ID);
                this.flowentries.add(fe);

                node1 = nodes.get(i);
                node2 = nodes.get(i+1);

                node1Ports = node1.getPorts();
                node2Ports = node2.getPorts();

                port1ID = node1Ports.get(node1Ports.size()-1).getId();
                port2ID = node2Ports.get(0).getId();
            }


            node2 = nodes.get(nodes.size()-1);
            node2Ports = node2.getPorts();

            port2ID = node2Ports.get(k).getId();

            FlowEntry fe = new FlowEntry(node1.getId(), 
                                         node2.getId(), 
                                         port1ID,
                                         port2ID);

            this.flowentries.add(fe);
        }
        
        node2 = nodes.get(nodes.size()-1);
        node2Ports = node2.getPorts();
            
        FlowEntrySAPIn feSAP2In = new FlowEntrySAPIn(conf.getId(), node2.getId(), conf.getPort2ID(), node2Ports.get(node2Ports.size()-1).getId());
        this.flowentries.add(feSAP2In);
        
        FlowEntrySAPOut feSAP2Out = new FlowEntrySAPOut(conf.getId(), node2.getId(), conf.getPort2ID(), node2Ports.get(node2Ports.size()-1).getId());
        this.flowentries.add(feSAP2Out);
    }
    
    
}
