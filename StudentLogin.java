import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentLogin extends JFrame implements ActionListener {

    JTextField nameField, rollField;
    JButton loginBtn;

    public StudentLogin() {

        setTitle("Student Login");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

     
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30, 30, 60));

     
        JLabel title = new JLabel("Quiz Application");
        title.setBounds(270, 40, 400, 70);
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(Color.WHITE);

        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setBounds(160, 180, 220, 50);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);

      
        nameField = new JTextField();
        nameField.setBounds(380, 180, 330, 50);
        nameField.setFont(new Font("Arial", Font.PLAIN, 22));

     
        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(160, 280, 220, 50);
        rollLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rollLabel.setForeground(Color.WHITE);

  
        rollField = new JTextField();
        rollField.setBounds(380, 280, 330, 50);
        rollField.setFont(new Font("Arial", Font.PLAIN, 22));

     
        loginBtn = new JButton("Start Quiz");
        loginBtn.setBounds(320, 430, 250, 70);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 24));
        loginBtn.setBackground(new Color(0, 153, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

    
        loginBtn.addActionListener(this);

        panel.add(title);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(rollLabel);
        panel.add(rollField);
        panel.add(loginBtn);

        add(panel);

        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {

        String name = nameField.getText();
        String rollNo = rollField.getText();

        if (name.isEmpty() || rollNo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter all details"
            );

        } else {

           
            new QuizApp(name, rollNo);

            dispose();
        }
    }


    public static void main(String[] args) {

        new StudentLogin();
    }
}