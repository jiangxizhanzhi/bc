alter table cr_tool_tool alter column homepage set default '';

alter table cr_tool_tool alter column manual set default '';

alter table cr_tool_tool alter column public set default 1;

alter table cr_tool_tool alter column tag set default '';

alter table cr_tool_tool alter column scene set default '';
create unique index cr_tool_tool_name_status_uindex on cr_tool_tool (name, status);