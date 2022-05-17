package com.example.samplefunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SpringBootApplication
public class SampleFunctionApplication {

    List<TollStation>  tollStations;


    public SampleFunctionApplication() {
        tollStations = new ArrayList<>();
        tollStations.add(new TollStation("100A", 112.5f, 2));
        tollStations.add(new TollStation("111C", 124f, 4));
        tollStations.add(new TollStation("112C", 126f, 2));
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleFunctionApplication.class, args);
    }


    @Bean
    public Function<String, TollStation> retrieveStation() {
        return value -> {
            System.out.println("retrieved request for station - " + value);
           return   tollStations.stream().filter(t -> t.getStationId().equals(value))
                    .findAny().orElse(null);
        };
    }
}
