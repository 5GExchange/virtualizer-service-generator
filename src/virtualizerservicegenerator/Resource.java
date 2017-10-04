package virtualizerservicegenerator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uceeftu
 */
public class Resource {
    private float cpu;
    private float mem;
    private float storage;

    public Resource() {
        this.cpu = 1.0F;
        this.mem = 1.0F;
        this.storage = 1.0F;
    }

    public Resource(float cpu, float mem, float storage) {
        this.cpu = cpu;
        this.mem = mem;
        this.storage = storage;
    }

    public void setCpu(float cpu) {
        this.cpu = cpu;
    }

    public void setMem(float mem) {
        this.mem = mem;
    }

    public void setStorage(float storage) {
        this.storage = storage;
    }
    
    
    public float getCpu() {
        return cpu;
    }

    public float getMem() {
        return mem;
    }

    public float getStorage() {
        return storage;
    }
    
    
    
    
    
    
    
}
