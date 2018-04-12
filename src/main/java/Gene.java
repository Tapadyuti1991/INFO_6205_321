public class Gene {

    public String DNA;
    public String activity;
    public Double reward;
    public Double timeTaken;
    public Gene nextGene;


    Gene(String dna,String activity,Double timeTaken,Double reward ){
//        System.out.println("Inside Gene Constructor"+ timeTaken + reward);
        this.DNA = dna;
        this.timeTaken = timeTaken;
        this.reward = reward;

    }


//    private class DNA<Key,Value>
//    { // linked-list node
//        int base;
//        DNA next;
//
//        public DNA(int base,DNA next)
//        {
//            this.base = base;
//            this.next = next;
//        }
//        String a = "abc";
//
//    }


}
