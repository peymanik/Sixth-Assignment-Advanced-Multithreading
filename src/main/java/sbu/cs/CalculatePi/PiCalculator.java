package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
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
            roundedMc= new MathContext(floatingPoint+1);
        }


        public static synchronized void addToPi(BigDecimal num) {
            Pi = Pi.add(num);
        }

        @Override
        public void run() {
            BigDecimal sum1 = new BigDecimal("0");
            BigDecimal sum2 = new BigDecimal("0");
            BigDecimal sum3 = new BigDecimal("0");
            BigDecimal sum4 = new BigDecimal("0");

            BigDecimal one = new BigDecimal("1");
            BigDecimal denominator = new BigDecimal("3");
            BigDecimal eight = new BigDecimal("8");
            BigDecimal minus = new BigDecimal("-1");

            if (part == 1) {
                for (int i = 0; i < Math.pow(2,15); i++) {
                    sum1 = sum1.add(one.divide(denominator, mc),mc);
                    denominator = denominator.add(eight);
                }
                addToPi(sum1.multiply(minus));
            }
            if (part == 2) {
                denominator = new BigDecimal("5");
                for (int i = 0; i < Math.pow(2,15); i++) {
                    sum2 = sum2.add(one.divide(denominator, mc), mc);
                    denominator = denominator.add(eight);
                }
                addToPi(sum2);
            }
            if (part == 3) {
                denominator = new BigDecimal("7");
                for (int i = 0; i < Math.pow(2,15); i++) {
                    sum3 = sum3.add(one.divide(denominator, mc), mc);
                    denominator = denominator.add(eight);
                }
                addToPi(sum3.multiply(minus));

            }
            if (part == 4) {
                denominator = new BigDecimal("9");
                for (int i = 0; i < Math.pow(2,15); i++) {
                    sum4 = sum4.add(one.divide(denominator, mc), mc);
                    denominator = denominator.add(eight);
                }
                addToPi(sum4);
            }

        }
    }

    public static String calculate(int floatingPoint) {
        ExecutorService pool = Executors.newFixedThreadPool(4);;

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

        BigDecimal one = new BigDecimal("1");
        BigDecimal four = new BigDecimal("4");
        Part.Pi = Part.Pi.add(one).multiply(four,Part.mc);
        return Part.Pi.round(Part.roundedMc).toString();
    }

    public static void main(String[] args) {
        System.out.println(PiCalculator.calculate(8));
    }
}


