package UserPackage;

public class User {
    private String id;
    private String password;
    private String question;
    private String answer;

    public User(String id, String password, String question, String answer) {
        this.id = id;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    public String getPassword() {
        return password;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return id + "," + password + "," + question + "," + answer;
    }
}