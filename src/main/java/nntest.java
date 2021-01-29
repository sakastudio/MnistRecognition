import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.model.stats.StatsListener;
import org.deeplearning4j.ui.model.storage.InMemoryStatsStorage;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.shade.protobuf.GeneratedMessageV3;
import org.nd4j.shade.protobuf.compiler.PluginProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class nntest {

    public static void main(String[] args) throws Exception {
        //number of rows and columns in the input pictures
        final int numRows = 28;
        final int numColumns = 28;
        int outputNum = 10; // number of output classes
        int batchSize = 128; // batch size for each epoch
        int rngSeed = 123; // random number seed for reproducibility
        int numEpochs = 15; // number of epochs to perform
        String modelPath = "モデルパスを入力";

        //Get the DataSetIterators:
        DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
        DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, false, rngSeed);


        System.out.println("Build model....");
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(rngSeed) //include a random seed for reproducibility
            // use stochastic gradient descent as an optimization algorithm
            .updater(new Nesterovs(0.006, 0.9))
            .l2(1e-4)
            .list()
            .layer(new DenseLayer.Builder() //create the first, input layer with xavier initialization
                .nIn(numRows * numColumns)
                .nOut(1000)
                .activation(Activation.RELU)
                .weightInit(WeightInit.XAVIER)
                .build())
            .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD) //create hidden layer
                .nIn(1000)
                .nOut(outputNum)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .build();


        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        //print the score with every 1 iteration
        UIServer uiServer = UIServer.getInstance();
        InMemoryStatsStorage statsStorage = new InMemoryStatsStorage();
        uiServer.attach(statsStorage);
        model.setListeners(new StatsListener(statsStorage),new ScoreIterationListener(1));

        System.out.println("Train model....");
        model.fit(mnistTrain, numEpochs);




        System.out.println("Evaluate model....");
        Evaluation eval = model.evaluate(mnistTest);
        System.out.println(eval.stats());
        ModelSerializer.writeModel(model, new File(modelPath), false);
        System.out.println("****************Example finished********************");

    }
}
