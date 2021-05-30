package ca.fabiose.broadcast.domain;

import com.opencsv.bean.CsvBindByPosition;

import java.io.Serializable;

public class Broadcast implements Serializable {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByPosition(position = 1)
    private String provider;

    @CsvBindByPosition(position = 2)
    private String title;

    @CsvBindByPosition(position = 3)
    private long views;

    public String getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }

    public String getTitle() {
        return title;
    }

    public long getViews() {
        return views;
    }
}
