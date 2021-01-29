import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;

public class MNISTDataCheck {
    static MNISTDataCheck instance = new MNISTDataCheck();
    public static MNISTDataCheck Instance(){
        return instance;
    }

    MultiLayerNetwork model;

    int batchSize = 128;
    int rngSeed = 123;
    public void Init(String modelPath){
        try {
            //モデルファイルの読み込み
            model = ModelSerializer.restoreMultiLayerNetwork(modelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int GetOutput(INDArray indArray){
        INDArray output = model.output(indArray);
        System.out.println(output);
        return  Nd4j.argMax(output).getInt(0);
    }
}
