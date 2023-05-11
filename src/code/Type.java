package code;

class Type {
    final String name;
    final Feature[] features;

    int count = 1;
    double prob = 0;

    public Type(String name, Feature[] features) {
        this.name = name;
        this.features = features;
    }
}