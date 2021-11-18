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
import view.BoardFrm;
import view.ClientMainFrm;
import view.ClubFrm;
import view.CreateClubFrm;
import view.InvitationJrm;
import view.GameUIFrm;
import view.HomeFrm;
import view.JoinRoomByIdFrm;
import view.LoginFrm;
import view.RegisterFrm;
import view.SearchPaticipantFrm;

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
        this.view = client;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public ClientCtr(ClientMainFrm view, IPAddress serverAddr) {
        super();
        this.view = view;
        this.serverAddress = serverAddr;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public void run() {
    }

    public boolean openConnection() {
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
            this.view.showMessage("Host:"+serverAddress.getHost()+" Port:"+serverAddress.getPort());
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
                            System.out.println("inform client");
                        } else {
                            for (int i = 0 ; i < myFunction.size() ;i++ ) {
                                ObjectWrapper fto = myFunction.get(i);
                                if (fto.getPerformative() == data.getPerformative()) {
                                    switch (data.getPerformative()) {
                                        case ObjectWrapper.REPLY_LOGIN_PATICIPANT: {
                                            System.out.println("client login");
                                            LoginFrm loginView = (LoginFrm) fto.getData();
                                            loginView.receivedDataProcessing(data);
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
                                            InvitationJrm friendView = (InvitationJrm) fto.getData();
                                            friendView.receivedSearchFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_REQUEST_ADD_FRIEND: {
                                            System.out.println("client add friend recive");
                                            InvitationJrm friendView = (InvitationJrm) fto.getData();
                                            friendView.receivedAddFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_GET_PENDING_FRIEND: {
                                            System.out.println("client get pending friend recive");
                                            InvitationJrm friendView = (InvitationJrm) fto.getData();
                                            friendView.receivedPendingFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_ACCEPT_FRIEND: {
                                            System.out.println("client accept friend recive");
                                            InvitationJrm friendView = (InvitationJrm) fto.getData();
                                            friendView.receivedAcceptFriendProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_DENY_FRIEND: {
                                            System.out.println("client deny friend recive");
                                            InvitationJrm friendView = (InvitationJrm) fto.getData();
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
                                            if(data.getData() instanceof String){
                                                GameUIFrm gameView = (GameUIFrm) fto.getData();
                                                gameView.receivedInviteRoRoomSuccessProcessing(data);
                                            }
                                            else{
                                                HomeFrm homeView = (HomeFrm) fto.getData();
                                                homeView.receivedInviteToRoomProcessing(data);
                                            }
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_GET_RANK: {
                                            System.out.println("client get rank recive");
                                            HomeFrm homeView = (HomeFrm) fto.getData();
                                            homeView.receivedGetRankProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_CREATE_CLUB: {
                                            System.out.println("client get rank recive");
                                            CreateClubFrm createClubView = (CreateClubFrm) fto.getData();
                                            createClubView.receivedCreateClubProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_SEARCH_PATICIPANT: {
                                            System.out.println("client search paticipant recive");
                                            SearchPaticipantFrm searchPaticipantView = (SearchPaticipantFrm) fto.getData();
                                            searchPaticipantView.receivedSearchPaticipantProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_INVITE_TO_CLUB: {
                                            System.out.println("client invite club paticipant recive");
                                            SearchPaticipantFrm searchPaticipantView = (SearchPaticipantFrm) fto.getData();
                                            searchPaticipantView.receivedInviteToClubProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_GET_PENDING_INVITE_TO_CLUB: {
                                            System.out.println("client invite club paticipant recive");
                                            InvitationJrm invitationView = (InvitationJrm) fto.getData();
                                            invitationView.receivedPendingInvitationClubProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_ACCEPT_INVITE_TO_CLUB: {
                                            System.out.println("client invite club paticipant recive");
                                            InvitationJrm invitationView = (InvitationJrm) fto.getData();
                                            invitationView.receivedAcceptInvitationClubProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_DENY_INVITE_TO_CLUB: {
                                            System.out.println("client invite club paticipant recive");
                                            InvitationJrm invitationView = (InvitationJrm) fto.getData();
                                            invitationView.receivedDenyInvitationClubProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_GET_PATICIPANT_ROOM: {
                                            System.out.println("client REPLY_GET_PATICIPANT_ROOM recive");
                                            GameUIFrm gameView = (GameUIFrm) fto.getData();
                                            gameView.receivedGetPaticipantRoomProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_JOIN_ROOM: {
                                            System.out.println("client REPLY_JOIN_ROOM recive");
                                            if(fto.getData() instanceof HomeFrm){
                                                HomeFrm homeView = (HomeFrm) fto.getData();
                                                homeView.receivedJoinToRoomProcessing(data);
                                            }
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_START_GAME: {
                                            System.out.println("client REPLY_START_GAME recive");
                                            BoardFrm boardView = (BoardFrm) fto.getData();
                                            boardView.receivedStartGameProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_MOVE: {
                                            System.out.println("client REPLY_MOVE recive");
                                            BoardFrm boardView = (BoardFrm) fto.getData();
                                            boardView.receivedMovementProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_QUIT_GAME: {
                                            System.out.println("client REPLY_QUIT_GAME recive");
                                            BoardFrm boardView = (BoardFrm) fto.getData();
                                            boardView.receivedChallengerQuitGameProcessing(data);
                                            break;
                                        }
                                        case ObjectWrapper.REPLY_GET_ALL_PATICIPANT_CLUB: {
                                            System.out.println("client REPLY_GET_ALL_PATICIPANT_CLUB recive");
                                            ClubFrm view = (ClubFrm) fto.getData();
                                            view.receivedAllPaticipantClubProcessing(data);
                                            break;
                                        }

                                    }
                                }
                            }
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
