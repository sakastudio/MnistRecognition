

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class DrawArea  extends JComponent {

    //画像を描画
    private Image image;
    //Graphics2D objectを描画に使う
    private Graphics2D g2;
    //マウス座標
    private int currentX,currentY,oldX,oldY;
    public static final int PIXELSIZE= 28;
    public static final int DOUBLENUM= 10;

    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // マウスが押されたときにx,y座標を保存
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // マウスをドラッグするときの座標
                currentX = e.getX();
                currentY = e.getY();

                if (g2 != null) {
                    // g2コンテキストがnullじゃない場合は線を引く
                    g2.drawLine(oldX, oldY, currentX, currentY);
                    // 再描画
                    repaint();
                    // 現在の座標x、yを古いx、yとして保存
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            // 描画する画像を作成
            image = createImage(PIXELSIZE*DOUBLENUM, PIXELSIZE*DOUBLENUM);

            g2 = (Graphics2D) image.getGraphics();
            g2.setStroke(new BasicStroke(25, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            // アンチエイリアスを有効
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }

    public void clear() {
        g2.setPaint(Color.white);
        // 絵を消すために描画領域全体を白くする
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    public BufferedImage getG2(){
        return (BufferedImage)image;
    }
}
