import java.util.concurrent.Callable;

public class CalculMoyenne implements Callable<Double> {

    Tableau tab;

    CalculMoyenne(Tableau tab) {
        this.tab = tab;
    }

    public Double call() {
        return moyenne();
    }

    public Double moyenne() {
        double sum = 0;
        for (Personne personne : tab.personnes)
            sum += personne.note;
        return sum / tab.personnes.length;
    }

}