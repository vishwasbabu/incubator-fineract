package org.apache.fineract.portfolio.client.familymember.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.service.CodeValueReadPlatformService;
import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.portfolio.client.familymember.api.FamilyMemberApiConstants;
import org.apache.fineract.portfolio.client.familymember.data.FamilyMemberData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class FamilyMemberReadPlatformServiceImpl implements FamilyMemberReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final CodeValueReadPlatformService codeValueReadPlatformService;

    @Autowired
    public FamilyMemberReadPlatformServiceImpl(RoutingDataSource dataSource, CodeValueReadPlatformService codeValueReadPlatformService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.codeValueReadPlatformService = codeValueReadPlatformService;
    }

    @Override
    public Collection<FamilyMemberData> retrieveAllFamilyMembers(Long clientId) {

        FamilyMemberMapper familyMemberMapper = new FamilyMemberMapper();

        final String sql = "select " + familyMemberMapper.schema() + "where f.client_id = ?";

        return this.jdbcTemplate.query(sql, familyMemberMapper, new Object[] { clientId });

    }

    private static final class FamilyMemberMapper implements RowMapper<FamilyMemberData> {

        @Override
        public FamilyMemberData mapRow(ResultSet rs, @SuppressWarnings("unused") int rowNum) throws SQLException {

            final Long id = JdbcSupport.getLong(rs, "id");
            final String firstname = rs.getString("firstname");
            final String middlename = rs.getString("middlename");
            final String lastname = rs.getString("lastname");
            final Date dateOfBirth = rs.getDate("dateOfBirth");

            /** Populate Code ValuesR306**/
            final CodeValueData relationship = CodeValueData.instance(rs.getLong("relationshipId"), rs.getString("relationshipLabel"));
            final CodeValueData gender = CodeValueData.instance(rs.getLong("genderId"), rs.getString("genderLabel"));
            final CodeValueData occupation = CodeValueData.instance(rs.getLong("occupationId"), rs.getString("occupationLabel"));
            final CodeValueData education = CodeValueData.instance(rs.getLong("educationId"), rs.getString("educationLabel"));
            final CodeValueData salutation = CodeValueData.instance(rs.getLong("salutationId"), rs.getString("salutationLabel"));

            return new FamilyMemberData(id, firstname, middlename, lastname, salutation, relationship, gender, dateOfBirth, education,
                    occupation);
        }

        public String schema() {
            StringBuilder query = new StringBuilder();
            query.append(" f.id as id, f.client_id as clientId, f.firstname as firstname, f.middlename as middlename, f.lastname as lastname, ");
            query.append(" f.salutation_cv_id as salutationId, salutation_code_value.code_value as salutationLabel, ");
            query.append(" f.relationship_cv_id as relationshipId, relationship_code_value.code_value as relationshipLabel, ");
            query.append(" f.occupation_details_cv_id as occupationId, occupation_code_value.code_value as occupationLabel, ");
            query.append(" f.education_cv_id as educationId, education_code_value.code_value as educationLabel, ");
            query.append(" f.gender_cv_id as genderId, gender_code_value.code_value as genderLabel, ");
            query.append(" f.date_of_birth as dateOfBirth");
            query.append(" FROM f_family_member f ");
            query.append(" JOIN m_client mc ON mc.id = f.client_id ");
            query.append(" LEFT JOIN m_code_value salutation_code_value ON salutation_code_value.id = f.salutation_cv_id ");
            query.append(" LEFT JOIN m_code_value relationship_code_value ON relationship_code_value.id = f.relationship_cv_id ");
            query.append(" LEFT JOIN m_code_value gender_code_value ON gender_code_value.id = f.gender_cv_id ");
            query.append(" LEFT JOIN m_code_value occupation_code_value ON occupation_code_value.id = f.occupation_details_cv_id ");
            query.append(" LEFT JOIN m_code_value education_code_value ON education_code_value.id = f.education_cv_id ");

            return query.toString();
        }
    }

    @Override
    public FamilyMemberData retrieveTemplate() {

        final Collection<CodeValueData> genderOptions = this.codeValueReadPlatformService
                .retrieveCodeValuesByCode(FamilyMemberApiConstants.CODE_GENDER);

        final Collection<CodeValueData> occupationOptions = this.codeValueReadPlatformService
                .retrieveCodeValuesByCode(FamilyMemberApiConstants.CODE_OCCUPATION);

        final Collection<CodeValueData> salutationOptions = this.codeValueReadPlatformService
                .retrieveCodeValuesByCode(FamilyMemberApiConstants.CODE_SALUTATION);

        final Collection<CodeValueData> educationOptions = this.codeValueReadPlatformService
                .retrieveCodeValuesByCode(FamilyMemberApiConstants.CODE_EDUCATION);

        final Collection<CodeValueData> relationshipOptions = this.codeValueReadPlatformService
                .retrieveCodeValuesByCode(FamilyMemberApiConstants.CODE_RELATIONSHIP);

        return FamilyMemberData.templateOnTop(salutationOptions, relationshipOptions, genderOptions, educationOptions, occupationOptions);
    }

}