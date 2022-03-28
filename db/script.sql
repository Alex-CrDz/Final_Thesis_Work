/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2016                    */
/* Created on:     23/03/2022 09:39:25 p. m.                    */
/*==============================================================*/

USE [master]
GO

/****** Object:  Database [final_thesis_db]    Script Date: 23/03/2022 09:46:35 p. m. ******/
CREATE DATABASE [final_thesis_db]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'final_thesis_db', FILENAME = N'/var/opt/mssql/data/final_thesis_db.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON
( NAME = N'final_thesis_db_log', FILENAME = N'/var/opt/mssql/data/final_thesis_db_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO

USE [final_thesis_db]
GO

/*==============================================================*/
/* Table: LABEL_MESSAGE                                         */
/*==============================================================*/
create table LABEL_MESSAGE (
    idLabel              bigint              identity,
    username             varchar(50)          null,
    idMessage            bigint              null,
    label                varchar(255)         not null,
    constraint PK_LABEL_MESSAGE primary key (idLabel)
)
go

/*==============================================================*/
/* Table: MESSAGE                                               */
/*==============================================================*/
create table MESSAGE (
    idMessage            bigint              identity,
    sender               varchar(50)          null,
    subject              varchar(255)         null,
    body                 varchar(Max)         not null,
    creationDate         datetime             not null,
    deleted              bit                  not null,
    constraint PK_MESSAGE primary key (idMessage)
)
go

/*==============================================================*/
/* Table: MESSAGE_RECEPTOR                                      */
/*==============================================================*/
create table MESSAGE_RECEPTOR (
    idReceptor           bigint              identity,
    idMessage            bigint              null,
    username             varchar(50)          null,
    idTypeReceptor       bigint              null,
    constraint PK_MESSAGE_RECEPTOR primary key (idReceptor)
)
go

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE (
    idRole               bigint              identity,
    nameRole             varchar(255)         not null,
    descriptionRole      varchar(255)         null,
    constraint PK_ROLE primary key (idRole)
)
go

/*==============================================================*/
/* Table: TYPE_RECEPTOR                                         */
/*==============================================================*/
create table TYPE_RECEPTOR (
    idTypeReceptor       bigint              not null,
    nameTypeReceptor     varchar(50)          not null,
    descriptionTypeReceptor     varchar(50)          not null,
    constraint PK_TYPE_RECEPTOR primary key (idTypeReceptor)
)
go

/*==============================================================*/
/* Table: [USER]                                                */
/*==============================================================*/
create table [USER] (
    username             varchar(50)          not null,
    password             varchar(Max)         not null,
    idNumber             varchar(50)          not null,
    firstName            varchar(255)         null,
    lastName             varchar(255)         null,
    address              varchar(255)         null,
    zipCode              varchar(50)          null,
    state                varchar(50)          null,
    country              varchar(50)          null,
    enabled              bit                  not null,
    creationDate         datetime             not null,
    lastModifiedDate     datetime             not null,
    constraint PK_USER primary key (username)
)
go

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE (
    idRole               bigint              null,
    username             varchar(50)          null
)
go

alter table LABEL_MESSAGE
    add constraint FK_LABEL_MESSAGE_REFERENCE_MESSAGE foreign key (idMessage)
        references MESSAGE (idMessage)
go

alter table LABEL_MESSAGE
    add constraint FK_LABEL_MESSAGE_REFERENCE_USER foreign key (username)
        references [USER] (username)
go

alter table MESSAGE
    add constraint FK_MESSAGE_REFERENCE_USER foreign key (sender)
        references [USER] (username)
go

alter table MESSAGE_RECEPTOR
    add constraint FK_MESSAGE_RECEPTOR_REFERENCE_MESSAGE foreign key (idMessage)
        references MESSAGE (idMessage)
go

alter table MESSAGE_RECEPTOR
    add constraint FK_MESSAGE_RECEPTOR_REFERENCE_TYPE_RECEPTOR foreign key (idTypeReceptor)
        references TYPE_RECEPTOR (idTypeReceptor)
go

alter table MESSAGE_RECEPTOR
    add constraint FK_MESSAGE_RECEPTOR_REFERENCE_USER foreign key (username)
        references [USER] (username)
go

alter table USER_ROLE
    add constraint FK_USER_ROLE_REFERENCE_ROLE foreign key (idRole)
        references ROLE (idRole)
go

alter table USER_ROLE
    add constraint FK_USER_ROLE_REFERENCE_USER foreign key (username)
        references [USER] (username)
go

USE [master]
GO

/****** Object:  Login [accessUser]    Script Date: 23/03/2022 09:58:53 p. m. ******/
CREATE LOGIN [accessUser] WITH PASSWORD=N'password', DEFAULT_DATABASE=[final_thesis_db], DEFAULT_LANGUAGE=[us_english], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
ALTER LOGIN [accessUser] ENABLE
GO

USE [final_thesis_db]
GO

/****** Object:  User [accessUser]    Script Date: 23/03/2022 09:59:53 p. m. ******/
CREATE USER [accessUser] FOR LOGIN [accessUser] WITH DEFAULT_SCHEMA=[dbo]
GO
GRANT CONTROL ON DATABASE :: final_thesis_db TO accessUser
GO

/****** Inserts ******/
if not exists (select * from ROLE where nameRole like 'STD_USER')
insert into ROLE (nameRole, descriptionRole) values ('STD_USER', 'STANDARD_USER');
GO
if not exists (select * from ROLE where nameRole like 'ADM')
insert into ROLE (nameRole, descriptionRole) values ('ADM', 'ADMIN_USER');
GO
if not exists (select * from TYPE_RECEPTOR where nameTypeReceptor like 'TO')
insert into TYPE_RECEPTOR (idTypeReceptor, nameTypeReceptor, descriptionTypeReceptor) values (1, 'TO', 'Primary Receptor');
GO
if not exists (select * from TYPE_RECEPTOR where nameTypeReceptor like 'CC')
insert into TYPE_RECEPTOR (idTypeReceptor, nameTypeReceptor, descriptionTypeReceptor) values (2, 'CC', 'Carbon Copy Receptor');
GO
if not exists (select * from TYPE_RECEPTOR where nameTypeReceptor like 'BCC')
insert into TYPE_RECEPTOR (idTypeReceptor, nameTypeReceptor, descriptionTypeReceptor) values (3, 'BCC', 'Blind Copy Receptor');
GO