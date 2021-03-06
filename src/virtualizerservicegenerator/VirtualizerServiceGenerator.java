/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtualizerservicegenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Properties;


public class VirtualizerServiceGenerator {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String server;
        int port;
        int chainSize = 4;
        int chains = 1;
        int totalVnfs;
        
        String nodeType = "vlsp:router";
        String SAP1id = null;
        String SAP2id = null;
        boolean submit = false;
        
        Properties prop = new Properties();
	InputStream input = null;
        String propertiesFile = null;
        Configuration conf = Configuration.getInstance();
        
        String xmlsPath = "xmls";
        FileWriter fw;
        PrintWriter pw;
        
        switch (args.length) {
            // using settings from properties file
            case 1:
                propertiesFile = args[0];
                try {
                    input = new FileInputStream(propertiesFile);
                    prop.load(input);
                    
                    server = prop.getProperty("orchestrator.address");
                    port = Integer.parseInt(prop.getProperty("orchestrator.port"));
                    chains = Integer.parseInt(prop.getProperty("chains.number"));
                    chainSize = Integer.parseInt(prop.getProperty("chain.size"));
                    nodeType = prop.getProperty("nodes.type");
                    SAP1id = prop.getProperty("sap.in");
                    SAP2id = prop.getProperty("sap.out");
                    submit = Boolean.valueOf(prop.getProperty("submit"));
                    
                    if (SAP1id != null && SAP2id != null) {
                        if (submit) {
                            conf.initialiseConfiguration(server, port);
                            conf.setPort1ID(SAP1id);
                            conf.setPort2ID(SAP2id);
                        }
                        
                        else
                            conf.initialiseConfiguration(SAP1id, SAP2id);
                    }
                    else {
                        System.err.println("Error: no SAPs have been found");
                        System.exit(3);
                    }
                    
                }
                catch (FileNotFoundException fex) {
                    System.err.println("Error while accessing the property file: " + fex.getMessage());
                    System.exit(2);   
                } 
                catch (IOException ioex) {
                    System.err.println("Error while getting infrastructure information: " + ioex.getMessage());
                    System.exit(1);
                }
                break;
                
            case 4:
                try {
                    // generating the request without performing the submission
                    chains = Integer.valueOf(args[0]);
                    chainSize = Integer.valueOf(args[1]);
                    SAP1id = args[2];
                    SAP2id = args[3];
                    submit = false;
                    conf.initialiseConfiguration(SAP1id, SAP2id);
                } catch (Exception e) {
                    // generating the request and performing the submission
                    server = args[0];
                    port = Integer.valueOf(args[1]);
                    chains = Integer.valueOf(args[2]);
                    chainSize = Integer.valueOf(args[3]);
                    submit = true;
                    try {
                        conf.initialiseConfiguration(server, port);
                    } catch (Exception ex) {
                        System.err.println("Error while getting infrastructure information: " + ex.getMessage());
                        System.exit(1);
                      }
                  }
                break;
                
            default:
                SAP1id = "SAP0";
                SAP2id = "SAP1";
                conf.initialiseConfiguration(SAP1id, SAP2id);
                
                System.err.println(  "Wrong number of parameters - please use either:"
                                   + "\n ROaddress ROport Nnodes Nchains"
                                   + "\n Nnodes Nchains sap_in sap_out");
                
                System.out.println("Using default values: " + chains + "x" + chainSize + "and" + SAP1id + "-" + SAP2id);
        }
        
        try {
            totalVnfs = chains * chainSize;
            Instant t = Instant.now();
            conf.setNodeTypeToDeploy(nodeType);
            Virtualizer s1 = new Virtualizer(conf.getId(), conf.getName(), totalVnfs);
            s1.instantiateMultiLinear(chains);
            String request = s1.toXML();
            
            fw = new FileWriter(xmlsPath + "/edit_config_create_" + chains + "-" + chainSize + "_" + t.toEpochMilli() + ".xml");
            pw = new PrintWriter(fw);
            pw.print(request);
            pw.close();
            
            if (submit) {
                System.out.println("create request");
                s1.submitRequest();
                System.in.read(); // FIXME
            }
            
            s1.setDeleteOperation();
            request = s1.toXML();
            fw = new FileWriter(xmlsPath + "/edit_config_delete_" + chains + "-" + chainSize + "_" + t.toEpochMilli() + ".xml");
            pw = new PrintWriter(fw);
            pw.print(request);
            pw.close();
            
            
            if (submit) {
                System.out.println("delete request");
                s1.submitRequest();
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}