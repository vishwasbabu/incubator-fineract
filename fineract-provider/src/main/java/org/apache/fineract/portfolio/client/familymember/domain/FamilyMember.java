package org.apache.fineract.portfolio.client.familymember.domain;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.familymember.api.FamilyMemberApiConstants;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

@SuppressWarnings("serial")
@Entity
@Table(name = "f_family_member", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }, name = "id"),
        @UniqueConstraint(columnNames = { "client_id" }, name = "FK1_client_id") })
public class FamilyMember extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "client_id", unique = true, nullable = false)
    private Client client;

    @Column(name = "date_of_birth", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "middlename", length = 50, nullable = true)
    private String middlename;

    @Column(name = "lastname", length = 50, nullable = true)
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "relationship_cv_id", nullable = true)
    private CodeValue relationship;

    @ManyToOne
    @JoinColumn(name = "gender_cv_id", nullable = true)
    private CodeValue gender;

    @ManyToOne
    @JoinColumn(name = "salutation_cv_id", nullable = true)
    private CodeValue salutation;

    @ManyToOne
    @JoinColumn(name = "occupation_details_cv_id")
    private CodeValue occupation;

    @ManyToOne
    @JoinColumn(name = "education_cv_id")
    private CodeValue education;

    protected FamilyMember() {

    }

    private FamilyMember(Client client, CodeValue salutation, String firstname, String middlename, String lastname, CodeValue relationship,
            CodeValue gender, LocalDate dateOfBirth, CodeValue occupation, CodeValue education) {
        this.client = client;
        this.salutation = salutation;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.relationship = relationship;
        this.gender = gender;
        if (dateOfBirth != null) {
            this.dateOfBirth = dateOfBirth.toDate();
        }

        this.occupation = occupation;
        this.education = education;
    }

    public static FamilyMember create(final Client client, final CodeValue salutation, final String firstname, final String middlename,
            final String lastname, final CodeValue relationship, final CodeValue gender, final LocalDate dateOfBirth,
            final CodeValue occupation, final CodeValue education) {

        return new FamilyMember(client, salutation, firstname, middlename, lastname, relationship, gender, dateOfBirth, occupation,
                education);
    }

    /**
     * Updates FamilyMember details based on the passed JSON command and returns
     * Details of the actual updates made. Additionally, any foreign Key
     * relationships are not updated in this method, the change is identified in
     * the returned response
     * 
     * @param command
     * @return
     */
    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>();
        final String dateFormatAsInput = command.dateFormat();
        final String localeAsInput = command.locale();

        if (command.isChangeInStringParameterNamed(FamilyMemberApiConstants.firstnameParamName, this.firstname)) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.firstnameParamName);
            actualChanges.put(FamilyMemberApiConstants.firstnameParamName, newValue);
            this.firstname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(FamilyMemberApiConstants.middlenameParamName, this.middlename)) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.middlenameParamName);
            actualChanges.put(FamilyMemberApiConstants.middlenameParamName, newValue);
            this.middlename = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(FamilyMemberApiConstants.lastnameParamName, this.lastname)) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.lastnameParamName);
            actualChanges.put(FamilyMemberApiConstants.lastnameParamName, newValue);
            this.lastname = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInLocalDateParameterNamed(FamilyMemberApiConstants.dobParamName, dateOfBirthLocalDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(FamilyMemberApiConstants.dobParamName);
            actualChanges.put(FamilyMemberApiConstants.dobParamName, valueAsInput);
            actualChanges.put(FamilyMemberApiConstants.dobParamName, dateFormatAsInput);
            actualChanges.put(FamilyMemberApiConstants.dobParamName, localeAsInput);

            final LocalDate newValue = command.localDateValueOfParameterNamed(FamilyMemberApiConstants.dobParamName);
            this.dateOfBirth = newValue.toDate();
        }

        /** Only Identify changes for Foreign key relationships **/

        if (command.isChangeInLongParameterNamed(FamilyMemberApiConstants.salutationParamName, salutationId())) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.salutationParamName);
            actualChanges.put(FamilyMemberApiConstants.salutationParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(FamilyMemberApiConstants.relationshipParamName, relationshipId())) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.relationshipParamName);
            actualChanges.put(FamilyMemberApiConstants.relationshipParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(FamilyMemberApiConstants.genderParamName, genderId())) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.genderParamName);
            actualChanges.put(FamilyMemberApiConstants.genderParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(FamilyMemberApiConstants.occupationalDetailsParamName, occupationalDetsilsId())) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.occupationalDetailsParamName);
            actualChanges.put(FamilyMemberApiConstants.occupationalDetailsParamName, newValue);
        }

        if (command.isChangeInLongParameterNamed(FamilyMemberApiConstants.educationParamName, educationId())) {
            final String newValue = command.stringValueOfParameterNamed(FamilyMemberApiConstants.educationParamName);
            actualChanges.put(FamilyMemberApiConstants.educationParamName, newValue);
        }
        return actualChanges;
    }

    public LocalDate dateOfBirthLocalDate() {
        LocalDate dateOfBirth = null;
        if (this.dateOfBirth != null) {
            dateOfBirth = LocalDate.fromDateFields(this.dateOfBirth);
        }
        return dateOfBirth;
    }

    public Long salutationId() {
        Long salutationId = null;
        if (this.salutation != null) {
            salutationId = this.salutation.getId();
        }
        return salutationId;
    }

    public Long relationshipId() {
        Long relationshipId = null;
        if (this.relationship != null) {
            relationshipId = this.relationship.getId();
        }
        return relationshipId;
    }

    public Long educationId() {
        Long educationId = null;
        if (this.education != null) {
            educationId = this.education.getId();
        }
        return educationId;
    }

    public Long genderId() {
        Long genderId = null;
        if (this.gender != null) {
            genderId = this.gender.getId();
        }
        return genderId;
    }

    public Long occupationalDetsilsId() {
        Long occupationalDetsilsId = null;
        if (this.occupation != null) {
            occupationalDetsilsId = this.occupation.getId();
        }
        return occupationalDetsilsId;
    }

    public void updateGender(CodeValue gender) {
        this.gender = gender;
    }

    public void updateSalutation(CodeValue salutation) {
        this.salutation = salutation;
    }

    public void updateOccupation(CodeValue occupation) {
        this.occupation = occupation;
    }

    public void updateEducation(CodeValue education) {
        this.education = education;
    }

    public void updateRelationship(CodeValue relationship) {
        this.relationship = relationship;
    }

    public Client getClient() {
        return client;
    }

    public CodeValue getSalutation() {
        return salutation;
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

    public CodeValue getRelationship() {
        return relationship;
    }

    public CodeValue getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public CodeValue getOccupation() {
        return occupation;
    }

    public CodeValue getEducation() {
        return education;
    }

}