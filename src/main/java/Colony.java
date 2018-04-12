public class Colony {
    Person firstPerson;
    Person lastPerson;
    private int size;
    private int totalMaximumsize;
    Colony nextColony;

    Colony(int n){
        this.totalMaximumsize = n;
        this.size = 0;
    }
    
    public int size(){return size;}

    public void deleteAllContentInColony(){
        this.firstPerson = null;
        this.lastPerson = null;
        this.nextColony = null;
        this.size = 0;
    }
    
    public Person getFirstPerson(){return firstPerson;}
    
    public boolean addPerson(Person newPerson){
        if(size < totalMaximumsize) {

            if(size == 0) {firstPerson = newPerson;lastPerson = newPerson;size++;return true;}

            newPerson.previousPerson = lastPerson;
            lastPerson.nextPerson = newPerson;
            this.lastPerson = newPerson;
            this.size++;
            return true;
        }
        return false;
        
    }

    public Iterable<Person> getAllPerson() {
        Bag<Person> b = new Bag<Person>();
        Person c = firstPerson;
        while(c!=null) {
            b.add(c);
            c = c.nextPerson;
        }
        return b;
    }
    


}
