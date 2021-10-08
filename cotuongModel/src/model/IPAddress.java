/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author phamhung
 */
public class IPAddress implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private String host;
    private int port;
     
    public IPAddress() {
        super();
    }
 
    public IPAddress(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }
 
    public String getHost() {
        return host;
    }
 
    public void setHost(String host) {
        this.host = host;
    }
 
    public int getPort() {
        return port;
    }
 
    public void setPort(int port) {
        this.port = port;
    }
}
