import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

public class mnistModelTest {

    public static void main(String[] args) throws Exception {
        String modelFile = "モデルパスを入力";
        int batchSize = 128; // batch size for each epoch
        int rngSeed = 123; // random number seed for reproducibility


        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelFile); // 作成したファイルを読み込み
        DataSetIterator dataset = new MnistDataSetIterator(batchSize, false, rngSeed);

        DataSet data = dataset.next().get(74);

        System.out.println("label "+Nd4j.argMax(data.getLabels()).getInt(0));

        System.out.println(data.getFeatures());

        INDArray output = model.output(data.getFeatures());
        int result = Nd4j.argMax(output).getInt(0);
        System.out.println("output "+result);
    }

}
