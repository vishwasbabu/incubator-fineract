package org.apache.fineract.portfolio.client.familymember.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FamilyMemberApiConstants {

    // Resource Name
    public static final String FAMILY_MEMBER_RESOURCE_NAME = "familyMember";

    // Code Values used
    public static final String CODE_GENDER = "Gender";
    public static final String CODE_SALUTATION = "Salutation";
    public static final String CODE_RELATIONSHIP = "Relationship";
    public static final String CODE_EDUCATION = "Education";
    public static final String CODE_OCCUPATION = "Occupation";

    // Input Params
    public static final String idParamName = "id";
    public static final String clientParamName = "client";
    public static final String salutationParamName = "salutation";
    public static final String firstnameParamName = "firstname";
    public static final String middlenameParamName = "middlename";
    public static final String lastnameParamName = "lastname";
    public static final String relationshipParamName = "relationship";
    public static final String genderParamName = "gender";
    public static final String dobParamName = "dateOfBirth";
    public static final String ageParamName = "age";
    public static final String occupationalDetailsParamName = "occupation";
    public static final String educationParamName = "education";
    public static final String dateFormatParamName = "dateFormat";
    public static final String localeParamName = "locale";

    public static final Set<String> FAMILY_MEMBER_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName, clientParamName,
            salutationParamName, firstnameParamName, middlenameParamName, lastnameParamName, relationshipParamName, genderParamName,
            dobParamName, localeParamName, occupationalDetailsParamName, educationParamName, dateFormatParamName));

}
