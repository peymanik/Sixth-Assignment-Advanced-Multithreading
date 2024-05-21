package sbu.cs.CalculatePi;

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


        public static synchronized void addToPi(BigDecimal num){Pi = Pi.add(num,mc);}

        public void partCalculator(int start, int end){

            for (int i = start; i < end; i++) {
                BigDecimal n1 = new BigDecimal("1");
                BigDecimal n2 = new BigDecimal("1");
                BigDecimal n3 = new BigDecimal("1");
                BigDecimal n4 = new BigDecimal("1");
                BigDecimal n5 = new BigDecimal("1");
                BigDecimal n6 = new BigDecimal("1");
                BigDecimal n7 = new BigDecimal("1");
                BigDecimal minus = new BigDecimal("-1");
                BigDecimal number1 = new BigDecimal("545140134");
                BigDecimal number2 = new BigDecimal("13591409");
                BigDecimal number3 = new BigDecimal("640320");

                n1 = minus.pow(i);
                for (int j=1; j<=(6*i) ; j++)
                    n2= n2.multiply(new BigDecimal(j));
                n3= number1.multiply(new BigDecimal(i),mc).add(number2,mc);
                for (int j=1; j<=(3*i) ; j++)
                    n4= n4.multiply(new BigDecimal(j));
                for (int j=1; j<=i ; j++)
                    n5= n5.multiply(new BigDecimal(j));
                n5 = n5.pow(3);
                n6 = number3.pow(3*i);
                n7 = number3.pow(3).sqrt(mc);
                addToPi(n1.multiply(n2,mc).multiply(n3,mc).divide(n4,mc).divide(n5,mc).divide(n6,mc).divide(n7,mc).multiply(new BigDecimal(12,mc)));
            }
        }


        @Override
        public void run() {
            int loop=80;
            if (part == 1)
                partCalculator(0,loop/4);
            if (part == 2)
                partCalculator(loop/4, loop/2);
            if (part == 3)
                partCalculator(loop/2,loop*3/4);
            if (part == 4)
                partCalculator(loop*3/4,loop);
        }
    }
    public static String calculate(int floatingPoint) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Part.Pi = new BigDecimal("0");

        Part part1 = new Part(1,floatingPoint);
        Part part2 = new Part(2,floatingPoint);
        Part part3 = new Part(3,floatingPoint);
        Part part4 = new Part(4,floatingPoint);
        Thread task1 = new Thread(part1);
        Thread task2 = new Thread(part2);
        Thread task3 = new Thread(part3);
        Thread task4 = new Thread(part4);
        pool.execute(task1);
        pool.execute(task2);
        pool.execute(task3);
        pool.execute(task4);
        pool.shutdown();

        try {
            boolean finished = pool.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BigDecimal One = new BigDecimal("1");

        return One.divide(Part.Pi,Part.mc).round(Part.roundedMc).toString();
    }

    public static void main(String[] args) {
        System.out.println(PiCalculator.calculate(2));

    }
}


