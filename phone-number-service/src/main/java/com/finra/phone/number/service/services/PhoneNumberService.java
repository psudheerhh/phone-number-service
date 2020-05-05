package com.finra.phone.number.service.services;

import com.finra.phone.number.service.models.phonenumber.response.AlphaNumericCombination;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;

@Service
public class PhoneNumberService {
    
    private static final Map<String, Set<String>> CHARACTER_TO_COMBINATION;
    
    static {
        Map<String, Set<String>> tempMap = new HashMap<>();
        tempMap.put("0", generateSet("0"));
        tempMap.put("1", generateSet("1"));
        tempMap.put("2", generateSet("2", "A", "B", "C"));
        tempMap.put("3", generateSet("3", "D", "E", "F"));
        tempMap.put("4", generateSet("4", "G", "H", "I"));
        tempMap.put("5", generateSet("5", "J", "K", "L"));
        tempMap.put("6", generateSet("6", "M", "N", "0"));
        tempMap.put("7", generateSet("7", "P", "Q", "R", "S"));
        tempMap.put("8", generateSet("8", "T", "U", "V"));
        tempMap.put("9", generateSet("9", "W", "X", "Y", "Z"));
        CHARACTER_TO_COMBINATION = Collections.unmodifiableMap(tempMap);
    }
    
    
    public AlphaNumericCombination getAlphaNumericCombination(String phoneNumber, int limit, int pageNumber) {
        
        List<String> combinations = generateAlphaNumericCombinationsStream(phoneNumber)
                .skip(pageNumber * limit)
                .limit(limit)
                .map(PhoneNumberService::formatPhoneNumber)
                .collect(toList());
        
        long totalCount = generateAlphaNumericCombinationsStream(phoneNumber).count();
        
        return AlphaNumericCombination
                .builder()
                .totalCount(totalCount)
                .combinations(combinations)
                .build();
        
    }
    
    private static Set<String> generateSet(String... values) {
        return of(values).collect(toSet());
    }
    
    private static String formatPhoneNumber(String phoneNumber) {
        int length = phoneNumber.length();
        
        return length == 7 ?
                String.format("(%s)-(%s)",
                        phoneNumber.substring(0, 3),
                        phoneNumber.substring(3, 7)) :
                String.format("(%s)-(%s)-(%s)",
                        phoneNumber.substring(0, 3),
                        phoneNumber.substring(3, 6),
                        phoneNumber.substring(6, 10));
    }
    
    private Stream<String> generateAlphaNumericCombinationsStream(String phoneNumber) {
        
        if (!phoneNumber.isEmpty()) {
            if (phoneNumber.length() == 1) {
                return CHARACTER_TO_COMBINATION.get(phoneNumber).stream();
            } else {
                Stream<String> tempResult = generateAlphaNumericCombinationsStream(phoneNumber.substring(1));
                String startingChar = Character.toString(phoneNumber.charAt(0));
                
                return tempResult.flatMap(tempStr -> CHARACTER_TO_COMBINATION
                        .get(startingChar)
                        .stream()
                        .map(str -> str + tempStr));
            }
        }
        return Stream.empty();
    }
    
}
