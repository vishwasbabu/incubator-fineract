package org.apache.fineract.portfolio.client.familymember.service;

import java.util.Collection;

import org.apache.fineract.portfolio.client.familymember.data.FamilyMemberData;

public interface FamilyMemberReadPlatformService {

    Collection<FamilyMemberData> retrieveAllFamilyMembers(Long clientId);

    FamilyMemberData retrieveTemplate();

}