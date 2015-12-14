/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redesocial;

import java.util.List;
import redesocial.RedeSocial.Post;
import redesocial.RedeSocial.Relation;
import redesocial.RedeSocial.User;

/**
 *
 * @author Kevin
 */
public class Observer {
    protected Subject subject;
    protected User user;
    public Observer(Subject s, User u){
        Boolean hasObserver = false;
        List<Observer> observers = s.getObservers();
        for (Observer observer : observers) {
            if (observer.user == u) {
                hasObserver = true;
            }
        }
        if (!hasObserver){
            this.subject = s;
            this.subject.attach(this);
            this.user = u;
        }
    }
    
    public void dettach() {
        subject.getObservers().remove(this);
    }

    public void update(Post p) {
        if (user.group) {
            for (Relation membership : user.relations) {
                if (membership.relative != user){
                    membership.relative.attachFeed(p);
                }
            }
        }
        else {
            user.attachFeed(p);
        }
    }
}