insert into `platform_data`.`user_type_md`
(`user_type_uuid`, `name`, `description`, `insert_ts`, `update_ts`, `deleted`, `user_session_id`) 
values (NULL, 'gec_field_user', 'GEC field user. With only data submission priviledge.', unix_timestamp()*1000, unix_timestamp()*1000,0,NULL);

insert into user_type_md
(`user_type_uuid`, `name`, `description`, `insert_ts`, `update_ts`, `deleted`, `user_session_id`) 
values (NULL, 'gec_district_admin', 'GEC department user. With data submission and verification priviledge.', unix_timestamp()*1000, unix_timestamp()*1000,0,NULL);

insert into user_type_md
(`user_type_uuid`, `name`, `description`, `insert_ts`, `update_ts`, `deleted`, `user_session_id`) 
values (NULL, 'gec_state_admin', 'GEC department state head. He approves all the district report under his state.', unix_timestamp()*1000, unix_timestamp()*1000,0,NULL);
