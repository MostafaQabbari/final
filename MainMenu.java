package fruitninja;

import fruitninja.controller.GameController;

import javax.swing.*;
import java.awt.*;

public class MainMenu {


    public MainMenu() {
        JFrame f = new JFrame("Fruit Ninja");
        JButton b = new JButton("Normal");
        b.setBounds(100, 100, 140, 40);
        JButton b2 = new JButton("Arcade");
        b2.setBounds(100, 160, 140, 40);
        JLabel label = new JLabel();
        label.setText("Fruit Ninja ");
        label.setBounds(100, 5, 100, 100);
        f.add(label);
        f.add(b);
        f.add(b2);
        f.setSize(300, 300);
        f.setLayout(null);

        f.getContentPane().setBackground(Color.black );
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //action listener
        b.addActionListener(arg0 -> {
            GameController controller = new GameController();

            // Center the frame on monitor
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = controller.getSize();
            controller.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
            // Show the application on screen
            controller.setVisible(true);
            // Run the game
            controller.run();
            f.setVisible(false);

        });

        b2.addActionListener(arg0 -> {
            GameController controller = new GameController();

            // Center the frame on monitor
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = controller.getSize();
            controller.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
            // Show the application on screen
            controller.setVisible(true);
            // Run the game
            controller.run2();
            f.setVisible(false);

        });


    }}
