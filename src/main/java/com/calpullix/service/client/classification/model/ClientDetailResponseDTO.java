package com.calpullix.service.client.classification.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDetailResponseDTO {

	private Integer id;
	
	private String branch;
	
	private String bestProducts;
	
	private String header;
	
	private String classification;
		
	private String schoolingLevel;
	
	private String employment;
		
	private String state;
	
	private String municipality;
	
	private BigDecimal monthlyPurchases;
	
}
