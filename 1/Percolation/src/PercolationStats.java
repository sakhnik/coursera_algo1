
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class PercolationStats {

	private double mean;
	private double stddev;
	private double confidence;

	public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();

		double[] probs = new double[trials];

		for (int i = 0; i < trials; ++i) {
			Percolation p = new Percolation(n);
			int[] idx = StdRandom.permutation(n*n);
			for (int j = 0; j < idx.length && !p.percolates(); ++j) {
				int row = 1 + idx[j] / n;
				int col = 1 + idx[j] % n;
				assert !p.isOpen(row, col);
				p.open(row, col);
			}
			probs[i] = 1.0 * p.numberOfOpenSites() / idx.length;
		}

		mean = StdStats.mean(probs);
		stddev = StdStats.stddev(probs);
		confidence = 1.96 * stddev / Math.sqrt(trials);
	}

	public double mean() {                          // sample mean of percolation threshold
		return mean;
	}

	public double stddev() {                       // sample standard deviation of percolation threshold
		return stddev;
	}

	public double confidenceLo() {                 // low  endpoint of 95% confidence interval
		return mean - confidence;
	}

	public double confidenceHi() {                 // high endpoint of 95% confidence interval
		return mean + confidence;
	}

	public static void main(String[] args) {        // test client (described below)
		int n = Integer.parseInt(args[1]);
		int trials = Integer.parseInt(args[2]);

		PercolationStats stats = new PercolationStats(n, trials);
		System.out.println("mean                    = " + stats.mean());
		System.out.println("stddev                  = " + stats.stddev());
		System.out.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
	}
}