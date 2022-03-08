import java.rmi.*;

public interface ITableau extends Remote {
  public double moyenne(double[] x) throws RemoteException;

  public double min(Tableau t) throws RemoteException;

  public Personne plusJeune(Personne[] x) throws RemoteException;
}
