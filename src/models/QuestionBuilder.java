/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.HashSet;
import utility.ReturnValue;

/**
 *
 * @author ac186112
 */
public class QuestionBuilder {

    private String question;
    private ArrayList<Reply> choices;
    private HashSet<Integer> correctAnswer = new HashSet<>();

    public QuestionBuilder(String question) {
        this.question = question;
        this.choices = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<Reply> getChoices() {
        return choices;
    }

    public HashSet<Integer> getCorrectAnswer() {
        return correctAnswer;
    }

    public QuestionBuilder addChoice(String desc, boolean correct) {
        Reply rep;
        if (correct) {
            rep = new ReplyCorrect(desc);
            correctAnswer.add(choices.size());
        } else {
            rep = new ReplyFalse(desc);
        }
        choices.add(rep);
        return this;
    }

    public ReturnValue<Question> build() {
        ReturnValue<Question> retValue = new ReturnValue<>();
        Question question = new Question(this);
        retValue.setObject(question);
        if (question.isValid()) {
            retValue.setSuccess(true);
        }
        return retValue;
    }

    public class Question {

        private String question;
        private ArrayList<Reply> choices;
        private HashSet<Integer> correctAnswer;

        private Question(QuestionBuilder builder) {
            this.question = builder.getQuestion();
            this.choices = builder.getChoices();
            this.correctAnswer = builder.getCorrectAnswer();
        }

        public String getQuestion(boolean showAnswer) {
            String retString = question + "\n";
            for (int i = 0; i < choices.size(); i++) {
                retString += i + ". " + choices.get(i).getDesc() + (showAnswer ? " (" + choices.get(i).isCorrect() + ")" : "") + "\n";
            }
            return retString;
        }

        public boolean isValid() {
            return (!question.isEmpty() && choices.size() >= 2 && correctAnswer.size() > 0);
        }

        public boolean isCorrect(HashSet<Integer> replies) {
            return replies.equals(this.correctAnswer);
        }
        
        public int rightAnswers() {
            return correctAnswer.size();
        }
    }

}
