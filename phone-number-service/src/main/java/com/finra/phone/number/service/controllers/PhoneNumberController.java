package com.finra.phone.number.service.controllers;

import com.finra.phone.number.service.models.phonenumber.response.AlphaNumericCombination;
import com.finra.phone.number.service.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@RestController
@Validated
public class PhoneNumberController {
    
    private static final Integer LIMIT = 10;
    
    @Autowired
    private PhoneNumberService phoneNumberService;
    
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET, path = "/phone/number/{phoneNumber}/combinations")
    public ResponseEntity<AlphaNumericCombination> getAllCombinations(
            @PathVariable("phoneNumber")
            @Pattern(regexp = "^(\\d{7})(\\d{3})?$", message = "A phone number can only contain 7 or 10 digits.")
                    String phoneNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<@Min(value = 10, message = "A limit should be >=1.") Integer> optionalLimit,
            @RequestParam(value = "pageNumber")
            @Min(value = 0, message = "A page number should be >=0.")
                    Integer pageNumber) {
        
        Integer limit = optionalLimit.orElseGet(() -> LIMIT);
        
        AlphaNumericCombination alphaNumericCombination = phoneNumberService
                .getAlphaNumericCombination(phoneNumber, limit, pageNumber);
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alphaNumericCombination);
        
    }
}
    
    

