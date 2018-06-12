/************************************************/
-- Date:2016-10-24，operator:袁林玲
-- 1.修改表np_sys_on_line_service，添加字段EFFECTIVE_TERMINAL，用于客服模块生效终端字段
ALTER  TABLE  np_sys_on_line_service  ADD  EFFECTIVE_TERMINAL       VARCHAR(50)  comment  '生效终端 1:pc 2:App 3:移动版';
-- 2.修改表np_kuai_shang_tong，添加字段KST_EFFECTIVE_TERMINAL，用于快商通客服模块生效终端字段
ALTER  TABLE  np_kuai_shang_tong      ADD  KST_EFFECTIVE_TERMINAL   VARCHAR(50)  comment  '生效终端 1:pc 2:App 3:移动版';
/************************************************/


/************************************************/
-- Date:2016-10-26，operator:袁林玲
-- 1.修改表np_kuai_shang_tong，添加字段APP_OPERATION，用于app手机端生效代码
ALTER TABLE   np_kuai_shang_tong   ADD APP_OPERATION   TEXT  comment 'app手机端生效代码 ';
/************************************************/

/************************************************/
-- Date:2016-10-31，operator:袁林玲
-- 1.修改表np_kuai_shang_tong，添加字段operation，pc端javascript脚本
ALTER TABLE kstore_v2.np_kuai_shang_tong MODIFY operation TEXT COMMENT 'pc端javascript脚本';
/************************************************/
