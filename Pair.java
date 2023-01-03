import java.util.Collection;

//This is the pair class to store the alphabet character, and it's probability.
public class Pair implements Comparable<Pair> {

    //Instance variables.
    private char value;
    private double prob;

    //Constructor.
    public Pair(char value, double prob) {
        this.value = value;
        this.prob = prob;
    }

    //Getters and setters.
    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    //Compare method to compare and arrange the alphabets in ascending order of their probability.
    public int compareTo(Pair p){
        return Double.compare(this.getProb(), p.getProb()); }

    @Override
    public String toString() {
        return "Pair{" +
                "value=" + value +
                ", prob=" + prob +
                '}';
    }
}
