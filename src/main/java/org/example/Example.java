package org.example;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example {


    public static void example() throws InterruptedException {
        System.out.println("========================");
        System.out.println("------------------------");
        System.out.println("Obliczenia przykladowe:");
        System.out.println("------------------------");
        System.out.println();
        //Tworzymy nowego Thread Poola z 4 watkami.
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        //Do tej tablicy watki beda zapisywac wyniki
        final long [] result = new long[20];

        for(int i=1;i<=20;i++)
        {
            //Tworzymy kopie zmiennej "i", nadajac jej wlasciwosc "final". W Javie wartosci przekazane do Thread Poola nie moga sie zmienic po wywolaniu.
            //Uzycie keyworda "final" nie jest konieczne, ale jest dobra praktyka - aby upewnic sie, ze wartosc nie zostanie zmieniona po przekazani do Thread Poola
            final int num = i;

            //Wywolujemy metode "execute", przekazujac jej funkcje lambda ktora wykonuje to co chcemy zrobic. W tym wypadku obliczenie i^i.
            //W funkcji ktora Thread Pool otrzymal do wykonania nie powinnismy uzywac semaforow, moniorow itp. Zaklociloby to dzialanie Thread Poola
            threadPool.execute(()->{
                System.out.println("Watek "+num+" rozpoczal prace!");
                long power = 1;
                for(int j=0;j<num;j++)
                {
                    power = power * num;
                }
                result[num-1] = power;
                System.out.println("Watek "+num+" zakonczyl prace!");
            });
        }

        //ThreadPool otrzymal wszystkie zadania, za pomoca tej metody zamykamy kolejke. Thread Pool zakonczy wszystkie watki, gdy szkoncza one prace i kolejka bedzie pusta
        threadPool.shutdown();

        //Teraz oczekujemy az Thread Pool zakonczy prace za pomoca metody ,,awaitTermination", aby moc wykorzystac wyniki pracy w dalszych obliczeniach.
        //Metodzie tej podajemy maksymalny czas oczekiwania, na wypadek gdyby ktorys z watkow np. zablokowal sie.
        threadPool.awaitTermination(2, TimeUnit.SECONDS);

        //Wystietlamy wyniki. W metodzie map-reduce tablica "posluzyla by jako dane dla kolejnego Thread Poola.
        System.out.println();
        System.out.println("Wyniki obliczen:");
        for(int i=1;i<=20;i++)
        {
            System.out.println(i+"^"+i+" = "+result[i-1]);
        }
        System.out.println();
        System.out.println("========================");
        System.out.println();
    }
}
