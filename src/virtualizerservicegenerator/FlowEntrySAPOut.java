/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

/**
 *
 * @author uceeftu
 */
public class FlowEntrySAPOut extends FlowEntry{
    private String infraID;
    private String nodeID;
    private String iface;
    private int portOutID;
    
    
    public FlowEntrySAPOut(String infraID, String nodeID, String iface, int out) {
        this(String.format("%01d", idCounter++), infraID, nodeID, iface, out);
    }
    
    public FlowEntrySAPOut(String id, String infraID, String nodeID, String iface, int out) {
        super.id = id;
        this.infraID = infraID;
        this.nodeID = nodeID;
        this.iface = iface;
        this.portOutID = out;
        
        this.setPort();
        this.setOut();
    }
    
    
    @Override
    public void setOut() {
        this.out = "/virtualizer/nodes/node[id=" + infraID + "]/ports/port[id=" + iface + "]";
    }

    @Override
    public void setPort() {
        //this.port  = "../../../NF_instances/node[id=" + nodeID + "]/ports/port[id=" + portOutID + "]";
        this.port  = "/virtualizer/nodes/node[id=" + infraID + "]/NF_instances/node[id=" + nodeID + "]/ports/port[id=" + portOutID + "]";
    }    
    
}
