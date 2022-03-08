import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(1099);
            Tableau Moyenne = new Tableau(10);
            Naming.rebind("rmi//localhost:1099/Moyenne", Moyenne);
            System.out.println("Server is running");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());

        }

    }

}
