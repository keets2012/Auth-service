SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------

-- Table structure for `group_role`

-- ----------------------------

DROP TABLE IF EXISTS `group_role`;
CREATE TABLE `group_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------

-- Records of group_role

-- ----------------------------



-- ----------------------------

-- Table structure for `oauth_access_token`

-- ----------------------------

DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------

-- Table structure for `oauth_client_details`

-- ----------------------------

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(36) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `purpose` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------

-- Records of oauth_client_details

-- ----------------------------

INSERT INTO `oauth_client_details` VALUES ('frontend', null, '$e0801$65x9sjjnRPuKmqaFn3mICtPYnSWrjE7OB/pKzKTAI4ryhmVoa04cus+9sJcSAFKXZaJ8lcPO1I9H22TZk6EN4A==$o+ZWccaWXSA2t7TxE5VBRvz2W8psujU3RPPvejvNs4U=', 'all', 'password,refresh_token,authorization_code', 'http://localhost:8080', null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------

-- Table structure for `oauth_client_token`

-- ----------------------------

DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------

-- Records of oauth_client_token

-- ----------------------------


-- ----------------------------

-- Table structure for `oauth_code`

-- ----------------------------

DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------

-- Records of oauth_code

-- ----------------------------


-- ----------------------------

-- Table structure for `oauth_refresh_token`

-- ----------------------------

DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------

-- Table structure for `permission`

-- ----------------------------

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(36) NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `permission_index` (`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------

-- Records of permission

-- ----------------------------


-- ----------------------------

-- Table structure for `role`

-- ----------------------------

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_name_desc_index` (`name`,`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------

-- Table structure for `role_permission`

-- ----------------------------

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` varchar(36) NOT NULL,
  `permission_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------

-- Records of role_permission

-- ----------------------------


-- ----------------------------

-- Table structure for `security_group`

-- ----------------------------

DROP TABLE IF EXISTS `security_group`;
CREATE TABLE `security_group` (
  `id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`,`tenant_id`),
  KEY `group_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------

-- Records of security_group

-- ----------------------------

