import java.util.Date;
import java.util.List;

public class redeSocial
{
	public class Relation {
		String type;
		Date start;
		Date end;
		Integer afinity;

		public Relation(String t, int a) {
			this.type = t;
			this.start = new Date();
			this.end = null;
			this.afinity = a;
		}
	}

	abstract class Post { }

	public class Thought extends Post {
		String text;
	}

	public class Comment extends Post {
		String text;
		Thought thought;
	}

	public class Like extends Post {
		Post post;
	}

	public class Dislike extends Post {
		Post post;
	}

	abstract class User {
		String name;
		String login;
		Date created_at;
		List<Post> feed;
	}

	public class Group extends User {
		List<Person> members;
	}

	public class Person extends User {
		List<Relation> relations;
		List<Group> groups;
	}

	public static void main(String[] args) {
		System.out.println("Init!");
	}
}