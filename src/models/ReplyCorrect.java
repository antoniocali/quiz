/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ac186112
 */
public class ReplyCorrect implements Reply {

    private String desc;

    public ReplyCorrect(String desc) {
        this.desc = desc;
    }
    
    public String getDesc() {
        return desc;
    }

    public boolean isCorrect() {
        return true;
    }
    
}
