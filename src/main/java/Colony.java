public class Colony {
    Person firstPerson;
    Person lastPerson;
    private int size;
    private int totalMaximumsize;
    Colony nextColony;

    Colony(int n){
        this.totalMaximumsize = n;
        this.size = 0;
        this.nextColony = null;
    }
    
    public int size(){return this.size;}

    public void deleteAllContentInColony(){
        this.firstPerson = null;
        this.lastPerson = null;
        this.nextColony = null;
        this.size = 0;
    }
    
    public Person getFirstPerson(){return firstPerson;}
    
    public boolean addPerson(Person newPerson){
        if(this.size < totalMaximumsize) {
//            System.out.print(" cB"+this.size);
            if(this.size == 0) {
                newPerson.nextPerson = null;
                newPerson.previousPerson = null;
                firstPerson = newPerson;
                lastPerson = newPerson;
                this.size++;
                return true;}
            newPerson.nextPerson = null;
            newPerson.previousPerson = lastPerson;
            lastPerson.nextPerson = newPerson;
            this.lastPerson = newPerson;
            this.size++;
//            System.out.print(" cA"+this.size);
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
//        System.out.println("Size of Bag Colony " + b.size());
        return b;
    }
    


}
