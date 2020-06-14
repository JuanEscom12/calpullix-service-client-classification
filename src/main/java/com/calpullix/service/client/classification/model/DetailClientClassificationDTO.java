package com.calpullix.service.client.classification.model;

import java.util.List;

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
public class DetailClientClassificationDTO {
	
	private Integer id;

	private byte[] kNearestGraphic;
	
	private String kNearestDate;
	
	private byte[] kMeansGraphic;
	
	private String kMeansDate;
	
	private byte[] regressionGraphic;
	
	private String regressionDate;
	
	private String label;
	
	private String regressionClassA;
	
	private String regressionClassB;
	
	private String quantityLeftCurrent;
	
	private String quantityLoyalCurrent;
	
	private String quantityLeftLast;
	
	private String quantityLoyalLast;
	
	private String colorClassA;
	
	private String colorClassB;
	
	private Integer itemCountProducts;
	
	private Integer itemCountPromotions;
	
	private byte[] confusionRegression;
	
	private List<List<String>> lastKNearest;
	
	private List<List<String>> currentKNearest;
	
	private String graphicColor;
	
	private List<ProfileDTO> profiles;
	
	private List<List<String>> productsProfile;
	
	private String productsDate;
	
	private List<List<String>> promotionsProfile;
	
	private String promotionsDate;
	
	private String kNeighbor;
	
	private Integer itemCountLast; 
	
	private Integer itemCountCurrent;
	
	private List<List<String>> rowsLast; 
	
	private List<List<String>> rowsCurrent;
	
}
