package com.ss.scrumptious_restaurant.security;


import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class TokenModel {
    private String email;
    private Date expiration_date;
    private String role;
    private UUID uid;
}
