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
public class ClientResponseDTO {

	private Boolean isUpdated;
	
	private Integer itemCount;
	
	private String lastClassificationDate;
	
	private List<ClientDetailResponseDTO> clients;
	
}
