import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;


public class server extends JFrame implements ActionListener,Runnable {
    //decleartions
    JButton send_but;
    JButton music_but;
    JTextField textField;
    JTextArea textArea;
    ServerSocket serverSocket;
    Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    Thread thread;

    public server() {
        JFrame chat_room = new JFrame("Server Chat Room");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel(new FlowLayout());
        textArea = new JTextArea(10, 25);
        textArea.setEditable(false);
        JScrollPane pane = new JScrollPane(textArea);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textField = new JTextField(10);
        send_but = new JButton("send");
        send_but.addActionListener(this);
        music_but = new JButton("music");
        music_but.addActionListener(this);

        chat_room.addWindowListener(new Wind_ad());//adding window listener to the frame.

        panel1.add(textArea);
        panel1.add(pane);
        panel2.add(textField);
        panel2.add(send_but);
        panel2.add(music_but);
        chat_room.add(panel1,BorderLayout.PAGE_START);
        chat_room.add(panel2,BorderLayout.PAGE_END);

        //this ones will come after the thread start

        //woops
        try {
            serverSocket = new ServerSocket(12000);
            socket = serverSocket.accept();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            //error
        }
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        chat_room.setFont(new Font("Arial", Font.BOLD, 20));
        chat_room.setSize(400, 400);
        chat_room.setLocation(300,300);
        chat_room.pack();
        chat_room.setVisible(true);



    }

    private class Wind_ad extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent we) {
            System.exit(0);
        }
    }

    //this method will be called after clicking buttons
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == send_but) {
            printWriter.println(textField.getText());
            textArea.append("You: " + textField.getText() + "\n");
            textField.setText("");
        } else {
            //musicpar
        }

    }

    public void run() {
        while (true) {
            try {
                String text = bufferedReader.readLine();
                textArea.append("Parter: " + text + "\n");
            } catch (Exception e) {

            }
        }
    }


    public static void main(String[] args) {
        server serv = new server();

    }
}
