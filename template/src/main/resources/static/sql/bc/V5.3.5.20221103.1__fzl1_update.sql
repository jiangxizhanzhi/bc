alter table cr_mission_template add time int not null;
alter table cr_mission add time int not null;
alter table cr_task add pause_time datetime null;
alter table cr_task add close_time datetime not null;

alter table cr_team_template add attachment_id varchar(64) null;