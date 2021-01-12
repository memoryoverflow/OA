/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : activiti_db

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 12/01/2021 20:12:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ACT_EVT_LOG
-- ----------------------------
DROP TABLE IF EXISTS `ACT_EVT_LOG`;
CREATE TABLE `ACT_EVT_LOG` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_GE_BYTEARRAY
-- ----------------------------
DROP TABLE IF EXISTS `ACT_GE_BYTEARRAY`;
CREATE TABLE `ACT_GE_BYTEARRAY` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_GE_BYTEARRAY
-- ----------------------------
BEGIN;
INSERT INTO `ACT_GE_BYTEARRAY` VALUES ('45006', 1, 'source', NULL, 0x7B226964223A2263616E766173222C227265736F757263654964223A2263616E766173222C227374656E63696C736574223A7B226E616D657370616365223A22687474703A2F2F62336D6E2E6F72672F7374656E63696C7365742F62706D6E322E3023227D7D, NULL);
COMMIT;

-- ----------------------------
-- Table structure for ACT_GE_PROPERTY
-- ----------------------------
DROP TABLE IF EXISTS `ACT_GE_PROPERTY`;
CREATE TABLE `ACT_GE_PROPERTY` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_GE_PROPERTY
-- ----------------------------
BEGIN;
INSERT INTO `ACT_GE_PROPERTY` VALUES ('next.dbid', '47501', 20);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('schema.history', 'create(5.22.0.0)', 1);
INSERT INTO `ACT_GE_PROPERTY` VALUES ('schema.version', '5.22.0.0', 1);
COMMIT;

-- ----------------------------
-- Table structure for ACT_HI_ACTINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_ACTINST`;
CREATE TABLE `ACT_HI_ACTINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_HI_ATTACHMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_ATTACHMENT`;
CREATE TABLE `ACT_HI_ATTACHMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_HI_COMMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_COMMENT`;
CREATE TABLE `ACT_HI_COMMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_HI_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_DETAIL`;
CREATE TABLE `ACT_HI_DETAIL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_HI_IDENTITYLINK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_IDENTITYLINK`;
CREATE TABLE `ACT_HI_IDENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_HI_PROCINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_PROCINST`;
CREATE TABLE `ACT_HI_PROCINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_HI_TASKINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_TASKINST`;
CREATE TABLE `ACT_HI_TASKINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_HI_VARINST
-- ----------------------------
DROP TABLE IF EXISTS `ACT_HI_VARINST`;
CREATE TABLE `ACT_HI_VARINST` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_ID_GROUP
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_GROUP`;
CREATE TABLE `ACT_ID_GROUP` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_ID_INFO
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_INFO`;
CREATE TABLE `ACT_ID_INFO` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_ID_MEMBERSHIP
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_MEMBERSHIP`;
CREATE TABLE `ACT_ID_MEMBERSHIP` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `ACT_ID_GROUP` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `ACT_ID_USER` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_ID_USER
-- ----------------------------
DROP TABLE IF EXISTS `ACT_ID_USER`;
CREATE TABLE `ACT_ID_USER` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_PROCDEF_INFO
-- ----------------------------
DROP TABLE IF EXISTS `ACT_PROCDEF_INFO`;
CREATE TABLE `ACT_PROCDEF_INFO` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RE_DEPLOYMENT
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_DEPLOYMENT`;
CREATE TABLE `ACT_RE_DEPLOYMENT` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RE_MODEL
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_MODEL`;
CREATE TABLE `ACT_RE_MODEL` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `ACT_RE_DEPLOYMENT` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of ACT_RE_MODEL
-- ----------------------------
BEGIN;
INSERT INTO `ACT_RE_MODEL` VALUES ('45005', 2, '请假', 'leaf', NULL, '2021-01-08 03:08:47.603', '2021-01-08 03:08:47.608', 1, '{\"name\":\"请假\",\"description\":\"\",\"revision\":1}', NULL, '45006', NULL, '');
COMMIT;

-- ----------------------------
-- Table structure for ACT_RE_PROCDEF
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_PROCDEF`;
CREATE TABLE `ACT_RE_PROCDEF` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RU_EVENT_SUBSCR
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_EVENT_SUBSCR`;
CREATE TABLE `ACT_RU_EVENT_SUBSCR` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RU_EXECUTION
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_EXECUTION`;
CREATE TABLE `ACT_RU_EXECUTION` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RU_IDENTITYLINK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_IDENTITYLINK`;
CREATE TABLE `ACT_RU_IDENTITYLINK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `ACT_RU_TASK` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RU_JOB
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_JOB`;
CREATE TABLE `ACT_RU_JOB` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RU_TASK
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_TASK`;
CREATE TABLE `ACT_RU_TASK` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `ACT_RE_PROCDEF` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for ACT_RU_VARIABLE
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_VARIABLE`;
CREATE TABLE `ACT_RU_VARIABLE` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `ACT_GE_BYTEARRAY` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `ACT_RU_EXECUTION` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for act_re_procdef_extend
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef_extend`;
CREATE TABLE `act_re_procdef_extend` (
  `id` varchar(64) NOT NULL,
  `use_role` varchar(64) DEFAULT NULL COMMENT '给流程定义角色使用权限',
  `type` varchar(64) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程定义扩展表：给定义赋予角色权限，对流程定义分个类';

-- ----------------------------
-- Table structure for act_ru_task_user
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task_user`;
CREATE TABLE `act_ru_task_user` (
  `id` varchar(64) NOT NULL,
  `task_user` varchar(225) DEFAULT NULL COMMENT '任务处理人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_department
-- ----------------------------
DROP TABLE IF EXISTS `tb_department`;
CREATE TABLE `tb_department` (
  `id` varchar(64) NOT NULL,
  `dept_name` varchar(64) DEFAULT NULL,
  `dept_phone` varchar(64) DEFAULT NULL,
  `dept_code` varchar(64) DEFAULT NULL,
  `dept_parent_id` varchar(64) DEFAULT NULL,
  `dept_address` varchar(255) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_department
-- ----------------------------
BEGIN;
INSERT INTO `tb_department` VALUES ('047ddd1b-d02e-4d4f-8cfd-2934e674a09f', '技术部Q', '12891389123', 'JI_SHU_BU_12w', '19c5c8e4-0a6a-4c80-84cb-70bd2da6c51b', '广白云区', '111111111111', NULL, NULL, '2020-12-09 23:27:50');
INSERT INTO `tb_department` VALUES ('1', '永健科技', NULL, 'GZ', NULL, '广州', NULL, NULL, '2020-11-30 14:14:19', '2020-11-30 14:14:23');
INSERT INTO `tb_department` VALUES ('19c5c8e4-0a6a-4c80-84cb-70bd2da6c51b', '广州公司总部', '18078542914', 'SUPER', '1', '广州', '111111111111', NULL, '2020-12-09 23:26:16', '2020-12-09 23:26:16');
INSERT INTO `tb_department` VALUES ('2', '研发部', '18078542914', 'CODE_Q123', '19c5c8e4-0a6a-4c80-84cb-70bd2da6c51b', '广州', '111111111111', NULL, '2020-11-30 11:59:28', '2020-12-09 23:28:01');
INSERT INTO `tb_department` VALUES ('4e57025a-cb38-493b-bdb4-1ad564e7a410', '市场部门', NULL, 'SHI_CHANG_BUMEN', '19c5c8e4-0a6a-4c80-84cb-70bd2da6c51b', '广州', NULL, NULL, '2020-11-30 01:43:37', '2020-12-09 23:28:07');
INSERT INTO `tb_department` VALUES ('6d4c6f7c-ab0d-4524-a996-c6ed3e30bda8', '深圳分公司', NULL, 'SHEN_ZHNE', '1', '深圳福田区', NULL, NULL, '2020-12-09 23:33:00', '2020-12-09 23:33:00');
INSERT INTO `tb_department` VALUES ('9b359246-7ed4-4c94-8914-7d35c343fb67', '销售部门', NULL, 'xiaoshou', '6d4c6f7c-ab0d-4524-a996-c6ed3e30bda8', NULL, NULL, NULL, '2020-12-09 23:33:18', '2020-12-09 23:33:18');
INSERT INTO `tb_department` VALUES ('eab46d73-c517-41c4-ae85-3b7960769662', '测试部门', NULL, 'CE_SHI', '19c5c8e4-0a6a-4c80-84cb-70bd2da6c51b', '广州', NULL, NULL, '2020-11-30 01:44:15', '2020-12-09 23:28:13');
INSERT INTO `tb_department` VALUES ('edc9f788-8817-4cdf-a862-e9df13f79775', '运维部门', NULL, 'YUNWEI', '19c5c8e4-0a6a-4c80-84cb-70bd2da6c51b', '广州', NULL, NULL, '2020-11-30 01:44:47', '2020-12-09 23:28:19');
COMMIT;

-- ----------------------------
-- Table structure for tb_form
-- ----------------------------
DROP TABLE IF EXISTS `tb_form`;
CREATE TABLE `tb_form` (
  `id` varchar(64) NOT NULL,
  `form_name` varchar(64) DEFAULT NULL,
  `form_content` text,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `proc_inst_id` varchar(64) DEFAULT NULL COMMENT '流程实例Id',
  `status` int(11) DEFAULT NULL COMMENT '状态：100:表单填写，101:表单提交。1002:待审核',
  `reply` varchar(255) DEFAULT NULL COMMENT '审核意见',
  `agent_user_code` varchar(64) DEFAULT NULL COMMENT '代理人',
  `proc_def_id` varchar(64) DEFAULT NULL COMMENT '流程定义ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `id` varchar(255) NOT NULL,
  `per_name` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `out_join` tinyint(2) DEFAULT NULL,
  `router` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
BEGIN;
INSERT INTO `tb_permission` VALUES ('1', '系统管理', '0', NULL, 1, NULL, '', 'el-icon-s-tools', '2020-12-15 12:02:23', '2020-11-29 18:19:58', 0, 0, 100, 0, NULL);
INSERT INTO `tb_permission` VALUES ('10', '岗位列表', '1', NULL, 2, NULL, 'post:list', 'el-icon-document\n', '2020-11-30 23:31:20', '2020-11-30 12:44:08', 0, 0, 5, 0, '/admin/position/list');
INSERT INTO `tb_permission` VALUES ('11', '添加岗位', '10', NULL, 3, NULL, 'post:add', 'el-icon-document\n', '2020-11-30 23:31:22', '2020-11-30 16:08:54', 0, 0, 5, 0, NULL);
INSERT INTO `tb_permission` VALUES ('12', '删除岗位', '10', NULL, 3, NULL, 'post:del', 'el-icon-document\n', '2020-11-30 23:31:21', '2020-11-30 16:08:54', 0, 0, 5, 0, NULL);
INSERT INTO `tb_permission` VALUES ('13', '修改岗位', '10', NULL, 3, NULL, 'post:update', 'el-icon-document\n', '2020-11-30 23:31:23', '2020-11-30 16:08:54', 0, 0, 5, 0, NULL);
INSERT INTO `tb_permission` VALUES ('14', '工作流', '0', NULL, 1, NULL, '', 'el-icon-rank\n', '2020-12-10 17:19:49', '2020-12-10 17:18:12', 0, 0, 99, 0, NULL);
INSERT INTO `tb_permission` VALUES ('15', '模型列表', '14', NULL, 2, NULL, NULL, 'el-icon-document\n', '2020-12-10 20:11:38', '2020-12-10 20:10:12', 0, 0, NULL, 0, '/admin/activiti/model/list');
INSERT INTO `tb_permission` VALUES ('16', '已发布流程', '14', NULL, 2, NULL, NULL, 'el-icon-document\n', '2020-12-11 14:15:31', '2020-12-10 20:10:12', 0, 0, NULL, 0, '/admin/activiti/process/list');
INSERT INTO `tb_permission` VALUES ('17', '系统监控', '0', NULL, 1, NULL, NULL, 'el-icon-video-camera-solid', '2021-01-12 17:43:51', '2021-01-12 17:43:57', 0, 0, 99, 0, NULL);
INSERT INTO `tb_permission` VALUES ('18', '操作日志', '17', NULL, 2, NULL, 'log:list', 'el-icon-view', '2021-01-12 17:45:26', '2021-01-12 17:45:32', 0, 0, 99, 0, '/admin/syslog/list');
INSERT INTO `tb_permission` VALUES ('2', '用户列表', '1', NULL, 2, '', 'user:list', 'el-icon-document\n', '2020-11-30 23:31:27', '2020-11-29 21:44:36', 0, 0, 2, 0, '/admin/user/list');
INSERT INTO `tb_permission` VALUES ('284234', '表单申请', '413434534', NULL, 2, NULL, NULL, 'el-icon-document\n', '2020-12-11 11:57:31', '2020-12-11 10:52:23', 0, 0, NULL, 0, '/admin/activiti/form/list');
INSERT INTO `tb_permission` VALUES ('3', '角色列表', '1', NULL, 2, NULL, 'role:list', 'el-icon-document\n', '2020-11-30 23:31:24', '2020-11-29 21:52:58', 0, 0, 3, 0, '/admin/role/list');
INSERT INTO `tb_permission` VALUES ('3d6ec426-60bb-4281-8c75-2cfc798a4264', '1324', 'fb4b796c-24bc-4894-905f-bb369b2a7635', NULL, 2, '234', NULL, 'el-icon-platform-eleme', '2021-01-12 01:13:48', '2021-01-12 01:13:48', 0, 0, 99, 1, NULL);
INSERT INTO `tb_permission` VALUES ('4', '菜单列表', '1', NULL, 2, NULL, 'permission:list', 'el-icon-document\n', '2020-11-30 23:31:26', '2020-11-29 21:52:58', 0, 0, 3, 0, '/admin/menu/list');
INSERT INTO `tb_permission` VALUES ('413434534', '个人办公', '0', NULL, 1, NULL, NULL, 'el-icon-s-platform\n', '2020-12-11 10:51:49', '2020-12-11 10:51:49', 0, 0, 2, 0, NULL);
INSERT INTO `tb_permission` VALUES ('5', '添加用户', '2', NULL, 3, NULL, 'user:add', 'el-icon-document\n', '2020-11-30 23:31:25', '2020-11-30 11:26:07', 0, 0, 4, 0, NULL);
INSERT INTO `tb_permission` VALUES ('6', '删除用户', '2', NULL, 3, NULL, 'user:del', 'el-icon-document\n', '2020-11-30 23:31:28', '2020-11-30 11:26:35', 0, 0, 4, 0, NULL);
INSERT INTO `tb_permission` VALUES ('7', '修改用户', '2', NULL, 3, NULL, 'user:update', 'el-icon-document\n', '2020-11-30 23:31:29', '2020-11-30 11:26:53', 0, 0, 4, 0, NULL);
INSERT INTO `tb_permission` VALUES ('8', '密码重置', '2', NULL, 3, NULL, 'user:pwd', 'el-icon-document\n', '2020-11-30 23:31:30', '2020-11-30 11:27:23', 0, 0, 4, 0, NULL);
INSERT INTO `tb_permission` VALUES ('9', '部门列表', '1', NULL, 2, NULL, 'dept:list', 'el-icon-document\n', '2020-11-30 23:31:30', '2020-11-30 12:44:08', 0, 0, 5, 0, '/admin/department/list');
INSERT INTO `tb_permission` VALUES ('9f78fa89-55e6-46bb-bd89-d2e7d008f5c7', 'GitHub', 'fb4b796c-24bc-4894-905f-bb369b2a7635', NULL, 2, 'https://github.com/memoryoverflow', NULL, 'el-icon-s-opportunity', '2020-11-30 23:33:34', '2020-11-30 09:28:36', 0, 0, 99, 1, NULL);
INSERT INTO `tb_permission` VALUES ('9f984419-f044-4b2c-b4d2-e3a0e7403825', '我的博客', 'fb4b796c-24bc-4894-905f-bb369b2a7635', NULL, 2, 'http://thisforyou.cn:180', NULL, 'el-icon-view', '2020-11-30 10:01:31', '2020-11-30 10:01:31', 0, 0, 99, 1, NULL);
INSERT INTO `tb_permission` VALUES ('cfd2fda7-92f3-4454-9d2a-84dfbfd40a20', '博客', 'fb4b796c-24bc-4894-905f-bb369b2a7635', NULL, 2, 'https://blog.memoryoverflow.cn', NULL, 'el-icon-s-promotion', '2021-01-12 01:10:02', '2021-01-12 01:10:02', 0, 0, 99, 1, NULL);
INSERT INTO `tb_permission` VALUES ('fb4b796c-24bc-4894-905f-bb369b2a7635', '外链管理', '0', NULL, 1, NULL, NULL, 'el-icon-s-promotion', '2020-12-15 12:02:50', '2020-11-30 09:15:31', 0, 0, 101, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_position
-- ----------------------------
DROP TABLE IF EXISTS `tb_position`;
CREATE TABLE `tb_position` (
  `id` varchar(64) NOT NULL,
  `position_name` varchar(64) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `dept_id` varchar(64) DEFAULT NULL,
  `position_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_position
-- ----------------------------
BEGIN;
INSERT INTO `tb_position` VALUES ('1', '研发工程师', '狗逼程序员', '2020-11-30 11:59:59', '2020-11-30 02:24:49', '1', 'DOG');
INSERT INTO `tb_position` VALUES ('6490f5d1-a155-4952-a343-3c0d0b83b1c1', '清洁工', NULL, '2020-11-30 02:25:40', '2020-11-30 02:25:40', NULL, 'clear');
INSERT INTO `tb_position` VALUES ('8143ef66-3270-4043-9399-61c5c0d64a0b', 'HR', NULL, '2020-11-30 02:25:06', '2020-11-30 02:25:06', NULL, 'Hr');
INSERT INTO `tb_position` VALUES ('9280d5d3-698d-4602-830d-3e706f62658e', '项目经理', NULL, '2020-11-30 02:20:16', '2020-11-30 02:22:14', NULL, 'code');
INSERT INTO `tb_position` VALUES ('a2910aa8-b4f5-4de9-b183-0dc3fe37f272', '董事长', NULL, '2020-11-30 02:19:46', '2020-11-30 02:21:35', NULL, 'code1');
INSERT INTO `tb_position` VALUES ('c52d5c95-02a2-422c-85c4-4e53f5383e0b', '技术总监', NULL, '2020-11-30 02:24:40', '2020-11-30 02:24:40', NULL, 'JI_SHU_ZONGJIAN');
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` varchar(255) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES ('1', '超级管理员', '最高权限', '2020-04-05 13:37:14', '2020-04-04 14:07:52', 'superAdmin');
INSERT INTO `tb_role` VALUES ('30', '挖掘机', '', '2020-10-24 11:42:04', '2020-10-24 11:41:17', 'wajueji');
INSERT INTO `tb_role` VALUES ('6cd3408c-8761-4ffa-a63e-81ec0cc96f53', '游客', '', '2020-12-10 00:32:51', '2020-12-10 00:32:51', 'tourist');
COMMIT;

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(255) DEFAULT NULL,
  `permission_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_permission` VALUES (1, '1', '1');
INSERT INTO `tb_role_permission` VALUES (2, '1', '2');
INSERT INTO `tb_role_permission` VALUES (3, '1', '3');
INSERT INTO `tb_role_permission` VALUES (4, '1', '4');
INSERT INTO `tb_role_permission` VALUES (5, '1', '5');
INSERT INTO `tb_role_permission` VALUES (6, '1', '6');
INSERT INTO `tb_role_permission` VALUES (7, '1', '7');
INSERT INTO `tb_role_permission` VALUES (8, '1', '8');
INSERT INTO `tb_role_permission` VALUES (9, '1', '9');
INSERT INTO `tb_role_permission` VALUES (10, '1', '10');
INSERT INTO `tb_role_permission` VALUES (16, '1', 'fb4b796c-24bc-4894-905f-bb369b2a7635');
INSERT INTO `tb_role_permission` VALUES (17, '1', '9f78fa89-55e6-46bb-bd89-d2e7d008f5c7');
INSERT INTO `tb_role_permission` VALUES (18, '1', '9f984419-f044-4b2c-b4d2-e3a0e7403825');
INSERT INTO `tb_role_permission` VALUES (28, '1', '14');
INSERT INTO `tb_role_permission` VALUES (29, '1', '11');
INSERT INTO `tb_role_permission` VALUES (30, '1', '13');
INSERT INTO `tb_role_permission` VALUES (31, '1', '15');
INSERT INTO `tb_role_permission` VALUES (32, '1', '413434534');
INSERT INTO `tb_role_permission` VALUES (33, '1', '284234');
INSERT INTO `tb_role_permission` VALUES (34, '1', '16');
INSERT INTO `tb_role_permission` VALUES (36, '1', 'cfd2fda7-92f3-4454-9d2a-84dfbfd40a20');
INSERT INTO `tb_role_permission` VALUES (37, '1', '3d6ec426-60bb-4281-8c75-2cfc798a4264');
INSERT INTO `tb_role_permission` VALUES (38, '1', '17');
INSERT INTO `tb_role_permission` VALUES (39, '1', '18');
COMMIT;

-- ----------------------------
-- Table structure for tb_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_log`;
CREATE TABLE `tb_sys_log` (
  `id` varchar(64) NOT NULL,
  `descr` varchar(255) DEFAULT NULL,
  `host` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` text,
  `error_msg` text,
  `oper_user` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(64) NOT NULL DEFAULT '0',
  `browser` varchar(255) DEFAULT NULL,
  `sys` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sys_log
-- ----------------------------
BEGIN;
INSERT INTO `tb_sys_log` VALUES ('0019308a-f238-4f00-875d-ef1a9297a530', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:02:16', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:02:16');
INSERT INTO `tb_sys_log` VALUES ('0327071e-4063-4009-89f3-67ca2ea9270f', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:35:00', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:35:00');
INSERT INTO `tb_sys_log` VALUES ('0471bcc9-a938-49b0-9ff0-3f7c3686c211', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:51:17', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:51:17');
INSERT INTO `tb_sys_log` VALUES ('04b4466a-9607-4ed7-8a79-763cdf0185d0', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:51:25', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:51:25');
INSERT INTO `tb_sys_log` VALUES ('05470a84-eef0-4ac3-af38-f26332049016', '用户退出', '127.0.0.1', NULL, '/oa/logout', 'LoginController/logout()/POST', '[]', NULL, NULL, '2021-01-12 05:59:19', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:19');
INSERT INTO `tb_sys_log` VALUES ('05747961-317e-4412-8309-8f6c113090e2', '用户登陆', '127.0.0.1', NULL, '/oa/login', 'LoginController/login()/POST', '[{\"loginModel\":{\"loginName\":\"admin\",\"password\":\"admin\"}}]', NULL, 'admin', '2021-01-12 05:59:20', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:20');
INSERT INTO `tb_sys_log` VALUES ('05854105-8adb-4c19-82c2-f101f670a398', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:47:38', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:47:38');
INSERT INTO `tb_sys_log` VALUES ('070d99f5-20a7-459a-bb91-20adecc456d1', '菜单列表', '127.0.0.1', NULL, '/oa/permission/tree/all', 'PermissionController/menuTreeAll()/GET', '[null]', NULL, 'admin', '2021-01-12 06:10:07', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:10:07');
INSERT INTO `tb_sys_log` VALUES ('0999f05f-ba75-49cd-b7e3-25e6efc5f1c4', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:01:41', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:01:41');
INSERT INTO `tb_sys_log` VALUES ('0cb33363-0132-4912-891a-1f21ab8133e0', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:23', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:23');
INSERT INTO `tb_sys_log` VALUES ('0d979c14-e869-487b-8bcf-5d8475924364', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:58:43', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:58:43');
INSERT INTO `tb_sys_log` VALUES ('1391608d-5453-4224-965a-41899050de25', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:48:19', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:48:19');
INSERT INTO `tb_sys_log` VALUES ('1733b8ce-e738-4eff-b81a-edf7ae22c8cd', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:39:32', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('17e9fcdf-5703-465e-a43e-fe07ee01f86a', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:58:54', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:58:54');
INSERT INTO `tb_sys_log` VALUES ('18a002e0-4214-4d71-823e-729bda90f849', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:36:17', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:36:17');
INSERT INTO `tb_sys_log` VALUES ('19c14048-10e7-4e8a-8ec3-f39ac3abd87e', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:00', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:00');
INSERT INTO `tb_sys_log` VALUES ('1b093738-3a0b-4c4f-9561-5410af0d2df6', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:11', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:11');
INSERT INTO `tb_sys_log` VALUES ('1c87f6ee-42f2-4f77-93de-28b2a1916435', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:50:34', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:50:34');
INSERT INTO `tb_sys_log` VALUES ('1f2a1542-54d2-47de-aff4-320c0b22056d', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:04:51', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:04:51');
INSERT INTO `tb_sys_log` VALUES ('20668b28-6222-4bc8-abaa-c7fa4ba5395c', '岗位列表', '127.0.0.1', NULL, '/oa/post/list', 'PositionController/list()/GET', '[{\"param\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:13', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:13');
INSERT INTO `tb_sys_log` VALUES ('208206e0-da92-43b1-a6d6-e4591e0cc60e', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:11:48', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:11:48');
INSERT INTO `tb_sys_log` VALUES ('221a1218-5d99-43ba-98e9-5efa2c2e20ff', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:11:25', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:11:25');
INSERT INTO `tb_sys_log` VALUES ('226d38bb-dcbd-4466-a743-b512e89e01ba', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:10', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:10');
INSERT INTO `tb_sys_log` VALUES ('22bb08a3-043a-4e9f-8459-79d6299a9311', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:10', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:10');
INSERT INTO `tb_sys_log` VALUES ('233c7566-d9e8-4e89-8b76-080173f8e910', '用户退出', '127.0.0.1', NULL, '/oa/logout', 'LoginController/logout()/POST', '[]', NULL, NULL, '2021-01-12 06:00:19', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:00:19');
INSERT INTO `tb_sys_log` VALUES ('24a43970-f9c8-4954-abbd-da095358dbb1', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:51:23', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:51:23');
INSERT INTO `tb_sys_log` VALUES ('24ebf637-26f4-4d2c-92ae-0f7354bff915', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:10:33', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:10:33');
INSERT INTO `tb_sys_log` VALUES ('26854d28-ea75-4e30-9fc8-0715fd139f67', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"正常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:56:30', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:56:30');
INSERT INTO `tb_sys_log` VALUES ('2963fbb6-5e87-4bc9-af55-e10babc563e0', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:55:01', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:55:01');
INSERT INTO `tb_sys_log` VALUES ('2cd1ab9c-91ca-45d8-8258-678654dffaad', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:19:04', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:19:04');
INSERT INTO `tb_sys_log` VALUES ('2de3461d-557a-4edd-95cf-147e5f35278b', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', 'org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.executor.ExecutorException: A query was run and no Result Maps were found for the Mapped Statement \'cn.yj.syslog.mapper.SysLogMapper.findList\'.  It\'s likely that neither a Result Type nor a Result Map was specified.\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:77)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:446)\n	at com.sun.proxy.$Proxy76.selectList(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:230)\n	at org.apache.ibatis.binding.MapperMethod.executeForMany(MapperMethod.java:137)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:75)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:59)\n	at com.sun.proxy.$Proxy82.findList(Unknown Source)\n	at cn.yj.common.ServiceImpl.findList(ServiceImpl.java:52)\n	at cn.yj.syslog.controller.SysLogController.list(SysLogController.java:38)\n	at cn.yj.syslog.controller.SysLogController$$FastClassBySpringCGLIB$$d5fc5108.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at cn.yj.syslog.aspect.OperateLogMethodInterceptor.invoke(OperateLogMethodInterceptor.java:67)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\n	at cn.yj.syslog.controller.SysLogController$$EnhancerBySpringCGLIB$$edd86459.list(<generated>)\n	at cn.yj.syslog.controller.SysLogController$$FastClassBySpringCGLIB$$d5fc5108.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at cn.yj.syslog.aspect.OperateLogMethodInterceptor.invoke(OperateLogMethodInterceptor.java:67)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\n	at cn.yj.syslog.controller.SysLogController$$EnhancerBySpringCGLIB$$d74e6e78.list(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:888)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:793)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\n	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:645)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:750)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\n	at org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\n	at org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)\n	at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\n	at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\n	at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:92)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:526)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:861)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1579)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: org.apache.ibatis.executor.ExecutorException: A query was run and no Result Maps were found for the Mapped Statement \'cn.yj.syslog.mapper.SysLogMapper.findList\'.  It\'s likely that neither a Result Type nor a Result Map was specified.\n	at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.validateResultMapsCount(DefaultResultSetHandler.java:287)\n	at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleResultSets(DefaultResultSetHandler.java:189)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:64)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:79)\n	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:63)\n	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:324)\n	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)\n	at com.github.pagehelper.PageInterceptor.intercept(PageInterceptor.java:136)\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:61)\n	at com.sun.proxy.$Proxy137.query(Unknown Source)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:148)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:141)\n	at sun.reflect.GeneratedMethodAccessor163.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:433)\n	... 99 more\n', 'admin', '2021-01-12 03:49:46', '异常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:49:46');
INSERT INTO `tb_sys_log` VALUES ('2e55850a-1440-4b70-b0c8-43f1d2edac7d', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:53:09', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:53:09');
INSERT INTO `tb_sys_log` VALUES ('308898e1-4017-460c-8119-1c2d751ea67d', '用户登陆', '127.0.0.1', NULL, '/oa/login', 'LoginController/login()/POST', '[{\"loginModel\":{\"loginName\":\"admin\",\"password\":\"admin\"}}]', NULL, NULL, '2021-01-12 06:03:22', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:03:22');
INSERT INTO `tb_sys_log` VALUES ('30b33254-3894-4d23-b08b-c706fc0bfa52', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:32:04', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('327b4789-2fd4-4d36-b238-b57cdbbcb023', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:57:41', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:57:41');
INSERT INTO `tb_sys_log` VALUES ('32c9a214-46df-46b2-9ccc-0123b7cf5e3a', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:58:59', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:58:59');
INSERT INTO `tb_sys_log` VALUES ('36415945-28a7-48fe-88ce-4462dbd36c17', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:50:36', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:50:36');
INSERT INTO `tb_sys_log` VALUES ('37bf5b6e-dd67-48db-a88f-b5c118169e4b', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:18:40', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:18:40');
INSERT INTO `tb_sys_log` VALUES ('389443e9-e3c7-450a-a350-f84d03bd0e73', '用户登陆', '127.0.0.1', NULL, '/oa/login', 'LoginController/login()/POST', '[{\"loginModel\":{\"loginName\":\"admin\",\"password\":\"admin\"}}]', NULL, 'admin', '2021-01-12 06:00:53', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:00:53');
INSERT INTO `tb_sys_log` VALUES ('3a8656ee-12ec-4e82-bbf3-be31e4622105', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 03:51:17', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:51:17');
INSERT INTO `tb_sys_log` VALUES ('3ca96102-1fba-42bf-8109-150ea2a24145', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:09:58', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:09:58');
INSERT INTO `tb_sys_log` VALUES ('3deb19ff-e686-4537-9be1-2c18176948ff', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:01:40', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:01:40');
INSERT INTO `tb_sys_log` VALUES ('3ff2ffbf-43cf-408d-aedd-81a97a9da08d', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:36:39', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:36:39');
INSERT INTO `tb_sys_log` VALUES ('40baaf85-0552-425c-8db6-d403496664fe', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:12:33', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:12:33');
INSERT INTO `tb_sys_log` VALUES ('40bbe135-8ba0-4f8b-818b-f337f3574ef7', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:36:41', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('412ecd4d-3ea6-45b9-a148-7242dcb479d2', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:48:10', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:48:10');
INSERT INTO `tb_sys_log` VALUES ('4162e7cd-0d1a-42da-a5c2-cc9e421082be', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:09:03', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:09:03');
INSERT INTO `tb_sys_log` VALUES ('42f102bd-c4b7-4a2b-96b8-6d680c5b0a74', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:37:15', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:37:15');
INSERT INTO `tb_sys_log` VALUES ('43a9e22c-aac9-4253-9886-f35835404437', '用户退出', '127.0.0.1', NULL, '/oa/logout', 'LoginController/logout()/POST', '[]', NULL, NULL, '2021-01-12 05:58:43', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:58:43');
INSERT INTO `tb_sys_log` VALUES ('43bf5f47-01d0-4b64-8f30-87c20c46788d', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"正常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:58:20', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:58:20');
INSERT INTO `tb_sys_log` VALUES ('471fa72e-04ea-4f62-850e-e06d8a185089', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:49:31', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:49:31');
INSERT INTO `tb_sys_log` VALUES ('4806fbd6-76ce-4787-9d01-319f42d165f1', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:09', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:09');
INSERT INTO `tb_sys_log` VALUES ('4942a5f3-43f3-4b89-9aa2-940249758097', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:06:51', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:06:51');
INSERT INTO `tb_sys_log` VALUES ('49d8d6b1-575d-417f-9102-05822e5a1767', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:56:31', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:56:31');
INSERT INTO `tb_sys_log` VALUES ('49fb99fa-3abd-4135-96d4-cee73faf6bdd', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:10:37', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:10:37');
INSERT INTO `tb_sys_log` VALUES ('4a30cd2c-d83b-4848-9271-c650279c17dd', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:04:41', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:04:41');
INSERT INTO `tb_sys_log` VALUES ('4cccfc67-9086-4090-835f-2ac965f2bb05', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:03:54', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:03:54');
INSERT INTO `tb_sys_log` VALUES ('4d4666f2-7389-4457-ac6a-8ad80797a33d', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:11', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:11');
INSERT INTO `tb_sys_log` VALUES ('4e22b40f-1d77-4017-a3ec-66916a59cb5e', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 03:55:00', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:55:00');
INSERT INTO `tb_sys_log` VALUES ('4e543424-f8ec-4070-9c80-bd0035c991bf', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"正常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:57:43', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:57:43');
INSERT INTO `tb_sys_log` VALUES ('4eac907b-490d-471b-ae60-2de074be3586', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 06:03:28', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:03:28');
INSERT INTO `tb_sys_log` VALUES ('4f7b3ab3-2d37-4c4e-9807-cc2f2fdc41ab', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"正常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', 'org.springframework.jdbc.BadSqlGrammarException: \n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'u.type\' in \'where clause\'\n### The error may exist in file [/Users/yongjian/work/oa-project/sys-oa/service-syslog/target/classes/mapper/SysLogMapper.xml]\n### The error may involve cn.yj.syslog.mapper.SysLogMapper.findList-Inline\n### The error occurred while setting parameters\n### SQL: SELECT count(0) FROM tb_sys_log WHERE u.type = ?\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'u.type\' in \'where clause\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'u.type\' in \'where clause\'\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:235)\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:73)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:446)\n	at com.sun.proxy.$Proxy76.selectList(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:230)\n	at org.apache.ibatis.binding.MapperMethod.executeForMany(MapperMethod.java:137)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:75)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:59)\n	at com.sun.proxy.$Proxy82.findList(Unknown Source)\n	at cn.yj.common.ServiceImpl.findList(ServiceImpl.java:52)\n	at cn.yj.syslog.controller.SysLogController.list(SysLogController.java:38)\n	at cn.yj.syslog.controller.SysLogController$$FastClassBySpringCGLIB$$d5fc5108.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at cn.yj.syslog.aspect.OperateLogMethodInterceptor.invoke(OperateLogMethodInterceptor.java:67)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\n	at cn.yj.syslog.controller.SysLogController$$EnhancerBySpringCGLIB$$addc8506.list(<generated>)\n	at cn.yj.syslog.controller.SysLogController$$FastClassBySpringCGLIB$$d5fc5108.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at cn.yj.syslog.aspect.OperateLogMethodInterceptor.invoke(OperateLogMethodInterceptor.java:67)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\n	at cn.yj.syslog.controller.SysLogController$$EnhancerBySpringCGLIB$$37247c38.list(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:888)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:793)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\n	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:645)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:750)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\n	at org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\n	at org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)\n	at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\n	at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\n	at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:92)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:526)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:861)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1579)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: java.sql.SQLSyntaxErrorException: Unknown column \'u.type\' in \'where clause\'\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)\n	at com.mysql.cj.jdbc.ClientPreparedStatement.execute(ClientPreparedStatement.java:370)\n	at com.alibaba.druid.pool.DruidPooledPreparedStatement.execute(DruidPooledPreparedStatement.java:497)\n	at sun.reflect.GeneratedMethodAccessor123.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.apache.ibatis.logging.jdbc.PreparedStatementLogger.invoke(PreparedStatementLogger.java:59)\n	at com.sun.proxy.$Proxy97.execute(Unknown Source)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:63)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:79)\n	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:63)\n	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:324)\n	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)\n	at com.github.pagehelper.PageInterceptor.executeAutoCount(PageInterceptor.java:201)\n	at com.github.pagehelper.PageInterceptor.intercept(PageInterceptor.java:113)\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:61)\n	at com.sun.proxy.$Proxy137.query(Unknown Source)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:148)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:141)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:433)\n	... 99 more\n', 'admin', '2021-01-12 03:55:41', '异常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:55:41');
INSERT INTO `tb_sys_log` VALUES ('510aa85a-53a0-4458-b668-46f73e7d72fa', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 06:03:32', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:03:32');
INSERT INTO `tb_sys_log` VALUES ('528e9f13-1806-4e13-90ce-dc184a7a4330', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:02:31', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:02:31');
INSERT INTO `tb_sys_log` VALUES ('52d9a0f3-304e-4a27-b0e9-a00db310dcff', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:46:10', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:46:10');
INSERT INTO `tb_sys_log` VALUES ('52de4393-56b8-497c-b360-c8e71a32326c', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:58:56', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:58:56');
INSERT INTO `tb_sys_log` VALUES ('535c2acf-3744-4aaf-86ad-c2594f6523af', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"正常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:58:40', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:58:40');
INSERT INTO `tb_sys_log` VALUES ('56282ead-87c1-4a3b-af8d-31168c0b1e56', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:11', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:11');
INSERT INTO `tb_sys_log` VALUES ('591e0552-296e-40ce-9047-6564932644ab', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:10', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:10');
INSERT INTO `tb_sys_log` VALUES ('5963eab3-490f-400a-bf22-4d0b5cae4407', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:12:40', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:12:40');
INSERT INTO `tb_sys_log` VALUES ('5b006e7e-b007-4bb8-8613-98c33e304c5d', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:01:25', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:01:25');
INSERT INTO `tb_sys_log` VALUES ('5ec449ba-5b27-4364-83b5-9ea849646a5d', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:10', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:10');
INSERT INTO `tb_sys_log` VALUES ('5ef2eb05-8204-48ff-aeac-7a9daab101e4', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:04:51', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:04:51');
INSERT INTO `tb_sys_log` VALUES ('63836360-b15d-4f3e-94ea-c282cac98528', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:59:05', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:59:05');
INSERT INTO `tb_sys_log` VALUES ('66302cb5-56cf-42d1-a35c-ddfe02583dc9', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:45:49', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:45:49');
INSERT INTO `tb_sys_log` VALUES ('675caa2d-6fad-47af-ab77-d92c69cb8161', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:35:12', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:35:12');
INSERT INTO `tb_sys_log` VALUES ('6b8038d0-d691-42f8-b7f4-40425830ce14', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:56:29', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:56:29');
INSERT INTO `tb_sys_log` VALUES ('6ca511de-5ff7-4ab3-85af-1e2e7c1623ad', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:09:13', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:09:13');
INSERT INTO `tb_sys_log` VALUES ('702bcf99-9a2b-4c7a-912b-50f24da5850b', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:08', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:08');
INSERT INTO `tb_sys_log` VALUES ('70b13d4c-0ee0-4898-9a39-f0e285dab5a1', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:35:39', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:35:39');
INSERT INTO `tb_sys_log` VALUES ('70b55176-8815-4118-9e52-1cc6b0240707', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:10', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:10');
INSERT INTO `tb_sys_log` VALUES ('71557771-9081-458f-8c42-eb81ed97fbe2', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:10:33', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:10:33');
INSERT INTO `tb_sys_log` VALUES ('716c8f41-fde6-46f7-82a9-47364fd85876', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:10:49', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:10:49');
INSERT INTO `tb_sys_log` VALUES ('78066104-dd94-46a0-b535-e52494a7860c', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:51:19', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:51:19');
INSERT INTO `tb_sys_log` VALUES ('7c8e30ed-90e3-4863-93a8-e79190fa9f2a', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:10:32', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:10:32');
INSERT INTO `tb_sys_log` VALUES ('7d2b5d1d-ea13-4a3e-a6ac-e68a6ea1baab', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:36:42', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:36:42');
INSERT INTO `tb_sys_log` VALUES ('7e899515-f02d-4634-97c6-0e8d43e54485', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:03:09', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:03:09');
INSERT INTO `tb_sys_log` VALUES ('7e9539bb-b5e8-47eb-a4a0-8e991db9b9cf', '岗位列表', '127.0.0.1', NULL, '/oa/post/list', 'PositionController/list()/GET', '[{\"param\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 06:10:06', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:10:06');
INSERT INTO `tb_sys_log` VALUES ('7f35292c-3569-4c89-99da-a363e44ef1ef', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:37:30', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('7fee6b8f-9b9d-4bc0-97de-fd0feff7df2e', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:11:31', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:11:31');
INSERT INTO `tb_sys_log` VALUES ('82a56ae1-46e1-4969-93e6-431b63e45dd2', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:04:55', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:04:55');
INSERT INTO `tb_sys_log` VALUES ('8591a187-72db-4942-8939-acfa8395344a', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:34:56', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:34:56');
INSERT INTO `tb_sys_log` VALUES ('8d84e650-8ba0-4461-aa64-9c9d935de5ad', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:47:36', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:47:36');
INSERT INTO `tb_sys_log` VALUES ('907d8bdb-ed55-466d-9c85-d808366477b0', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:06:38', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:06:38');
INSERT INTO `tb_sys_log` VALUES ('91d7c29d-b487-4186-9975-3f510a6b8f42', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:09', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:09');
INSERT INTO `tb_sys_log` VALUES ('935d5652-bdda-4e1f-95ea-96f700f974f1', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:18:51', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:18:51');
INSERT INTO `tb_sys_log` VALUES ('93962e82-b2c1-41dd-aec6-7a187ac8bddc', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 06:00:57', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:00:57');
INSERT INTO `tb_sys_log` VALUES ('997bf7a4-bb82-48d0-bf19-a691e4388965', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:34:19', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:34:19');
INSERT INTO `tb_sys_log` VALUES ('99954a04-52bf-45d6-a9ea-2a26acc55ae8', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:37:53', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('9bd57a5f-9511-4206-87e8-ca556a97cb6f', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:34:37', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:34:37');
INSERT INTO `tb_sys_log` VALUES ('9c4973a0-adce-4540-a636-2cd6baa265a8', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:19:08', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:19:08');
INSERT INTO `tb_sys_log` VALUES ('9c9cdc92-9b42-4240-8e4b-542558fce461', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:12:52', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:12:52');
INSERT INTO `tb_sys_log` VALUES ('9ccecdba-bb3a-4347-997b-73e17bd15f37', '用户退出', '127.0.0.1', NULL, '/oa/logout', 'LoginController/logout()/POST', '[]', NULL, 'admin', '2021-01-12 06:10:08', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:10:08');
INSERT INTO `tb_sys_log` VALUES ('9db19e31-9fbf-4a9f-9b5b-7ec83d8d3c78', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:03', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:03');
INSERT INTO `tb_sys_log` VALUES ('a22dd89e-bcc5-4afe-a366-3c6ee52828b6', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 03:53:43', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:53:43');
INSERT INTO `tb_sys_log` VALUES ('a357670d-18d0-4f90-9b4f-1bda7c26e28a', '用户登陆', '127.0.0.1', NULL, '/oa/login', 'LoginController/login()/POST', '[{\"loginModel\":{\"loginName\":\"admin\",\"password\":\"admin\"}}]', NULL, 'admin', '2021-01-12 05:58:45', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:58:45');
INSERT INTO `tb_sys_log` VALUES ('a46c4464-e5d6-4cfc-9141-6bb324cafbe3', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:35:09', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:35:09');
INSERT INTO `tb_sys_log` VALUES ('a7c8ba8b-d4a4-4309-ad5d-87fd689fb0d2', '查看部门列表', '127.0.0.1', NULL, '/oa/dept/treeData', 'DepartmentController/treeData()/GET', '[]', NULL, 'admin', '2021-01-12 05:58:42', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:58:42');
INSERT INTO `tb_sys_log` VALUES ('a961bd7f-ebce-4f05-9f10-0662a68a1b86', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:10:33', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:10:33');
INSERT INTO `tb_sys_log` VALUES ('ac771aac-4bc1-49d5-bdc9-a19703356a8b', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"异常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:58:26', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:58:26');
INSERT INTO `tb_sys_log` VALUES ('ad658994-2691-4aef-b775-280febeb2423', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:49:02', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:49:02');
INSERT INTO `tb_sys_log` VALUES ('af69338f-30f3-4212-8bc4-737d2a92c765', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"3\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:57:56', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:57:56');
INSERT INTO `tb_sys_log` VALUES ('b156168d-8844-4d40-a6c5-cb8311edc22b', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:35:48', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:35:48');
INSERT INTO `tb_sys_log` VALUES ('b187d035-df3d-47ce-b3e1-8f9bfad93e8a', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:16', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:16');
INSERT INTO `tb_sys_log` VALUES ('b23a3fc3-d4ed-4d78-bba4-0b39b335c422', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:02:07', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:02:07');
INSERT INTO `tb_sys_log` VALUES ('b3988245-3200-47c1-b15d-0077d059225d', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:08:57', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:08:57');
INSERT INTO `tb_sys_log` VALUES ('b4861d2c-77cf-47aa-83a7-62969e6c3ee5', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"正常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:57:45', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:57:45');
INSERT INTO `tb_sys_log` VALUES ('b50df25e-e6fa-4f3a-9707-5d49004fe151', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:03:42', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:03:42');
INSERT INTO `tb_sys_log` VALUES ('b66c43d1-31c8-43ee-9264-590d67011eaf', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 03:57:49', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:57:49');
INSERT INTO `tb_sys_log` VALUES ('b73458a1-d689-453d-aebe-931fcd0a9318', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:37:30', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('babda889-8acd-4c70-80ab-77ea25c86a28', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:45:47', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:45:47');
INSERT INTO `tb_sys_log` VALUES ('bb2c146f-6e96-47df-bca8-b37f10d7952a', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:39:48', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('bcb63896-dd6c-477f-aacd-f7d4f369321b', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"type\":\"异常\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:56:34', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:56:34');
INSERT INTO `tb_sys_log` VALUES ('bd3fed8d-6edf-4199-b316-9c4e3fc19880', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:55:24', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:55:24');
INSERT INTO `tb_sys_log` VALUES ('bddabd76-ad40-4b94-a17e-91e4d5f93f95', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:41:48', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:41:48');
INSERT INTO `tb_sys_log` VALUES ('c28abc60-d086-47f9-9b3e-5f30fbf7a275', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:03:45', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:03:45');
INSERT INTO `tb_sys_log` VALUES ('caa6ac9f-cdee-4277-944d-dba5f90ff48d', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"2\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:57:55', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:57:55');
INSERT INTO `tb_sys_log` VALUES ('cc2cd5da-2c0f-4ec5-9afb-d437098094ac', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:32:04', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('ce368786-c104-41e7-9869-d1e477856be0', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:46:51', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:46:51');
INSERT INTO `tb_sys_log` VALUES ('cee05849-7b8f-42d2-a121-011667a32c78', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:48:59', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:48:59');
INSERT INTO `tb_sys_log` VALUES ('cf19c2ea-a1dc-4e6e-80d2-0b9cc8b196fa', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:04:04', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:04:04');
INSERT INTO `tb_sys_log` VALUES ('d262e9e6-0cbb-40d4-9993-2df3cd596a89', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:01:09', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:01:09');
INSERT INTO `tb_sys_log` VALUES ('d474cafe-d759-4007-9e6d-0fabc2bc7dfd', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:10:30', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:10:30');
INSERT INTO `tb_sys_log` VALUES ('d67d405b-957b-4c3d-ab23-3e19245b3938', '用户退出', '127.0.0.1', NULL, '/oa/logout', 'LoginController/logout()/POST', '[]', NULL, 'admin', '2021-01-12 06:04:01', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:04:01');
INSERT INTO `tb_sys_log` VALUES ('d7074bda-1ca0-4868-ab91-d5328acba2e9', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:04:04', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:04:04');
INSERT INTO `tb_sys_log` VALUES ('d917cb35-d3e3-4416-aa45-f02fad065698', '用户登陆', '127.0.0.1', NULL, '/oa/login', 'LoginController/login()/POST', '[{\"loginModel\":{\"loginName\":\"admin\",\"password\":\"admin\"}}]', NULL, NULL, '2021-01-12 06:10:00', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:10:00');
INSERT INTO `tb_sys_log` VALUES ('d94ecec3-3908-43f3-8c06-c8a8cd72d481', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:37:57', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('db56087c-75eb-4f85-bcb3-82856e05b136', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:11:51', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:11:51');
INSERT INTO `tb_sys_log` VALUES ('dba63603-c3f3-43b2-a9bc-52406128a8f2', '用户退出', '127.0.0.1', NULL, '/oa/logout', 'LoginController/logout()/POST', '[]', NULL, 'admin', '2021-01-12 06:03:21', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 06:03:21');
INSERT INTO `tb_sys_log` VALUES ('dbf26222-79cc-4245-95e5-7ccef7fafa16', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:02:57', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:02:57');
INSERT INTO `tb_sys_log` VALUES ('e0075be3-f490-4686-b7d2-f525e3c33c8e', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:51:12', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:51:12');
INSERT INTO `tb_sys_log` VALUES ('e386049d-0a25-44dd-90d9-b934d5d56169', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:12:49', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:12:49');
INSERT INTO `tb_sys_log` VALUES ('ead8a243-1cc3-4aa1-9671-cc1f1168addd', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:46:34', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:46:34');
INSERT INTO `tb_sys_log` VALUES ('ecfb5543-4c9e-40b3-a97d-29f71dcc0201', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:02:41', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:02:41');
INSERT INTO `tb_sys_log` VALUES ('ee44f47c-e291-4a73-a5d9-f0bc17872d52', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:57:57', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:57:57');
INSERT INTO `tb_sys_log` VALUES ('eef448c3-f95d-499e-a270-3a20c18b8751', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 03:58:46', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:58:46');
INSERT INTO `tb_sys_log` VALUES ('efb4a61b-38a0-4b84-aabb-e4c11839a1bf', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:59:09', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:59:09');
INSERT INTO `tb_sys_log` VALUES ('f126120b-7b53-4ef7-8fdd-b9a7a1bc8ea0', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:53:51', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:53:51');
INSERT INTO `tb_sys_log` VALUES ('f12b99dd-97cf-4f40-9d50-4aa223d279f2', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', NULL, 'admin', '2021-01-12 05:03:22', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:03:22');
INSERT INTO `tb_sys_log` VALUES ('f1e5ce35-8fc3-43b6-82c4-d6514a58b359', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\"}}]', 'org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.executor.ExecutorException: A query was run and no Result Maps were found for the Mapped Statement \'cn.yj.syslog.mapper.SysLogMapper.findList\'.  It\'s likely that neither a Result Type nor a Result Map was specified.\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:77)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:446)\n	at com.sun.proxy.$Proxy76.selectList(Unknown Source)\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:230)\n	at org.apache.ibatis.binding.MapperMethod.executeForMany(MapperMethod.java:137)\n	at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:75)\n	at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:59)\n	at com.sun.proxy.$Proxy82.findList(Unknown Source)\n	at cn.yj.common.ServiceImpl.findList(ServiceImpl.java:52)\n	at cn.yj.syslog.controller.SysLogController.list(SysLogController.java:38)\n	at cn.yj.syslog.controller.SysLogController$$FastClassBySpringCGLIB$$d5fc5108.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at cn.yj.syslog.aspect.OperateLogMethodInterceptor.invoke(OperateLogMethodInterceptor.java:67)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:93)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\n	at cn.yj.syslog.controller.SysLogController$$EnhancerBySpringCGLIB$$edd86459.list(<generated>)\n	at cn.yj.syslog.controller.SysLogController$$FastClassBySpringCGLIB$$d5fc5108.invoke(<generated>)\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at cn.yj.syslog.aspect.OperateLogMethodInterceptor.invoke(OperateLogMethodInterceptor.java:67)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175)\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\n	at cn.yj.syslog.controller.SysLogController$$EnhancerBySpringCGLIB$$d74e6e78.list(<generated>)\n	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:888)\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:793)\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040)\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\n	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:645)\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:750)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\n	at org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\n	at org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)\n	at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\n	at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\n	at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\n	at org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\n	at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:92)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:526)\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:861)\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1579)\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	at java.lang.Thread.run(Thread.java:748)\nCaused by: org.apache.ibatis.executor.ExecutorException: A query was run and no Result Maps were found for the Mapped Statement \'cn.yj.syslog.mapper.SysLogMapper.findList\'.  It\'s likely that neither a Result Type nor a Result Map was specified.\n	at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.validateResultMapsCount(DefaultResultSetHandler.java:287)\n	at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleResultSets(DefaultResultSetHandler.java:189)\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:64)\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:79)\n	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:63)\n	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:324)\n	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)\n	at com.github.pagehelper.PageInterceptor.intercept(PageInterceptor.java:136)\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:61)\n	at com.sun.proxy.$Proxy137.query(Unknown Source)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:148)\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:141)\n	at sun.reflect.GeneratedMethodAccessor163.invoke(Unknown Source)\n	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.lang.reflect.Method.invoke(Method.java:498)\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:433)\n	... 99 more\n', 'admin', '2021-01-12 03:50:06', '异常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:50:06');
INSERT INTO `tb_sys_log` VALUES ('f32676dd-8383-4792-adc1-e06622b994d2', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:08:48', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:08:48');
INSERT INTO `tb_sys_log` VALUES ('f5079a06-932d-42b5-821d-db8e8a302bc2', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:36:41', '正常', 'Chrome 8', 'Mac OS X', NULL);
INSERT INTO `tb_sys_log` VALUES ('f80dc86c-3b9d-42c5-9242-c7f9a8c44157', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"2\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:53:49', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:53:49');
INSERT INTO `tb_sys_log` VALUES ('fa0b812c-a753-43f5-b410-5e368c4619e9', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 05:18:47', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 05:18:47');
INSERT INTO `tb_sys_log` VALUES ('fbee8846-0193-4551-bc2d-b90b27a99f80', '查看操做日志列表', '127.0.0.1', NULL, '/oa/sysLog/list', 'SysLogController/list()/GET', '[{\"arg0\":{\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:51:26', '正常', 'Chrome 8', 'Mac OS X', '2021-01-12 03:51:26');
INSERT INTO `tb_sys_log` VALUES ('fcbdf36f-52f7-4c95-8717-a533c7a3bd31', '用户列表', '127.0.0.1', NULL, '/oa/user/list', 'UserController/list()/GET', '[{\"param\":{\"name\":\"\",\"phone\":\"\",\"email\":\"\",\"pageNum\":\"1\",\"pageSize\":\"15\"}}]', NULL, 'admin', '2021-01-12 03:39:02', '正常', 'Chrome 8', 'Mac OS X', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `position_id` varchar(255) DEFAULT NULL,
  `dept_id` varchar(255) DEFAULT NULL,
  `emp_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES ('111111111111', '管理员', 'admin', 'f6fdffe48c908deb0f4c3bd36c032e72', '18078542914', 1, '2020-12-10 04:05:17', '2020-11-29 17:30:46', 0, '1375668614@qq.com', NULL, '22a1be39-998f-402e-912f-379ba755720b', '1', '047ddd1b-d02e-4d4f-8cfd-2934e674a09f', 'admin');
INSERT INTO `tb_user` VALUES ('a65a1aec-856a-47be-9b5c-0372d41d10ce', '1', '1', 'bb5e12f554fea6dc579e0ac4d75877e2', '1', 1, '2021-01-12 01:36:07', '2021-01-12 01:36:07', 0, '11231@qq.com', NULL, NULL, '1', '047ddd1b-d02e-4d4f-8cfd-2934e674a09f', '1');
COMMIT;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_role` VALUES (5, '111111111111', '1');
INSERT INTO `tb_user_role` VALUES (6, 'a65a1aec-856a-47be-9b5c-0372d41d10ce', '6cd3408c-8761-4ffa-a63e-81ec0cc96f53');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
