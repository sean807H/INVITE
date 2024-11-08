public class Story {
    private String content;
    private String[] choices;
    private int[] points;

    // Example constructor
    public Story(String content, String[] choices, int[] points) {
        this.content = content;
        this.choices = choices;
        this.points = points;
    }

    public String getContent() {
        return content;
    }

    public String[] getChoices() {
        return choices;
    }

    public int[] getPoints() {
        return points;
    }
}
