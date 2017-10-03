/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class VirtualizerServiceGenerator {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String server = "localhost";
        int port = 8888;
        int nodes = 50;
        int branches = 4
                ;
        String nodeType = "vlsp:router";
        String SAP1id = null;
        String SAP2id = null;
        
        Properties prop = new Properties();
	InputStream input = null;
        String propertiesFile = null;

        if (args.length == 4) {
            System.out.println("Overriding default parameters");
            server = args[0];
            port = Integer.valueOf(args[1]);
            nodes = Integer.valueOf(args[2]);
            branches = Integer.valueOf(args[3]);
        } else if (args.length == 1) {
            propertiesFile = args[0];
            
            try {
                input = new FileInputStream(propertiesFile);
                prop.load(input);
                
                server = prop.getProperty("orchestrator.address");
                port = Integer.parseInt(prop.getProperty("orchestrator.port"));
                nodes = Integer.parseInt(prop.getProperty("nodes.total"));
                branches = Integer.parseInt(prop.getProperty("nodes.branches"));
                nodeType = prop.getProperty("nodes.type");
                SAP1id = prop.getProperty("sap.in");
                SAP2id = prop.getProperty("sap.out");
            
            } catch (Exception ex) {
                System.err.println("Error while accessing/parsing the property file" + ex.getMessage());
              }
          }
        
        try {
            Configuration conf = Configuration.getInstance();
            conf.initialiseConfiguration(server, port);
            conf.setNodeTypeToDeploy(nodeType);
            
            if (SAP1id != null && SAP2id != null) {
                conf.setPort1ID(SAP1id);
                conf.setPort2ID(SAP2id);
            } //else we use first two ports from get-config
            
            
            
            Virtualizer s1 = new Virtualizer(conf.getId(), conf.getName(), nodes);
            s1.instantiateMultiLinear(branches);
            System.out.println(s1.toXML());
            s1.submitRequest();
            
            System.in.read();
            
            s1.setDeleteOperation();
            System.out.println(s1.toXML());
            s1.submitRequest();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
