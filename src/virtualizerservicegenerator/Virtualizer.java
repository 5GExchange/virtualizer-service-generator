/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.namespace.QName;
import us.monoid.json.JSONException;
import us.monoid.web.Resty;
import us.monoid.web.Resty.Option;
import static us.monoid.web.Resty.content;
import static us.monoid.web.Resty.content;

/**
 *
 * @author uceeftu
 */
public class Virtualizer {
    private int size;
    private String id;
    private String name;
    private List<BiSBiSNode> nodes;
    private final UUID instanceId = UUID.randomUUID();
    
    private Configuration conf = Configuration.getInstance();
    
    
    public Virtualizer(String id, String name, int s) {
        this.size = s;
        this.id = id;
        this.name = name;
        nodes = new ArrayList<>();
        this.addBiSBiS();
    }

    
    @XmlElement(name="id", type=String.class)
    public String getId() {
        return id;
    }

    public void setId(String serviceID) {
        this.id = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstanceId() {
        return this.instanceId.toString();
    }
    
    @XmlElement(name="node", type=BiSBiSNode.class)
    @XmlElementWrapper(name="nodes")
    public List<BiSBiSNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<BiSBiSNode> nodes) {
        this.nodes = nodes;
    }

    
    public void instantiateMultiLinear(int branches) {
        this.nodes.get(0).createMultiLinearTopology(branches);
    }
    
    
    private void addBiSBiS() {
        this.nodes.add(new BiSBiSNode(conf.getNodeId(), conf.getNodeName(), conf.getNodeType(), size));
    }
    
    
    public void setDeleteOperation() {
        nodes.get(0).setNFInstancesDelete();
        nodes.get(0).setFlowEntriesDelete();
    }
    
    public String toXML() {
        try {
            StringWriter sw = new StringWriter();
            
            JAXBContext jaxbContext = JAXBContext.newInstance(Virtualizer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            JAXBElement<Virtualizer> je2 = new JAXBElement<>(new QName("virtualizer"), Virtualizer.class, this);
            jaxbMarshaller.marshal(je2, sw);
            return sw.toString();
	    } catch (JAXBException e) {
		System.out.println("Error while marshalling to XML: " + e.getMessage());
                return null;
	      }
    }
    
    
    public void submitRequest() throws IOException, JSONException {
        System.out.println("Submitting service request to: " + conf.getUrl());
        Resty rest = new Resty(Option.timeout(5000));
        rest.json(conf.getUrl() + "/edit-config?blocking&message-id=" + this.getInstanceId(), content(this.toXML()));
        System.out.println("Done");
    }  
}