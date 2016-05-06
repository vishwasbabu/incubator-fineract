package org.apache.fineract.portfolio.client.familymember.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;

public interface FamilyMemberWritePlatformService {

    CommandProcessingResult createFamilyMember(Long clientId, JsonCommand command);

    CommandProcessingResult updateFamilyMember(Long clientId, Long familyMemberId, JsonCommand command);

    CommandProcessingResult deleteFamilyMember(Long familyMemberId, Long clientId);

}