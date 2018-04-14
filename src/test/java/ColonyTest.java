import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ColonyTest {

    @Test
    public void deleteAllContentInColony() {
        Population p = new Population(5);
        ArrayList<Gene> testGene = null;
        for(int i = 0 ; i < 50;i++) {
            p.addPerson(new Person(new Chromosome(testGene)));
        }
        assertTrue(5==p.firstColony.size());
        assertFalse(0==p.firstColony.size());
        p.firstColony.deleteAllContentInColony();
        assertTrue(0==p.firstColony.size());
    }

    @Test
    public void addPerson() {
        Population p = new Population(5);
        ArrayList<Gene> testGene = null;
        for(int i = 0 ; i < 50;i++) {
            p.addPerson(new Person(new Chromosome(testGene)));
        }

        assertTrue(5==p.firstColony.size());
        assertTrue(5==p.firstColony.nextColony.size());





    }
}