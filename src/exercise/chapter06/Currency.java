package exercise.chapter06;

public class Currency {

    private final String name;

    public Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Currency{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
