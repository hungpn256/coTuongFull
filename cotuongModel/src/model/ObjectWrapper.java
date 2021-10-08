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
public class ObjectWrapper  implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    public static final int SERVER_INFORM_CLIENT_NUMBER = 0;
    
    public static final int LOGIN_PATICIPANT = 1;
    public static final int REPLY_LOGIN_PATICIPANT = 2;
    
    public static final int REGISTER_PATICIPANT = 3;
    public static final int REPLY_REGISTER_PATICIPANT = 4;
    
    public static final int HOME = 5;
    public static final int REPLY_HOME = 6;
    
    public static final int SEARCH_ADD_FRIEND = 7;
    public static final int REPLY_SEARCH_ADD_FRIEND = 8;
    
    public static final int REQUEST_ADD_FRIEND = 9;
    public static final int REPLY_REQUEST_ADD_FRIEND = 10;
    
    public static final int REMOVE_FRIEND = 11;
    public static final int REPLY_REMOVE_FRIEND = 12;
    
    public static final int GET_PENDING_FRIEND = 13;
    public static final int REPLY_GET_PENDING_FRIEND = 14;  
    
    public static final int ACCEPT_FRIEND = 15;
    public static final int REPLY_ACCEPT_FRIEND = 16;
    
    public static final int DENY_FRIEND = 17;
    public static final int REPLY_DENY_FRIEND = 18;
    
    public static final int GET_ALL_FRIEND = 19;
    public static final int REPLY_GET_ALL_FRIEND = 20;
    
    public static final int LOG_OUT = 21;
    public static final int REPLY_LOG_OUT = 22;
    
    public static final int INFORM_FRIEND_CHANGE = 23;
    public static final int REPLY_INFORM_FRIEND_CHANGE = 24;
    
    public static final int CREATE_ROOM = 25;
    public static final int REPLY_CREATE_ROOM = 26;
    
    public static final int INVITE_TO_ROOM = 27;
    public static final int REPLY_INVITE_TO_ROOM = 28;
    
    public static final int ACCEPT_JOIN_ROOM = 29;
    public static final int REPLY_ACCEPT_JOIN_ROOM = 30;
    
    public static final int DENY_JOIN_ROOM = 31;
    public static final int REPLY_DENY_JOIN_ROOM = 32;
    
    public static final int JOIN_ROOM = 33;
    public static final int REPLY_JOIN_ROOM= 34;
    
    public static final int LEAVE_ROOM = 35;
    public static final int REPLY_LEAVE_ROOM= 36;
    
    public static final int GET_RANK = 37;
    public static final int REPLY_GET_RANK= 38;
    
     
    private int performative;
    private Object data;
    public ObjectWrapper() {
        super();
    }
    
    public ObjectWrapper(int performative) {
        super();
        this.performative = performative;
    }
    
    public ObjectWrapper(int performative, Object data) {
        super();
        this.performative = performative;
        this.data = data;
    }
    public int getPerformative() {
        return performative;
    }
    public void setPerformative(int performative) {
        this.performative = performative;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }   
}