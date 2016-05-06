package org.apache.fineract.portfolio.client.familymember.handler;

import org.apache.fineract.commands.annotation.CommandType;
import org.apache.fineract.commands.handler.NewCommandSourceHandler;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.portfolio.client.familymember.service.FamilyMemberWritePlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CommandType(entity = "FAMILYMEMBER", action = "DELETE")
public class DeleteFamilyMemberCommandHandler implements NewCommandSourceHandler {

    private final FamilyMemberWritePlatformService familyMemberWritePlatformService;

    @Autowired
    public DeleteFamilyMemberCommandHandler(FamilyMemberWritePlatformService familyMemberWritePlatformService) {
        this.familyMemberWritePlatformService = familyMemberWritePlatformService;
    }

    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.familyMemberWritePlatformService.deleteFamilyMember(command.entityId(), command.getClientId());

    }

}
