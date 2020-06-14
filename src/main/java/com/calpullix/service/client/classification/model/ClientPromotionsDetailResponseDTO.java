package com.calpullix.service.client.classification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientPromotionsDetailResponseDTO {

	private Integer id;
	
	private String content;

	private Boolean accepted;
	
}
