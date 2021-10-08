/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.FriendDAO;
import dao.PaticipantDAO;
import dao.RoomDAO;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import model.Friend;
import model.FriendInvitation;

import model.IPAddress;
import model.ObjectWrapper;
import model.Paticipant;
import model.Room;
import model.RoomInvitation;
import view.ServerMainFrm;

/**
 *
 * @author phamhung
 */
public class ServerCtr {

    private ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private IPAddress myAddress = new IPAddress("localhost", 8888);  //default server host and port

    public ServerCtr(ServerMainFrm view) {
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();
    }

    public ServerCtr(ServerMainFrm view, int serverPort) {
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();
    }

    private void openServer() {
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            //System.out.println("server started!");
            view.showMessage("TCP server is running at the port " + myAddress.getPort() + "...");
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    public void stopServer() {
        try {
            for (ServerProcessing sp : myProcess) {
                sp.stop();
            }
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publicClientNumber() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER, myProcess.size());
        for (ServerProcessing sp : myProcess) {
            sp.sendData(data);
        }
    }

    /**
     * The class to listen the connections from client, avoiding the blocking of
     * accept connection
     *
     */
    class ServerListening extends Thread {

        public ServerListening() {
            super();
        }

        public void run() {
            view.showMessage("server is listening... ");
            try {
                while (true) {
                    Socket clientSocket = myServer.accept();
                    ServerProcessing sp = new ServerProcessing(clientSocket);
                    sp.start();
                    myProcess.add(sp);
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    publicClientNumber();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The class to treat the requirement from client
     *
     */
    class ServerProcessing extends Thread {

        private Socket mySocket;
        private Paticipant paticipant;
        //private ObjectInputStream ois;
        //private ObjectOutputStream oos;

        public Socket getMySocket() {
            return mySocket;
        }

        public void setMySocket(Socket mySocket) {
            this.mySocket = mySocket;
        }

        public Paticipant getPaticipant() {
            return paticipant;
        }

        public void setPaticipant(Paticipant paticipant) {
            this.paticipant = paticipant;
        }

        public ServerProcessing(Socket s) {
            super();
            mySocket = s;
        }

        public void sendData(Object obj) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                    Object o = ois.readObject();
                    if (o instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) o;
                        switch (data.getPerformative()) {
                            case ObjectWrapper.LOGIN_PATICIPANT: {
                                Paticipant paticipant = (Paticipant) data.getData();
                                PaticipantDAO pd = new PaticipantDAO();
                                paticipant = pd.login(paticipant);
                                if (paticipant != null) {
                                    this.paticipant = paticipant;
                                    System.out.println(paticipant.getId());
                                    List<Friend> friends = paticipant.getListFriend();
                                    System.out.println(paticipant.getListFriend().size() + "size friend");
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_PATICIPANT, paticipant));
                                    for (Friend f : friends) {
                                        for (ServerProcessing x : myProcess) {
                                            if (x.getPaticipant() != null && f.getFriend().getId() == x.getPaticipant().getId()) {
                                                ObjectOutputStream os = new ObjectOutputStream(x.getMySocket().getOutputStream());
                                                FriendDAO fd = new FriendDAO();

                                                try {
                                                    List<Friend> listFriend = fd.getAllFriend(x.getPaticipant());
                                                    os.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, listFriend));

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    os.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, "false"));
                                                }
                                                System.out.println("send data all friend");
                                            }
                                        }
                                    }
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_PATICIPANT, "false"));
                                }
                                System.out.println("send data login");
                                break;
                            }
                            case ObjectWrapper.REGISTER_PATICIPANT: {
                                Paticipant paticipant = (Paticipant) data.getData();
                                PaticipantDAO pd = new PaticipantDAO();
                                try {
                                    pd.register(paticipant);
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_PATICIPANT, "ok"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_PATICIPANT, "false"));
                                }
                                System.out.println(paticipant.getId());
                                System.out.println(paticipant.getListFriend().size() + "size friend");
                                if (paticipant != null) {

                                } else {
                                    System.out.println("send data register");
                                }
                                break;
                            }
                            case ObjectWrapper.LOG_OUT: {
                                if (paticipant != null) {
                                    PaticipantDAO pd = new PaticipantDAO();
                                    pd.logout(this.paticipant);
                                    List<Friend> friends = paticipant.getListFriend();
                                    System.out.println(paticipant.getListFriend().size() + "size friend");
                                    for (Friend f : friends) {
                                        for (ServerProcessing x : myProcess) {
                                            if (x.getPaticipant() != null && f.getFriend().getId() == x.getPaticipant().getId()) {
                                                ObjectOutputStream os = new ObjectOutputStream(x.getMySocket().getOutputStream());
                                                FriendDAO fd = new FriendDAO();
                                                try {
                                                    List<Friend> listFriend = fd.getAllFriend(x.getPaticipant());
                                                    os.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, listFriend));

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    os.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, "false"));
                                                }
                                                System.out.println("send data all friend");
                                            }
                                        }
                                    }
                                    this.paticipant = null;
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOG_OUT, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOG_OUT, "false"));
                                }
                                System.out.println("send data logout");
                                break;
                            }
                            case ObjectWrapper.SEARCH_ADD_FRIEND: {
                                String key = (String) data.getData();
                                FriendDAO fd = new FriendDAO();

                                try {
                                    if (paticipant != null) {
                                        List<Paticipant> listPaticipant = fd.searchAddFriend(this.paticipant, key);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_ADD_FRIEND, listPaticipant));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_PATICIPANT, "false"));
                                }
                                System.out.println("send data search friend");
                                break;
                            }
                            case ObjectWrapper.REQUEST_ADD_FRIEND: {
                                Paticipant p = (Paticipant) data.getData();
                                FriendDAO fd = new FriendDAO();

                                try {
                                    System.out.println(this.paticipant.getId() + " " + p.getId());
                                    if (paticipant != null) {
                                        fd.requestAddFriend(this.paticipant, p);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_ADD_FRIEND, "ok"));
                                        System.out.println("add fr oke");
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_ADD_FRIEND, "false"));
                                }
                                System.out.println("send data add friend");
                                break;
                            }
                            case ObjectWrapper.GET_PENDING_FRIEND: {
                                FriendDAO fd = new FriendDAO();

                                try {
                                    if (paticipant != null) {
                                        List<FriendInvitation> listFriendInvitation = fd.getAllPendingFriend(this.paticipant);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_PENDING_FRIEND, listFriendInvitation));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_PENDING_FRIEND, "false"));
                                }
                                System.out.println("send data pending friend");
                                break;
                            }
                            case ObjectWrapper.ACCEPT_FRIEND: {
                                FriendInvitation fi = (FriendInvitation) data.getData();
                                System.out.println(fi.getSender().getId());
                                System.out.println(fi.getAccepter().getId());

                                FriendDAO fd = new FriendDAO();

                                try {
                                    if (paticipant != null) {
                                        fd.acceptFriend(fi);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_FRIEND, "ok"));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_FRIEND, "false"));
                                }
                                System.out.println("send data accept friend");
                                break;
                            }
                            case ObjectWrapper.DENY_FRIEND: {
                                FriendInvitation fi = (FriendInvitation) data.getData();
                                FriendDAO fd = new FriendDAO();

                                try {
                                    if (paticipant != null) {
                                        fd.denyFriend(fi);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DENY_FRIEND, "ok"));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DENY_FRIEND, "false"));
                                }
                                System.out.println("send data deny friend");
                                break;
                            }
                            case ObjectWrapper.REMOVE_FRIEND: {
                                Paticipant p = (Paticipant) data.getData();
                                FriendDAO fd = new FriendDAO();

                                try {
                                    if (paticipant != null) {
                                        fd.removeFriend(this.paticipant, p);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REMOVE_FRIEND, "ok"));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REMOVE_FRIEND, "false"));
                                }
                                System.out.println("send data remove friend");
                                break;
                            }
                            case ObjectWrapper.GET_ALL_FRIEND: {
                                FriendDAO fd = new FriendDAO();

                                try {
                                    if (paticipant != null) {
                                        List<Friend> listFriend = fd.getAllFriend(this.paticipant);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, listFriend));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, "false"));
                                }
                                System.out.println("send data all friend");
                                break;
                            }
                            case ObjectWrapper.CREATE_ROOM: {
                                Room r = (Room) data.getData();
                                RoomDAO roomDAO = new RoomDAO();

                                try {
                                    if (paticipant != null) {
                                        roomDAO.createRoom(r);
                                        roomDAO.createParticipantRoom(r.getPaticipantRoom().get(0));
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_ROOM, r));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_ROOM, "false"));
                                }
                                System.out.println("send data create room");
                                break;
                            }
                            case ObjectWrapper.LEAVE_ROOM: {
                                Room r = (Room) data.getData();
                                RoomDAO roomDAO = new RoomDAO();

                                try {
                                    if (paticipant != null) {
                                        roomDAO.leaveRoom(r);
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_ROOM, "ok"));
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_ROOM, "false"));
                                }
                                System.out.println("send data REPLY_LEAVE_ROOM");
                                break;
                            }
                            case ObjectWrapper.INVITE_TO_ROOM: {
                                RoomInvitation ri = (RoomInvitation) data.getData();

                                try {
                                    if (paticipant != null) {
                                        for (ServerProcessing x : myProcess) {
                                            if (x.getPaticipant() != null && ri.getAcceptor().getId() == x.getPaticipant().getId()) {
                                                ObjectOutputStream os = new ObjectOutputStream(x.getMySocket().getOutputStream());
                                                os.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_INVITE_TO_ROOM, ri));
                                                System.out.println("send invite to room");
                                            }
                                        }
                                    } else {
                                        System.out.println("paticipant null");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_INVITE_TO_ROOM, "false"));
                                }
                                System.out.println("send invite to room");
                                break;
                            }

                        }

                    }
                    //ois.reset();
                    //oos.reset();
                }
            } catch (EOFException | SocketException e) {
                e.printStackTrace();
                if (paticipant != null) {
                    PaticipantDAO pd = new PaticipantDAO();
                    pd.logout(this.paticipant);
                    List<Friend> friends = paticipant.getListFriend();
                    System.out.println(paticipant.getListFriend().size() + "size friend");
                    for (Friend f : friends) {
                        for (ServerProcessing x : myProcess) {
                            if (x.getPaticipant() != null && f.getFriend().getId() == x.getPaticipant().getId()) {
                                try {
                                ObjectOutputStream oss = new ObjectOutputStream(x.getMySocket().getOutputStream());
                                FriendDAO fd = new FriendDAO();
                                
                                    List<Friend> listFriend = fd.getAllFriend(x.getPaticipant());
                                    oss.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, listFriend));

                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                                System.out.println("send data all friend");
                            }
                        }
                    }
                    this.paticipant = null;
                }
                System.out.println("send data logout");
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
                publicClientNumber();
                try {
                    mySocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
