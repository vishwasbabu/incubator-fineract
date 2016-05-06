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
@CommandType(entity = "FAMILYMEMBER", action = "CREATE")
public class CreateFamilyMemberCommandHandler implements NewCommandSourceHandler {

    private final FamilyMemberWritePlatformService familyMemberWritePlatformService;

    @Autowired
    public CreateFamilyMemberCommandHandler(FamilyMemberWritePlatformService familyMemberWritePlatformService) {
        this.familyMemberWritePlatformService = familyMemberWritePlatformService;
    }

    @Transactional
    @Override
    public CommandProcessingResult processCommand(final JsonCommand command) {
        return this.familyMemberWritePlatformService.createFamilyMember(command.getClientId(), command);
    }

}