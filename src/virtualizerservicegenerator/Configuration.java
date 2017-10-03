/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import us.monoid.web.Resty;
import us.monoid.web.XMLResource;


/**
 *
 * @author uceeftu
 */
public class Configuration {
    private static Configuration CONFIGURATION;
    private Resty rest;
    private String url;
    private XMLResource xml;
    
    private String id;
    private String name;
    
    private String nodeId;
    private String nodeName;
    private String nodeType;
    
    private String port1ID;
    private String port2ID;
    
    private String nodeToDeployType;
    
    private Configuration() {}
    
    public void initialiseConfiguration(String server, int port) throws Exception {
        rest = new Resty();
        url = "http://" + server + ":" + port + "/escape";
        xml = rest.xml(url + "/get-config?blocking");
        
        // xpath queries normalised on 'virtualizer' element
        id = getXMLNodeValue("id");
        name = getXMLNodeValue("name");
        
        nodeId = getXMLNodeValue("nodes/node/id");
        nodeName = getXMLNodeValue("nodes/node/name");
        nodeType = getXMLNodeValue("nodes/node/type");
        
        port1ID = getXMLNodeValues("nodes/node/ports/port/id").get(0);
        port2ID = getXMLNodeValues("nodes/node/ports/port/id").get(1);
    }
    
      
    private List<String> getXMLNodeValues(String node) throws Exception {
        NodeList get = xml.get("/virtualizer/" + node);
        List<String> elements = new ArrayList<>();
        
        for (int i=0; i<get.getLength(); i++) {
             Node item = get.item(i);
             elements.add(item.getFirstChild().getNodeValue());
        }
        
        return elements;  
    }
    
    
    private String getXMLNodeValue(String node) throws Exception {
        NodeList get = xml.get("/virtualizer/" + node);
        Node item = get.item(0);
        return item.getFirstChild().getNodeValue();  
    }
    
    
    public static Configuration getInstance() {
        if (CONFIGURATION == null) {
            CONFIGURATION = new Configuration();
        }
        
        return CONFIGURATION;
    }
    
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getPort1ID() {
        return port1ID;
    }

    public String getPort2ID() {
        return port2ID;
    }

    public String getUrl() {
        return url;
    }

    public String getNodeToDeployType() {
        return nodeToDeployType;
    }
    
    public void setNodeTypeToDeploy(String type) {
        nodeToDeployType = type;
    }

    public void setPort1ID(String port1ID) {
        this.port1ID = port1ID;
    }

    public void setPort2ID(String port2ID) {
        this.port2ID = port2ID;
    }
}
