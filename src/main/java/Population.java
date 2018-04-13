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
        for(Colony colony = firstColony;colony !=null; colony = colony.nextColony){
            Colony colonyCurrent = colony;
            for(Person p: colonyCurrent.getAllPerson()){
                p=null;
            }
            colonyCurrent =null;
        }
        this.firstColony = null;
        this.lastColony = null;
        this.size = 0;
    }
    public void addPerson(Person p){
        if (this.size == 0){
//            System.out.print(this.size +"Ini"+ " ");
            firstColony = new Colony(eachColonyMaximumSize);
            firstColony.addPerson(p);
            lastColony = firstColony;
            this.size++;
            return;
        }
        else if(this.size > 0) {
            Colony c = this.firstColony;
            while (c != null) {
//                System.out.print(this.size + "I"+ " ");
                if (c.size() < eachColonyMaximumSize) {
                    c.addPerson(p);
                    this.size++;
                    return;
                }
                if (c.nextColony == null && c.size() >= eachColonyMaximumSize) {
//                    System.out.print(this.size + "I2"+ " ");
                    Colony nextColony = new Colony(eachColonyMaximumSize);
                    c.nextColony = nextColony;
                    c = c.nextColony;
                }
                if (c.nextColony != null && c.size() >= eachColonyMaximumSize) {
//                    System.out.print(this.size + "I2"+ " ");
                    c = c.nextColony;
                }

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
