/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author busra
 */
public class grupOlustur extends javax.swing.JFrame {

    /**
     * Creates new form grupOlustur
     *
     */
    public static grupOlustur ThisGrupOlustur;
    public DefaultListModel grup;

    public grupOlustur() {
        initComponents();
        ThisGrupOlustur = this;
        ThisGrupOlustur.setPreferredSize(new Dimension(450, 640));
        grup = new DefaultListModel();
        this.users.setModel(grup);
    }

    public grupOlustur(DefaultListModel users) {
        initComponents();
        ThisGrupOlustur = this;
        ThisGrupOlustur.setPreferredSize(new Dimension(450, 640));
        grup = new DefaultListModel();
        this.users.setModel(grup);
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Logger.getLogger(grupOlustur.class.getName()).log(Level.SEVERE, null, ex);
        }
        Message msg = new Message(Message.Message_Type.grupUsers);
        msg.content = arayuz.ThisArayuz.txt_name.getText().toString();
        client.Client.Send(msg);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grup_adi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_grupOlustur = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        users = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(450, 640));
        setMinimumSize(new java.awt.Dimension(450, 640));
        getContentPane().setLayout(null);

        grup_adi.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        getContentPane().add(grup_adi);
        grup_adi.setBounds(180, 50, 210, 40);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Grup Adı Giriniz : ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(50, 50, 120, 40);

        jLabel1.setBackground(new java.awt.Color(0, 153, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 450, 109);

        btn_grupOlustur.setBackground(new java.awt.Color(0, 51, 153));
        btn_grupOlustur.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_grupOlustur.setForeground(new java.awt.Color(255, 255, 255));
        btn_grupOlustur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/next.png"))); // NOI18N
        btn_grupOlustur.setText("Grup Oluştur");
        btn_grupOlustur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_grupOlusturActionPerformed(evt);
            }
        });
        getContentPane().add(btn_grupOlustur);
        btn_grupOlustur.setBounds(220, 500, 220, 90);

        users.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        users.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jScrollPane1.setViewportView(users);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(0, 110, 440, 390);

        jButton1.setBackground(new java.awt.Color(0, 51, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/back.png"))); // NOI18N
        jButton1.setText("Anasayfaya Dön");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(0, 500, 220, 90);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_grupOlusturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_grupOlusturActionPerformed
        // TODO add your handling code here:
        //Listeden secili kisileri alir ve bu bilgiyi servera gonderir 
        //bu pencereyi kapatip grup sohbet penceresini baslatir
        String txt = ThisGrupOlustur.grup_adi.getText().toString() + "_";
        if (users.getSelectedIndex() != -1) {
            for (Object item : users.getSelectedValuesList()) {
                txt += item + "-";
            }
        }
        ThisGrupOlustur.setVisible(false);

        try {
            Thread.sleep(500);
            GrupSohbet d = new GrupSohbet(txt);
        } catch (InterruptedException ex) {
            Logger.getLogger(anasayfa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_grupOlusturActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //bu pencereyi kapatip anasayfaya geri doner
        ThisGrupOlustur.dispose();
        anasayfa.ThisAnasayfa.setVisible(true);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(sohbet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(grupOlustur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(grupOlustur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(grupOlustur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(grupOlustur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new grupOlustur().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_grupOlustur;
    public javax.swing.JTextField grup_adi;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JList<String> users;
    // End of variables declaration//GEN-END:variables
}
