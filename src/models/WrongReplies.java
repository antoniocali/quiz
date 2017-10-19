/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Arrays;
import java.util.HashSet;
import models.QuestionBuilder.Question;

/**
 *
 * @author ac186112
 */
public class WrongReplies {
    
    private Question question;
    private HashSet<Integer> replies;
    
    public WrongReplies(Question question, HashSet<Integer> replies) {
        this.question = question;
        this.replies = replies;
    }
    
    public Question getQuestion() {
        return question;
    }
    
    public String getReplies() {
        return Arrays.toString(replies.toArray());
    }
    
}
