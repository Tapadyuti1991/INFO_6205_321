public class Population {
    Colony firstColony;
    Colony lastColony;

    int size;
    int eachColonyMaximumSize;


    public Population(int eachColonyMaximumSize){
        this.eachColonyMaximumSize = eachColonyMaximumSize;
        this.firstColony = null;
        this.size = 0;
    }

    public void deleteAllColony(){
        this.firstColony = null;
        this.lastColony = null;
    }
    public void addPerson(Person p){
        if (this.size == 0){
            firstColony = new Colony(eachColonyMaximumSize);
            firstColony.addPerson(p);
            lastColony = firstColony;
            size++;
            return;
        }

        Colony c = firstColony;
         while(c != null){
             if(c.size() < eachColonyMaximumSize){
                 c.addPerson(p);
                 size++;
                 return;
             }
             if(c.nextColony == null && c.size() >= eachColonyMaximumSize){
                 Colony nextColony = new Colony(eachColonyMaximumSize);
                 c.nextColony = nextColony;
                 c = c.nextColony;
             }
         }
    }

    public int getColonyCount(){
        int count = 0;
        Colony c = firstColony;
        while(c != null){
            count++;
            c = c.nextColony;
        }
        return count;
    }

    public Iterable<Colony> colonies() {
        Bag<Colony> b = new Bag<Colony>();
        Colony c = firstColony;
        while(c!=null) {
           b.add(c);
           c = c.nextColony;
       }
        return b;
    }



}
