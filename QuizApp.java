import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class QuizApp extends JFrame implements ActionListener {

    JLabel questionLabel, timerLabel, progressLabel;

    JRadioButton opt1, opt2, opt3, opt4;

    ButtonGroup bg;

    JButton nextBtn;

    String studentName, rollNo;

    int index = 0;
    int score = 0;
    int timeLeft = 10;

    Timer timer;

    String[][] questions = {

            {"What is Java?",
                    "Programming Language",
                    "Coffee",
                    "OS",
                    "Browser",
                    "Programming Language"},

            {"Which is not OOP concept?",
                    "Inheritance",
                    "Encapsulation",
                    "Compilation",
                    "Polymorphism",
                    "Compilation"},

            {"Which company developed Java?",
                    "Microsoft",
                    "Sun Microsystems",
                    "Google",
                    "Apple",
                    "Sun Microsystems"},

            {"Which keyword is used to inherit a class?",
                    "this",
                    "super",
                    "extends",
                    "implements",
                    "extends"},

            {"Which method is entry point of Java program?",
                    "start()",
                    "main()",
                    "run()",
                    "init()",
                    "main()"},

            {"Which of these is not a primitive data type?",
                    "int",
                    "float",
                    "String",
                    "boolean",
                    "String"},

            {"Which package contains Scanner class?",
                    "java.util",
                    "java.io",
                    "java.lang",
                    "java.net",
                    "java.util"}
    };

    public QuizApp(String name, String rollNo) {

        this.studentName = name;
        this.rollNo = rollNo;

        setTitle("Quiz Application");

        setSize(650, 450);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(new Color(20, 20, 40));

        // HEADER

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(new Color(30, 30, 70));

        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        progressLabel = new JLabel();

        progressLabel.setForeground(Color.WHITE);

        progressLabel.setFont(new Font("Arial", Font.BOLD, 14));

        timerLabel = new JLabel("Time Left : 10");

        timerLabel.setForeground(Color.YELLOW);

        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));

        header.add(progressLabel, BorderLayout.WEST);

        header.add(timerLabel, BorderLayout.EAST);

        // CENTER PANEL

        JPanel center = new JPanel();

        center.setBackground(new Color(40, 40, 80));

        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel = new JLabel();

        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));

        questionLabel.setForeground(Color.WHITE);

        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        center.add(questionLabel);

        center.add(Box.createRigidArea(new Dimension(0, 20)));

        opt1 = createOption();
        opt2 = createOption();
        opt3 = createOption();
        opt4 = createOption();

        bg = new ButtonGroup();

        bg.add(opt1);
        bg.add(opt2);
        bg.add(opt3);
        bg.add(opt4);

        center.add(opt1);
        center.add(opt2);
        center.add(opt3);
        center.add(opt4);

        // FOOTER

        JPanel footer = new JPanel();

        footer.setBackground(new Color(30, 30, 70));

        nextBtn = new JButton("Next");

        nextBtn.setBackground(new Color(0, 153, 255));

        nextBtn.setForeground(Color.WHITE);

        nextBtn.setFocusPainted(false);

        nextBtn.setFont(new Font("Arial", Font.BOLD, 14));

        nextBtn.addActionListener(this);

        footer.add(nextBtn);

        // ADD PANELS

        mainPanel.add(header, BorderLayout.NORTH);

        mainPanel.add(center, BorderLayout.CENTER);

        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);

        loadQuestion();

        startTimer();
    }

    private JRadioButton createOption() {

        JRadioButton btn = new JRadioButton();

        btn.setFont(new Font("Arial", Font.PLAIN, 15));

        btn.setForeground(Color.WHITE);

        btn.setBackground(new Color(40, 40, 80));

        btn.setFocusPainted(false);

        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        return btn;
    }

    void loadQuestion() {

        if (index < questions.length) {

            questionLabel.setText(
                    (index + 1) + ". " + questions[index][0]);

            opt1.setText(questions[index][1]);

            opt2.setText(questions[index][2]);

            opt3.setText(questions[index][3]);

            opt4.setText(questions[index][4]);

            progressLabel.setText(
                    "Question " + (index + 1)
                            + " / " + questions.length);

            bg.clearSelection();

            timeLeft = 10;

            timerLabel.setText("Time Left : " + timeLeft);

        } else {

            showResult();
        }
    }

    void startTimer() {

        timer = new Timer(1000, e -> {

            timeLeft--;

            timerLabel.setText("Time Left : " + timeLeft);

            if (timeLeft <= 0) {

                timer.stop();

                checkAnswer();

                index++;

                if (index < questions.length) {

                    loadQuestion();

                    startTimer();

                } else {

                    showResult();
                }
            }
        });

        timer.start();
    }

    void checkAnswer() {

        String selected = "";

        if (opt1.isSelected())
            selected = opt1.getText();

        if (opt2.isSelected())
            selected = opt2.getText();

        if (opt3.isSelected())
            selected = opt3.getText();

        if (opt4.isSelected())
            selected = opt4.getText();

        if (selected.equals(questions[index][5])) {

            score++;
        }
    }

    void showResult() {

        if (timer != null) {

            timer.stop();
        }

        int totalMarks = questions.length;

        int attempted = index;

        try {

            Connection con = DBConnection.getConnection();

            if (con == null) {

                JOptionPane.showMessageDialog(this,
                        "Database Connection Failed");

                return;
            }

            String query =
                    "INSERT INTO result(student_name, roll_no, marks, questions_attempted, total_marks) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setString(1, studentName);

            pst.setString(2, rollNo);

            pst.setInt(3, score);

            pst.setInt(4, attempted);

            pst.setInt(5, totalMarks);

            int rows = pst.executeUpdate();

            if (rows > 0) {

                System.out.println("Result Inserted Successfully");
            }

            pst.close();

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this,
                "Quiz Completed\n\n"
                        + "Name : " + studentName
                        + "\nRoll No : " + rollNo
                        + "\nScore : " + score
                        + " / " + totalMarks);

        System.exit(0);
    }

    public void actionPerformed(ActionEvent e) {

        timer.stop();

        checkAnswer();

        index++;

        if (index < questions.length) {

            loadQuestion();

            startTimer();

        } else {

            showResult();
        }
    }
}