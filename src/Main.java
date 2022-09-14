/*
***********************************************************
Made by Elio Thadhani, 5/26/22
Yay!
* ***********************************************************
 */

import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main implements Runnable, KeyListener {
    
    final int WIDTH = 200;
    final int HEIGHT = 200;
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    ArrayList <Penguin> store = new ArrayList();

    public void savepen(){

        try {
            //this will try and save whatever you tell it to save
            FileOutputStream fileOut = new FileOutputStream("Penguinlist.ser"); //you create the file in this location with this name
            //if the file already exists it will simply replace it
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(store);
            //writes your object into the file, saves it, and closes it
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in Penguinlist.ser");

        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    public void loadpen(){
        try {
            FileInputStream fileIn = new FileInputStream("Penguinlist.ser"); //this will read the file specified here, note it has to be in the prodject folder
            ObjectInputStream in = new ObjectInputStream(fileIn);
            store = (ArrayList<Penguin>) in.readObject(); //sets your variable to the thing it reads in the file, you have to specify what it is your reading
            in.close();
            fileIn.close();
            //closes file
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Penguin class not found");

            c.printStackTrace();
            return;
        }
        System.out.println("error 404");

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if(key == 'a'){
            //allows you to add a penguin, how this works does not matter to much
            String spec;
            //declare a variable
            spec = (String) JOptionPane.showInputDialog(
                    //pops up a Jpanel which allows you to input a string, you can specify what the panel says
                    frame,
                    "What species is your penguin?",
                    "Penguin",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "Emperor Penguin"
            );
            String heigh;
            heigh = (String) JOptionPane.showInputDialog(
                    frame,
                    "How tall is your penguin in ft?",
                    "Penguin",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "5.5"
            );
            String ag;
            ag = (String) JOptionPane.showInputDialog(
                    frame,
                    "How old is your penguine in years?",
                    "Penguin",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "4"
            );
            try{
                //add a penguin based off of defined variables
                store.add(new Penguin(Double.parseDouble(heigh),Integer.parseInt(ag),spec));

            }
            catch(Exception b ){

            }

        }
        if(key == 'p'){
            System.out.println();
            System.out.println();
            System.out.println();
            for(int x = 0; x<store.size();x++){
                store.get(x).print();
            }
        }
        if(key == 's'){
            //calls our save function, which saves the array list
            savepen();
        }
        if(key == 'l'){
            //calls the load function, which will load any saved data
            loadpen();
        }

    }

    //below is nothing special to saving and loading, just the standard graphics and render etc

    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main() {
        setUpGraphics();
    }

    public void run() {
        while (true) {
            render();
            pause(20);
        }
    }

    public void pause(int time ){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");
        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        panel.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        canvas.addKeyListener(this);

        frame.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt) {
                Component c = (Component)evt.getSource();
                canvas.setBounds(0,0,frame.getWidth(),frame.getHeight());
            }
        });

        System.out.println("DONE graphic setup");
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, frame.getWidth(), frame.getHeight());

        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}