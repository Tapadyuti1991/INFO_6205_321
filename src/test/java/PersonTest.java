import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void getTotalTimeSpent() {
        ArrayList<Gene> testGene = new ArrayList<Gene>();

        testGene.add(new Gene("AGTC","Test Sample",100.0,10.0));
        testGene.add(new Gene("AGTC","Test Sample",100.0,10.0));
        testGene.add(new Gene("AGTC","Test Sample",100.0,10.0));
        Person p = new Person(new Chromosome(testGene));
        System.out.println(p.getTotalTimeSpent());
        assertTrue(300.0 == p.getTotalTimeSpent());

    }

    @Test
    public void getfitnessScore() {
        ArrayList<Gene> testGene = new ArrayList<Gene>();

        testGene.add(new Gene("AGTC","Test Sample",100.0,10.0));
        testGene.add(new Gene("AGTC","Test Sample",100.0,10.0));
        testGene.add(new Gene("AGTC","Test Sample",100.0,10.0));
        Person p = new Person(new Chromosome(testGene));
        assertTrue(p.getTotalTimeSpent()/960==300.0/960);


    }

    @Test
    public void compareTo() {
    }
}