-- ----------------------------
-- Table structure for "C##FHBOOT"."QD_SIGNUSER"
-- ----------------------------
-- DROP TABLE "C##FHBOOT"."QD_SIGNUSER";
CREATE TABLE "C##FHBOOT"."QD_SIGNUSER" (
	"ACTIVITY_ID" VARCHAR2(200 BYTE) NULL ,
	"DEPARTMENT" VARCHAR2(200 BYTE) NULL ,
	"POSOTIONS" VARCHAR2(200 BYTE) NULL ,
	"NAME" VARCHAR2(200 BYTE) NULL ,
	"PHONE" VARCHAR2(200 BYTE) NULL ,
	"STATE" VARCHAR2(200 BYTE) NULL ,
	"SIGNUSER_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##FHBOOT"."QD_SIGNUSER"."ACTIVITY_ID" IS '活动ID';
COMMENT ON COLUMN "C##FHBOOT"."QD_SIGNUSER"."DEPARTMENT" IS '部门';
COMMENT ON COLUMN "C##FHBOOT"."QD_SIGNUSER"."POSOTIONS" IS '职位';
COMMENT ON COLUMN "C##FHBOOT"."QD_SIGNUSER"."NAME" IS '姓名';
COMMENT ON COLUMN "C##FHBOOT"."QD_SIGNUSER"."PHONE" IS '电话';
COMMENT ON COLUMN "C##FHBOOT"."QD_SIGNUSER"."STATE" IS '状态';
COMMENT ON COLUMN "C##FHBOOT"."QD_SIGNUSER"."SIGNUSER_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table QD_SIGNUSER
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##FHBOOT"."QD_SIGNUSER"

-- ----------------------------

ALTER TABLE "C##FHBOOT"."QD_SIGNUSER" ADD CHECK ("SIGNUSER_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##FHBOOT"."QD_SIGNUSER"
-- ----------------------------
ALTER TABLE "C##FHBOOT"."QD_SIGNUSER" ADD PRIMARY KEY ("SIGNUSER_ID");
