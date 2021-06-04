/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import message.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author busra
 */
class ServerThread extends Thread {

    public void run() {
        //server kapanana kadar dinle
        while (!Server.serverSocket.isClosed()) {
            try {
                Server.Display("Client Bekleniyor...");
                // clienti bekleyen satır
                //bir client gelene kadar bekler
                Socket clientSocket = Server.serverSocket.accept();
                //client gelirse bu satıra geçer
                Server.Display("Client Geldi...");
                //gelen client soketinden bir sclient nesnesi oluştur
                //bir adet id de kendimiz verdik
                SClient nclient = new SClient(clientSocket, Server.IdClient);
                Server.IdClient++;
                //clienti listeye ekle.
                Server.Clients.add(nclient);
                //client mesaj dinlemesini başlat
                nclient.listenThread.start();

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Server {

    //server soketi eklemeliyiz
    public static ServerSocket serverSocket;
    public static int IdClient = 0;
    // Serverın dileyeceği port
    public static int port = 0;
    //Serverı sürekli dinlemede tutacak thread nesnesi
    public static ServerThread runThread;

    public static Semaphore pairTwo = new Semaphore(1, true);

    public static ArrayList<SClient> Clients = new ArrayList<>();

    // başlaşmak için sadece port numarası veriyoruz
    public static void Start(int openport) {
        try {
            Server.port = openport;
            Server.serverSocket = new ServerSocket(Server.port);

            Server.runThread = new ServerThread();
            Server.runThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String msg) {
        System.out.println(msg);
    }

    // serverdan clientlara mesaj gönderme
    //clienti alıyor ve mesaj yolluyor
    public static void Send(SClient cl, Message msg) {
        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void CreateGrup(Message msg) {

        String[] parts = msg.content.toString().split("_");
        String grup_adi = parts[0];
        String geri_kalan = parts[1];
        System.out.println("Server ici grup_adi  :   " + grup_adi);

        String[] parts2 = geri_kalan.split("-");

        msg.content = grup_adi + "_" + geri_kalan;

        for (SClient c : Clients) {
            for (String p : parts2) {
                if (c.name.equals(p)) {
                    Server.Send(c, msg);
                }
            }
        }
    }

    public static void tumUyelereGonder(Message msg) {

        String[] parts = msg.content.toString().split("_");
        String kisiler = parts[0];
        String mesaj = parts[1];

        msg.content = mesaj;

        String[] parts2 = kisiler.split("-");

        for (SClient c : Clients) {
            for (String p : parts2) {
                if (c.name.equals(p)) {
                    Server.Send(c, msg);
                }
            }
        }
    }

    public static void dosyaGonder(Message msg) throws InterruptedException {
        System.out.println(msg.content.toString());
        Thread.sleep(100);
        String[] parts = msg.content.toString().split("&");
        String kisiler = parts[0];
        String fileName_Content = parts[1];
        

        msg.content = fileName_Content;

        String[] parts2 = kisiler.split("-");

        for (SClient c : Clients) {
            for (String p : parts2) {
                if (c.name.equals(p)) {
                    Server.Send(c, msg);
                }
            }
        }
    }

    public static SClient ClientBul(String s, String s2) {
        Message msg2 = new Message(Message.Message_Type.icerik);
        msg2.content = s2;

        for (SClient c : Clients) {
            if (c.name.equals(s)) {
                //Server.Send(c, msg2);
                return c;
            }
        }
        return null;
    }

    public static void Baglandi(Message msg) throws InterruptedException {

        Thread.sleep(500);
        if (Server.Clients.size() > 0) {

            DefaultListModel userList = new DefaultListModel();

            for (SClient client : Clients) {
                userList.addElement(client.name);

            }

            Message msg2 = new Message(Message.Message_Type.newUser);
            msg2.content = userList;

            for (SClient c : Clients) {
                Server.Send(c, msg2);
            }
        }
    }

    public static void Baglandi2(Message msg) throws InterruptedException {

        Thread.sleep(500);
        if (Server.Clients.size() > 0) {

            DefaultListModel userList = new DefaultListModel();

            for (SClient client : Clients) {
                userList.addElement(client.name);

            }

            Message msg2 = new Message(Message.Message_Type.grupUsers);
            msg2.content = userList;

            for (SClient c : Clients) {
                if (c.name.equals(msg.content.toString())) {
                    Server.Send(c, msg2);
                    break;
                }
            }

        }
    }
}
