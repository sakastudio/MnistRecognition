
import org.nd4j.linalg.api.ndarray.INDArray;

import javax.swing.*;

public class DrawResult extends JLabel {
    public void ShowResult(int indArray){
        super.setText(indArray+"");
    }
}
