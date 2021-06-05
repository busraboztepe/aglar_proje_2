/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import client.Client;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author busra
 */
public class arayuz extends javax.swing.JFrame {

    /**
     * Creates new form arayuz
     */
    //framedeki komponentlere erişim için statik sayfa değişkeni
    public static arayuz ThisArayuz;

    public arayuz() {
        initComponents();
        ThisArayuz = this;
        ThisArayuz.setPreferredSize(new Dimension(450, 640));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_name = new javax.swing.JTextField();
        btn_connect = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(450, 640));
        setMinimumSize(new java.awt.Dimension(450, 640));
        setPreferredSize(new java.awt.Dimension(450, 640));
        getContentPane().setLayout(null);

        txt_name.setFont(new java.awt.Font("Constantia", 1, 14)); // NOI18N
        txt_name.setForeground(new java.awt.Color(102, 102, 102));
        txt_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_name.setText("İsim giriniz");
        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });
        getContentPane().add(txt_name);
        txt_name.setBounds(80, 280, 250, 40);

        btn_connect.setBackground(new java.awt.Color(255, 255, 255));
        btn_connect.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btn_connect.setForeground(new java.awt.Color(255, 255, 255));
        btn_connect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/next.png"))); // NOI18N
        btn_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectActionPerformed(evt);
            }
        });
        getContentPane().add(btn_connect);
        btn_connect.setBounds(150, 340, 119, 73);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Aşağıdaki kutucuğa isim-soy isim girerek ileri butonuna basınız");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 240, 410, 16);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/message/b_1.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.setMaximumSize(new java.awt.Dimension(450, 640));
        jLabel2.setMinimumSize(new java.awt.Dimension(450, 640));
        jLabel2.setPreferredSize(new java.awt.Dimension(450, 640));
        getContentPane().add(jLabel2);
        jLabel2.setBounds(-20, 0, 450, 640);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectActionPerformed
        // TODO add your handling code here:
        try {
            //bağlanılacak server ve portu veriyoruz
            Client.Start("127.0.0.1", 7000);
            //arayuzu kapatip anasayfaya baglaniyoruz
            ThisArayuz.dispose();
            new anasayfa(txt_name.getText().toString()).setVisible(true);
        } catch (Exception e) {
            System.out.println("Sisteme bağlanılamadı.");
        }
    }//GEN-LAST:event_btn_connectActionPerformed

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameActionPerformed

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
            java.util.logging.Logger.getLogger(arayuz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(arayuz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(arayuz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(arayuz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new arayuz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_connect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JTextField txt_name;
    // End of variables declaration//GEN-END:variables
}
