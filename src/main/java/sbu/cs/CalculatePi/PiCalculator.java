package sbu.cs.CalculatePi;

import java.awt.geom.QuadCurve2D;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {
    static class Part implements Runnable {
        public int part;
        public int FloatingPoint;
        public static BigDecimal Pi = new BigDecimal("0");
        static MathContext roundedMc;
        static MathContext mc = new MathContext(1010);
        public Part(int part, int floatingPoint){
            this.part = part;
            this.FloatingPoint = floatingPoint;
            roundedMc = new MathContext(floatingPoint+1, RoundingMode.FLOOR);
        }


        public static synchronized void addToPi(BigDecimal num) {

            Pi = Pi.add(num,mc);
        }

        @Override
        public void run() {
            BigDecimal sum1 = new BigDecimal("1");
            BigDecimal sum2 = new BigDecimal("1");
            BigDecimal sum3 = new BigDecimal("1");

            BigDecimal one = new BigDecimal("1");
            BigDecimal two = new BigDecimal("2");
            BigDecimal T = new BigDecimal("1");
            int loop=16000;

            if (part == 1) {
                for (int i = 0; i < loop/4; i++) {
                    sum1 = new BigDecimal("1");
                    sum2 = new BigDecimal("1");
                    sum3 = new BigDecimal("1");
                    T = new BigDecimal("1");

                    for (int j=1 ; j<=i; j++){
                        sum1 = sum1.multiply(new BigDecimal(j),mc);
                    }
                    sum1 = sum1.pow(2);
                    sum2 = two.pow(i+1);

                    sum3 = two.multiply(new BigDecimal(i),mc).add(one,mc);
                    for (int j=1; j<=sum3.intValue() ; j++ ){
                        T = T.multiply(new BigDecimal(j),mc);
                    }
                    addToPi(sum1.multiply(sum2,mc).divide(T,mc));
                }
            }
            if (part == 2) {
                for (int i = loop/4 ;i <loop/2; i++) {
                    for (int j=1 ; j<=i; j++){
                        sum1 = sum1.multiply(new BigDecimal(j),mc);
                    }
                    sum2 = two.pow(i+1);
                    sum3 = two.multiply(new BigDecimal(i),mc).add(one,mc);
                    for (int j=1; j<=sum3.intValue() ; j++ ){
                        T = T.multiply(new BigDecimal(j),mc);
                    }
                    addToPi(sum1.multiply(sum2,mc).divide(T,mc));
                }
            }
            if (part == 3) {
                for (int i = loop/2; i < loop*3/4; i++) {
                    for (int j=1 ; j<=i; j++){
                        sum1 = sum1.multiply(new BigDecimal(j),mc);
                    }
                    sum2 = two.pow(i+1);
                    sum3 = two.multiply(new BigDecimal(i),mc).add(one,mc);
                    for (int j=1; j<=sum3.intValue() ; j++ ){
                        T = T.multiply(new BigDecimal(j),mc);
                    }
                    addToPi(sum1.multiply(sum2,mc).divide(T,mc));
                }
            }
            if (part == 4) {
                for (int i = loop*3/4; i < loop; i++) {
                    for (int j=1 ; j<=i; j++){
                        sum1 = sum1.multiply(new BigDecimal(j),mc);
                    }
                    sum2 = two.pow(i+1);
                    sum3 = two.multiply(new BigDecimal(i),mc).add(one,mc);
                    for (int j=1; j<=sum3.intValue() ; j++ ){
                        T = T.multiply(new BigDecimal(j),mc);
                    }
                    addToPi(sum1.multiply(sum2,mc).divide(T,mc));
                }
            }
        }
    }

    public static String calculate(int floatingPoint) {
        ExecutorService pool = Executors.newFixedThreadPool(3);;

        Part part1 = new Part(1,floatingPoint);
        Thread task1 = new Thread(part1);
        pool.execute(task1);

        Part part2 = new Part(2,floatingPoint);
        Thread task2 = new Thread(part2);
        pool.execute(task2);

        Part part3 = new Part(3,floatingPoint);
        Thread task3 = new Thread(part3);
        pool.execute(task3);

        Part part4 = new Part(4,floatingPoint);
        Thread task4 = new Thread(part4);
        pool.execute(task4);

        pool.shutdown();

        try {
            boolean finished = pool.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Part.Pi.round(Part.roundedMc).toString();
    }

    public static void main(String[] args) {
        System.out.println(PiCalculator.calculate(9));
    }
}


