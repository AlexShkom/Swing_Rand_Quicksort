package SR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class IntroFrame extends JFrame {

    public static void main(String[] args) {
        IntroFrame mainframe = new IntroFrame();
    }

    IntroFrame() {
        EventQueue.invokeLater(() -> {

            ScreenGetter screen = new ScreenGetter();

            //main setup
            JFrame introframe = new JFrame();
            JLabel num_question = new JLabel("How many numbers do display?");
            JTextField num_count = new JTextField(6);
            JButton num_submit = new JButton("Enter ");
            JPanel center = new JPanel();
            Font font = new Font("Canvas", Font.PLAIN, 12);

            //farme setup
            introframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            introframe.setSize(screen.getscreen('w') / 2, screen.getscreen('h') / 2);
            introframe.setLocationByPlatform(true);
            introframe.setVisible(true);

            //layout setup
            GridBagConstraints layout_cons = new GridBagConstraints();
            layout_cons.insets = new Insets(5,5,5,5);
            layout_cons.weightx = 0;
            layout_cons.weighty = 0;
            layout_cons.gridx = 0;
            layout_cons.gridwidth = 0;
            layout_cons.gridheight = 1;

            //panel setup (CENTER align is default so there's no reason in writing it)
            center.setLayout(new GridBagLayout());
            introframe.add(center);

            //label setup
            layout_cons.gridy = 0;
            num_question.setFont(font);
            center.add(num_question, layout_cons);

            //textarea setup
            layout_cons.gridy = 1;
            center.add(num_count, layout_cons);

            //button setup
            layout_cons.gridy = 2;
            center.add(num_submit, layout_cons);
            num_submit.setBackground(new Color (52, 114, 175));
            num_submit.setForeground(Color.WHITE);
            num_submit.setFont(font);

            //action listener setup
            ActionListener sending = e -> {
                if(!num_count.getText().trim().isEmpty()) {
                    try {
                        int numbers = Integer.parseInt(num_count.getText());
                        if (numbers <= 0) {
                            throw new NumberFormatException();
                        }
                        else {
                            num_count.setText("");
                            SortFrame sortframe = new SortFrame(numbers);
                            introframe.dispose();
                        }
                    } catch (Exception ex) {
                        new JOptionPane().showMessageDialog
                                (introframe,"Positive digits only!", "Error", JOptionPane.ERROR_MESSAGE);
                        num_count.setText("");
                    }
                }
                else {
                    new JOptionPane().showMessageDialog(introframe,"Type something...");
                }
            };

            //action listener adding
            num_submit.addActionListener(sending);
            num_count.addActionListener(sending);
        });
    }
}
