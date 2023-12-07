package com.example.service.impl;

import com.example.service.SingleUrlApplyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Getter
@Setter
public class ApplyGfpgan implements SingleUrlApplyService {

    public static final String SCALE_PARAM = "scale";
    public static final int DEFAULT_SCALE = 2;
    private final UpscaleServiceGfpgan upscaleService;


    @Override
    public String apply(String url,Map<String,Object> params) {
        double scale = getScale(params);

        return getUpscaleService().upscale(url,scale);

    }

    @Override
    public List<String> applyAll(List<String> urls, Map<String,Object> params) {
//        List<String> results = new ArrayList<>();
        List<String> results = Collections.synchronizedList(new ArrayList<>());
        ExecutorService e = startReportProgressTask(results,urls.size());

        double scale = getScale(params);
        try {
            for (String url: urls){
                String result = getUpscaleService().upscale(url,scale);
                if (!result.isEmpty()){

                    results.add(result);
                }

            }
        } finally {
            e.shutdown();
        }

        return new ArrayList<>(results);


    }

    private double getScale(Map<String, Object> params) {
        if (params.containsKey(SCALE_PARAM)){
            try {
                return (Double) params.get(SCALE_PARAM);
            } catch (Exception e) {
                throw new RuntimeException("error get scale param from Map" + e);
            }
        }
        return DEFAULT_SCALE;
    }

    private ExecutorService startReportProgressTask(List<String> results, int total) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            System.out.println("Progress: " + Math.round( ((double)results.size()/total) * 100) + "%");
        }, 1, 1, TimeUnit.MINUTES);
        return executor;
    }
}
