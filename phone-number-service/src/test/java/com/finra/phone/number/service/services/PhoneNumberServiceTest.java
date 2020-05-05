package com.finra.phone.number.service.services;


import com.finra.phone.number.service.models.phonenumber.response.AlphaNumericCombination;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhoneNumberServiceTest {
    
    private PhoneNumberService service;
    
    @Before
    public void setUp() {
        service = new PhoneNumberService();
    }
    
    @Test
    public void testGetAlphaNumericCombination() {
        AlphaNumericCombination expected = AlphaNumericCombination
                .builder()
                .totalCount(25)
                .combinations(singletonList("(1W1)-(1W11)"))
                .build();
        
        AlphaNumericCombination alphaNumericCombination = service.getAlphaNumericCombination("1911911", 10, 0);
        
        assertEquals(expected.getTotalCount(), alphaNumericCombination.getTotalCount());
        assertTrue(alphaNumericCombination.getCombinations().contains(expected.getCombinations().get(0)));
    }
    
}
