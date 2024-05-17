package it.unibo.risiko;

public interface TripleDice {

    /**
     * This method is used to get the number of valid throws,
     * even though the number of actual throws is always 3.
     * 
     * @return a number from 1 to 3 that represents the
     * number of valid throws.
     */
    public int getNumberOfThrows();

    /**
     * This method is used to get the array of throws,
     * but not always all the throws are valid.
     * 
     * @return an array of lenght 3 containing the values
     * of the 3 dice throws.
     */
    public int[] getResults();

}
