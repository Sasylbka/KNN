package main;

import java.util.ArrayList;

public class KNN {

    private DataVector dataVector;

    public KNN(DataVector dataVector) {
        this.dataVector=dataVector;
    }

    /**
     * алгоритм
     *
     * @param dataVector дата сет
     * @param image      тестовое изображение
     */
    public int classify(DataVector dataVector, int image[],int K) {
        int[] tables = dataVector.getTables();//классы изображений
        int[][] vector = dataVector.getVector();//данные об изображении

        float[][] resMat = new float[vector.length][vector[0].length];
        //сбор данных
        for (int y = 0; y < resMat.length; ++y)
            for (int x = 0; x < resMat[y].length; ++x)
                resMat[y][x] = image[x];


        float[][] diffMat = squareOfEuclideanDistance(vector,resMat);
        //float[][] diffMat = euclideanDistance(vector,resMat);
        //float[][] diffMat = distance(vector,resMat);

        ArrayList<Point> pointArrayList = new ArrayList<>();

        //Векторная разность
        for (int y = 0; y < resMat.length; ++y) {
            int ans = 0;
            for (int x = 0; x < resMat[y].length; ++x)
                ans += diffMat[y][x];

            pointArrayList.add(new Point(tables[y], ans));
        }

        pointArrayList.sort((a,b)->(int)((a.distance*100)-(b.distance*100)));// сортируем все точки по близости

        int[] data = new int[100];

        int maxLabAns = 0;
        int maxLab = -1;
        for(int i=0  ; i < K ; ++i){
            ++data[pointArrayList.get(i).lab];
            if(maxLabAns<data[pointArrayList.get(i).lab]){
                maxLabAns = data[pointArrayList.get(i).lab];
                maxLab = pointArrayList.get(i).lab;
            }
        }
        System.out.println("class:"+maxLab);
        return maxLab;
    }


    public int classifyWithUniformNoise(DataVector dataVector, int image[],int K) {
        int[] tables = dataVector.getTables();//классы изображений
        int[][] vector = dataVector.getVector();//данные об изображении

        float[][] resMat = new float[vector.length][vector[0].length];
        //сбор данных
        int iterationOfNoise=0;
        for (int y = 0; y < resMat.length; ++y)
            for (int x = 0; x < resMat[y].length; ++x) {
                if(iterationOfNoise!=80){
                resMat[y][x] = image[x];
                }
                else{
                    resMat[y][x] = 0;
                    iterationOfNoise=0;
                }
                iterationOfNoise++;
            }


        float[][] diffMat = squareOfEuclideanDistance(vector,resMat);
        //float[][] diffMat = euclideanDistance(vector,resMat);
        //float[][] diffMat = distance(vector,resMat);

        ArrayList<Point> pointArrayList = new ArrayList<>();

        //Векторная разность
        for (int y = 0; y < resMat.length; ++y) {
            int ans = 0;
            for (int x = 0; x < resMat[y].length; ++x)
                ans += diffMat[y][x];

            pointArrayList.add(new Point(tables[y], ans));
        }

        pointArrayList.sort((a,b)->(int)((a.distance*100)-(b.distance*100)));// сортируем все точки по близости

        int[] data = new int[100];

        int maxLabAns = 0;
        int maxLab = -1;
        for(int i=0  ; i < K ; ++i){
            ++data[pointArrayList.get(i).lab];
            if(maxLabAns<data[pointArrayList.get(i).lab]){
                maxLabAns = data[pointArrayList.get(i).lab];
                maxLab = pointArrayList.get(i).lab;
            }
        }
        System.out.println("class:"+maxLab);
        return maxLab;
    }

    public int classifyWithUnevenNoise(DataVector dataVector, int image[],int K) {
        int[] tables = dataVector.getTables();//классы изображений
        int[][] vector = dataVector.getVector();//данные об изображении

        float[][] resMat = new float[vector.length][vector[0].length];
        //сбор данных

        for (int y = 0; y < resMat.length; ++y)
            for (int x = 0; x < resMat[y].length; ++x)
                resMat[y][x] = image[x];
        int randomCountOfNoise= (int) (Math.random()+10*resMat[0].length);
        for (int i = 0; i < randomCountOfNoise; ++i) {
            int randomY = (int) (Math.random() * resMat.length);
            int randomX = (int) (Math.random() * resMat[0].length);
            resMat[randomY][randomX] = 0;
        }


        //float[][] diffMat = squareOfEuclideanDistance(vector,resMat);
        //float[][] diffMat = euclideanDistance(vector,resMat);
        float[][] diffMat = distance(vector,resMat);

        ArrayList<Point> pointArrayList = new ArrayList<>();

        //Векторная разность
        for (int y = 0; y < resMat.length; ++y) {
            int ans = 0;
            for (int x = 0; x < resMat[y].length; ++x)
                ans += diffMat[y][x];

            pointArrayList.add(new Point(tables[y], ans));
        }

        pointArrayList.sort((a,b)->(int)((a.distance*100)-(b.distance*100)));// сортируем все точки по близости

        int[] data = new int[100];

        int maxLabAns = 0;
        int maxLab = -1;
        for(int i=0  ; i < K ; ++i){
            ++data[pointArrayList.get(i).lab];
            if(maxLabAns<data[pointArrayList.get(i).lab]){
                maxLabAns = data[pointArrayList.get(i).lab];
                maxLab = pointArrayList.get(i).lab;
            }
        }
        System.out.println("class:"+maxLab);
        return maxLab;
    }
    public float[][] squareOfEuclideanDistance(int[][] vector, float[][] resMat){
        // квадрат еклидового расстояния
        float diffMat[][] = new float[vector.length][vector[0].length];// растояния от входного расстояния до всех остальных изображений
        for (int y = 0; y < resMat.length; ++y)
            for (int x = 0; x < resMat[y].length; ++x)
                diffMat[y][x] =  (float) Math.pow(vector[y][x] - resMat[y][x], 2);
        return diffMat;
    }
    public float[][] euclideanDistance(int[][] vector, float[][] resMat){
        // еклидово расстояние
        float diffMat[][] = new float[vector.length][vector[0].length];// растояния от входного расстояния до всех остальных изображений
        for (int y = 0; y < resMat.length; ++y)
            for (int x = 0; x < resMat[y].length; ++x)
                diffMat[y][x] =  (float)Math.sqrt( Math.pow(vector[y][x] - resMat[y][x], 2));
        return diffMat;
    }
    public float[][] distance(int[][] vector, float[][] resMat){
        // расстояние
        float diffMat[][] = new float[vector.length][vector[0].length];// растояния от входного расстояния до всех остальных изображений
        for (int y = 0; y < resMat.length; ++y)
            for (int x = 0; x < resMat[y].length; ++x)
                diffMat[y][x] =  Math.abs(vector[y][x] - resMat[y][x]);
        return diffMat;
    }
}
