package main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> resluts= new ArrayList<Integer>();
        DataVector dataVector=new DataVector();
        dataVector=dataVector.loadTrainGrayscale("scalegray/train");
        //dataVector=dataVector.loadTrainBinary("binary/xl");
        KNN knn = new KNN(dataVector);

        URL url = Main.class.getClassLoader().getResource("scalegray/test");
        //URL url = Main.class.getClassLoader().getResource("binary/test");
        File packageFiles = null;
        if (url != null) {
            packageFiles = new File(url.getPath());
        }

        File[] files = new File[0];
        if (packageFiles != null) {
            files = packageFiles.listFiles();
        }

        if (files != null) {
            for (File file:
                 files) {
                System.out.println(file.getName());
                int[][] ints=dataVector.imageTrainGrayscale(file);
                //int[][] ints = dataVector.imageTrainBit(file);
                int[] ints1 = dataVector.bitImageTrainVector(ints);
                //resluts.add(knn.classify(dataVector,ints1,12));
                resluts.add(knn.classifyWithUnevenNoise(dataVector,ints1,7));
                //resluts.add(knn.classifyWithUniformNoise(dataVector,ints1,12));
            }
        }
        float metric=0;
            for(int i =0;i<resluts.size();i++){
                if(i<20){
                    if(resluts.get(i)==1){
                        metric++;
                    }
                }
                else {
                    if(resluts.indexOf(i)==0){
                        metric++;
                    }
                }
            }

        System.out.println("Result is: "+(metric/resluts.size())*100+"%");

        }
    }


