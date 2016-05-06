package org.apache.fineract.portfolio.client.familymember.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long>, JpaSpecificationExecutor<FamilyMember> {

    FamilyMember findByIdAndClientId(final Long id, final Long clientId);

}