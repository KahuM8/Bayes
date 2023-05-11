package code;

import java.util.List;

public class Bayes {
    public static final Type[] types = makTypes();

    public static void main(String[] args) {

        // check if 2 args
        if (args.length != 2) {
            System.out.println("Usage: java Bayes <training-set-file> <test-set-file>");
            System.exit(1);
        }
        String s = "training \n";
        train(Parser.parseData(Parser.readAllLines(args[0])));
        // string builder stuff
        s += "          " + types[0].name + ", " + types[1].name + "\n" +
                "P(Class)  " + types[0].prob + ", " + types[1].prob + "\n\n\n"
                + "Feature probabilities:\n---------------------\n" + types[0].name + ":\n";

        for (Feature feature : types[0].features) {
            s += feature.toString();
        }

        s += "\n\n\n" + types[1].name + ":\n";
        for (Feature feature : types[0].features) {
            s += feature.toString();
        }

        s += "\nTesting!!n\n";
        int numInstances = 0;
        double numCorrect = 0;
        for (String[] instance : Parser.parseData(Parser.readAllLines(args[1]))) {
            Type bestClass = getBestClass(instance, List.of(types));
            s += ("Instance " + numInstances + ": Best class: " + bestClass.name + ". ");
            if (bestClass.name.equals(instance[1])) {
                s += ("Right! \n");
                numCorrect++;
            } else {
                s += ("wrong! \n");
            }
            numInstances++;
        }

        double accuracy = (numCorrect / numInstances) * 100.0;
        s += "Accuracy: " + accuracy + "%\n";

        Parser.toFile("output.txt", s);
        System.out.println(s);
    }

    public static void train(String[][] instances) {
        for (String[] instance : instances) {
            Type y = getType(instance[1]);
            y.count++;
            for (int i = 2; i < instance.length; i++) {
                updateFeatureCounts(y.features[i - 2], instance[i]);
            }
        }
        int classTotal = calculateClassTotal();
        calculateProbabilities(classTotal);
    }

    private static Type getType(String typeName) {
        for (Type t : types) {
            if (t.name.equals(typeName)) {
                return t;
            }
        }
        return null;
    }

    private static Type getBestClass(String[] instance, List<Type> types) {
        double bestScore = 0;
        Type bestClass = null;

        for (Type y : types) {
            double score = test(instance, y);
            if (score > bestScore) {
                bestScore = score;
                bestClass = y;
            }
        }

        return bestClass;
    }

    private static void updateFeatureCounts(Feature feature, String valueName) {
        if (feature.valueCounts.containsKey(valueName)) {
            int count = feature.valueCounts.get(valueName);
            feature.valueCounts.put(valueName, count + 1);
        } else {
            feature.valueCounts.put(valueName, 1);
        }
    }

    private static int calculateClassTotal() {
        int classTotal = 0;
        for (Type y : types) {
            classTotal += y.count;
            for (Feature X : y.features) {
                X.total = calculateFeatureTotal(X);
            }
        }
        return classTotal;
    }

    private static int calculateFeatureTotal(Feature feature) {
        int featureTotal = 0;
        for (int count : feature.valueCounts.values()) {
            featureTotal += count;
        }
        return featureTotal;
    }

    private static void calculateProbabilities(int classTotal) {
        for (Type y : types) {
            y.prob = (1.0 * y.count) / classTotal;
            for (Feature X : y.features) {
                X.calculateProbabilities();
            }
        }
    }

    private static double test(String[] instance, Type y) {
        double score = y.prob;
        for (int i = 2; i < instance.length; i++) {
            String valueName = instance[i];
            if (y.features[i - 2].valueProbabilities.containsKey(valueName)) {
                double valueProb = y.features[i - 2].valueProbabilities.get(valueName);
                score *= valueProb;
            } else {
                score = 0;
                break;
            }
        }
        return score;
    }

    private static Type[] makTypes() {
        Type[] res = { new Type("no-recurrence-events", initFeatures()),
                new Type("recurrence-events", initFeatures()) };
        return res;
    }

    public static Feature[] initFeatures() {
        Feature[] features = {
                initFeature("age", "10-19", "20-29", "30-39", "40-49", "50-59", "60-69", "70-79", "80-89", "90-99"),
                initFeature("menopause", "lt40", "ge40", "premeno"),
                initFeature("tumor-size", "0-4", "5-9", "10-14", "15-19", "20-24", "25-29", "30-34", "35-39", "40-44",
                        "45-49", "50-54", "55-59"),
                initFeature("inv-nodes", "0-2", "3-5", "6-8", "9-11", "12-14", "15-17", "18-20", "21-23", "24-26",
                        "27-29", "30-32", "33-35", "36-39"),
                initFeature("node-caps", "yes", "no"),
                initFeature("deg-malig", "1", "2", "3"),
                initFeature("breast", "left", "right"),
                initFeature("breast-quad", "left_up", "left_low", "right_up", "right_low", "central"),
                initFeature("irradiat", "yes", "no")
        };
        return features;
    }

    private static Feature initFeature(String name, String... values) {
        Feature feature = new Feature(name);
        for (String value : values) {
            feature.addValue(value);
        }
        return feature;
    }
}
