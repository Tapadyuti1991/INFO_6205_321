import java.util.ArrayList;

import static org.junit.Assert.*;

public class PopulationTest {

    @org.junit.Test
    public void deleteAllColony() {
        Population p = new Population(5);
        ArrayList<Gene> testGene = null;
        for(int i = 0 ; i < 50;i++) {
            p.addPerson(new Person(new Chromosome(testGene)));
        }

        p.deleteAllColony();
        //Checking the deleting Process in Population
        assertTrue(0 == p.size);

    }

    @org.junit.Test
    public void populationCount(){
        Population p = new Population(5);
        ArrayList<Gene> testGene = null;
        for(int i = 0 ; i < 50;i++) {
            p.addPerson(new Person(new Chromosome(testGene)));
        }
        //Checking For Population Count
        assertTrue(50== p.size);
    }

    @org.junit.Test
    public void ColonyCount(){
        Population p = new Population(5);
        ArrayList<Gene> testGene = null;
        for(int i = 0 ; i < 50;i++) {
            p.addPerson(new Person(new Chromosome(testGene)));
        }

        //Checking For Colony Count
        assertTrue(10== p.getColonyCount());


    }
    @org.junit.Test
    public void addPerson() {
        Population p = new Population(5);
        Colony firstColony = new Colony(5);
        ArrayList<Gene> testGene = null;
        for(int i = 0 ; i < 4; i++) {
            firstColony.addPerson(new Person(new Chromosome(testGene)));
        }
        assertTrue(4==4);
    }

    @org.junit.Test
    public void getColonyCount(){
        Colony c1 = new Colony(5);
        c1.nextColony = new Colony(5);
        Colony c2 = c1.nextColony;
        c2.nextColony = new Colony(5);
        Colony c3 = c2.nextColony ;
        c3.nextColony = new Colony(5);

        Population p = new Population(5);
        assertTrue(0 == p.getColonyCount());
        p.firstColony = c1;
        System.out.println(p.getColonyCount());
        assertTrue(4 == p.getColonyCount());




    }

}