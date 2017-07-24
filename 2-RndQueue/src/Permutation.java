
import edu.princeton.cs.algs4.StdIn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class Permutation {
    
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            q.enqueue(s);
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; ++i)
            System.out.println(q.dequeue());
    }
}
