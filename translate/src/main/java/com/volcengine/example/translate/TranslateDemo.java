package com.volcengine.example.translate;

import com.alibaba.fastjson.JSON;
import com.volcengine.model.request.translate.LangDetectRequest;
import com.volcengine.model.request.translate.TranslateTextRequest;
import com.volcengine.model.response.translate.LangDetectResponse;
import com.volcengine.model.response.translate.TranslateTextResponse;
import com.volcengine.service.translate.ITranslateService;
import com.volcengine.service.translate.impl.TranslateServiceImpl;

import java.util.Arrays;

public class TranslateDemo {
    public static void main(String[] args) {
        ITranslateService translateService = TranslateServiceImpl.getInstance();
        // call below method if you dont set ak and sk in ～/.volc/config

        translateService.setAccessKey("AKLTNjc2YzY1NWRiYjg2NGJmMDgwMTQ5MzJlYWJmMGM5NWE");
        translateService.setSecretKey("TTJReVpqRXpOV1V3TkdNek5EWmtOR0UwT0RRelltWmpNV1ptT0RabE9UUQ==");

        // lang detect
        try {
            LangDetectRequest langDetectRequest = new LangDetectRequest();
            langDetectRequest.setTextList(Arrays.asList("hello world", "how are you"));

            LangDetectResponse langDetectResponse = translateService.langDetect(langDetectRequest);
            System.out.println(JSON.toJSONString(langDetectResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // translate text
        try {
            TranslateTextRequest translateTextRequest = new TranslateTextRequest();
            // translateTextRequest.setSourceLanguage("en"); // 不设置表示自动检测
            translateTextRequest.setTargetLanguage("zh");
            translateTextRequest.setTextList(Arrays.asList("hello world", "how are you"));

            TranslateTextResponse translateText = translateService.translateText(translateTextRequest);
            System.out.println(JSON.toJSONString(translateText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}