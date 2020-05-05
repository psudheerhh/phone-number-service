package com.finra.phone.number.service.models.phonenumber.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AlphaNumericCombination {
    
    long totalCount;
    List<String> combinations;
}
