import java.net.CacheRequest;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Tableau extends UnicastRemoteObject implements ITableau {
    Personne[] personnes;
    CalculMoyenne calculMoyenne;
    Min calculMin;

    Tableau(int numberOfEls) throws RemoteException {
        personnes = new Personne[numberOfEls];
        for (int i = 0; i < numberOfEls; i++) {
            personnes[i] = new Personne(1 * i, 10.0 * Math.random());
        }

        calculMoyenne = new CalculMoyenne(this);
        calculMin = new Min(this);
    }

    @Override
    public double moyenne(double[] x) throws RemoteException {
        return calculMoyenne.call();

    }

    @Override
    public double min(Tableau t) throws RemoteException {
        return calculMin.call();
    }

    @Override
    public Personne plusJeune(Personne[] x) throws RemoteException {
        Personne plusJeune = personnes[0];
        for (Personne personne : personnes)
            if (personne.age < plusJeune.age)
                plusJeune = personne;

        return plusJeune;
    }

}
