import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

public class Personne implements Serializable {
    int age;
    double note;

    Personne(int age, double d) {
        this.age = age;
        this.note = d;
    }

    @Override
    public String toString() {
        return "Age:" + age + " note:" + note;
    }

}
