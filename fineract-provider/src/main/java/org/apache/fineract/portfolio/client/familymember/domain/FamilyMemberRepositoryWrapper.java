package org.apache.fineract.portfolio.client.familymember.domain;

import org.apache.fineract.portfolio.client.familymember.exception.FamilyMemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyMemberRepositoryWrapper {

    private final FamilyMemberRepository repository;

    @Autowired
    public FamilyMemberRepositoryWrapper(final FamilyMemberRepository repository) {
        this.repository = repository;
    }

    public FamilyMember findByIdAndClientId(final Long familyMemberId, final Long clientId) {
        FamilyMember familyMember = this.repository.findByIdAndClientId(familyMemberId, clientId);
        if (familyMember == null) { throw new FamilyMemberNotFoundException(familyMemberId, clientId); }
        return familyMember;
    }

    public void save(final FamilyMember familyDetails) {
        this.repository.save(familyDetails);
    }

    public void delete(final FamilyMember familyDetails) {
        this.repository.delete(familyDetails);
    }

}