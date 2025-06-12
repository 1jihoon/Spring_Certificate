package com.example.spring_certificate.Loader;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final List<DataCsvLoader> loaders;

    @Override
    public void run(String... args) {
        for(int i= loaders.size() -1; i>=0; i--){
            loaders.get(i).clear();
        }

        for(DataCsvLoader loader: loaders){
            loader.load();
        }
    }
}
