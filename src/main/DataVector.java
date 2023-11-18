package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DataVector {
    private int[] tables;
    private int[][] vector;

    public int[] getTables() {
        return tables;
    }

    public void setTables(int[] tables) {
        this.tables = tables;
    }

    public int[][] getVector() {
        return vector;
    }

    public void setVector(int[][] vector) {
        this.vector = vector;
    }

    /**
     * загрузка обучающей выборки
     *
     * @param trainPath
     */
    public DataVector loadTrainBinary(String trainPath) {
        //получение пути
        URL url = this.getClass().getClassLoader().getResource(trainPath);
        File file = new File(url.getPath());
        File[] files = file.listFiles();

        //хранение тегов
        int tab[] = new int[files.length];
        //хранение данных
        int imageData[][] = new int[files.length][25 * 25];
        for (int i = 0; i < files.length; ++i) {
            File file1 = files[i];
            String name = file1.getName();
            //тип класса
            String tabName = name.split("-")[0];
            //System.out.println(name);
            int[][] ints = imageTrainBit(new File(file + "/" + name));
            int[] ints1 = bitImageTrainVector(ints);
            imageData[i] = ints1;
            tab[i] = Integer.parseInt(tabName);
            //System.out.println(Arrays.toString(imageData[i]));
        }

        //загрузка данных
        DataVector dataVector = new DataVector();
        dataVector.setVector(imageData);
        dataVector.setTables(tab);
        return dataVector;
    }

    public DataVector loadTrainGrayscale(String trainPath) {
        String arrayOfClasses[]=new String[]{"horse",
                "airplane"};
        //получение пути
        URL url = this.getClass().getClassLoader().getResource(trainPath);
        File file = new File(url.getPath());
        File[] files = file.listFiles();
        //хранение тегов
        int tab[] = new int[files.length];
        //хранение данных
        int imageData[][] = new int[files.length][32 * 32];
        for (int i = 0; i < files.length; ++i) {
            File file1 = files[i];
            String name = file1.getName();
            //тип класса
            String tabName = name.split("_")[0];
            for (int j=0;j<arrayOfClasses.length;++j){
                if(arrayOfClasses[j].equals(tabName)){
                    tab[i]=j;
                }
            }
            int[][] ints = imageTrainGrayscale(new File(file + "/" + name));
            int[] ints1 = bitImageTrainVector(ints);
            imageData[i] = ints1;
        }

        //загрузка данных
        DataVector dataVector = new DataVector();
        dataVector.setVector(imageData);
        dataVector.setTables(tab);
        return dataVector;
    }

    /**
     * обучение бинаризированных изображений
     *
     * @param file
     */
    public int[][] imageTrainBit(File file) {
        try {

            BufferedImage read = ImageIO.read(file);

            int width = read.getWidth();
            int height = read.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(read, 0, 0, null);
            graphics.dispose();


            //двоичный массив для хранения пикчи
            int data[][] = new int[height][width];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    int gray = new Color(bufferedImage.getRGB(x, y)).getRed();
                    data[y][x] = gray != 0 ? 1 : 0;
                }
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * обучение изображений в оттенках серого
     *
     * @param file
     */
    public int[][] imageTrainGrayscale(File file) {
        try {

            BufferedImage read = ImageIO.read(file);

            int width = read.getWidth();
            int height = read.getHeight();
            BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(read, 0, 0, null);
            graphics.dispose();


            //двоичный массив для хранения пикчи
            int data[][] = new int[height][width];
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    int gray = new Color(bufferedImage.getRGB(x, y)).getRed();
                    data[y][x] = gray;
                }
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * преобразование бинаризированного массива в вектор признаков
     *
     * @return
     */
    public int[] bitImageTrainVector(int imageBit[][]) {
        int data[] = new int[imageBit.length * imageBit[0].length];
        for (int y = 0; y < imageBit.length; ++y) {
            for (int x = 0; x < imageBit[y].length; ++x) {
                data[imageBit[y].length * y + x] = imageBit[y][x];
            }
        }
        return data;
    }
}
