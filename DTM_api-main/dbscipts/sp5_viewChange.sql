

 drop view valltask;

 create  view valltask as 
SELECT 
    UUID() AS uniquid,
    t.id AS id,
    t.user_id AS user_id,
    t.pro_id AS pro_id,
    p.pro_name AS pro_name,
    IFNULL(t.cat_id, '') AS cat_id,
    IFNULL(c.cat_name, '') AS cat_name,
    IFNULL(t.act_id, '') AS act_id,
    IFNULL(a.act_name, '') AS act_name,
    t.task_detail AS task_detail,
    t.is_active AS tsk_act,
    s.is_active AS time_act,
    e.is_active AS time_end,
    CASE 
        WHEN t.is_active = 1 AND s.is_active = 3 THEN 3
        WHEN t.is_active = 2 AND e.is_active = 3 THEN 3 
        ELSE t.is_active 
    END AS is_active,
    t.init_ts AS init_ts,
    s.system_time AS startTimeSystem,
    s.enterd_time AS startTimeEnterd,
    e.system_time AS endTimeSystem,
    e.enterd_time AS endTimeEnterd,
    u.first_name AS first_name,
    u.last_name AS last_name,
    u.email AS email,
    u.user_group_id AS user_group_id,
    g.group_name AS group_name,
    u.user_role_id AS user_role_id,
    r.role_name AS role_name,
    COALESCE(TIMESTAMPDIFF(SECOND, s.enterd_time, e.enterd_time), 0) AS enterd_duration_seconds,
    COALESCE(TIMESTAMPDIFF(SECOND, s.system_time, e.system_time), 0) AS system_duration_seconds,
    tt.tt_name AS tasktype_name,  -- Adding the tasktype_name column from t_tasktype
    tt.id AS tasktype_id,  -- Adding the tasktype_id column from t_tasktype
    t.bug_id
FROM 
    t_task t
LEFT JOIN 
    t_task_start s ON t.id = s.task_id
LEFT JOIN 
    t_task_end e ON s.id = e.id AND s.task_id = e.task_id
JOIN 
    t_project p ON t.pro_id = p.id
LEFT JOIN 
    t_category c ON t.cat_id = c.id
LEFT JOIN 
    t_activity a ON t.act_id = a.id
JOIN 
    t_user u ON t.user_id = u.id
JOIN 
    t_user_role r ON u.user_role_id = r.id
JOIN 
    t_user_group g ON u.user_group_id = g.id
LEFT JOIN 
    t_tasktype tt ON t.tasktype_id = tt.id;


