alter table cr_mission_template add time int not null;
alter table cr_mission add time int not null;
alter table cr_task add pause_time datetime null;
alter table cr_task add close_time datetime not null;