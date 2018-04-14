import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class AllTest {

    //COLONY TESTING *********************
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

    //*** population Testing ****************************************
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
    public void PopulationaddPerson() {
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
    //** Person Testing ****
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




}
