package com.ss.scrumptious_restaurant.dto;


import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;

import org.javamoney.moneta.Money;

public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(MonetaryAmount attribute) {
    	return attribute.getNumber().numberValue(BigDecimal.class);
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(BigDecimal dbData) {
    	
    	return Money.of(dbData, "USD");
    }

}
