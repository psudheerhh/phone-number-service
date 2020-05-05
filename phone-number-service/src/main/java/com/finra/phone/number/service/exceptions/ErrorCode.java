package com.finra.phone.number.service.exceptions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ErrorCode {
    
    UNKNOWN_ERROR("1000"),
    CONSTRAINT_VIOLATION("1001");
    
    String code;
    
}
