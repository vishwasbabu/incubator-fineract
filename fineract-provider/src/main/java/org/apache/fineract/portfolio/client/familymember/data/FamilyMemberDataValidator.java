package org.apache.fineract.portfolio.client.familymember.data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
import org.apache.fineract.portfolio.client.familymember.api.FamilyMemberApiConstants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;

@Component
public class FamilyMemberDataValidator {

    private final FromJsonHelper fromApiJsonHelper;

    @Autowired
    public FamilyMemberDataValidator(FromJsonHelper fromApiJsonHelper) {
        super();
        this.fromApiJsonHelper = fromApiJsonHelper;
    }

    /**
     * Sanity check of input JSON for creating a family member
     * 
     * @param json
     */
    public void validateForCreate(final String json) {

        /** Ensure JSON is not empty **/
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        /** Check for unsupported parameters **/
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();

        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                FamilyMemberApiConstants.FAMILY_MEMBER_REQUEST_DATA_PARAMETERS);

        /** Setup Data validator for the resource **/
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(FamilyMemberApiConstants.FAMILY_MEMBER_RESOURCE_NAME);

        final JsonElement element = this.fromApiJsonHelper.parse(json);

        /*** Validate all input parameters **/

        final String firstname = this.fromApiJsonHelper.extractStringNamed(FamilyMemberApiConstants.firstnameParamName, element);
        baseDataValidator.reset().parameter(FamilyMemberApiConstants.firstnameParamName).value(firstname).notBlank()
                .notExceedingLengthOf(50);

        final String middlename = this.fromApiJsonHelper.extractStringNamed(FamilyMemberApiConstants.middlenameParamName, element);
        baseDataValidator.reset().parameter(FamilyMemberApiConstants.middlenameParamName).value(middlename).ignoreIfNull()
                .notExceedingLengthOf(50);

        final String lastname = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(lastname).ignoreIfNull().notExceedingLengthOf(50);

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.dobParamName, element)) {
            final LocalDate dateOfBirth = this.fromApiJsonHelper.extractLocalDateNamed(FamilyMemberApiConstants.dobParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.dobParamName).value(dateOfBirth).ignoreIfNull()
                    .validateDateBefore(DateUtils.getLocalDateOfTenant());
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.genderParamName, element)) {
            final Long genderId = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.genderParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.genderParamName).value(genderId).longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.relationshipParamName, element)) {
            final Long relationshipId = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.relationshipParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.relationshipParamName).value(relationshipId).longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.salutationParamName, element)) {
            final Long salutationId = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.salutationParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.salutationParamName).value(salutationId).longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.occupationalDetailsParamName, element)) {
            final Long occupationalDetailsId = this.fromApiJsonHelper.extractLongNamed(
                    FamilyMemberApiConstants.occupationalDetailsParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.occupationalDetailsParamName).value(occupationalDetailsId)
                    .longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.educationParamName, element)) {
            final Long educationId = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.educationParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.educationParamName).value(educationId).longGreaterThanZero();
        }

        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

    /**
     * Sanity check of input JSON for creating a family member
     * 
     * @param json
     */
    public void validateForUpdate(final String json) {

        /** Ensure JSON is not empty **/
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }

        /** Check for unsupported parameters **/
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();

        this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
                FamilyMemberApiConstants.FAMILY_MEMBER_REQUEST_DATA_PARAMETERS);

        /** Setup Data validator for the resource **/
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
                .resource(FamilyMemberApiConstants.FAMILY_MEMBER_RESOURCE_NAME);

        final JsonElement element = this.fromApiJsonHelper.parse(json);

        /*** Validate all input parameters **/

        final String firstname = this.fromApiJsonHelper.extractStringNamed(FamilyMemberApiConstants.firstnameParamName, element);
        baseDataValidator.reset().parameter(FamilyMemberApiConstants.firstnameParamName).value(firstname).ignoreIfNull()
                .notExceedingLengthOf(50);

        final String middlename = this.fromApiJsonHelper.extractStringNamed(FamilyMemberApiConstants.middlenameParamName, element);
        baseDataValidator.reset().parameter(FamilyMemberApiConstants.middlenameParamName).value(middlename).ignoreIfNull()
                .notExceedingLengthOf(50);

        final String lastname = this.fromApiJsonHelper.extractStringNamed(ClientApiConstants.middlenameParamName, element);
        baseDataValidator.reset().parameter(ClientApiConstants.middlenameParamName).value(lastname).ignoreIfNull().notExceedingLengthOf(50);

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.dobParamName, element)) {
            final LocalDate dateOfBirth = this.fromApiJsonHelper.extractLocalDateNamed(FamilyMemberApiConstants.dobParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.dobParamName).value(dateOfBirth).ignoreIfNull()
                    .validateDateBefore(DateUtils.getLocalDateOfTenant());
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.genderParamName, element)) {
            final Long genderId = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.genderParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.genderParamName).value(genderId).longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.relationshipParamName, element)) {
            final Long relationship = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.relationshipParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.relationshipParamName).value(relationship).longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.salutationParamName, element)) {
            final Long salutation = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.salutationParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.salutationParamName).value(salutation).longGreaterThanZero();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.occupationalDetailsParamName, element)) {
            final Long occupationalDetails = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.occupationalDetailsParamName,
                    element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.occupationalDetailsParamName).value(occupationalDetails)
                    .longZeroOrGreater();
        }

        if (this.fromApiJsonHelper.parameterExists(FamilyMemberApiConstants.educationParamName, element)) {
            final Long educationId = this.fromApiJsonHelper.extractLongNamed(FamilyMemberApiConstants.educationParamName, element);
            baseDataValidator.reset().parameter(FamilyMemberApiConstants.educationParamName).value(educationId).longGreaterThanZero();
        }

        throwExceptionIfValidationWarningsExist(dataValidationErrors);
    }

    /**
     * Throws Data validation exception if there are any Validation errors
     * 
     * @param dataValidationErrors
     */
    private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException(dataValidationErrors); }
    }

}