package bayes;

import java.util.HashMap;

public class Feature {
    final String name;
    public HashMap<String, Integer> valueCounts;
    public HashMap<String, Double> valueProbabilities;

    int total = 0;

    public Feature(String name) {
        this.name = name;
        this.valueCounts = new HashMap<String, Integer>();
        this.valueProbabilities = new HashMap<String, Double>();
    }

    public void addValue(String value) {
        int count = this.valueCounts.getOrDefault(value, 0);
        this.valueCounts.put(value, count + 1);
        this.total++;
    }

    public void calculateProbabilities() {
        for (String value : this.valueCounts.keySet()) {
            int count = this.valueCounts.get(value);
            double probability = (1.0 * count) / this.total;
            this.valueProbabilities.put(value, probability);
        }
    }

    // get total probability of this feature
    public double getProbability(String value) {
        return this.valueProbabilities.getOrDefault(value, 0.0);
    }

    @Override
    public String toString() {
        String sb = "\n";
        sb += "P(" + (name) + " | Class)";
        for (String value : valueProbabilities.keySet()) {
            sb += ("\n ") + (value) + (": ") + (valueProbabilities.get(value));
        }
        sb += ("\n");
        return sb.toString();
    }

}
