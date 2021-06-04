/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author busra
 */
import static client.Client.sInput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
import message.arayuz;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import message.anasayfa;
import message.grupOlustur;
import message.sohbet;
import static message.sohbet.ThisSohbet;
import static message.sohbet.durum;

/**
 *
 * @author busra
 */
class Listen extends Thread {

    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            try {
                //mesaj gelmesini blocking olarak dinleyen komut
                Message received = (Message) (sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre yapılacak işlemi ayır.
                switch (received.type) {
                    case newUser:
                        //System.out.println(received.content.toString());
                        //String s = received.content.toString();
                        //anasayfa.ThisAnasayfa.onlineOlanlar(s);
                        //anasayfa.ThisAnasayfa.users.addElement(received.content.toString());
                        //anasayfa.ThisAnasayfa.online_users.setModel(anasayfa.ThisAnasayfa.users);
                        anasayfa.ThisAnasayfa.getUser((DefaultListModel) received.content);
                        Thread.sleep(300);

                        break;

                    //rakip ile bağlantı bilgileri gelir 
                    case RivalConnected:
                        String name = received.content.toString();

                        Thread.sleep(10000);

                        Thread.sleep(5000);
                        break;

                    //rakibin mesajları gelir   
                    case Text:

                        break;

                    case Name:
                        Client.client_name = received.content.toString();
                        break;

                    case icerik:
                        String mesajlasilan;
                        String mesajakisi;
                        System.out.println("baaaaaaa : " + received.content.toString());
                        String[] parts = received.content.toString().split("_");
                        mesajlasilan = parts[0];
                        mesajakisi = parts[1];
                        Thread.sleep(100);

                        System.out.println("mesajlasilan : " + mesajlasilan);
                        System.out.println("mesajakisi : " + mesajakisi);
                        message.anasayfa.ThisAnasayfa.setVisible(false);
                        new message.sohbet(mesajlasilan).setVisible(true);
                        message.sohbet.ThisSohbet.mesaj_akisi.setText(mesajakisi);
                        break;

                    case icerik2:
                        String mesajlasilan2;
                        String mesajakisi2;
                        System.out.println("baaaaaaa : " + received.content.toString());
                        String[] parts2 = received.content.toString().split("_");
                        mesajakisi2 = parts2[1];
                        Thread.sleep(100);

                        System.out.println("mesajakisi : " + mesajakisi2);
                        message.sohbet.ThisSohbet.setVisible(true);
                        message.sohbet.ThisSohbet.mesaj_akisi.setText(mesajakisi2);
                        break;

                    case durum:
                        Thread.sleep(500);
                        sohbet.durum = (boolean) received.content;
                        break;

                    case baglantiKopar:
                        ThisSohbet.setVisible(false);
                        anasayfa.ThisAnasayfa.setVisible(true);
                        durum = false;
                        Thread.sleep(100);
                        Message msg2 = new Message(Message.Message_Type.baglantiKopar2);
                        msg2.content = "baglanti kopar";
                        client.Client.Send(msg2);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(sohbet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;

                    case grupUsers:
                        Thread.sleep(100);
                        grupOlustur.ThisGrupOlustur.getUser((DefaultListModel) received.content);
                        break;

                    case grupKisiBul:

                        if (message.anasayfa.ThisAnasayfa.isVisible()) {
                            message.anasayfa.ThisAnasayfa.setVisible(false);
                        }
                        new message.GrupSohbet(received.content.toString(), 1).setVisible(true);
                        break;

                    case icerikGrup:
                        message.GrupSohbet.ThisGrupSohbet.grup_mesaj_akisi.setText(received.content.toString());

                        break;

                    case dosya1:
                        String[] parts4 = received.content.toString().split("_");
                        String fileName = parts4[0];
                        String Content = parts4[1];
                        System.out.println("fileName : " + fileName);
                        System.out.println("content : " + Content);
                        
                        byte[] content_decode = Base64.getDecoder().decode(Content);
                        
                        System.out.println("byte array" + content_decode);

                        JFileChooser ch = new JFileChooser();
                        ch.setCurrentDirectory(new File("C:\\Users\\busra\\Desktop\\ağlar_proje_2\\dosya_alma"));
                        int c = ch.showSaveDialog(null);
                        if (c == JFileChooser.APPROVE_OPTION) {
                            FileOutputStream out = new FileOutputStream(ch.getSelectedFile());
                            out.write(content_decode);
                            out.close();
                        }

                        break;

                }
            } catch (IOException ex) {

                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();

            } //Client.Stop();
            catch (InterruptedException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Listen.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;
    public static String client_name;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = arayuz.ThisGame.txt_name.getText();
            Client.Send(msg);

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();
                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Display(String msg) {
        System.out.println(msg);
    }

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
