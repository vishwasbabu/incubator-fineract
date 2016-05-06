package org.apache.fineract.portfolio.client.familymember.data;

import java.util.Collection;
import java.util.Date;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;

public class FamilyMemberData {

    private final Long id;
    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final Date dateOfBirth;
    private final CodeValueData salutation;
    private final CodeValueData relationship;
    private final CodeValueData gender;
    private final CodeValueData education;
    private final CodeValueData occupation;
    private Collection<CodeValueData> salutationOptions;
    private Collection<CodeValueData> relationshipOptions;
    private Collection<CodeValueData> genderOptions;
    private Collection<CodeValueData> educationOptions;
    private Collection<CodeValueData> occupationOptions;

    public FamilyMemberData(Long id, String firstname, String middlename, String lastname, CodeValueData salutation,
            CodeValueData relationship, CodeValueData gender, Date dateOfBirth, CodeValueData education, CodeValueData occupation) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.salutation = salutation;
        this.relationship = relationship;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.education = education;
        this.occupation = occupation;
    }

    public static FamilyMemberData templateOnTop(Collection<CodeValueData> salutationOptions, Collection<CodeValueData> relationshipOptions,
            Collection<CodeValueData> genderOptions, Collection<CodeValueData> educationOptions, Collection<CodeValueData> occupationOptions) {
        FamilyMemberData familyMemberData = new FamilyMemberData(null, null, null, null, null, null, null, null, null, null);
        familyMemberData.setEducationOptions(educationOptions);
        familyMemberData.setGenderOptions(genderOptions);
        familyMemberData.setOccupationOptions(occupationOptions);
        familyMemberData.setRelationshipOptions(relationshipOptions);
        familyMemberData.setSalutationOptions(salutationOptions);
        return familyMemberData;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public CodeValueData getSalutation() {
        return salutation;
    }

    public CodeValueData getRelationship() {
        return relationship;
    }

    public CodeValueData getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public CodeValueData getEducation() {
        return education;
    }

    public CodeValueData getOccupation() {
        return occupation;
    }

    public void setSalutationOptions(Collection<CodeValueData> salutationOptions) {
        this.salutationOptions = salutationOptions;
    }

    public void setRelationshipOptions(Collection<CodeValueData> relationshipOptions) {
        this.relationshipOptions = relationshipOptions;
    }

    public void setGenderOptions(Collection<CodeValueData> genderOptions) {
        this.genderOptions = genderOptions;
    }

    public void setEducationOptions(Collection<CodeValueData> educationOptions) {
        this.educationOptions = educationOptions;
    }

    public void setOccupationOptions(Collection<CodeValueData> occupationOptions) {
        this.occupationOptions = occupationOptions;
    }

    public Collection<CodeValueData> getSalutationOptions() {
        return this.salutationOptions;
    }

    public Collection<CodeValueData> getRelationshipOptions() {
        return this.relationshipOptions;
    }

    public Collection<CodeValueData> getGenderOptions() {
        return this.genderOptions;
    }

    public Collection<CodeValueData> getEducationOptions() {
        return this.educationOptions;
    }

    public Collection<CodeValueData> getOccupationOptions() {
        return this.occupationOptions;
    }

}