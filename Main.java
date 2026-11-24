
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Question {
    String question;
    String[] options;
    char correctAnswer;

    Question(String question, String[] options, char answer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = answer;
    }

    void display(int number) {
        System.out.println("\nQ" + number + ". " + question);

        for (String option : options) {
            System.out.println(option);
        }
    }
}

class Exam {
    ArrayList<Question> questions = new ArrayList<>();

    Exam() {
        questions.add(new Question(
            "Which language is used for Android development?",
            new String[]{"A. HTML", "B. Java", "C. CSS", "D. SQL"},
            'B'
        ));

        questions.add(new Question(
            "Which data structure follows FIFO?",
            new String[]{"A. Stack", "B. Tree", "C. Queue", "D. Graph"},
            'C'
        ));

        questions.add(new Question(
            "Which keyword creates an object in Java?",
            new String[]{"A. new", "B. class", "C. static", "D. void"},
            'A'
        ));

        questions.add(new Question(
            "Which collection stores key-value pairs?",
            new String[]{"A. ArrayList", "B. HashMap", "C. Queue", "D. Stack"},
            'B'
        ));

        questions.add(new Question(
            "Which method starts a Java program?",
            new String[]{"A. start()", "B. run()", "C. main()", "D. print()"},
            'C'
        ));
    }

    int conductExam(Scanner sc, HashMap<Integer, Character> answers) {
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).display(i + 1);

            System.out.print("Your answer (A/B/C/D): ");
            String input = sc.next().trim().toUpperCase();

            if (input.length() != 1 ||
                input.charAt(0) < 'A' ||
                input.charAt(0) > 'D') {

                System.out.println("Invalid answer. Marked incorrect.");
                answers.put(i + 1, '-');
                continue;
            }

            char answer = input.charAt(0);
            answers.put(i + 1, answer);

            if (answer == questions.get(i).correctAnswer) {
                score++;
            }
        }

        return score;
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== ONLINE EXAMINATION SYSTEM =====");

        System.out.print("Enter student name: ");
        String studentName = sc.nextLine().trim();

        if (studentName.isEmpty()) {
            System.out.println("Student name cannot be empty.");
            sc.close();
            return;
        }

        Exam exam = new Exam();

        HashMap<Integer, Character> answers = new HashMap<>();

        System.out.println("\nExam Started!");
        System.out.println("Total Questions: " + exam.questions.size());

        int score = exam.conductExam(sc, answers);

        int total = exam.questions.size();
        double percentage = (score * 100.0) / total;

        String grade;

        if (percentage >= 80)
            grade = "A";
        else if (percentage >= 60)
            grade = "B";
        else if (percentage >= 40)
            grade = "C";
        else
            grade = "Fail";

        System.out.println("\n===== EXAM RESULT =====");
        System.out.println("Student: " + studentName);
        System.out.println("Correct Answers: " + score);
        System.out.println("Wrong Answers: " + (total - score));
        System.out.println("Total Marks: " + total);

        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Grade: " + grade);

        try {
            FileWriter writer = new FileWriter(
                "exam_results.csv", true
            );

            writer.write(
                "\"" + studentName.replace("\"", "\"\"") + "\"," +
                score + "," +
                total + "," +
                String.format("%.2f", percentage) + "," +
                grade + "\n"
            );

            writer.close();

            System.out.println("Result saved to exam_results.csv");

        } catch (IOException e) {
            System.out.println("Could not save result.");
        }

        sc.close();
    }
}
