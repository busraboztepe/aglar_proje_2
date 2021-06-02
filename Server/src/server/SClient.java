/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author busra
 */
public class SClient {

    int id;
    public String name = "NoName";
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    //clientten gelenleri dinleme threadi
    Listen listenThread;
    //rakip client
    public SClient rival;

    PairingThread pairThread;

    public String geriKalan;

    public boolean threadDurum = false;
    //rakip client
    //eşleşme durumu
    public boolean paired = false;

    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
        this.listenThread = new Listen(this);
        this.pairThread = new PairingThread(this);
    }

    //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client dinleme threadi
    //her clientin ayrı bir dinleme threadi var
    class Listen extends Thread {

        SClient TheClient;

        //thread nesne alması için yapıcı metod
        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //client bağlı olduğu sürece dönsün
            while (TheClient.soket.isConnected()) {
                try {
                    //mesajı bekleyen kod satırı               
                    Message received = (Message) (TheClient.sInput.readObject());
                    //mesaj gelirse bu satıra geçer
                    //mesaj tipine göre işlemlere ayır
                    switch (received.type) {
                        case Name:
                            TheClient.name = received.content.toString();
                            Thread.sleep(500);
                            Server.Send(TheClient, received);
                            Thread.sleep(500);
                            Server.Baglandi(received);
                            break;

                        case kisiBul:
                            String kisi;

                            String[] parts = received.content.toString().split("-");
                            kisi = parts[0];
                            geriKalan = parts[1];
                            if (!TheClient.paired) {
                                Thread.sleep(300);
                                rival = Server.ClientBul(kisi, geriKalan);
                                Thread.sleep(300);
                                TheClient.rival = rival;
                                System.out.println("client : " + TheClient.name);
                                System.out.println("rival: " + TheClient.rival.name);
                                rival.rival = TheClient;
                                TheClient.paired = true;

                                // isim verisini gönderdikten sonra eşleştirme işlemine başla
                                Thread.sleep(1000);
                                System.out.println("theclient naber : " + TheClient.name);
                                Thread.sleep(300);
                                TheClient.pairThread = new SClient.PairingThread(TheClient);
                                pairThread.start();

                            }

                            break;

                        case Text:
                            //gelen metni direkt rakibe gönder
                            //Server.Send(TheClient.rival, received);
                            Server.AllSend(received);
                            break;

                        case icerik2:
                            Server.Send(TheClient.rival, received);
                            break;

                        case baglantiKopar:
                            Server.Send(rival, received);
                            Thread.sleep(100);
                            TheClient.paired = false;
                            TheClient.pairThread.stop();

                            break;

                        case baglantiKopar2:
                            Thread.sleep(100);
                            TheClient.paired = false;
                            TheClient.pairThread.stop();
                            break;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //eşleştirme threadi
    //her clientin ayrı bir eşleştirme threadi var
    class PairingThread extends Thread {

        SClient TheClient;

        PairingThread(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //client bağlı ve eşleşmemiş olduğu durumda dön
            while (TheClient.soket.isConnected() && TheClient.paired == true) {

                if (!TheClient.rival.paired) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    TheClient.rival.paired = true;
                    Message msg2 = new Message(Message.Message_Type.icerik);
                    msg2.content = geriKalan;
                    Server.Send(rival, msg2);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Message msg3 = new Message(Message.Message_Type.durum);
                    msg3.content = true;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Server.Send(rival, msg3);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Server.Send(TheClient, msg3);

                }

                Message msg = new Message(Message.Message_Type.icerik2);
                msg.content = geriKalan;
                Server.Send(TheClient.rival, msg);

                try {
                    //sürekli dönmesin 1 saniyede bir dönsün
                    //threadi uyutuyoruz
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            }

            //lock mekanizmasını serbest bırak
            //bırakılmazsa deadlock olur.
        }

    }

}
