package org.apache.fineract.portfolio.client.familymember.service;

import java.util.Map;

import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.codes.domain.CodeValueRepositoryWrapper;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.client.familymember.api.FamilyMemberApiConstants;
import org.apache.fineract.portfolio.client.familymember.data.FamilyMemberDataValidator;
import org.apache.fineract.portfolio.client.familymember.domain.FamilyMember;
import org.apache.fineract.portfolio.client.familymember.domain.FamilyMemberRepositoryWrapper;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class FamilyMemberWritePlatformServiceImpl implements FamilyMemberWritePlatformService {

    private final FamilyMemberRepositoryWrapper familyMemberRepository;
    private final FamilyMemberDataValidator validator;
    private final ClientRepositoryWrapper clientRepository;
    private final CodeValueRepositoryWrapper codeValueRepository;

    @Autowired
    public FamilyMemberWritePlatformServiceImpl(final FamilyMemberRepositoryWrapper familyMemberRepository,
            final FamilyMemberDataValidator validator, final ClientRepositoryWrapper clientRepository,
            final CodeValueRepositoryWrapper codeValueRepository) {
        super();
        this.familyMemberRepository = familyMemberRepository;
        this.validator = validator;
        this.clientRepository = clientRepository;
        this.codeValueRepository = codeValueRepository;
    }

    @Transactional
    @Override
    public CommandProcessingResult createFamilyMember(final Long clientId, final JsonCommand command) {
        try {

            /** Validate input JSON **/
            this.validator.validateForCreate(command.json());

            /** Ensure Client exists **/
            final Client client = this.clientRepository.findOneWithNotFoundDetection(clientId);

            /** Create Family Member **/
            FamilyMember familyMember = createFamilyMember(client, command);

            /** Save Family Member **/
            this.familyMemberRepository.save(familyMember);

            return new CommandProcessingResultBuilder() //
                    .withCommandId(command.commandId()) //
                    .withClientId(clientId) //
                    .withEntityId(familyMember.getId()) //
                    .build();

        } catch (final DataIntegrityViolationException dve) {
            return CommandProcessingResult.empty();
        }
    }

    @Transactional
    @Override
    public CommandProcessingResult updateFamilyMember(final Long clientId, final Long id, final JsonCommand command) {
        try {
            /** Validate input JSON **/
            this.validator.validateForUpdate(command.json());

            /** Ensure Client exists **/
            this.clientRepository.findOneWithNotFoundDetection(clientId);

            /*** Determine actual changes made to the Family member ***/
            FamilyMember familyMember = this.familyMemberRepository.findByIdAndClientId(id, clientId);
            final Map<String, Object> changes = familyMember.update(command);

            /*** Update applicable foreign key references **/
            familyMember = updateFamilyMember(familyMember, changes, command);

            /** Save Changes **/

            if (!CollectionUtils.isEmpty(changes)) {
                this.familyMemberRepository.save(familyMember);
            }

            return new CommandProcessingResultBuilder() //
                    .withCommandId(command.commandId()) //
                    .withEntityId(familyMember.getId()) //
                    .withClientId(command.getClientId()) //
                    .with(changes) //
                    .build();

        } catch (final DataIntegrityViolationException dve) {
            throw new PlatformDataIntegrityException("error.msg.client.family.member.data.integrity.issue", dve.getMessage());
        }

    }

    @Transactional
    @Override
    public CommandProcessingResult deleteFamilyMember(final Long familyMemberId, final Long clientId) {
        try {
            final FamilyMember familyMember = this.familyMemberRepository.findByIdAndClientId(familyMemberId, clientId);
            this.familyMemberRepository.delete(familyMember);
            return new CommandProcessingResultBuilder() //
                    .withEntityId(familyMemberId) //
                    .build();
        } catch (final DataIntegrityViolationException dve) {
            throw new PlatformDataIntegrityException("error.msg.client.family.member.data.integrity.issue", dve.getMessage());
        }
    }

    /**
     * Creates A FamilyMember Object from jsonCommand
     * 
     * @param command
     * @return
     */
    private FamilyMember createFamilyMember(Client client, JsonCommand command) {

        final String firstname = command.stringValueOfParameterNamed(FamilyMemberApiConstants.firstnameParamName);

        final String middlename = command.stringValueOfParameterNamed(FamilyMemberApiConstants.middlenameParamName);

        final String lastname = command.stringValueOfParameterNamed(ClientApiConstants.lastnameParamName);

        final LocalDate dateOfBirth = command.localDateValueOfParameterNamed(FamilyMemberApiConstants.dobParamName);

        final Long genderId = command.longValueOfParameterNamed(FamilyMemberApiConstants.genderParamName);
        CodeValue gender = null;
        if (genderId != null) {
            gender = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(FamilyMemberApiConstants.CODE_GENDER, genderId);
        }

        final Long relationshipId = command.longValueOfParameterNamed(FamilyMemberApiConstants.relationshipParamName);
        CodeValue relationship = null;
        if (relationshipId != null) {
            relationship = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(FamilyMemberApiConstants.CODE_RELATIONSHIP,
                    relationshipId);
        }

        final Long salutationId = command.longValueOfParameterNamed(FamilyMemberApiConstants.salutationParamName);
        CodeValue salutaion = null;
        if (salutationId != null) {
            salutaion = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(FamilyMemberApiConstants.CODE_SALUTATION,
                    salutationId);
        }

        final Long occupationalDetailsId = command.longValueOfParameterNamed(FamilyMemberApiConstants.occupationalDetailsParamName);
        CodeValue occupationDetails = null;
        if (occupationalDetailsId != null) {
            occupationDetails = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(
                    FamilyMemberApiConstants.CODE_OCCUPATION, occupationalDetailsId);
        }

        final Long educationId = command.longValueOfParameterNamed(FamilyMemberApiConstants.educationParamName);
        CodeValue education = null;
        if (educationId != null) {
            education = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(FamilyMemberApiConstants.CODE_EDUCATION,
                    educationId);
        }

        FamilyMember familyMember = FamilyMember.create(client, salutaion, firstname, middlename, lastname, relationship, gender,
                dateOfBirth, occupationDetails, education);

        return familyMember;
    }

    /**
     * Update foreign key references for Family member
     * 
     * @param command
     * @return
     */
    private FamilyMember updateFamilyMember(FamilyMember familyMember, Map<String, Object> changes, JsonCommand command) {

        if (changes.containsKey(FamilyMemberApiConstants.genderParamName)) {
            final Long genderId = command.longValueOfParameterNamed(FamilyMemberApiConstants.genderParamName);
            CodeValue gender = null;
            if (genderId != null) {
                gender = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(FamilyMemberApiConstants.CODE_GENDER,
                        genderId);
            }
            familyMember.updateGender(gender);
        }

        if (changes.containsKey(FamilyMemberApiConstants.relationshipParamName)) {
            final Long relationshipId = command.longValueOfParameterNamed(FamilyMemberApiConstants.relationshipParamName);
            CodeValue relationship = null;
            if (relationshipId != null) {
                relationship = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(
                        FamilyMemberApiConstants.CODE_RELATIONSHIP, relationshipId);
            }
            familyMember.updateRelationship(relationship);
        }

        if (changes.containsKey(FamilyMemberApiConstants.salutationParamName)) {
            final Long salutationId = command.longValueOfParameterNamed(FamilyMemberApiConstants.salutationParamName);
            CodeValue salutation = null;
            if (salutationId != null) {
                salutation = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(FamilyMemberApiConstants.CODE_SALUTATION,
                        salutationId);
            }
            familyMember.updateSalutation(salutation);
        }

        if (changes.containsKey(FamilyMemberApiConstants.occupationalDetailsParamName)) {
            final Long occupationalDetailsId = command.longValueOfParameterNamed(FamilyMemberApiConstants.occupationalDetailsParamName);
            CodeValue occupationDetails = null;
            if (occupationalDetailsId != null) {
                occupationDetails = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(
                        FamilyMemberApiConstants.CODE_OCCUPATION, occupationalDetailsId);
            }
            familyMember.updateOccupation(occupationDetails);
        }

        if (changes.containsKey(FamilyMemberApiConstants.educationParamName)) {
            final Long educationId = command.longValueOfParameterNamed(FamilyMemberApiConstants.educationParamName);
            CodeValue education = null;
            if (educationId != null) {
                education = this.codeValueRepository.findOneByCodeNameAndIdWithNotFoundDetection(FamilyMemberApiConstants.CODE_EDUCATION,
                        educationId);
            }
            familyMember.updateEducation(education);
        }
        return familyMember;
    }

}