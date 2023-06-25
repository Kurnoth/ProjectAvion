DROP DATABASE IF EXISTS AvionsData;

CREATE DATABASE AvionsData;

USE AvionsData;

DROP TABLE if EXISTS AvionsData;

CREATE TABLE AvionsData(
   ID_data INT AUTO_INCREMENT,
   FlightNumber INT NOT NULL,
   Latitude DOUBLE NOT NULL,
   Longitude DOUBLE NOT NULL,
   Vitesse INT NOT NULL,
   Altitude INT NOT NULL,
   Cap INT NOT NULL,
   PRIMARY KEY(ID_data)
);

DROP TABLE if EXISTS Ordre;

CREATE TABLE Ordre(
   ID_ordre INT AUTO_INCREMENT,
   FlightNumber INT NOT NULL,
   Vitesse INT NOT NULL,
   Altitude INT NOT NULL,
   Cap INT NOT NULL,
   PRIMARY KEY(ID_ordre)
);