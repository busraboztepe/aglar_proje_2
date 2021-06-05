/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

/**
 *
 * @author busra
 */
public class Message implements java.io.Serializable {

    //mesaj tipleri enum 
    public static enum Message_Type {
        Name, Connected, kisiBul, icerik, icerik2, durum, baglantiKopar, baglantiKopar2, grupUsers, grupKisiBul, icerikGrup, dosya1, dosya2
    }
    //mesajın tipi
    public Message_Type type;
    //mesajın içeriği obje tipinde bunun nedeni istenilen tip içerik yüklenebilsin
    public Object content;

    public Message(Message_Type t) {
        this.type = t;
    }

}
