package Core;

/**
 * Created by Rahal on 12/7/2016.
 */
public class BasicNeuralNetwork {

    private int inputCount;
    private int hiddenCount;
    private int outputCount;
    private double learnRate;
    private double momentun;
    private double globalError;
    private double ooutputsOfLevels[];
    private double weightMatrix[];
    private double errors[]; //errors from the last calculations

    public BasicNeuralNetwork(int inputCount,
                              int hiddenCount,
                              int outputCount,
                              double learnRate,
                              double momentum) {

        this.inputCount = inputCount;
        this.hiddenCount = hiddenCount;
        this.outputCount = outputCount;
        this.learnRate = learnRate;
        this.momentun = momentum;


    }

    public double[] computeOutPuts(double input[]) {
        //input size should be equal to the number of inputs

        return input;
    }

    public void calcualteError(double ideal[]) {

    }

    public double getError(int len) {

        return 0.00;
    }

    public int getInputCount() {
        return inputCount;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    public int getHiddenCount() {
        return hiddenCount;
    }

    public void setHiddenCount(int hiddenCount) {
        this.hiddenCount = hiddenCount;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(int outputCount) {
        this.outputCount = outputCount;
    }

    public double getLearnRate() {
        return learnRate;
    }

    public void setLearnRate(double learnRate) {
        this.learnRate = learnRate;
    }

    public double getMomentun() {
        return momentun;
    }

    public void setMomentun(double momentun) {
        this.momentun = momentun;
    }
}
