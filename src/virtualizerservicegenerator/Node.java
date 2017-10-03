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
public class Node {
    protected String id;
    protected String name;
    protected String type;
    
    
    public Node() {}
    
    public Node(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
