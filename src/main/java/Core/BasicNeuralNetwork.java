package Core;

/**
 * Created by Rahal on 12/7/2016.
 */
public class BasicNeuralNetwork {

    private int inputCount;
    private int hiddenCount;
    private int outputCount;
    private double learnRate;
    private double momentum;
    private double globalError;
    private double outputsOfLevels[];   //instead for fire
    private double weightMatrix[];
    private double error[]; //errors from the last calculations
    private double errorDelta[];
    private int neuronCount; //Total number of neurons in the network
    private int weightsCount; //Total number of weights in the network
    private double accMatrixDelta[];   //accumulates matrix data for training
    private double matrixDelta[];   //changes which should be applied to the weights matrix
    private double accThresholdDelta[]; //accumulation of threshold deltas
    private double thresholdDelta[];  //the thresold deltas
    private double thresholds[];


    public BasicNeuralNetwork(int inputCount,
                              int hiddenCount,
                              int outputCount,
                              double learnRate,
                              double momentum) {

        this.inputCount = inputCount;
        this.hiddenCount = hiddenCount;
        this.outputCount = outputCount;
        this.learnRate = learnRate;
        this.momentum = momentum;

        neuronCount = inputCount + outputCount + hiddenCount;               //calculating the required total number of neurons
        weightsCount = (inputCount * hiddenCount) + (hiddenCount * outputCount);  //calculating the required total number of weights

        outputsOfLevels = new double[neuronCount];
        weightMatrix = new double[neuronCount];

        matrixDelta = new double[weightsCount];
        thresholds = new double[neuronCount];
        errorDelta = new double[neuronCount];
        error = new double[neuronCount];
        accThresholdDelta = new double[neuronCount];
        accMatrixDelta = new double[weightsCount];
        thresholdDelta = new double[neuronCount];

        reset();
    }

    //reset the weights matrix and threshold
    public void reset() {


        for (int i = 0; i < neuronCount; i++) {
            thresholds[i] = 0.5 - Math.random();
            thresholdDelta[i] = 0;
        }

        for (int i = 0; i < weightMatrix.length; i++) {
            weightMatrix[i] = 0.5 - Math.random();
            matrixDelta[i] = 0;
            accMatrixDelta[i] = 0;
        }
    }

    //calculate the error

    public void calculateError(double ideal[]) {
        int i, j;
        final int hiddenIndex = inputCount;
        final int outputIndex = inputCount + hiddenCount;

        // clear hidden layer errors
        for (i = inputCount; i < neuronCount; i++) {
            error[i] = 0;
        }

        // layer errors and deltas for output layer
        for (i = outputIndex; i < neuronCount; i++) {
            error[i] = ideal[i - outputIndex] - outputsOfLevels[i];
            globalError += error[i] * error[i];
            errorDelta[i] = error[i] * outputsOfLevels[i] * (1 - outputsOfLevels[i]);
        }

        // hidden layer errors
        int winx = inputCount * hiddenCount;

        for (i = outputIndex; i < neuronCount; i++) {
            for (j = hiddenIndex; j < outputIndex; j++) {
                accMatrixDelta[winx] += errorDelta[i] * outputsOfLevels[j];
                error[j] += weightMatrix[winx] * errorDelta[i];
                winx++;
            }
            accThresholdDelta[i] += errorDelta[i];
        }

        // hidden layer deltas
        for (i = hiddenIndex; i < outputIndex; i++) {
            errorDelta[i] = error[i] * outputsOfLevels[i] * (1 - outputsOfLevels[i]);
        }

        // input layer errors
        winx = 0; // offset into weight array
        for (i = hiddenIndex; i < outputIndex; i++) {
            for (j = 0; j < hiddenIndex; j++) {
                accMatrixDelta[winx] += errorDelta[i] * outputsOfLevels[j];
                error[j] += weightMatrix[winx] * errorDelta[i];
                winx++;
            }
            accThresholdDelta[i] += errorDelta[i];
        }
    }

    public double threshold(double sum) {
        return 1.0 / (1 + Math.exp(-1.0 * sum));
    }


    public double[] computeOutPuts(double input[]) {
        //input size should be equal to the number of inputs
        int i, j;
        final int hiddenIndex = inputCount;
        final int outIndex = inputCount + hiddenCount;

        for (i = 0; i < inputCount; i++) {
            outputsOfLevels[i] = input[i];
        }

        // first layer
        int inx = 0;

        for (i = hiddenIndex; i < outIndex; i++) {
            double sum = thresholds[i];

            for (j = 0; j < inputCount; j++) {
                sum += outputsOfLevels[j] * weightMatrix[inx++];
            }
            outputsOfLevels[i] = threshold(sum);
        }

        // hidden layer

        double result[] = new double[outputCount];

        for (i = outIndex; i < neuronCount; i++) {
            double sum = thresholds[i];

            for (j = hiddenIndex; j < outIndex; j++) {
                sum += outputsOfLevels[j] * weightMatrix[inx++];
            }
            outputsOfLevels[i] = threshold(sum);
            result[i - outIndex] = outputsOfLevels[i];
        }

        return result;


    }

    public void learn() {
        int i;

        // process the matrix
        for (i = 0; i < weightMatrix.length; i++) {
            matrixDelta[i] = (learnRate * accMatrixDelta[i]) + (momentum * matrixDelta[i]);
            weightMatrix[i] += matrixDelta[i];
            accMatrixDelta[i] = 0;
        }

        // process the thresholds
        for (i = inputCount; i < neuronCount; i++) {
            thresholdDelta[i] = learnRate * accThresholdDelta[i] + (momentum * thresholdDelta[i]);
            thresholds[i] += thresholdDelta[i];
            accThresholdDelta[i] = 0;
        }
    }


    public double getError(int len) {
        double err = Math.sqrt(globalError / (len * outputCount));
        globalError = 0; // clear the accumulator
        return err;
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
        return momentum;
    }

    public void setMomentun(double momentun) {
        this.momentum = momentun;
    }
}
