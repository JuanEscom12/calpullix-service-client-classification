package com.calpullix.service.client.classification.service;

import com.calpullix.service.client.classification.model.ClientPromotionsRequestDTO;
import com.calpullix.service.client.classification.model.ClientRequestDTO;
import com.calpullix.service.client.classification.model.ClientResponseDTO;
import com.calpullix.service.client.classification.model.DetailClientClassificationDTO;
import com.calpullix.service.client.classification.model.DetailRecomendationsDTO;
import com.calpullix.service.client.classification.model.DetailRecomendationsRequestDTO;
import com.calpullix.service.client.classification.model.UsersRegressionDTO;

public interface ClientClassificationService {

	ClientResponseDTO getProfileInformation(ClientRequestDTO request);
	
	DetailClientClassificationDTO getDetailProfileInformation(ClientPromotionsRequestDTO request);
	
	DetailRecomendationsDTO getDetailProductRecomendations(DetailRecomendationsRequestDTO request);
	
	DetailRecomendationsDTO getDetailPromotionRecomendations(DetailRecomendationsRequestDTO request);

	ClientResponseDTO updateClientsToClassify(ClientRequestDTO request);
	
	UsersRegressionDTO getUsersRegression(DetailRecomendationsRequestDTO request);
	
}
