/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.*;

import java.util.*;
import java.io.*;
import java.net.Socket;
import model.Friend;
import model.IPAddress;
import model.ObjectWrapper;
import model.Paticipant;
import view.ClientMainFrm;
import view.FriendJrm;
import view.GameUIFrm;
import view.HomeFrm;
import view.LoginFrm;
import view.RegisterFrm;

/**
 *
 * @author phamhung
 */
public class ClientCtr {

    private Socket mySocket;
    private ClientMainFrm view;
    private ArrayList<ObjectWrapper> myFunction;
    private ClientListening myListening;
    private Paticipant paticipantLogin;
    private IPAddress serverAddress = new IPAddress("localhost", 8888);

    public ClientCtr(ClientMainFrm client) {
        super();
        this.view = view;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public ClientCtr(ClientMainFrm view, IPAddress serverAddr) {
        super();
        this.view = view;
        this.serverAddress = serverAddr;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public void run() {
        try {
            while (true) {
                ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                System.out.println("client reci");
                Object obj = ois.readObject();
                if (obj instanceof ObjectWrapper) {
                    ObjectWrapper data = (ObjectWrapper) obj;
                    if (data.getPerformative() == ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER) {
                        //show change
                    } else {
                        for (ObjectWrapper fto : myFunction) {
                            if (fto.getPerformative() == data.getPerformative()) {
                                switch (data.getPerformative()) {
                                    default:
                                        return;
                                }
                                //view.showMessage("Received an object: " + data.getPerformative());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean openConnection() {
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
//            view.showMessage("Connected to the server at host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean closeConnection() {
        try {
            if(paticipantLogin != null){
                sendData(new ObjectWrapper(ObjectWrapper.LOG_OUT));
            }
            if (myListening != null) {
                myListening.stop();
            }
            if (mySocket != null) {
                mySocket.close();
                System.out.println("disconnect");
            }
            myFunction.clear();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error when disconnecting from the server!");
            return false;
        }
        return true;
    }

    public boolean sendData(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println("send data fail");
            return false;
        }
        return true;
    }

    public Paticipant getPaticipantLogin() {
        return paticipantLogin;
    }

    public void setPaticipantLogin(Paticipant paticipantLogin) {
        this.paticipantLogin = paticipantLogin;
    }

    public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }

    public void removeFunction(Object o) {
        System.out.println(myFunction.size());
        for (int i = 0; i < myFunction.size(); i++) {
            ObjectWrapper x = myFunction.get(i);
            System.out.println(x.getData().getClass() + " " + o.getClass());
            if (o.getClass().toString().contains(x.getData().getClass().toString())) {
                i--;
                myFunction.remove(x);
            }
        }
        System.out.println(myFunction.size());
    }

    class ClientListening extends Thread {

        public ClientListening() {
            super();
        }

        public void run() {
            while (true) {
                try {

                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    System.out.println("client reci");
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) obj;
                        if (data.getPerformative() == ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER) {
//                            view.showMessage("Number of client connecting to the server: " + data);
                            // lam gi do
                            System.out.println("inform client");
                        } else {
                            for (ObjectWrapper fto : myFunction) {
                                if (fto.getPerformative() == data.getPerformative()) {
                                    switch (data.getPerformative()) {
                                        case ObjectWrapper.REPLY_LOGIN_PATICIPANT: {
                                            System.out.println("client login");
                                            LoginFrm loginView = (LoginFrm) fto.getData();
                                            loginView.receivedDataProcessing(data);
                                            System.out.println("client login");
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_LOG_OUT: {
                                            System.out.println("log out");
                                            System.exit(0);
                                            break;
                                        }

                                        case ObjectWrapper.REPLY_REGISTER_PATICIPANT: {
                                            System.out.println("client register");
                                            RegisterFrm registerView = (RegisterFrm) fto.getData();
                                            registerView.receivedDataProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_SEARCH_ADD_FRIEND: {
                                            System.out.println("client search friend recive");
                                            FriendJrm friendView = (FriendJrm) fto.getData();
                                            friendView.receivedSearchFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_REQUEST_ADD_FRIEND: {
                                            System.out.println("client add friend recive");
                                            FriendJrm friendView = (FriendJrm) fto.getData();
                                            friendView.receivedAddFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_GET_PENDING_FRIEND: {
                                            System.out.println("client get pending friend recive");
                                            FriendJrm friendView = (FriendJrm) fto.getData();
                                            friendView.receivedPendingFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_ACCEPT_FRIEND: {
                                            System.out.println("client accept friend recive");
                                            FriendJrm friendView = (FriendJrm) fto.getData();
                                            friendView.receivedAcceptFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_DENY_FRIEND: {
                                            System.out.println("client deny friend recive");
                                            FriendJrm friendView = (FriendJrm) fto.getData();
                                            friendView.receivedDenyFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_GET_ALL_FRIEND: {
                                            System.out.println("client get all friend recive");
                                            if(fto.getData() instanceof HomeFrm){
                                                HomeFrm homeView = (HomeFrm) fto.getData();
                                                homeView.receivedAllFriendProcessing(data);
                                            }
                                            else if(fto.getData() instanceof GameUIFrm){
                                                GameUIFrm gameView = (GameUIFrm) fto.getData();
                                                gameView.receivedAllFriendProcessing(data);
                                            }
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_CREATE_ROOM: {
                                            System.out.println("client create room recive");
                                            HomeFrm homeView = (HomeFrm) fto.getData();
                                            homeView.receivedCreateRoomProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_LEAVE_ROOM: {
                                            System.out.println("client leave room recive");
                                            GameUIFrm gameView = (GameUIFrm) fto.getData();
                                            gameView.receivedLeaveRoomProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_INVITE_TO_ROOM: {
                                            System.out.println("client invite to room recive");
                                            HomeFrm homeView = (HomeFrm) fto.getData();
                                            homeView.receivedInviteToRoomProcessing(data);
                                            break;
                                        }

                                    }
                                }
                            }
                            //view.showMessage("Received an object: " + data.getPerformative());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("co loi ");
                }
            }
        }
    }
}
