package com.example.samplefunction;

import com.example.samplefunction.model.TollRecord;
import com.example.samplefunction.model.TollStation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class SampleFunctionApplication {

    List<TollStation> tollStations;


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
            return tollStations.stream().filter(t -> t.getStationId().equals(value))
                    .findAny().orElse(null);
        };
    }

    @Bean
    public Consumer<TollRecord> processTollRecord() {
        return value -> {
            System.out.println("received toll for car with license plate -" + value.getLicensePlate());
        };
    }

    @Bean
    public Function<TollRecord, Mono<Void>> processTollRecordReactive() {
        return value -> {
            System.out.println("received reactive for car with license plate -" + value.getLicensePlate());
            return Mono.empty();
        };
    }

    @Bean
    public Consumer<Flux<TollRecord>> processListOfTollRecord() {
        return value -> {
            value.subscribe(t -> {
                System.out.println("received toll for car with license plate -" + t.getLicensePlate());
            });
        };
    }

    @Bean
    public Supplier<Flux<TollStation>> processListOfTollStation() {
        return ()-> Flux.fromIterable(tollStations);
    }
}
