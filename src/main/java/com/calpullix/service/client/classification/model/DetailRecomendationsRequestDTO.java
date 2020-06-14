package com.calpullix.service.client.classification.model;

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
public class DetailRecomendationsRequestDTO {
	
	private Integer idProfile;
	
	private Integer page;

	private Boolean isLast;
	
}
