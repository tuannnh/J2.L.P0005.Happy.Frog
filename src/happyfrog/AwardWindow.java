/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package happyfrog;

/**
 *
 * @author tuannnh
 */
public class AwardWindow extends javax.swing.JFrame {

    /**
     * Creates new form AwardWindow
     */
    public AwardWindow(int points) {
        initComponents();
         lblScore.setText("" + points);
        if (points < 10) {
            lblMedal.setText("");
        } else if (10 <= points && points < 20) {
            lblMedal.setText("You got Bronze Medal!");
        } else if (20 <= points && points < 30) {
            lblMedal.setText("You got Silver Medal!");
        } else if (30 <= points && points < 40) {
            lblMedal.setText("You got Gold Medal!");
        } else {
            lblMedal.setText("You got Platinum Medal!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        lblScore = new javax.swing.JLabel();
        lblMedal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(400, 300));
        setResizable(false);
        setSize(new java.awt.Dimension(400, 300));
        getContentPane().setLayout(new java.awt.FlowLayout());

        jLabel4.setFont(new java.awt.Font("Lucida Calligraphy", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Your Score:");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setPreferredSize(new java.awt.Dimension(400, 100));
        getContentPane().add(jLabel4);

        lblScore.setFont(new java.awt.Font("Lucida Handwriting", 0, 80)); // NOI18N
        lblScore.setForeground(new java.awt.Color(255, 51, 51));
        lblScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblScore.setText("0");
        lblScore.setPreferredSize(new java.awt.Dimension(400, 100));
        getContentPane().add(lblScore);

        lblMedal.setFont(new java.awt.Font("Marker Felt", 0, 36)); // NOI18N
        lblMedal.setForeground(new java.awt.Color(0, 153, 153));
        lblMedal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMedal.setText("You got medal Silver");
        lblMedal.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblMedal.setPreferredSize(new java.awt.Dimension(400, 100));
        getContentPane().add(lblMedal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblMedal;
    private javax.swing.JLabel lblScore;
    // End of variables declaration//GEN-END:variables
}