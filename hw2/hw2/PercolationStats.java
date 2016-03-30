package hw2;                       
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] samples;
    private double mu;
    private double sigma;
    private int sampleNumber;
    private Percolation sample;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        sampleNumber = T;
        samples = new double[T];
        int row;
        int col;
        for (int i = 0; i < T; i++) {
            sample = new Percolation(N);
            while (!sample.percolates()) {
                row = StdRandom.uniform(N);
                col = StdRandom.uniform(N);
                if (!sample.isOpen(row, col)) {
                    sample.open(row, col);
                }
            }
            samples[i] = (double) sample.numberOfOpenSites() / (N * N);
        }
        mu = StdStats.mean(samples);
        sigma = StdStats.stddev(samples);
    }
    public double mean() {
        return mu;
    }
    public double stddev() {
        return sigma;
    }
    public double confidenceLow() {
        return mu - (1.96 * sigma / Math.sqrt(sampleNumber));
    }
    public double confidenceHigh() {
        return mu + (1.96 * sigma / Math.sqrt(sampleNumber));
    }
}                       
