GO
/****** FHQQ313596790 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE QD_SIGNUSER (
 		SIGNUSER_ID  nvarchar(100) NOT NULL,
		ACTIVITY_ID nvarchar(200) DEFAULT NULL,
		DEPARTMENT nvarchar(200) DEFAULT NULL,
		POSOTIONS nvarchar(200) DEFAULT NULL,
		NAME nvarchar(200) DEFAULT NULL,
		PHONE nvarchar(200) DEFAULT NULL,
		STATE nvarchar(200) DEFAULT NULL,
PRIMARY KEY CLUSTERED 
(
	[SIGNUSER_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
