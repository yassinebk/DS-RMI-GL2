
import java.util.concurrent.Callable;

public class Min implements Callable<Integer> {

    Tableau tab;

    Min(Tableau tab) {
        this.tab = tab;

    }

    public Integer call() {
        return min();
    }

    public Integer min() {
        Integer i = 0;
        int min = i;
        for (i = 1; i < tab.personnes.length; i++)
            if (tab.personnes[min].note < tab.personnes[i].note)
                min = i;

        return i;
    }

}