package SR;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class SortFrame extends JFrame {
    private int counter = 1;

    SortFrame(int num) {
        EventQueue.invokeLater(() -> setsortframe(num));
    }

    public synchronized void setsortframe(int num) {
        ArrayList<JButton> randombuttons = new ArrayList<>();
        ScreenGetter screen = new ScreenGetter();

        //main setup
        JFrame sortframe = new JFrame();
        JButton sort = new JButton(" Sort");
        JButton reset = new JButton("Reset");
        JPanel random = new JPanel();
        JPanel buttons = new JPanel();
        Font font = new Font("Canvas", Font.PLAIN, 12);

        //farme setup
        sortframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        sortframe.setSize(screen.getscreen('w') / 2, screen.getscreen('h') / 2);
        sortframe.setLocationByPlatform(true);
        sortframe.setVisible(true);

        //layout setup
        GridLayout gl = new GridLayout(10,10,5,5);

        //panels setup
        random.setLayout(gl);
        random.setBorder((BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        buttons.setLayout(gl);
        buttons.setBorder((BorderFactory.createEmptyBorder(100, 20, 20, 100)));
        sortframe.add(buttons, BorderLayout.EAST);
        sortframe.add(random, BorderLayout.WEST);

        //control buttons setup
        buttonplacer(sort, buttons, font, gl, 52, 175, 58);
        buttonplacer(reset, buttons, font, gl, 52, 175, 58);

        //randombuttons method
        randomizer(num, randombuttons);
        randomplacer(randombuttons, random, gl, font);
        randomlistener(randombuttons, random, gl, font, sortframe);

        //actionlistener to sort
        sort.addActionListener(e -> {
            quickSort(randombuttons, 0, randombuttons.size() - 1, random, gl, font);
            this.counter++;
        });

        //actionlistener to reset
        reset.addActionListener(e -> {
            IntroFrame mainframe = new IntroFrame();
            sortframe.dispose();
        });
    }

    public synchronized void buttonplacer (JButton button, JPanel panel, Font font, GridLayout gl, int r, int g, int b) {
        panel.add(button, gl);
        button.setBackground(new Color(r, g, b));
        button.setForeground(Color.WHITE);
        button.setFont(font);
    }

    public synchronized void randomizer(int num, ArrayList<JButton> randombuttons) {
        //random numbers filler setup
        boolean minimum = false;
        for (int i = 0; i < num; i++) {
            JButton randombutton = new JButton();
            int randomval = new Random().nextInt(1000);
            randombutton.setText(String.valueOf(randomval));
            if (randomval <= 30) {
                minimum = true;
            }
            if (!minimum && i == num - 1) {
                randombutton.setText(String.valueOf(new Random().nextInt(30)));
            }
            randombuttons.add(randombutton);
        }
    }

    public synchronized void randomplacer(ArrayList<JButton> randombuttons, JPanel random, GridLayout gl, Font font){
                for (JButton randombutton : randombuttons) {
                    buttonplacer(randombutton, random, font, gl, 52, 114, 175);
                }
    }

    public synchronized void randomlistener
            (ArrayList<JButton> randombuttons, JPanel random, GridLayout gl, Font font, JFrame sortframe) {
        //actionlistener to randombutton press
        for (JButton randombutton: randombuttons) {
            randombutton.addActionListener(e -> {
                if (Integer.parseInt(randombutton.getText()) > 30) {
                    new JOptionPane().showMessageDialog
                            (sortframe, "Please select a value smaller or equal to 30.");
                }
                else {
                    randomizer(new Random().nextInt(10-1), randombuttons);
                    randomplacer(randombuttons, random, gl, font);
                    randomlistener(randombuttons, random, gl, font, sortframe);
                }
            });
        }
    }

    public synchronized void quickSort(ArrayList<JButton> randombuttons, int lb, int rb, JPanel random, GridLayout gl, Font font) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                //where "lm" - leftmarker; "lb" - leftborder; "rm" - rightmarker; "rb" - rightborder
                int lm = lb;
                int rm = rb;
                int pivot = Integer.parseInt(randombuttons.get((lm + rm) / 2).getText());
                //counter counts every click and switches decs and asc sorts
                if (counter % 2 == 0) {
                    do {
                        while (Integer.parseInt(randombuttons.get(lm).getText()) > pivot) {
                            lm++;
                        }
                        while (Integer.parseInt(randombuttons.get(rm).getText()) < pivot) {
                            rm--;
                        }
                        if (lm <= rm) {
                            if (lm < rm) {
                                String tmp2 = randombuttons.get(lm).getText();
                                randombuttons.get(lm).setText(randombuttons.get(rm).getText());
                                randombuttons.get(rm).setText(tmp2);
                                Thread.sleep(500);
                                randomplacer(randombuttons, random, gl, font);
                            }
                            lm++;
                            rm--;
                        }
                    } while (lm <= rm);
                    if (lm < rb) {
                        quickSort(randombuttons, lm, rb, random, gl, font);
                    }
                    if (lb < rm) {
                        quickSort(randombuttons, lb, rm, random, gl, font);
                    }
                }
                else if (counter % 2 == 1) {
                    do {
                        while (Integer.parseInt(randombuttons.get(lm).getText()) < pivot) {
                            lm++;
                        }
                        while (Integer.parseInt(randombuttons.get(rm).getText()) > pivot) {
                            rm--;
                        }
                        if (lm <= rm) {
                            if (lm < rm) {
                                String tmp2 = randombuttons.get(lm).getText();
                                randombuttons.get(lm).setText(randombuttons.get(rm).getText());
                                randombuttons.get(rm).setText(tmp2);
                                Thread.sleep(500);
                                randomplacer(randombuttons, random, gl, font);
                            }
                            lm++;
                            rm--;
                        }
                    } while (lm <= rm);
                    if (lm < rb) {
                        quickSort(randombuttons, lm, rb, random, gl,font);
                    }
                    if (lb < rm) {
                        quickSort(randombuttons, lb, rm, random, gl, font);
                    }
                }
                return null;
            }
        }.execute();
    }
}