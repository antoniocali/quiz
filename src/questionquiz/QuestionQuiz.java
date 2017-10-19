/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questionquiz;

import brain.Retriever;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import models.QuestionBuilder.Question;
import models.WrongReplies;
import utility.ReturnValue;

/**
 *
 * @author ac186112
 */
public class QuestionQuiz {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\AC186112\\Documents\\NetBeansProjects\\QuestionQuiz\\questions.txt";
        Retriever retriever = new Retriever(path);
        ReturnValue<ArrayList<Question>> retValue = retriever.runRetriever();
        if (retValue.isSuccess()) {
            ArrayList<Question> questions = retValue.getObject();
            System.out.println("Questions Found(s): " + questions.size());
            System.out.println("Questions to do:");
            Scanner in = new Scanner(System.in);
            long questionsToDo = 0;
            boolean canStart = false;
            while (!canStart) {
                try {
                    questionsToDo = in.nextLong();
                    if (questionsToDo < questions.size()) {
                        canStart = true;
                    }
                } catch (Exception e) {
                    System.out.println("ERROR WITH INPUT");
                }
            }

            ArrayList<WrongReplies> wrongs = new ArrayList<>();
            Random rand = new Random();
            for (int j = 0; j < questionsToDo; j++) {
                int nextQuestion = rand.nextInt(questions.size());
                System.out.println("Question number: " + (j + 1) + " (#" + nextQuestion + ")");
                Question currentQuestion = questions.remove(nextQuestion);
                System.out.println(currentQuestion.getQuestion(false));
                boolean repValid = false;
                while (!repValid) {
                    String repString = in.nextLine();
                    String[] arrStrings = repString.split(" ");
                    if (arrStrings.length != currentQuestion.rightAnswers()) {
                        System.out.println("You need to choose " + currentQuestion.rightAnswers() + " answer(s)");
                    } else {
                        try {
                            HashSet<Integer> repliesGiven = new HashSet<>();
                            for (int k = 0; k < arrStrings.length; k++) {
                                repliesGiven.add(Integer.parseInt(arrStrings[k]));
                            }
                            if (!currentQuestion.isCorrect(repliesGiven)) {
                                wrongs.add(new WrongReplies(currentQuestion, repliesGiven));
                            }
                            repValid = true;
                        } catch (Exception e) {
                            System.out.println("Invalid replies. Choose number(s)");
                        }
                    }
                }
            }

           
            System.out.println("\n**********\nQuiz finito\n***********");
            System.out.println("Risposte errate " + wrongs.size());

            for (int i = 0; i < wrongs.size(); i++) {
                System.out.println("Answers Given to following question:" + wrongs.get(i).getReplies());
                System.out.println(wrongs.get(i).getQuestion().getQuestion(true));
            }

        } else {
            System.out.println(retValue.getMessage());
        }

    }

}
