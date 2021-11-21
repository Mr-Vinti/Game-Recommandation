package com.org.acs.gr.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StringResponse {

    private String response;
    private Boolean successReq;

    public StringResponse(String s) { 
       this.response = s;
    }
    
}