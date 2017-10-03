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
public class Port {
    private int id;
    private String port_type;
    
    public Port() {}
    
    public Port(int id) {
        this(id, "port-abstract");
    }
    
    public Port(int id, String type) {
        this.id = id;
        this.port_type = type;
    }

    public int getId() {
        return id;
    }

    public String getPort_type() {
        return port_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPort_type(String type) {
        this.port_type = type;
    }
    
    
    
}
