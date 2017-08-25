merge into `MEMBER_ROLE` (`ROLE_ID`, `ROLE`) values (21, 'ADMIN');
merge into `MEMBER_ROLE` (`ROLE_ID`, `ROLE`) values (22, 'USER');

merge into `MEMBER` (`ID`, `PASSWORD`, `ROLE_ID`) values ('agraph', 'agraph', 21);
merge into `MEMBER` (`ID`, `PASSWORD`, `ROLE_ID`) values ('test01', 'test01', 22);