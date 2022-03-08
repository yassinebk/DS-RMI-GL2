import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) {
        try {
            Personne[] personnes = new Personne[10];
            for (int i = 0; i < 10; i++) {
                personnes[i] = new Personne(1 * i, 10.0 * Math.random());
            }
            ITableau tableau = (ITableau) Naming.lookup("rmi//localhost:1099/Moyenne");
            double[] tableauTest = { 1, 2, 3, 4, 5, 6 };
            double result = (double) tableau.moyenne(tableauTest);
            Personne personnePlusJeune = (Personne) tableau.plusJeune(personnes);
            System.out.println(result);
            System.out.println(personnePlusJeune);


        } catch (MalformedURLException | RemoteException | NotBoundException exception) {
            exception.printStackTrace();
        }

    }

}
