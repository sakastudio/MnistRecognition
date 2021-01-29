
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SwingPaint {
    static String modelPath = "モデルパスを入力";
    JButton clearBtn, checkBtn;
    DrawArea drawArea;
    DrawResult drawResult;
    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearBtn) {
                drawArea.clear();
            }else if(e.getSource() == checkBtn){
                getNumber();
            }
        }
    };

    //各ピクセルを取得しINDArrayに変換
    void getNumber(){
        double[][] data = new double[1][DrawArea.PIXELSIZE*DrawArea.PIXELSIZE];
        for (int i = DrawArea.PIXELSIZE-1;i >= 0 ;i--){
            for (int j = DrawArea.PIXELSIZE-1;j >= 0;j--){

                int rgb = drawArea.getG2().getRGB(i*DrawArea.DOUBLENUM,j*DrawArea.DOUBLENUM);;
                if (rgb == -16777216){data[0][j*DrawArea.PIXELSIZE+i] = 0.9;
                }else {data[0][j*DrawArea.PIXELSIZE+i] = 0;}

            }
        }
        INDArray indArray = Nd4j.create(data);
        drawResult.ShowResult(MNISTDataCheck.Instance().GetOutput(indArray));
    }

    public static void main(String[] args) {
        MNISTDataCheck.Instance().Init(modelPath);
        new SwingPaint().show();
    }

    public void show() {
        JFrame frame = new JFrame("文字認識AI");
        Container content = frame.getContentPane();

        //描画エリアを作成
        content.setLayout(new BorderLayout());
        drawArea = new DrawArea();

        content.add(drawArea, BorderLayout.CENTER);

        //ボタンの配置
        JPanel controls = new JPanel();
        clearBtn = new JButton("クリア");
        clearBtn.addActionListener(actionListener);
        checkBtn = new JButton("チェック");
        checkBtn.addActionListener(actionListener);

        controls.add(clearBtn);
        controls.add(checkBtn);

        drawResult = new DrawResult();
        controls.add(drawResult,BorderLayout.SOUTH);
        drawResult.setSize(100,100);

        content.add(controls, BorderLayout.NORTH);

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
