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
public class ClientRequestDTO {
	
	private Integer page;
	
	private String date;
	
	private List<Integer> clients;

}
