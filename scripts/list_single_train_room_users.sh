#!/usr/bin/env bash
# root 免密 mysql；只读查询。参数为单兵训练模板的完整名称。
# “未关闭”按 BC 删除模板时的判定：cr_single_room 中仍存在房间记录。
# is_finish=1 表示已提交训练，仍可能阻止模板删除，所以不据此过滤。
set -euo pipefail

if [[ $# -ne 1 || -z ${1:-} ]]; then
    printf '用法：bash %s "单兵训练场景模板名称"\n' "$0" >&2
    exit 2
fi

# 将名称编码为十六进制，名称中的引号等字符不会进入 SQL 语法。
template_name_hex=$(printf '%s' "$1" | od -An -v -tx1 | tr -d ' \n')

mysql --default-character-set=utf8mb4 --table --skip-force --skip-reconnect <<SQL
SET @template_name = CONVERT(0x${template_name_hex} USING utf8mb4);

-- 第一个结果集：匹配的单兵模板及仍保留的房间数量；同名模板分别列出。
SELECT bt.name AS '模板名称',
       bt.resource_id AS '模板ID',
       COUNT(r.resource_id) AS '未关闭房间数'
FROM bc.cr_business_template bt
LEFT JOIN bc.cr_single_room r ON r.business_template_id = bt.resource_id
WHERE bt.business_type = 1 AND BINARY bt.name = BINARY @template_name
GROUP BY bt.resource_id, bt.name
ORDER BY bt.resource_id;

-- 第二个结果集：房间所属账号；账号记录缺失时保留用户ID供核查。
SELECT COALESCE(u.username, '[账号记录缺失]') AS '学员账号',
       u.name AS '学员姓名',
       r.resource_id AS '房间ID',
       r.user_id AS '用户ID',
       r.is_finish AS '训练已提交',
       bt.resource_id AS '模板ID'
FROM bc.cr_business_template bt
JOIN bc.cr_single_room r ON r.business_template_id = bt.resource_id
LEFT JOIN mp.mp_auth_user u ON u.id = r.user_id
WHERE bt.business_type = 1 AND BINARY bt.name = BINARY @template_name
ORDER BY bt.resource_id, u.username, r.resource_id;
SQL
