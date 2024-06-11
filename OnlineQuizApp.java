import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineQuizApp {
    private List<Question> questions;
    private int score;

    public OnlineQuizApp() {
        this.questions = new ArrayList<>();
        this.score = 0;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void startQuiz() {
        System.out.println("Welcome to the online quiz app!");
        System.out.println("You will be asked a series of questions. Please answer them correctly to earn points.");

        for (Question question : this.questions) {
            System.out.println(question.getQuestion());
            System.out.println("A) " + question.getOptionA());
            System.out.println("B) " + question.getOptionB());
            System.out.println("C) " + question.getOptionC());
            System.out.println("D) " + question.getOptionD());

            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                System.out.println("Correct answer!");
                this.score++;
            } else {
                System.out.println("Incorrect answer. The correct answer was " + question.getCorrectAnswer());
            }
        }

        System.out.println("Your final score is " + this.score + " out of " + this.questions.size());
    }

    public static void main(String[] args) {
        OnlineQuizApp quizApp = new OnlineQuizApp();

        // Add questions to the quiz app
        quizApp.addQuestion(new Question("What is the capital of France?", "A) Paris", "B) London", "C) Berlin", "D) Rome", "A"));
        quizApp.addQuestion(new Question("What is the largest planet in our solar system?", "A) Earth", "B) Saturn", "C) Jupiter", "D) Uranus", "C"));
        quizApp.addQuestion(new Question("What is the smallest country in the world?", "A) Vatican City", "B) Monaco", "C) Nauru", "D) Tuvalu", "A"));

        // Start the quiz
        quizApp.startQuiz();
    }
}

class Question {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getOptionA() {
        return this.optionA;
    }

    public String getOptionB() {
        return this.optionB;
    }

    public String getOptionC() {
        return this.optionC;
    }

    public String getOptionD() {
        return this.optionD;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
}