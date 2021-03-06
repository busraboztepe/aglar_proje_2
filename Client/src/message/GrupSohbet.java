/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author busra
 */
public class GrupSohbet extends javax.swing.JFrame {

    /**
     * Creates new form GrupSohbet
     *
     */
    public static GrupSohbet ThisGrupSohbet;
    public DefaultListModel kisilerListesi;
    public static boolean durum;
    public static String grup_adi;
    public static String kisiler;
    public static File fileToSend = null;

    public GrupSohbet() {
        initComponents();
        ThisGrupSohbet = this;
        ThisGrupSohbet.setPreferredSize(new Dimension(450, 640));
        durum = false;
        grup_mesaj_akisi.setEditable(true);
    }

    public GrupSohbet(String s) throws InterruptedException {
        initComponents();
        ThisGrupSohbet = this;
        ThisGrupSohbet.setPreferredSize(new Dimension(450, 640));
        durum = false;
        grup_mesaj_akisi.setEditable(true);
        Thread.sleep(500);

        String[] parts = s.split("_");
        grup_adi = parts[0];
        kisiler = parts[1];
        Thread.sleep(100);
        ThisGrupSohbet.grup_adi_.setText(grup_adi);

        Message msg = new Message(Message.Message_Type.grupKisiBul);
        msg.content = grup_adi + "_" + kisiler;
        Thread.sleep(100);
        client.Client.Send(msg);
    }

    public GrupSohbet(String s, int i) throws InterruptedException {
        initComponents();
        ThisGrupSohbet = this;
        ThisGrupSohbet.setPreferredSize(new Dimension(450, 640));
        durum = false;
        grup_mesaj_akisi.setEditable(true);
        Thread.sleep(500);

        String[] parts3 = s.split("_");
        grup_adi = parts3[0];
        kisiler = parts3[1];
        ThisGrupSohbet.grup_adi_.setText(grup_adi);
        Thread.sleep(100);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        grup_adi_ = new javax.swing.JLabel();
        grup_mesaj_kutusu = new javax.swing.JTextField();
        btn_mesaj_gonder_grup = new javax.swing.JButton();
        dosya = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        grup_mesaj_akisi = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(450, 640));
        getContentPane().setLayout(null);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/back.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(0, 0, 80, 80);

        grup_adi_.setBackground(new java.awt.Color(0, 102, 255));
        grup_adi_.setFont(new java.awt.Font("Comic Sans MS", 3, 18)); // NOI18N
        grup_adi_.setForeground(new java.awt.Color(255, 255, 255));
        grup_adi_.setOpaque(true);
        getContentPane().add(grup_adi_);
        grup_adi_.setBounds(80, 0, 360, 80);

        grup_mesaj_kutusu.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        grup_mesaj_kutusu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));
        getContentPane().add(grup_mesaj_kutusu);
        grup_mesaj_kutusu.setBounds(50, 500, 320, 80);

        btn_mesaj_gonder_grup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/send.png"))); // NOI18N
        btn_mesaj_gonder_grup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mesaj_gonder_grupActionPerformed(evt);
            }
        });
        getContentPane().add(btn_mesaj_gonder_grup);
        btn_mesaj_gonder_grup.setBounds(370, 500, 60, 80);

        dosya.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/file.png"))); // NOI18N
        dosya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosyaActionPerformed(evt);
            }
        });
        getContentPane().add(dosya);
        dosya.setBounds(0, 500, 50, 80);

        grup_mesaj_akisi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        grup_mesaj_akisi.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(grup_mesaj_akisi);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(0, 80, 430, 420);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_mesaj_gonder_grupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mesaj_gonder_grupActionPerformed
        // TODO add your handling code here:
        //eger dosya gonderme durumu yoksa clientin yazdigi mesaji mesaj akisina ekler 
        //tum mesaj akisini karsi clientlara gonderir
        if (fileToSend == null) {
            String ad = client.Client.client_name;
            String n_ad = ad.toUpperCase();
            String icerik = n_ad + ":\n" + grup_mesaj_kutusu.getText().toString() + "\n";
            grup_mesaj_akisi.setText(grup_mesaj_akisi.getText() + icerik);
            grup_mesaj_kutusu.setText("");

            Message msg = new Message(Message.Message_Type.icerikGrup);
            msg.content = kisiler + "_" + grup_mesaj_akisi.getText();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(GrupSohbet.class.getName()).log(Level.SEVERE, null, ex);
            }
            client.Client.Send(msg);
            //dosya gonderme durumu varsa
        } else if (fileToSend != null) {
            try {
                //dosya gonderildi bilgisini mesaj akisina ekler dosya adiyla
                //dosyayi karsiya gonderir
                String ad = client.Client.client_name;
                String n_ad = ad.toUpperCase();
                String icerik = n_ad + ":\n" + "--- Dosya : " + fileToSend.getName() + " ---\n" + grup_mesaj_kutusu.getText().toString() + "\n";
                grup_mesaj_akisi.setText(grup_mesaj_akisi.getText() + icerik);
                grup_mesaj_kutusu.setText("");

                Message msg = new Message(Message.Message_Type.icerikGrup);
                msg.content = kisiler + "_" + grup_mesaj_akisi.getText();
                Thread.sleep(100);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GrupSohbet.class.getName()).log(Level.SEVERE, null, ex);
                }
                client.Client.Send(msg);

                FileInputStream fileInputStream = new FileInputStream(fileToSend);
                String fileName = fileToSend.getName();
                byte[] fileContentBytes = new byte[fileInputStream.available()];
                fileInputStream.read(fileContentBytes);
                fileInputStream.close();
                fileToSend = null;

                String fileContentBytes_string = Base64.getEncoder().encodeToString(fileContentBytes);
                Message msg4 = new Message(Message.Message_Type.dosya1);
                msg4.content = kisiler + "&" + fileName + "_" + fileContentBytes_string;
                client.Client.Send(msg4);
                Thread.sleep(300);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GrupSohbet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GrupSohbet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(GrupSohbet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_mesaj_gonder_grupActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //client geri cikarsa sohbetten sohbet penceresi kapanir
        ThisGrupSohbet.dispose();
        message.anasayfa.ThisAnasayfa.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dosyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dosyaActionPerformed
        // TODO add your handling code here:
        //dosya cubugunu acar ve gondermek istenilen dosyayi secmeye yarar
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Dosya se??");
        fileChooser.setCurrentDirectory(new File("C:\\Users\\busra\\Desktop\\a??lar_proje_2\\dosya_gonderme"));
        int secim = fileChooser.showOpenDialog(this);

        if (secim == JFileChooser.APPROVE_OPTION) {
            fileToSend = fileChooser.getSelectedFile();
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                Logger.getLogger(GrupSohbet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_dosyaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GrupSohbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GrupSohbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GrupSohbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GrupSohbet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GrupSohbet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_mesaj_gonder_grup;
    private javax.swing.JButton dosya;
    public javax.swing.JLabel grup_adi_;
    public javax.swing.JTextPane grup_mesaj_akisi;
    private javax.swing.JTextField grup_mesaj_kutusu;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
