package redesocial;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class RedeSocial {

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
    
    public class Relation {
        public String type;
        public Date start;
        public Date end;
        public Integer afinity;
        public User user;
        public User relative;

        public Relation(String t, int a, User u, User r) {
            this.type = t;
            this.start = new Date();
            this.end = null;
            this.afinity = a;
            this.user = u;
            this.relative = r;
            u.relations.add(this);
            
            Observer observer = new Observer(u.subject, r);
        }
        
        public void endRelation() {
            this.end = new Date();
            List<Observer> observers = user.subject.getObservers();
            for (Observer observer : observers) {
                if (user == observer.user) {
                    observer.dettach();
                }
            }
        }
    }

    abstract class Post {
        public String text;
        public Post post;
    }

    public class Thought extends Post {
        public Thought(User u, String t) {
            this.text = t;
            this.post = null;
            u.subject.post(this);
        }
    }

    public class Comment extends Post {        
        public Comment(User u, String t, Thought th) {
            this.text = t;
            this.post = th;
            u.subject.post(this);
        }
    }

    public class Like extends Post {
        public Like(User u, Post p) {
            this.post = p;
            this.text = null;
            u.subject.post(this);
        }
    }

    public class Dislike extends Post {
        public Dislike(User u, Post p) {
            this.post = p;
            this.text = null;
            u.subject.post(this);
        }
    }

    public class User {
        public String name;
        public String login;
        public Boolean group; //Person if false
        public Date created_at;
        public List<Post> feed;
        public List<Relation> relations;
        public Subject subject;
        
        public User(String n, String l, Boolean g) {
            name = n;
            login = l;
            group = g;
            created_at = new Date();
            feed = new ArrayList<>();
            relations = new ArrayList<>();
            subject = new Subject(this);
        }
        
        public void attachFeed(Post p) {
            feed.add(p);
        }
        
        public void printFeed() {
            for (Post post : this.feed) {
                if (post instanceof Thought) {
                    System.out.println("Thought: " + post.text);
                }
                else if (post instanceof Comment) {
                    System.out.println("Comment: " + post.text);
                }
                else if (post instanceof Like) {
                    System.out.println("Like: " + post.post.text);
                }
                else if (post instanceof Dislike) {
                    System.out.println("Dislike: " + post.post.text);
                }
            }
        }
    }

    public static void main (String[] args) {
        try {
            RedeSocial obj = new RedeSocial();
            obj.run (args);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public void run(String[] args) {
        User g1 = new User("Mulheres", "women", true);
        User g2 = new User("Mezzo-sopranos", "mezzo", true);
        
        User p1 = new User("Marilia", "masp", false);
        User p2 = new User("Monica", "moso", false);
        User p3 = new User("Igor", "zarnis", false);
        
        Relation r11 = new Relation("Friend", 3, p1, p2);
//        Relation r12 = new Relation("Friend", 5, p1, p3);
//        Relation r13 = new Relation("In a Relationship", 5, p1, p3);
//        Relation r14 = new Relation("Member", 3, p1, g1);
//        Relation g41 = new Relation("Group", 3, g1, p1);
//        
//        Relation r21 = new Relation("Friend", 4, p2, p3);
//        Relation r22 = new Relation("Member", 3, p2, g1);
//        Relation g22 = new Relation("Group", 3, g1, p2);
//        Relation r23 = new Relation("Member", 3, p2, g2);
//        Relation g32 = new Relation("Group", 3, g2, p2);
//        
//        Relation r31 = new Relation("Family", 2, p3, p1);
//        Relation r32 = new Relation("Acquaintance", 1, p3, p2);
        
        Thought t11 = new Thought(p1, "Oi, Janete!");
        Comment c21 = new Comment(p2, "Oi!!!", t11);
        Like l22 = new Like(p2, t11);
        Thought t23 = new Thought(p2, "Indo pra casa");
        Thought t12 = new Thought(g1, "Artigo");
        Dislike l23 = new Dislike(p2, t12);
//        r22.endRelation();
//        g22.endRelation();
        
        p3.printFeed();
        
    }
    
}
