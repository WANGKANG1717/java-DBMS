if exists (
	select name from master.dbo.sysdatabases
	where name='Mydb'
)drop database Mydb;
CREATE DATABASE Mydb2;
create database Mydb on (
	name='Mydb',
	filename='D:\DataBase\Mydb.mdf',
	size=10mb,
	maxsize=100mb,
	filegrowth=1mb
)
log on (
	name='Mydb_log',
	filename='D:\DataBase\Mydb_log.ldf',
	size=5mb,
	maxsize=50mb,
	filegrowth=1mb
)

SELECT * FROM sysdatabases WHERE filename like'D:\DataBase%';
SELECT * FROM sysobjects WHERE xtype='U';
SELECT * FROM sysdatabases WHERE STATUS2 ='1627389952';
DROP DATABASE Mydb2;

USE Mydb;
go
CREATE TABLE Stu (
	id INT PRIMARY KEY
)


CREATE DATABASE SSSSaSa
USE SSSSS 
GO
CREATE TABLE Stu(
	id INT PRIMARY KEY
)
DROP TABLE Stu


INSERT INTO Stu values (1);

SELECT * FROM Stu;

DELETE FROM Stu WHERE id=1;
UPDATE Stu SET id=2 WHERE id=1;

SELECT * FROM Stu;



select a.name [column],b.name type
from syscolumns a,systypes b
where a.id=object_id('Stu') and a.xtype=b.xtype and a.name='upass'

SELECT * from Stu

USE Mydb
DROP TABLE Stu



CREATE TABLE user1 (
	id INT PRIMARY KEY,
	aaaa varchar(20),
	pass VARCHAR(20),
)

INSERT INTO Stu VALUES (1, 'adadada', 'aaaaa');

select a.name as [column],b.name as type from syscolumns a,systypes b where a.id=object_id('Stu') and a.xtype=b.xtype and a.name='id'

select name from systypes where
xtype in (select xtype  from syscolumns where name = 'upass' and
          id in (select ID from sysobjects where name = 'Stu'));

USE Mydb
INSERT INTO Stu (id, uname, upass) VALUES(11, 'wangkang', 'wawaw');


SELECT * from Stu

DELETE FROM Stu WHERE uname='wangkang';

UPDATE Stu SET uname='wadad' WHERE upass='123456';


USE Mydb; UPDATE Stu SET upass='aaab11b' WHERE uname='wadad';


USE Mydb; SELECT * from Stu

execute sp_addlogin 'zhangsan','112233'
EXEC sp_droplogin 'zhangsan'
EXEC sp_droprole 'zhangsan'

Select name FROM master..SysDatabases WHERE dbid>4 ORDER BY Name
Select * FROM master..SysDatabases WHERE dbid>4 ORDER BY Name  

SELECT  * FROM  SysObjects  Where  XType='U'  ORDER  BY  Name

select * from sysobjects where xtype='U'

SELECT * FROM INFORMATION_SCHEMA.TABLES

SELECT 
	TABLE_NAME
FROM 
	information_schema.tables 
WHERE 
	table_schema = 'data'
AND 
	table_type = 'base table';

select * from sysobjects WHERE name='User'


select * from sys.tables
exec sp_tables

select * from sys.tables


SELECT * from sysobjects WHERE xtype='U'

USE data
SELECT * FROM info

USE select name from syscolumns where id = object_id('SC');
use SC; select name from syscolumns where id = object_id('Stu');

use SC; select name from syscolumns where id = object_id('Stu');