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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static server.Server.Clients;

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
    //karsilikli mesajlasma icin eslestirme ve mesaj alisverisi threadi
    PairingThread2 pairThread;

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
        Message msg;

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
                            //kisinin isim bilgisini aldıktan sonra baglanti kurar
                            TheClient.name = received.content.toString();
                            Thread.sleep(500);
                            Server.Send(TheClient, received);
                            Thread.sleep(500);
                            Server.BaglantiKur(received);
                            break;

                        case kisiBul:
                            //karsilikli mesajlasilirken karsiki kisiyle konusmak icin thread baslatir
                            TheClient.pairThread = new SClient.PairingThread2(received);
                            pairThread.start();
                            break;

                        case grupKisiBul:
                            //Secili kisilerle grup olusturur
                            Server.CreateGrup(received);
                            break;

                        case icerik2:
                            //karsi clienta mesaji iletir
                            Server.KarsiyaGonder(received);
                            break;

                        case baglantiKopar:
                            //Clientlardan biri konusmadan ciktiktan sonra karsiki clienta bunun bilgisi gider 
                            //konusma sonlanir
                            SClient c = Server.ClientBul(received.content.toString());
                            Server.Send(c, received);
                            Thread.sleep(100);
                            break;

                        case baglantiKopar2:
                            //clientlardan biri konusmadan cikinca o konusmayi takip eden threadi durdurur
                            Thread.sleep(100);
                            TheClient.pairThread.stop();
                            break;

                        case grupUsers:
                            //grup olusturmak icin online kullanicilar bilgisini tasir
                            Server.BaglantiKur2(received);
                            Thread.sleep(100);
                            break;

                        case icerikGrup:
                            //gruptaki tum uyelere mesaji iletir
                            Server.tumUyelereGonder(received);
                            break;

                        case dosya1:
                            //gruptaki kullanicilara dosyayi gonderir
                            Server.dosyaGonder(received);
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

    class PairingThread2 extends Thread {

        //karsilikli mesajlasmayi saglamak icin baslatilan thread
        //her mesajlasma icin bir thread
        Message msg;

        PairingThread2(Message msg) {
            this.msg = msg;
        }

        public void run() {
            String[] parts = msg.content.toString().split("-");
            String kisi_adi = parts[0];
            String geri_kalan = parts[1];
            msg.content = geri_kalan;

            //secili kisiye yeni jframe olusturmak icin
            for (SClient c : Clients) {
                if (c.name.equals(kisi_adi)) {
                    Server.Send(c, msg);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
