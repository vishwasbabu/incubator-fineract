
/*Store details of Family members*/
CREATE TABLE `f_family_member` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`client_id` BIGINT(20) NOT NULL,
	`salutation_cv_id` INT(11) NULL DEFAULT NULL,
	`firstname` VARCHAR(50) NOT NULL,
	`middlename` VARCHAR(50) NULL DEFAULT NULL,
	`lastname` VARCHAR(50) NULL DEFAULT NULL,
	`relationship_cv_id` INT(11) NULL DEFAULT NULL,
	`gender_cv_id` INT(11) NULL DEFAULT NULL,
	`date_of_birth` DATE NULL DEFAULT NULL,
	`occupation_details_cv_id` INT(11) NULL DEFAULT NULL,
	`education_cv_id` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `client_id` (`client_id`),
	INDEX `FK_f_family_members_m_code_value` (`salutation_cv_id`),
	INDEX `FK_f_family_members_m_code_value_2` (`relationship_cv_id`),
	INDEX `FK_f_family_members_m_code_value_3` (`gender_cv_id`),
	INDEX `FK_f_family_members_m_code_value_4` (`occupation_details_cv_id`),
	INDEX `FK_f_family_members_m_code_value_5` (`education_cv_id`),
	CONSTRAINT `FK_f_family_members_m_code_value` FOREIGN KEY (`salutation_cv_id`) REFERENCES `m_code_value` (`id`),
	CONSTRAINT `FK_f_family_members_m_code_value_2` FOREIGN KEY (`relationship_cv_id`) REFERENCES `m_code_value` (`id`),
	CONSTRAINT `FK_f_family_members_m_code_value_3` FOREIGN KEY (`gender_cv_id`) REFERENCES `m_code_value` (`id`),
	CONSTRAINT `FK_f_family_members_m_code_value_4` FOREIGN KEY (`occupation_details_cv_id`) REFERENCES `m_code_value` (`id`),
	CONSTRAINT `FK_f_family_members_m_code_value_5` FOREIGN KEY (`education_cv_id`) REFERENCES `m_code_value` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1
;

/* Permissions for operations on Family Members*/

INSERT INTO `m_permission` (`grouping`, `code`, `entity_name`, `action_name`, `can_maker_checker`) VALUES ('portfolio', 'CREATE_FAMILYMEMBER', 'FAMILYMEMBER', 'CREATE', 0);
INSERT INTO `m_permission` (`grouping`, `code`, `entity_name`, `action_name`, `can_maker_checker`) VALUES ('portfolio', 'UPDATE_FAMILYMEMBER', 'FAMILYMEMBER', 'UPDATE', 0);
INSERT INTO `m_permission` (`grouping`, `code`, `entity_name`, `action_name`, `can_maker_checker`) VALUES ('portfolio', 'DELETE_FAMILYMEMBER', 'FAMILYMEMBER', 'DELETE', 0);
INSERT INTO `m_permission` (`grouping`, `code`, `entity_name`, `action_name`, `can_maker_checker`) VALUES ('portfolio', 'CREATE_FAMILYMEMBER_CHECKER', 'FAMILYMEMBER', 'CREATE', 0);
INSERT INTO `m_permission` (`grouping`, `code`, `entity_name`, `action_name`, `can_maker_checker`) VALUES ('portfolio', 'UPDATE_FAMILYMEMBER_CHECKER', 'FAMILYMEMBER', 'UPDATE', 0);
INSERT INTO `m_permission` (`grouping`, `code`, `entity_name`, `action_name`, `can_maker_checker`) VALUES ('portfolio', 'DELETE_FAMILYMEMBER_CHECKER', 'FAMILYMEMBER', 'DELETE', 0);

/* Codes for Family Members*/
INSERT INTO `m_code` (`code_name`, `is_system_defined`) VALUES ('Salutation', 1);
INSERT INTO `m_code` (`code_name`, `is_system_defined`) VALUES ('Relationship', 1);
INSERT INTO `m_code` (`code_name`, `is_system_defined`) VALUES ('Education', 1);
INSERT INTO `m_code` (`code_name`, `is_system_defined`) VALUES ('Occupation', 1);