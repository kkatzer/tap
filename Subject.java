/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redesocial;

/**
 *
 * @author Kevin
 */
import java.util.ArrayList;
import java.util.List;
import redesocial.RedeSocial.Post;
import redesocial.RedeSocial.User;

public class Subject {
	
    private final List<Observer> observers = new ArrayList<>();
    private User user;
    
    public Subject(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
    public List<Observer> getObservers() {
        return observers;
    }

    public void attach(Observer observer){
        observers.add(observer);		
    }

    public void post(Post p){
        for (Observer observer : observers) {
            observer.update(p);
        }
        user.attachFeed(p);
    } 	
}
