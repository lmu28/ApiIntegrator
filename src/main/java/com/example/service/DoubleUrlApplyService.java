package com.example.service;

import java.util.List;

public interface DoubleUrlApplyService {
    String apply(String url1, String url2);

    List<String> applyAll(List<String> urls1, List<String> urls2);
}