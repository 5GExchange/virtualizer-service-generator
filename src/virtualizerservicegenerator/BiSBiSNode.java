/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author uceeftu
 */
public final class BiSBiSNode extends Node {
    private NF_instances nfInstances;
    private FlowTable flowTable;
    private int nodes;
    
    public BiSBiSNode(String id, String name, String type, int nodes) {
        super(id, name, "BiSBiS");
        nfInstances = new NF_instances(); 
        flowTable = new FlowTable();
        this.nodes = nodes;
    }

    @XmlElement(name="NF_instances", type=NF_instances.class)
    public NF_instances getNfInstances() {
        return nfInstances;
    }

    public void setNfInstances(NF_instances nfInstances) {
        this.nfInstances = nfInstances;
    }

    @XmlElement(name="flowtable", type=FlowTable.class)
    public FlowTable getFlowTable() {
        return flowTable;
    }

    public void setFlowTable(FlowTable flowTable) {
        this.flowTable = flowTable;
    }
    
      
    public void createMultiLinearTopology(int chains) {
        addNode(chains+1);
        for (int i=1; i<nodes-1; i++)
            addNode(2);
        addNode(chains+1);
        createMultiLinearFlowEntries(chains);
        
    }

    
    public void setNFInstancesDelete() {
        nfInstances.setNodesDeleteOperation();
    }
    
    public void setFlowEntriesDelete() {
        flowTable.setEntriesDeleteOperation();
    }
    
    private void addNode(int ports) {
        nfInstances.addNode(ports);
    }
    
  
    private void createMultiLinearFlowEntries(int n) {
        flowTable.generateMultiLinearFlowEntries(nfInstances.getNodes(), n);
        
    }
}
