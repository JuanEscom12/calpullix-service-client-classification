package com.calpullix.service.client.classification.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientPromotionsResponseDTO {

	private List<ClientPromotionsDetailResponseDTO> promotions;
	
}
