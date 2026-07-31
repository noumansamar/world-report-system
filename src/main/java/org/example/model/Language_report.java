package org.example.model;
public class Language_report
{
    private final String language;
    private final long speakers;
    private final double percentageOfWorld;
    public Language_report(String language, long speakers, double percentageOfWorld)
    {
        this.language = language;
        this.speakers = speakers;
        this.percentageOfWorld = percentageOfWorld;
    }
    public String getLanguage()
    {
        return language;
    }
    public long getSpeakers()
    {
        return speakers;
    }
    public double getPercentageOfWorld()
    {
        return percentageOfWorld;
    }
}