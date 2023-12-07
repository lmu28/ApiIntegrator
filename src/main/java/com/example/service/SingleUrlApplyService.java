package com.example.service;

import java.util.List;
import java.util.Map;

public interface SingleUrlApplyService {

    String apply(String url, Map<String,Object> params);
    List<String> applyAll(List<String> urls,Map<String,Object> params);
}
