-- configuration
INSERT INTO hcsigc.configuration(x_conf, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, t_key, t_value) VALUES (nextval('seq_x_conf'), 'INIT', null, null, null, 0, 'Valor por defecto de la cuota asignada a un usuario en bytes', 'quota.default', '1000000000');
INSERT INTO hcsigc.configuration(x_conf, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, t_key, t_value) VALUES (nextval('seq_x_conf'), 'INIT', null, null, null, 0, 'Versión de la herramienta', 'version', '0.5');
INSERT INTO hcsigc.configuration(x_conf, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, t_key, t_value) VALUES (nextval('seq_x_conf'), 'INIT', null, null, null, 0, 'Fecha de la versión', 'version.date', '30/11/2018');

-- dataType
INSERT INTO hcsigc.datatypes(x_datatype, t_created, d_creation_date, d_modification_date, t_modified, version, t_name, t_description) VALUES (nextval('seq_x_datatype'), 'INIT', null, null, null, 0, 'TABLE', 'Tabla alfanumérica');
INSERT INTO hcsigc.datatypes(x_datatype, t_created, d_creation_date, d_modification_date, t_modified, version, t_name, t_description) VALUES (nextval('seq_x_datatype'), 'INIT', null, null, null, 0, 'LAYER', 'Capa vectorial');
INSERT INTO hcsigc.datatypes(x_datatype, t_created, d_creation_date, d_modification_date, t_modified, version, t_name, t_description) VALUES (nextval('seq_x_datatype'), 'INIT', null, null, null, 0, 'RASTER', 'Capa raster');

-- dbms
INSERT INTO hcsigc.dbms(x_dbs, t_created, d_creation_date, d_modification_date, t_modified, version, t_connection_regex, t_name) VALUES (nextval('seq_x_dbs'), 'INIT', null, null, null, 0, 'jdbc:postgresql://{{host}}:{{port}}/{{database}}', 'PostgreSQL');
INSERT INTO hcsigc.dbms(x_dbs, t_created, d_creation_date, d_modification_date, t_modified, version, t_connection_regex, t_name) VALUES (nextval('seq_x_dbs'), 'INIT', null, null, null, 0, 'jdbc:oracle:{{driver_type}}:[]{{username}}/{{password}}]@{{host}}:{{port}}:{{database}}', 'Oracle');

-- organizations_units
INSERT INTO hcsigc.organizations_units(x_ou, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, t_name) VALUES (nextval('seq_x_ou'), 'INIT', null, null, null, 0, 'Unidad Organizativa Global', 'Global');
INSERT INTO hcsigc.organizations_units(x_ou, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, t_name) VALUES (nextval('seq_x_ou'), 'INIT', null, null, null, 0, 'Unidad Organizativa Guadaltel', 'Guadaltel');

-- groups
INSERT INTO hcsigc.groups(x_group, t_created, d_creation_date, d_modification_date, t_modified, version, t_name, t_description, organizationunit_x_ou) VALUES (nextval('seq_x_group'), 'INIT', null, null, null, 0, 'Global', 'Grupo global', 1);
INSERT INTO hcsigc.groups(x_group, t_created, d_creation_date, d_modification_date, t_modified, version, t_name, t_description, organizationunit_x_ou) VALUES (nextval('seq_x_group'), 'INIT', null, null, null, 0, 'Guadaltel-desa', 'Grupo Guadaltel Desa', 2);

-- users
INSERT INTO hcsigc.users(x_user, t_created, d_creation_date, d_modification_date, t_modified, version, l_ldap, t_mail, t_name, t_password, n_quota, t_surname, n_used_quota, t_username, organizationunit_x_ou) VALUES (nextval('seq_x_user'), 'INIT', null, null, null, 0, FALSE, 'superadmin@juntadeandalucia.es', 'Super Administrador', 'MLWRBU6dOqnq1nDLWNM3dCBJ2xQXeGleDLbv6+GX8ic=', 999999, 'Super Administrador', 0, 'superadmin', 1);
INSERT INTO hcsigc.users(x_user, t_created, d_creation_date, d_modification_date, t_modified, version, l_ldap, t_mail, t_name, t_password, n_quota, t_surname, n_used_quota, t_username, organizationunit_x_ou) VALUES (nextval('seq_x_user'), 'INIT', null, null, null, 0, FALSE, 'admin@juntadeandalucia.es', 'Administrador', 'BJK8WTGts27O8x2Cbx9nTJS1HxpgoabbMUudumNQw8k=', 999999, 'de Unidad Organizativa', 0, 'admin', 2);
INSERT INTO hcsigc.users(x_user, t_created, d_creation_date, d_modification_date, t_modified, version, l_ldap, t_mail, t_name, t_password, n_quota, t_surname, n_used_quota, t_username, organizationunit_x_ou) VALUES (nextval('seq_x_user'), 'INIT', null, null, null, 0, FALSE, 'groupadmin@juntadeandalucia.es', 'Administrador de grupos', 'zv3pmJtzxJFjTQcCUxO6Bu+jFwLkCth+xCWY7v6Muk4=', 999999, 'de Usuario', 0, 'grupos', 2);
INSERT INTO hcsigc.users(x_user, t_created, d_creation_date, d_modification_date, t_modified, version, l_ldap, t_mail, t_name, t_password, n_quota, t_surname, n_used_quota, t_username, organizationunit_x_ou) VALUES (nextval('seq_x_user'), 'INIT', null, null, null, 0, FALSE, 'user@juntadeandalucia.es', 'Usuario normal', 'UpPtZvoXo3AmQaLKlYcgoIhwBRr5M4jGa/UozUcV3jc=', 999999, 'apellidos', 0, 'usuario', 2);
INSERT INTO hcsigc.users(x_user, t_created, d_creation_date, d_modification_date, t_modified, version, l_ldap, t_mail, t_name, t_password, n_quota, t_surname, n_used_quota, t_username, organizationunit_x_ou) VALUES (nextval('seq_x_user'), 'INIT', null, null, null, 0, FALSE, 'anonymous@juntadeandalucia.es', 'Usuario anonimo', 'J/tyiEu60jN+fJdMkzQ8YBZLvsBwpG/9crvnDxHKOic=', 999999, 'apellidos', 0, 'anonimo', 2);

-- permissions
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'ADMIN_CONFIG', 'Administrar configuración');
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'ADMIN_DATABASES', 'Administrar Bases de Datos de Grupos');
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'ADMIN_GROUP', 'Administrar grupos');
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'ADMIN_GROUP_USERS', 'Invitar/Desinvitar usuarios');
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'ADMIN_UO', 'Administrar Unidades Organizativas');
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'ADMIN_USERS', 'Administrar usuarios');
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'SHARE_DATA', 'Compartir catálogo de datos');
INSERT INTO hcsigc.permissions(x_perm, t_created, d_creation_date, d_modification_date, t_modified, version, t_code, t_name) VALUES (nextval('seq_x_perm'), 'INIT', null, null, null, 0, 'VIEW_DATA', 'Ver catálogos de datos');

-- permissions_grant
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (7, 4, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (8, 4, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (1, 5, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (2, 5, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (3, 5, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (4, 5, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (6, 5, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (7, 5, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (8, 5, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (3, 6, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (4, 6, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (7, 6, nextval('seq_x_pegr'));
INSERT INTO hcsigc.permissions_grant(permissionsgranted_x_perm, permissionsrequired_x_perm, x_pegr) VALUES (8, 6, nextval('seq_x_pegr'));

-- roles
INSERT INTO hcsigc.roles(x_role, t_created, d_creation_date, d_modification_date, t_modified, version, t_name) VALUES (nextval('seq_x_role'), 'INIT', null, null, null, 0, 'Super Administrador');
INSERT INTO hcsigc.roles(x_role, t_created, d_creation_date, d_modification_date, t_modified, version, t_name) VALUES (nextval('seq_x_role'), 'INIT', null, null, null, 0, 'Administrador UO');
INSERT INTO hcsigc.roles(x_role, t_created, d_creation_date, d_modification_date, t_modified, version, t_name) VALUES (nextval('seq_x_role'), 'INIT', null, null, null, 0, 'Administrador Grupo');
INSERT INTO hcsigc.roles(x_role, t_created, d_creation_date, d_modification_date, t_modified, version, t_name) VALUES (nextval('seq_x_role'), 'INIT', null, null, null, 0, 'Usuario editor');
INSERT INTO hcsigc.roles(x_role, t_created, d_creation_date, d_modification_date, t_modified, version, t_name) VALUES (nextval('seq_x_role'), 'INIT', null, null, null, 0, 'Usuario invitado');

-- datasets
INSERT INTO hcsigc.datasets(x_data, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, l_global, t_name, n_size, t_table_name, t_uuid_metadata, datatype_x_datatype, dbconnection_x_dbconn, remoteconnection_x_reco, user_x_user) VALUES (1, 'INIT', null, null, null, 0, 'Juego de datos de prueba', true, 'Nombre', null, 'Nommbre de tabla', null, 1, null, null, 1);
INSERT INTO hcsigc.datasets(x_data, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, l_global, t_name, n_size, t_table_name, t_uuid_metadata, datatype_x_datatype, dbconnection_x_dbconn, remoteconnection_x_reco, user_x_user) VALUES (2, 'INIT', null, null, null, 0, 'Juego de datos de prueba 2', true, 'Nombre 2', null, 'Nommbre de tabla 2', null, 1, null, null, 2);
INSERT INTO hcsigc.datasets(x_data, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, l_global, t_name, n_size, t_table_name, t_uuid_metadata, datatype_x_datatype, dbconnection_x_dbconn, remoteconnection_x_reco, user_x_user) VALUES (3, 'INIT', null, null, null, 0, 'Juego de datos de prueba con global false', false, 'Nombre global=false', null, 'Nommbre de tabla', null, 1, null, null, 1);
INSERT INTO hcsigc.datasets(x_data, t_created, d_creation_date, d_modification_date, t_modified, version, t_description, l_global, t_name, n_size, t_table_name, t_uuid_metadata, datatype_x_datatype, dbconnection_x_dbconn, remoteconnection_x_reco, user_x_user) VALUES (4, 'INIT', null, null, null, 0, 'Otra prueba mas con global = false', false, 'Nombre p', null, 'Nommbre de tabla 3', null, 1, null, null, 2);

-- role_x_perm
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 1, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 2, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 3, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 4, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 5, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 6, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 7, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (1, 8, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (2, 3, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (2, 4, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (2, 6, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (2, 7, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (2, 8, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (3, 2, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (3, 4, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (3, 7, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (3, 8, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (4, 7, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (4, 8, nextval('seq_x_roper'));
INSERT INTO hcsigc.role_x_perm(roles_x_role, permissions_x_perm, x_roper) VALUES (5, 8, nextval('seq_x_roper'));

-- groups_x_users
INSERT INTO hcsigc.groups_x_users(x_grus, t_created, d_creation_date, d_modification_date, t_modified, version, group_x_group, user_x_user) VALUES (nextval('seq_x_grus'), 'INIT', null, null, null, 0, 1, 1);
INSERT INTO hcsigc.groups_x_users(x_grus, t_created, d_creation_date, d_modification_date, t_modified, version, group_x_group, user_x_user) VALUES (nextval('seq_x_grus'), 'INIT', null, null, null, 0, 2, 2);
INSERT INTO hcsigc.groups_x_users(x_grus, t_created, d_creation_date, d_modification_date, t_modified, version, group_x_group, user_x_user) VALUES (nextval('seq_x_grus'), 'INIT', null, null, null, 0, 2, 3);


-- grus_x_perm
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 1, nextval('seq_x_grpe'));
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 2, nextval('seq_x_grpe'));
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 3, nextval('seq_x_grpe'));
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 4, nextval('seq_x_grpe'));
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 5, nextval('seq_x_grpe'));
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 6, nextval('seq_x_grpe'));
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 7, nextval('seq_x_grpe'));
INSERT INTO hcsigc.grus_x_perm(groupsusers_x_grus, permissions_x_perm, x_grpe) VALUES (1, 8, nextval('seq_x_grpe'));

-- user_x_uo_x_perm
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 1, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 2, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 3, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 4, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 5, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 6, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 7, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (1, 1, 8, nextval('seq_x_uope'), 'INIT', 0);

INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (2, 2, 2, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (2, 2, 3, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (2, 2, 4, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (2, 2, 6, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (2, 2, 7, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (2, 2, 8, nextval('seq_x_uope'), 'INIT', 0);

INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (3, 2, 2, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (3, 2, 4, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (3, 2, 7, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (3, 2, 8, nextval('seq_x_uope'), 'INIT', 0);

INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (4, 2, 7, nextval('seq_x_uope'), 'INIT', 0);
INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (4, 2, 8, nextval('seq_x_uope'), 'INIT', 0);

INSERT INTO hcsigc.user_x_uo_x_perm(user_x_user, ou_x_ou, perm_x_perm, x_uope, t_created, version) VALUES (5, 2, 8, nextval('seq_x_uope'), 'INIT', 0);